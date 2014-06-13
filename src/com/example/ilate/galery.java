package com.example.ilate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;

public class galery extends Activity implements GetArrayList{
	GridView gv;
	GaleryAdapter ga;
	List<Map<String,Object>> lempar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.galery);
		
		Bundle b = getIntent().getExtras();
		String[] dapat=  new String[2];
		dapat = b.getStringArray("id_user");
		async_tasked at = new async_tasked(galery.this,this);
		at.execute(dapat[1],dapat[0],"galery");
		
		
		
	}
	@Override
	public void getArray(List<Map<String, Object>> param) {
		lempar = new ArrayList<Map<String,Object>>(param);
		gv = (GridView) findViewById(R.id.gvgalery);
		ga = new GaleryAdapter(this,lempar);
		
		gv.setAdapter(ga);
		gv.setDrawSelectorOnTop(true);
		
		
	}
}
