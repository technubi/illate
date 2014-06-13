package com.example.ilate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class CapturePhoto extends Activity{

		private static final int foto_code = 100;
		public static final int media_foto =1;
		
		private static final String lokasi = "halo";
		private Uri fileUri;
		private ImageView imgPreview;
		private Button btnCapture;
		
		
		@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.capturedphoto);
	        
	        imgPreview = (ImageView) findViewById(R.id.imgPreview);
	        btnCapture = (Button) findViewById(R.id.btnCapturePicture);
	        System.out.println(Environment.getExternalStorageDirectory());
	        
	        
	        btnCapture.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					captureImage();
					
				}
	        	
	        });
		}
		
		private void captureImage()
		{
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			fileUri =  getOutputMediaFileUri(media_foto);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
			startActivityForResult(intent,foto_code); 
		}
		
		@Override
		protected void onActivityResult(int requestCode,int resultCode,Intent data)
		{
			if(requestCode == foto_code)
			{
				if(resultCode == RESULT_OK)
				{
					System.out.println("masuk sini lho");
					previewCapturedImage();
					
				}
				else if(resultCode == RESULT_CANCELED)
				{
					Toast.makeText(getApplicationContext(), "gagak", Toast.LENGTH_LONG).show();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "gagak2", Toast.LENGTH_LONG).show();
				}
			}
		}
		
		
		public Uri getOutputMediaFileUri(int type) {
			System.out.println(Uri.fromFile(getOutputMediaFile(type)));
	        return Uri.fromFile(getOutputMediaFile(type));}
		
		private File getOutputMediaFile(int type) {
			
			
			//File folder = new File(Environment.getExternalStorageDirectory()+"/aduh");
	        /*if(!folder.exists()){
	            if(folder.mkdirs())
	            Toast.makeText(this, "New Folder Created", Toast.LENGTH_SHORT).show();
	        }
	        */
	        //File sdCardFile = new File(Environment.getExternalStorageDirectory()+"/Saved CGPA/test.txt");
	        /*File fileWithinMyDir = new File(Environment.getExternalStorageDirectory()+"/aduh", "myfile2"); //Getting a file within the dir.
	        try {
				FileOutputStream out = new FileOutputStream(fileWithinMyDir);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //Use the stream as usual to write into the file.
	        */
			///////////////////////////////////////////////////////////////////////////////////////////////
	        // External sdcard location
	        File mediaStorageDir = new File(
	                Environment.getExternalStorageDirectory()+"/ilate");
	        mediaStorageDir.mkdirs();
	        
	        // Create the storage directory if it does not exist
	        
	 
	        // Create a media file name
	        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
	                Locale.getDefault()).format(new Date());
	        File mediaFile;
	        if (type == media_foto) {
	            mediaFile = new File(mediaStorageDir.getPath() + File.separator
	                    + "IMG_" + timeStamp + ".jpg");
	            System.out.print(mediaStorageDir.getPath() + File.separator
	                    + "IMG_" + timeStamp + ".jpg");
	            CapturePhoto.this.sendBroadcast(new Intent(
                        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri
                       .parse("file://" +mediaStorageDir)));
	        }  else {
	            return null;
	        }
	        
	       
	      
	        
	        
	        return mediaFile;
	    }
		
		
		private void previewCapturedImage()
		{
			try
			{
				
				
				BitmapFactory.Options opt = new BitmapFactory.Options();
				opt.inSampleSize = 0;
				final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),opt);
				imgPreview.setImageBitmap(bitmap);
			}
			catch(NullPointerException e)
			{
				e.printStackTrace();
			}
		}
		
		
		//prevent bug
		@Override
	    protected void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	 
	        // save file url in bundle as it will be null on scren orientation
	        // changes
	        outState.putParcelable("file_uri", fileUri);
	    }
	 
	    @Override
	    protected void onRestoreInstanceState(Bundle savedInstanceState) {
	        super.onRestoreInstanceState(savedInstanceState);
	 
	        // get the file url
	        fileUri = savedInstanceState.getParcelable("file_uri");
	    }
	 
		
}
