package com.example.ilate;

import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CameraUsing extends Activity{
	
	final static int request_camera = 1;
	Uri imageUri = null;
	static TextView imgDecs = null;
	public static ImageView showImg = null;
	CameraUsing CameraActivity = null;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
         
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_layout);
        
        CameraActivity= this;
        
        imgDecs = (TextView) findViewById(R.id.imageDetails);
        showImg = (ImageView) findViewById(R.id.showImg);
        final Button photo = (Button) findViewById(R.id.photo);
        
        photo.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				String fileName = "pizza.jpg";
				ContentValues values = new ContentValues();
				values.put(MediaStore.Images.Media.TITLE, fileName);
				values.put(MediaStore.Images.Media.DESCRIPTION, "percobaan");
				imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
				
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, request_camera);
			}
        	
        });
        
	}
	
	@Override
	protected void onActivityResult(int requestCode,int resultCode,Intent data)
	{
		if(requestCode == request_camera)
		{
			if(resultCode == RESULT_OK)
			{
				String imageId = convertImageUriToFile(imageUri,CameraActivity);
				new LoadImagesFromSDCard().execute(""+imageId);
			}
			else if(resultCode == RESULT_CANCELED)
			{
				Toast.makeText(this, "picture was not taken", Toast.LENGTH_SHORT).show();
			}
			else
			{
				Toast.makeText(this, "picture was not taken", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	public static String convertImageUriToFile(Uri imageUri,Activity activity)
	{
		Cursor cursor = null;
		int imageID = 0;
		
		try{
			String[] proj = {
					MediaStore.Images.Media.DATA,
					MediaStore.Images.Media._ID,
					MediaStore.Images.Thumbnails._ID,
					MediaStore.Images.ImageColumns.ORIENTATION
			};
			cursor = activity.managedQuery(imageUri, proj, null, null, null);
			
			int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
			int columnIndexThumb = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
			int file_ColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			
			int size = cursor.getCount();
			
			if(size == 0)
			{
				imgDecs.setText("No Image");
			}
			else
			{
				int thumbId = 0;
				if(cursor.moveToFirst())
				{
					imageID = cursor.getInt(columnIndex);
					thumbId = cursor.getInt(columnIndexThumb);
					String Path = cursor.getString(file_ColumnIndex);
					String CapturedImageDetails = "CapturedImageDetails haha";
					
					imgDecs.setText(CapturedImageDetails);
					
				}
			}
				
			}
		finally{
			if(cursor != null)
			{
				cursor.close();
			}
		}
		return ""+imageID;
		
		}
	
	public class LoadImagesFromSDCard extends AsyncTask<String,Void,Void>
	{
		
		private ProgressDialog dialog = new ProgressDialog(CameraUsing.this);
		Bitmap mBitmap;
		protected void onPreExecute()
		{
			dialog.setMessage("Loading image from sdcard..");
			dialog.show();
		}
				
		
		
		@Override
		protected Void doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			Bitmap bitmap = null;
			Bitmap newBitmap = null;
			Uri uri = null;

			try
			{
				uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,""+arg0[0]);
				bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
				if(bitmap != null)
				{
					newBitmap = Bitmap.createScaledBitmap(bitmap, 170, 170, true);
					bitmap.recycle();
					if(newBitmap != null)
					{
						mBitmap = newBitmap;
					}
					
				}
			}
			catch (IOException e)
			{
				cancel(true);
			}
			return null;
		}
		
		protected void onPostExecute(Void unused) {
            
            // NOTE: You can call UI Element here.
             
            // Close progress dialog
              dialog.dismiss();
             
            if(mBitmap != null)
            {
              // Set Image to ImageView  
               
               showImg.setImageBitmap(mBitmap);
            }  
             
        }
		
	}
	}


