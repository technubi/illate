package com.example.ilate;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements GetArrayList{
	
	
	Session session;
	
	private String id,pass;
	public static boolean verified;
	async_tasked at;
	TextView user;
	TextView password;
	String hasil;
	
	public static List<Map<String,Object>> dataUser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		session = new Session(getApplicationContext());
		user = (TextView) findViewById(R.id.username);
		password = (TextView) findViewById(R.id.password);
		ImageView login = (ImageView) findViewById(R.id.login);
		ImageView signup = (ImageView) findViewById(R.id.sign_up);
		login.setOnClickListener(new OnClickListener(){
		  
			@Override
			public void onClick(View v) {
				
				if(verify(user.getText().toString() ,password.getText().toString()).equals("sukses"))
				{
					//System.out.println("sukses" + dataUser.get(0).get("iduser").toString());
					
					/*session.createLoginSession(user.getText().toString(), password.getText().toString());
					startActivity(new Intent(MainActivity.this,home.class));
					finish();*/
				} 
				else
				{
					Toast.makeText(getApplicationContext(), "Cek kembali id dan password anda ", Toast.LENGTH_LONG).show();
				}
			}
			
		});
		
		signup.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this,signup.class));
			}
		});
		
		
		
	
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        
	    	Intent i = new Intent(getApplicationContext(), MainActivity.class);
	        // Closing all the Activities
	        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | 
                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
	         
	        // Add new Flag to start new Activity
	        //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    	finish();
	    }

	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	public String verify(String id,String pass)
	{
		hasil = "gagal";
		at = new async_tasked(MainActivity.this,this);
		try {
			 hasil = at.execute(user.getText().toString(),password.getText().toString(),"login").get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(hasil);
		return hasil;
	}
	@Override
	public void getArray(List<Map<String, Object>> param) {
		dataUser = new ArrayList<Map<String,Object>>(param);
		if(hasil.equals("sukses"));
		{
			System.out.println("sukses" + dataUser.get(0).get("iduser").toString());
			session.createLoginSession(user.getText().toString(), password.getText().toString(),dataUser.get(0).get("iduser").toString());
			startActivity(new Intent(MainActivity.this,home.class));
			finish();
		}
		
		
	}

}