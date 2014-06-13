package com.example.ilate;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyProfileAdapter extends BaseAdapter{
	
	Context ctx;
	LayoutInflater li;
	Integer[] foto = new Integer[4];
	String[] jumlah = new String[4];
	Integer[] idfood = new Integer[4];
	Integer ko ;
 	Session session;
 	List<Map<String,Object>> data;
	public MyProfileAdapter(Context ctx,List<Map<String,Object>> data)
	{
		this.ctx = ctx;
		li = LayoutInflater.from(ctx);
		foto[0] = R.drawable.album1;
		foto[1] = R.drawable.album2;
		foto[2] = R.drawable.album3;
		foto[3] = R.drawable.album4;
		this.data = new ArrayList<Map<String,Object>>(data);
		for(int k=0;k<4;k++)
			idfood[k]= 0;
		for(int k=0;k<data.size();k++)
		{
			ko = Integer.parseInt(data.get(k).get("idjenis").toString());
			ko = ko - 1;
			idfood[ko] = Integer.parseInt(data.get(ko).get("jmlhfotoperjenis").toString());
			
		}
		System.out.println("panjang " +idfood.length);
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return idfood.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * 
	 */
	
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View v = arg1;
		ImageView picture;
		TextView name;
		 
		if(arg1 == null) {
	        	v = new View(ctx);
	            v = li.inflate(R.layout.myprofileitem, arg2, false);
	            v.setTag(R.id.picture, v.findViewById(R.id.picture));
	            v.setTag(R.id.jumlahfotoMP, v.findViewById(R.id.jumlahfotoMP));
	        }
	            picture = (ImageView)v.getTag(R.id.picture);
		        name = (TextView)v.getTag(R.id.jumlahfotoMP);
		        
		        picture.setImageResource(foto[arg0]);
			    name.setText(idfood[arg0]+" photo");
		        //Integer ko = Integer.parseInt(data.get(arg0).get("idjenis").toString());
				System.out.println(" x " + arg0+ " ini " + idfood[arg0]);
		        //ko = ko - 1;
				//System.out.println(data.get(ko).get("jmlhfotoperjenis").toString());
		        
		      // HashMap<String,List<berita>> item = (HashMap<String,List<berita>>) getItem(i);
		        
		        
		      
	            
	        
	       
	         return v;
		
	}
	
	

}

	

