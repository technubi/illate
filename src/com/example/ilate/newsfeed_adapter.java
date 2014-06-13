package com.example.ilate;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.example.ilate.R.id;
import com.google.android.gms.drive.internal.i;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract.Profile;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class newsfeed_adapter extends BaseAdapter implements GetArrayList {
	public Context cx;
	LayoutInflater li;
	
	Dialog dialog;
	List<Map<String,Object>> coment;
	List<Map<String, Object>> content;
	Session session ;//= new Session(cx.getApplicationContext());
	
	int pubpos=0;
	public String kata;public String sended;
	public newsfeed_adapter(Context cx,List<Map<String, Object>> contents)
	{
		this.cx = cx;
		li = LayoutInflater.from(cx);
		
		
		this.content = new ArrayList<Map<String,Object>>(contents);
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return content.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return content.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if(convertView == null)
		{
			convertView = li.inflate(R.layout.timeline, null);
			vh = new ViewHolder();
			vh.nama = (TextView) convertView.findViewById(R.id.namaNF);
			vh.gbr = (ImageView) convertView.findViewById(R.id.profileNF);
			vh.foto = (ImageView) convertView.findViewById(R.id.imageView2);
			//vh.goToProf = (LinearLayout) convertView.findViewById(R.id.profileclickNF);
			vh.goToDetail = (RelativeLayout) convertView.findViewById(R.id.godetailNF);
			vh.namafood = (TextView) convertView.findViewById(R.id.namafoodNF);
			vh.like= (TextView) convertView.findViewById(R.id.likeNF);
			vh.mapz = (ImageView) convertView.findViewById(R.id.mapz);
			vh.blike = (ImageView) convertView.findViewById(R.id.likeke);
			vh.comment = (ImageView) convertView.findViewById(id.coment);
			
			convertView.setTag(vh);
		}
		else
		{
			vh = (ViewHolder)convertView.getTag();
		}
		vh.like.setText(content.get(position).get("like").toString());
		vh.nama.setText(content.get(position).get("user").toString());
		
		byte[] imageAsBytes = Base64.decode(content.get(position).get("fotouser").toString().getBytes(),0);
		vh.gbr.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
		
		byte[] imageAsBytes2 = Base64.decode(content.get(position).get("fotomakanan").toString().getBytes(),0);
		vh.foto.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes2, 0, imageAsBytes2.length));
		
		
		//vh.goToProf.setClickable(true);
		vh.namafood.setText(content.get(position).get("namamakanan").toString() + " | " + content.get(position).get("nmplace").toString()+"" +
				" | " + content.get(position).get("alamat").toString());
		//vh.mapz.setFocusable(true);
		List<Object> data = new ArrayList<Object>();
		//vh.blike.setTag(position);
		vh.mapz.setTag(position);
		final List<Object> infoo = new ArrayList<Object>();
		infoo.add(position);
		infoo.add(vh.like);
		vh.foto.setTag(position);
		vh.comment.setTag(content.get(position).get("idfood").toString());
		pubpos = position;
		
		
		vh.foto.setOnClickListener(new OnClickListener(){
		
			
			@Override
			public void onClick(View v) {
				
				int pos = (Integer) infoo.get(0);
				String[] kata = new String[5];
				kata[0]=content.get(pos).get("fotomakanan").toString();
				kata[1]=content.get(pos).get("namamakanan").toString();
				kata[2]=content.get(pos).get("nmplace").toString();
				kata[3]=content.get(pos).get("desc").toString();
				System.out.println(kata[3]);
				Bundle b = new Bundle();
				b.putStringArray("lempar", kata);
				Intent i = new Intent(cx,dialogimage.class);
				i.putExtras(b);
				cx.startActivity(i);
				
			}
			
		});
		vh.blike.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				newsfeed.like(content.get(position).get("id_user").toString(), content.get(position).get("idfood").toString());
				int post = (Integer) infoo.get(0);
				TextView a = (TextView) infoo.get(1);
				int kata = Integer.parseInt(a.getText().toString())+1;
				a.setText(kata+"");
				
				
				
			}
			
		});
		vh.comment.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				  kata = v.getTag().toString();
				  call();
				    
				  
				  
				  
			}
			
		});
		vh.mapz.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				int post = (Integer) v.getTag();
				
				Bundle b = new Bundle();
				
				double[] latlng = new double[2];
				latlng[0] = Double.parseDouble(content.get(post).get("lat").toString());
				latlng[1] = Double.parseDouble(content.get(post).get("long").toString());
				
				b.putDoubleArray("latlng", latlng);
				String[] info = new String[5];
				info[0] = content.get(position).get("nmplace").toString();
				info[1] = content.get(position).get("namamakanan").toString();
				b.putStringArray("info",info);
				Intent i = new Intent(cx,foodloc.class);
				i.putExtras(b);
				cx.startActivity(i);
				
			}
			
		});
		

		
	    
		return convertView;
	}
	
	
	
	static class ViewHolder
	{
		TextView lokasiNF;
		ImageView gbr;
		TextView nama;
		ImageView foto;
		//LinearLayout goToProf;
		RelativeLayout goToDetail;
		TextView namafood;
		TextView like;
		ImageView mapz;
		ImageView comment;
		ImageView blike;
		
	}


	public void call()
	{
		async_tasked at = new async_tasked(cx,this);
		at.execute("x",kata,"addcoment");
	}
	public void callC()
	{
		session = new Session(this.cx);
		String[] temp = new String[3];
		temp[0] = content.get(pubpos).get("idfood").toString();
		temp[1] = session.getIDuser();
		async_tasked at = new async_tasked(cx,"Test");
		at.execute(temp,sended,"sendcoment");
	}
	
	@Override
	public void getArray(List<Map<String, Object>> param) {
		
		dialog = new Dialog(cx);
		dialog.setContentView(R.layout.dialogcomment);
		ListView lv = (ListView) dialog.findViewById(R.id.listviewC);
		Button bt = (Button)dialog.findViewById(R.id.btsendC);
		final EditText et = (EditText)dialog.findViewById(R.id.sendC);
		if(param.get(0).get("user").toString().equals("no coment"))
		{
			
			dialog.setTitle("no coment");
			
		}
		else
		{
			dialog.setTitle("comment");
		}
		coment =  new ArrayList<Map<String,Object>>(param);
		comentadapter ca = new comentadapter(cx,coment);
		lv.setAdapter(ca);
		dialog.show();
			bt.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					sended = et.getText().toString();
					if(sended.equals(""))
					{
						Toast.makeText(v.getContext(), "no coment sent,Empty", Toast.LENGTH_SHORT).show();
					}
					else
					{
						callC();
						Toast.makeText(v.getContext(), " coment sent", Toast.LENGTH_SHORT).show();
					}
					
					dialog.dismiss();
				}
				
			});
	}

	

}
