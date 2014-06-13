package com.example.ilate;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class Post extends Activity{
	private Spinner sp1;
	private static final int foto_code = 100;
	public static final int media_foto =1;
	private static final int galery_code = 100;
	private static final String lokasi = "halo";
	
	
	private Uri fileUri;
	private String namefile;
	
	private ImageView imgPreview;
	private Button btnCapture;
	private String path_photo;//alamat global ambil dari kamera/galery yg akan dikirim ke asynctasked
	private ExifInterface exifInterface;
	private String namaphoto;
	
	async_tasked at;
	
	Location loc;
	LocationManager lm;
	Double lat;
	Double lon;
	GoogleMap gmap;
	String provider;
	

	ImageView poto ;
	ImageView fromcam,fromgal;
	Button submit;
	Spinner spin;
	EditText nmmakan;
	EditText nmtempat;
	EditText desc;
	EditText alamat;
	
	Session ses;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post);
		//addItemSp1();
		alamat = (EditText) findViewById(R.id.getnamatempatP);
		nmmakan = (EditText) findViewById(R.id.getnmmakanP);
		nmtempat = (EditText) findViewById(R.id.getnmtempatP);
		desc = (EditText) findViewById(R.id.getdescP);
		poto = (ImageView)findViewById(R.id.addImage);
		spin = (Spinner) findViewById(R.id.spinner1);
		poto.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				captureImage();
			}
        });
		
		
		fromcam = (ImageView)findViewById(R.id.fromcamera);
		fromgal = (ImageView)findViewById(R.id.fromgalery);
		submit = (Button)findViewById(R.id.subpost);
		fromcam.setOnClickListener(new OnClickListener(){//jika menggunakan camera
			@Override
			public void onClick(View v) {
				captureImage();
			}
		});
		fromgal.setOnClickListener(new OnClickListener(){//jika menggunakan galery

			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), 3);
			}
		});
		
		submit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), path_photo, Toast.LENGTH_LONG).show();
				submit();
			}
		});
	}
	private void submit()
	{
		lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		Criteria c = new Criteria();
		provider = lm.getBestProvider(c, false);
		loc = lm.getLastKnownLocation(provider);
		if(loc!=null)
		 {
		     //get latitude and longitude of the location
		     lon=loc.getLongitude();
		     lat=loc.getLatitude();
		     Toast.makeText(getApplicationContext(), lon+" "+lat, Toast.LENGTH_LONG).show();
		     
		  }
		 else
	      {
		    
		   }
		ses =  new Session(getApplicationContext());
		
		String[] info = new String[10];
		info[0] = spin.getSelectedItemPosition()+"";//kategori
		info[1] = nmmakan.getText().toString();//nama makanan
		info[2] = nmtempat.getText().toString();//nama tempat
		info[3] = desc.getText().toString();//deskripsi
		info[4] = lon+"";//longitude
		info[5] = lat+"";//latitude
		info[6] = ses.getIDuser();//id_user 
		info[7] = alamat.getText().toString();//alamat
		info[8] = getNamaFoto(info[1]);//nama foto 
		new async_tasked(this,"coba").execute(path_photo,info,"post");
	}
	private void captureImage()
	{
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		fileUri =  getOutputMediaFileUri(media_foto);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		startActivityForResult(intent,foto_code); 
	}
	
	public String getNamaFoto(String alamat)
	{
		String[] splited = alamat.split("\\s+");
		return splited[0];
	}
	@Override
	protected void onActivityResult(int requestCode,int resultCode,Intent data)
	{
		if(requestCode == foto_code)//camera
		{
			if(resultCode == RESULT_OK)
			{
				System.out.println("masuk sini lho");
				previewCapturedImage();
				System.out.println(namaphoto);
				
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
		else if(requestCode==3 && resultCode == Activity.RESULT_OK)//galery 
		{
			
            Uri selectedImage = data.getData();
            System.out.println("ini apa ya : "+selectedImage);
            String s = getRealPathFromURI(selectedImage);
           
            Bitmap bitmap = null;
            try {
            	 	exifInterface = new ExifInterface(s);
            	 	int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            	 	bitmap = scaleAndRotateImage(s, orientation, poto.getWidth(), poto.getHeight());
            	 	
            	 	//bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    path_photo = s;//melakukan set alamat yang nantinya akan di upload
                    poto.setImageBitmap(bitmap);
                    
                    //messageText.setText(s);
            } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            } catch (OutOfMemoryError e) {
                if (bitmap != null) {
                    bitmap.recycle();
                    bitmap = null;
                    System.gc();
                }
            }
        }
	}
	
	
	public Bitmap scaleAndRotateImage(String path, int orientation, final int targetWidth, final int targetHeight)
	{
	    Bitmap bitmap = null;

	    try
	    {
	        // Check the dimensions of the Image
	        final BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inJustDecodeBounds = true;
	        BitmapFactory.decodeFile(path, options);

	        // Adjust the Width and Height
	        int sourceWidth, sourceHeight;
	        if (orientation == 90 || orientation == 270)
	        {
	            sourceWidth = options.outHeight;
	            sourceHeight = options.outWidth;
	        }
	        else
	        {
	            sourceWidth = options.outWidth;
	            sourceHeight = options.outHeight;
	        }

	        // Calculate the maximum required scaling ratio if required and load the bitmap
	        if (sourceWidth > targetWidth || sourceHeight > targetHeight)
	        {
	            float widthRatio = (float)sourceWidth / (float)targetWidth;
	            float heightRatio = (float)sourceHeight / (float)targetHeight;
	            float maxRatio = Math.max(widthRatio, heightRatio);
	            options.inJustDecodeBounds = false;
	            options.inSampleSize = (int)maxRatio;
	            bitmap = BitmapFactory.decodeFile(path, options);
	        }
	        else
	        {
	            bitmap = BitmapFactory.decodeFile(path);
	        }

	        // We need to rotate the bitmap (if required)
	        int orientationInDegrees = exifToDegrees(orientation);
	        if (orientation > 0)
	        {
	            Matrix matrix = new Matrix();
	            if (orientation != 0f)
	            {
	                matrix.preRotate(orientationInDegrees);
	            };

	            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	        }

	        // Re-scale the bitmap if necessary
	        sourceWidth = bitmap.getWidth();
	        sourceHeight = bitmap.getHeight();

	        if (sourceWidth != targetWidth || sourceHeight != targetHeight)
	        {
	            float widthRatio = (float)sourceWidth / (float)targetWidth;
	            float heightRatio = (float)sourceHeight / (float)targetHeight;
	            float maxRatio = Math.max(widthRatio, heightRatio);
	            sourceWidth = (int)((float)sourceWidth / maxRatio);
	            sourceHeight = (int)((float)sourceHeight / maxRatio);
	            bitmap = Bitmap.createScaledBitmap(bitmap, sourceWidth, sourceHeight, true);
	        }
	    }
	    catch (Exception e)
	    {
	        
	    }
	    return bitmap;
	}
	
	
	
	private static int exifToDegrees(int exifOrientation) {        
	    if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) { return 90; } 
	    else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {  return 180; } 
	    else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {  return 270; }            
	    return 0;    
	 }
	public String getRealPathFromURI(Uri uri) { //untuk ambil alamat foto galery
	        String[] projection = { MediaStore.Images.Media.DATA };
	        @SuppressWarnings("deprecation")
	        Cursor cursor = managedQuery(uri, projection, null, null, null);
	        int column_index = cursor
	                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	        cursor.moveToFirst();
	        return cursor.getString(column_index);
	    }
	 
	public Uri getOutputMediaFileUri(int type) {//untuk ambil alamat foto camera
		
        return Uri.fromFile(getOutputMediaFile(type));}
	
	private File getOutputMediaFile(int type) {
		File mediaStorageDir = new File(
                Environment.getExternalStorageDirectory()+"/ilate");
        mediaStorageDir.mkdirs();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == media_foto) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
            System.out.print(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
            namaphoto = "IMG_" + timeStamp + ".jpg";
            Post.this.sendBroadcast(new Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri
                   .parse("file://" +mediaStorageDir)));
        }  else {
            return null;
        }
        return mediaFile;
    }
	
	
	private void previewCapturedImage()
	{
		path_photo = fileUri.getPath();//melakukan set alamat yang nantinya akan di upload
		try
		{
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inSampleSize = 0;
			final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),opt);
			poto.setImageBitmap(bitmap);
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