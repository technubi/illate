package com.example.ilate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GaleryAdapter extends BaseAdapter{
	
	Context cxt;
	LayoutInflater li;
	int tinggi;
	List<Map<String,Object>> fotofood;
	
	
	public GaleryAdapter(Context cxt,List<Map<String,Object>> fotos)
	{
		this.cxt = cxt;
		li = LayoutInflater.from(cxt);
		this.fotofood = new ArrayList<Map<String,Object>>(fotos);
		 System.out.println(fotofood.size());
		
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fotofood.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return fotofood.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public int getHeights()
	{
		return tinggi;
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = convertView;
		ImageView picture;
		TextView name;
		 
		if(convertView == null) {
	        	v = new View(cxt);
	            v = li.inflate(R.layout.galeryitem, parent, false);
	            v.setTag(R.id.gambargalery, v.findViewById(R.id.gambargalery));
	            v.setTag(R.id.ketgalery, v.findViewById(R.id.ketgalery));
	        }
	            picture = (ImageView)v.getTag(R.id.gambargalery);
		        name = (TextView)v.getTag(R.id.ketgalery);
		        tinggi = picture.getHeight();
		      // HashMap<String,List<berita>> item = (HashMap<String,List<berita>>) getItem(i);
		        
		       byte[] foto = Base64.decode(fotofood.get(position).get("foodfoto").toString().getBytes(),0);
		       picture.setImageBitmap(BitmapFactory.decodeByteArray(foto, 0, foto.length));
		       name.setText(fotofood.get(position).get("foodname").toString());
		      
	            
	        
	       
	         return v;
	}
	
}
