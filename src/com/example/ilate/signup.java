package com.example.ilate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class signup extends Activity implements GetArrayList{
	async_tasked at;
	private String[] info = new String[7];
	EditText namaAw;
	EditText namaAk;
	EditText email;
	EditText pass;
	EditText passC;
	String hasil;
	EditText user;
	Button daftar;
	public static List<Map<String,Object>> dataUser;
	Session session;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up);
		
		namaAw = (EditText) findViewById(R.id.getfirstnameSU);
		namaAk = (EditText) findViewById(R.id.getlastnameSU);
		email = (EditText) findViewById(R.id.getemailSU);
		pass = (EditText) findViewById(R.id.getpasswordSU);
		passC = (EditText) findViewById(R.id.getreconfirmSU);
		user = (EditText) findViewById(R.id.getusernameSU);
		session = new Session(getApplicationContext());
		daftar = (Button) findViewById(R.id.signupSU);
		
		daftar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				info[0] = user.getText().toString();
				info[1] = email.getText().toString();
				if(pass.getText().toString().equals(passC.getText().toString()))
				{
					
					System.out.println("sudah sama");
					info[2] = pass.getText().toString();
					verify();
					
				}
				else
				{
					Toast.makeText(getApplicationContext(), "maaf password tidak sama", Toast.LENGTH_SHORT).show();
				}
				
				
			}
			
		});
	}

	public void verify()
	{
		hasil = "gagal";
		at = new async_tasked(signup.this,this);
		at.execute("xx",info,"signup");
		
	}
	@Override
	public void getArray(List<Map<String, Object>> param) {
		
		dataUser = new ArrayList<Map<String,Object>>(param);
		System.out.println("Xxxxxxxxx" + dataUser.get(0).get("iduser").toString());
		if(dataUser.get(0).get("hasil").toString().equals("sukses"))
		{
			session.createLoginSession(user.getText().toString(), passC.getText().toString(),dataUser.get(0).get("iduser").toString());
			startActivity(new Intent(signup.this,home.class));
			finish();
		}
		else
		{
			Toast.makeText(getApplicationContext(), "maaf terdapat kesalahan sistem silahkan sign up ulang", Toast.LENGTH_SHORT).show();
		}
		
	}
}
