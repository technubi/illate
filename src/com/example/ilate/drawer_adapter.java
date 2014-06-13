package com.example.ilate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class drawer_adapter extends BaseAdapter{
	
	String[] content = new String[]{"snack - map","myLocation","Main Course - map", "Beverages - map" , "Desert - map"};
	
	LayoutInflater li;
	Context cx;
	public drawer_adapter(Context cx)
	{
		this.cx = cx;
		this.li = LayoutInflater.from(cx);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return content.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return content;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder hd = null;
		if(convertView == null)
		{
			convertView = li.inflate(R.layout.drawerlist_item, null);
			hd = new ViewHolder();
			hd.tvdrawer = (TextView)convertView.findViewById(R.id.textdrawer);
			convertView.setTag(hd);
		}
		else
		{
			hd = (ViewHolder) convertView.getTag();
		}
		hd.tvdrawer.setText(content[position]);
		return convertView;
		
	}
	
	static class ViewHolder
	{
		TextView tvdrawer;
	}

}
