package com.example.ilate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.ilate.drawer_adapter.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class comentadapter extends BaseAdapter{
	public Context cx;
	LayoutInflater li;
	
	List<Map<String, Object>> content;
	public comentadapter(Context cx,List<Map<String, Object>> contents)
	{
		this.cx = cx;
		li = LayoutInflater.from(cx);
		
		
		this.content = new ArrayList<Map<String,Object>>(contents);
		System.out.println(this.content.size());
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return content.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder hd = null;
		if(arg1 == null)
		{
			arg1 = li.inflate(R.layout.comentitem, null);
			hd = new ViewHolder();
			hd.tvdrawer = (TextView)arg1.findViewById(R.id.namaCm);
			hd.commentTxt = (TextView)arg1.findViewById(R.id.comentCm);
			arg1.setTag(hd);
		}
		else
		{
			hd = (ViewHolder) arg1.getTag();
		}
		
		
		if(content.get(0).get("user").toString().equals("no coment"))
		{
			hd.tvdrawer.setText("there is no coment");
			hd.commentTxt.setVisibility(View.GONE);
		}
		else
		{
			hd.tvdrawer.setText(content.get(0).get("user").toString());
			hd.commentTxt.setText(content.get(0).get("isikomen").toString());
		}
		
		return arg1;
		
	}
	
	static class ViewHolder
	{
		TextView tvdrawer;
		TextView commentTxt;
		ImageView imgC;
	}
	

}
