package com.example.ilate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class Comment extends Activity implements GetArrayList{
	
	async_tasked at;
	List<Map<String,Object>> lempar;
	ImageView goToProf;
	ImageView post;
	
	ImageView mapz;
	Session session;
	String res;
	static TextView likenf;
	static Context ctx;
	
	@Override
	protected void onCreate(Bundle savedInstanceStace)
	{
		super.onCreate(savedInstanceStace);
		setContentView(R.layout.newsfeed);
		ctx = this;
		likenf = (TextView) findViewById(R.id.likeNF);
		post = (ImageView) findViewById(R.id.imgPost);//buat ngepost

		post.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {//kalo diklik ngapain
				async_tasked at = new async_tasked(Comment.this,"tes");
				at.execute("aaaaa","abcde","addcomment");
			}
        });
		
		async_tasked at = new async_tasked(Comment.this,this);//laine tampilin komen
		at.execute("aaaaa","abcde","comment");
		
		//System.out.println(lempar.size());
		
		
		
		
		
	}
	public static void like(String satu , String dua)
	{
		
		System.out.println(satu+" "+dua+"xxxxxxxxxxxxxxx");
		async_tasked at = new async_tasked(ctx,"xx");
		at.execute(satu,dua,"like");
	}
	
	@Override
	public void getArray(List<Map<String,Object>> param) {
		lempar = new ArrayList<Map<String,Object>>(param);
		newsfeed_adapter na = new newsfeed_adapter(this,param);
		final ListView lvnf = (ListView) findViewById(R.id.listnewsfeed);
		
		lvnf.setAdapter(na);
		lvnf.setItemsCanFocus(true);
		lvnf.setFocusable(true);
		lvnf.setOnItemClickListener(new OnItemClickListener(){
			
			@Override
			public void onItemClick(AdapterView<?> arg0, final View arg1, final int arg2,
					long arg3) {
				goToProf = (ImageView) arg1.findViewById(R.id.profileNF);
				goToProf.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(Comment.this,MyProfile.class);
						Bundle b = new Bundle();
						b.putString("id_user", lempar.get(lvnf.getPositionForView(arg1)).get("id_user").toString());
						System.out.println("dilempar "+lempar.get(lvnf.getPositionForView(arg1)).get("id_user").toString());
						intent.putExtras(b);
						startActivity(intent);
						
					}
					
				});
				
				
				
				
				/*mapz = (ImageView) arg1.findViewById(R.id.mapz);
				mapz.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//int post = (Integer) v.getTag();
						System.out.println(arg2);
						Bundle b = new Bundle();
						double[] latlng = new double[2];
						latlng[0] = Double.parseDouble(lempar.get(arg2).get("lat"));
						latlng[1] = Double.parseDouble lempar.get(arg2).get("long");
						b.putDoubleArray("latlng", latlng);
						Intent i = new Intent(newsfeed.this,foodloc.class);
						i.putExtras(b);
						startActivity(i);
					}
					
				});*/
			}
			
			
			
		});
		
	}
}
