package com.example.ilate;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
  
public class imageUpload extends Activity {
     
    TextView messageText;
    Button uploadButton;
    Button coba;
    Button dialogbox;
    ImageView cobatampil;
    int serverResponseCode = 0;
    ProgressDialog dialog = null;
        
    String upLoadServerUri = null;
     
    /**********  File Path *************/
    File mediaStorageDir = new File(
               Environment.getExternalStorageDirectory()+"/ilate");
    final String uploadFilePath = mediaStorageDir.toString();
    final String uploadFileName = "/IMG_20140505_090841.jpg";
    private Intent pictureActionIntent = null; 
    @Override
    public void onCreate(Bundle savedInstanceState) {
         
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageupload);
          
        uploadButton = (Button)findViewById(R.id.uploadButton);
        messageText  = (TextView)findViewById(R.id.messageText);
        cobatampil = (ImageView)findViewById(R.id.cobatampil);
        coba = (Button)findViewById(R.id.coba);
        dialogbox = (Button) findViewById(R.id.dialogbox);
        
        messageText.setText("Uploading file path :- '"+uploadFilePath + "" + uploadFileName);
         
        /************* Php script path ****************/
        upLoadServerUri = "http://www.technubi.com/ilatte/upload.php";
         
        uploadButton.setOnClickListener(new OnClickListener() {            
            @Override
            public void onClick(View v) {
                 
                dialog = ProgressDialog.show(imageUpload.this, "", "Uploading file...", true);
                 
                new Thread(new Runnable() {
                        public void run() {
                             runOnUiThread(new Runnable() {
                                    public void run() {
                                        messageText.setText("uploading started.....");
                                    }
                                });                      
                           
                             uploadFile(uploadFilePath + "" + uploadFileName);
                                                      
                        }
                      }).start();        
                }
            });
        coba.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), 3);
			}
        	
        });
        
        dialogbox.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startDialog();
			}
        	
        });
        
        
    }
    
    private void startDialog() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");

        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        pictureActionIntent = new Intent(
                                Intent.ACTION_GET_CONTENT, null);
                        pictureActionIntent.setType("image/*");
                        pictureActionIntent.putExtra("return-data", true);
                        startActivityForResult(pictureActionIntent,
                               0);
                    }
                });

        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        pictureActionIntent = new Intent(
                                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(pictureActionIntent,
                                1);

                    }
                });
        myAlertDialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode==3 && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            String s = getRealPathFromURI(selectedImage);
            Bitmap bitmap = null;
            try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    cobatampil.setImageBitmap(bitmap);
                    
                    messageText.setText(s);
            } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }
        }
    }
    
    public String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    public int uploadFile(String sourceFileUri) {
           
           
          String fileName = sourceFileUri;
  
          HttpURLConnection conn = null;
          DataOutputStream dos = null;  
          String lineEnd = "\r\n";
          String twoHyphens = "--";
          String boundary = "*****";
          int bytesRead, bytesAvailable, bufferSize;
          byte[] buffer;
          int maxBufferSize = 1 * 1024 * 1024; 
          File sourceFile = new File(sourceFileUri); 
           
          if (!sourceFile.isFile()) {
               
               dialog.dismiss(); 
                
               Log.e("uploadFile", "Source File not exist :"
                                   +uploadFilePath + "" + uploadFileName);
                
               runOnUiThread(new Runnable() {
                   public void run() {
                       messageText.setText("Source File not exist :"
                               +uploadFilePath + "" + uploadFileName);
                   }
               }); 
                
               return 0;
            
          }
          else
          {
               try { 
                    
                     // open a URL connection to the Servlet
                   FileInputStream fileInputStream = new FileInputStream(sourceFile);
                   URL url = new URL(upLoadServerUri);
                    
                   // Open a HTTP  connection to  the URL
                   conn = (HttpURLConnection) url.openConnection(); 
                   conn.setDoInput(true); // Allow Inputs
                   conn.setDoOutput(true); // Allow Outputs
                   conn.setUseCaches(false); // Don't use a Cached Copy
                   conn.setRequestMethod("POST");
                   conn.setRequestProperty("Connection", "Keep-Alive");
                   conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                   conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                   conn.setRequestProperty("uploaded_file", fileName); 
                    
                   dos = new DataOutputStream(conn.getOutputStream());
                   
                   dos.writeBytes(twoHyphens + boundary + lineEnd); 
                   dos.writeBytes("Content-Disposition: form-data; name='uploaded_file';filename='"
                           + fileName + "'" + lineEnd);
                   
                   //dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + exsistingFileName +"\"" + lineEnd);
                   dos.writeBytes(lineEnd);
          
                   // create a buffer of  maximum size
                   bytesAvailable = fileInputStream.available(); 
          
                   bufferSize = Math.min(bytesAvailable, maxBufferSize);
                   buffer = new byte[bufferSize];
          
                   // read file and write it into form...
                   bytesRead = fileInputStream.read(buffer, 0, bufferSize);  
                      
                   while (bytesRead > 0) {
                        
                     dos.write(buffer, 0, bufferSize);
                     bytesAvailable = fileInputStream.available();
                     bufferSize = Math.min(bytesAvailable, maxBufferSize);
                     bytesRead = fileInputStream.read(buffer, 0, bufferSize);   
                      
                    }
          
                   // send multipart form data necesssary after file data...
                   dos.writeBytes(lineEnd);
                   dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
          
                   // Responses from the server (code and message)
                   serverResponseCode = conn.getResponseCode();
                   String serverResponseMessage = conn.getResponseMessage();
                     
                   Log.i("uploadFile", "HTTP Response is : "
                           + serverResponseMessage + ": " + serverResponseCode);
                    
                   if(serverResponseCode == 200){
                        
                       runOnUiThread(new Runnable() {
                            public void run() {
                                 
                                String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                                              +" http://www.androidexample.com/media/uploads/"
                                              +uploadFileName;
                                 
                                messageText.setText(msg);
                                Toast.makeText(imageUpload.this, "File Upload Complete.", 
                                             Toast.LENGTH_SHORT).show();
                            }
                        });                
                   }    
                    
                   //close the streams //
                   fileInputStream.close();
                   dos.flush();
                   dos.close();
                     
              } catch (MalformedURLException ex) {
                   
                  dialog.dismiss();  
                  ex.printStackTrace();
                   
                  runOnUiThread(new Runnable() {
                      public void run() {
                          messageText.setText("MalformedURLException Exception : check script url.");
                          Toast.makeText(imageUpload.this, "MalformedURLException", 
                                                              Toast.LENGTH_SHORT).show();
                      }
                  });
                   
                  Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
              } catch (Exception e) {
                   
                  dialog.dismiss();  
                  e.printStackTrace();
                   
                  runOnUiThread(new Runnable() {
                      public void run() {
                          messageText.setText("Got Exception : see logcat ");
                          Toast.makeText(imageUpload.this, "Got Exception : see logcat ", 
                                  Toast.LENGTH_SHORT).show();
                      }
                  });
                  Log.e("Upload file to server Exception", "Exception : "
                                                   + e.getMessage(), e);  
              }
              dialog.dismiss();       
              return serverResponseCode; 
               
           } // End else block 
         } 
}