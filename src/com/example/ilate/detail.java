package com.example.ilate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class detail extends FragmentActivity implements Listener,OnMarkerClickListener{
	
	private GoogleMap gmap;
	Double latitudeC = -7.78605;
	Double longitudeC = 110.37839;
	MarkerOptions marker2;
	Marker marker;
	LatLng latlng;
	LocationManager lm;
	Location loc;
	Button bt;
	Context ctx;
	
	HashMap<String,LatLng> obyeks = new HashMap<String,LatLng>();
	List<LatLng> obyek = new ArrayList<LatLng>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		this.ctx = this.getApplicationContext();
		
		 	gmap = ((SupportMapFragment) getSupportFragmentManager()
	                .findFragmentById(R.id.map)).getMap();
	        if (gmap == null) {
	            Toast.makeText(this, "Google Maps not available", 
	                Toast.LENGTH_LONG).show();
	        }
	        
	        lm =(LocationManager)getSystemService(Context.LOCATION_SERVICE);
	        loc =lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	      
	        
	         // This methods gets the users current longitude and latitude.
	         
	         //
	         String provider=lm.getBestProvider(new Criteria(), true);
	         latlng=new LatLng(latitudeC,longitudeC);
	         if(isLocation()==true)
	         {
	        	 
	         gmap.moveCamera(CameraUpdateFactory.newLatLng(latlng));//Moves the camera to users current longitude and latitude
	         gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,(float) 14.6));//Animates camera and zooms to preferred state on the user's current location.
	         }
	         else
	         {
	        	 AlertDialog.Builder builder = new AlertDialog.Builder(this);

	        	    builder.setTitle("Confirm");
	        	    builder.setMessage("to use this feature enable location");

	        	    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

	        	        public void onClick(DialogInterface dialog, int which) {
	        	        	startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),1);
		    	        	
	        	            dialog.dismiss();
	        	            
	        	            
	        	        }

	        	    });

	        	    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

	        	        @Override
	        	        public void onClick(DialogInterface dialog, int which) {
	        	            startActivity(new Intent(detail.this,home.class));
	        	            dialog.dismiss();
	        	        }
	        	    });

	        	    AlertDialog alert = builder.create();
	        	    alert.show();
	        	    
   		         
	        	
	        	 
	         }
	        
	         gmap.setOnMarkerClickListener(this);
	         /*gmap.setOnMarkerClickListener(new OnMarkerClickListener(){

				@Override
				public boolean onMarkerClick(Marker arg0) {
					if(arg0.getTitle().equals("xx")){
						double jarak ;
						jarak = distance(latitudeC,longitudeC,arg0.getPosition().latitude,arg0.getPosition().longitude);
						Toast.makeText(maps.this, "ini jaraknya "+jarak,Toast.LENGTH_LONG).show();}
					return true;
				}
	        	 
	         });*/

	        
	}
	
	
		 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is
	        // present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }
		 
		protected void onActivityResult(int requestCode, int resultCode, Intent data) 
		{
			 	if (requestCode == 1) {
			        if(isLocation()==true)
			        {
			        	 startActivity(new Intent(detail.this,home.class));
			        }
			    }
		}
		
		
		
		
		
		public boolean isLocation()
		{
			boolean enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
			return enabled;
		}
		
		private double distance(double lat1, double lon1, double lat2, double lon2) {
		  double theta = lon1 - lon2;
		  double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		  dist = Math.acos(dist);
		  dist = rad2deg(dist);
		  dist = dist * 60 * 1.1515;
		  dist = dist * 1.609344;
		  System.out.print(dist);
		  return (dist);
		}

		
		private double deg2rad(double deg) {
		  return (deg * Math.PI / 180.0);
		}

		
		private double rad2deg(double rad) {
		  return (rad * 180 / Math.PI);
		}

		

		

		@Override
		public void onGpsStatusChanged(int event) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public boolean onMarkerClick(Marker arg0) {
			if(arg0.getTitle().equals("xx"))
			Toast.makeText(detail.this, "ini jaraknya ",Toast.LENGTH_LONG).show();
			else
			Toast.makeText(detail.this, "ini jaxxxraknya ",Toast.LENGTH_LONG).show();
			return false;
		}
		
		
	 
	 
	
	
}