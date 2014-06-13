package com.example.ilate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;



public class MyProfile extends Activity implements GetArrayList{
	MyProfileAdapter pa;
	public static GridView gv;
	String dapat ="" ;
	Session session;
	List<Map<String,Object>> data;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myprofile);
		session = new Session(getApplicationContext());
		Bundle b = getIntent().getExtras();
		if(b==null)
		{
			async_tasked at = new async_tasked(MyProfile.this,this);
			dapat=session.getIDuser();
			System.out.println("1 zzzzzzzzzz"+dapat);
			at.execute("aaaaaa",dapat,"profile");
		}
		else
		{
		dapat = b.getString("id_user");
		System.out.println("asdsadsadsad zzzzzzzzzz"+dapat);
		async_tasked at = new async_tasked(MyProfile.this,this);
		at.execute("aaaaaa",dapat,"profile");
		}
		
		
	}
	@Override
	public void getArray(List<Map<String, Object>> param) {
		TextView nama = (TextView) findViewById(R.id.profilenameMP);
		TextView status = (TextView) findViewById(R.id.statusMP);
		ImageView fotoprofile = (ImageView) findViewById(R.id.fotoprofilMP);
		TextView posted = (TextView) findViewById(R.id.postedMP);
		byte[] imageAsBytes = Base64.decode(param.get(0).get("fotouser").toString().getBytes(),0);
		fotoprofile.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
		nama.setText(param.get(0).get("user").toString());
		posted.setText(param.get(0).get("jmlhfoto").toString()+" photo posted");
		status.setText("About me : " + param.get(0).get("status").toString());
		
		session = new Session(this.getApplicationContext());
		
		
		
		
		gv = (GridView) findViewById(R.id.mygalery);
		async_tasked at = new async_tasked(this,"X");
		at.execute("x",session.getIDuser()+"","albumfix");
		/*pa = new MyProfileAdapter(this,data);
		gv.setAdapter(pa);
		gv.setDrawSelectorOnTop(true);*/
		
		gv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(MyProfile.this,galery.class);
				Bundle b = new Bundle();
				String[] lempar = new String[2];
				lempar[0] = dapat;
				lempar[1] = ""+ (gv.getPositionForView(arg1)+1);
				b.putStringArray("id_user",lempar);
				intent.putExtras(b);
				startActivity(intent);
				
			}
			
		});
	
	
	
	}
}
