package com.example.ilate;

import java.util.List;
import java.util.Map;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.GpsStatus.Listener;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import android.widget.Toast;

public class foodloc extends FragmentActivity implements Listener,OnMarkerClickListener, LocationListener,GetArrayList{
	private GoogleMap gmap;
	Double latitudeC = 0.0;
	Double longitudeC = 0.0;
	MarkerOptions marker2;
	MarkerOptions marker3;
	Marker marker;
	LatLng latlngz;
	LocationManager lm;
	Location loc;
	Context ctx;
	
	Location location;
	LatLng latLng;//untuk current location
	private static final long MIN_TIME = 400;
	private static final float MIN_DISTANCE = 1000;
	@Override
	protected void onCreate(Bundle savedInstanceStace)
	{
		super.onCreate(savedInstanceStace);
		setContentView(R.layout.foodloc);
		/*TextView lat = (TextView) findViewById(R.id.latloc);
		TextView lon = (TextView) findViewById(R.id.longloc);*/
		
		double[] latlng = new double[2];
		Bundle b = getIntent().getExtras();
		String[] tangkap = b.getStringArray("info");
		
		latlng = b.getDoubleArray("latlng");
		String kata = latlng[0]+"";
		String kata2 = latlng[1]+"";
		double latitudeC = Double.parseDouble(kata);
		double longitudeC = Double.parseDouble(kata2);
		/*lat.setText(kata);
		lon.setText(kata2);*/
		
		
		this.ctx = this.getApplicationContext();
		
	 	gmap = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapzz)).getMap();
        if (gmap == null) {
            Toast.makeText(this, "Google Maps not available", 
                Toast.LENGTH_LONG).show();
        }
        lm =(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) { 
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
            
        }
        else { 
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
        }
        
         String provider=lm.getBestProvider(new Criteria(), true);
         location = lm.getLastKnownLocation(provider);
         double latsek = location.getLatitude();
         double longsek = location.getLongitude();
         if(isLocation()==true)
         {
        	 onLocationChanged(location);
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
	    	        	finish();
        	         }
        	    });
        	    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
        	    	@Override
        	        public void onClick(DialogInterface dialog, int which) {
        	            startActivity(new Intent(foodloc.this,home.class));
        	            dialog.dismiss();
        	        }
        	    });

        	    AlertDialog alert = builder.create();
        	    alert.show();
        }
         //getsetmarker();
         latlngz = new LatLng(latitudeC,longitudeC);
     	marker2 = new MarkerOptions().position(new LatLng(latitudeC,longitudeC)).title(tangkap[0]).snippet(tangkap[1]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
     	BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.me);
     	marker3 = new MarkerOptions().position(new LatLng(latsek,longsek)).title("posisi saya").snippet("lat : "+latsek+" long : "+longsek).icon(icon);
	    gmap.addMarker(marker2);
	    gmap.addMarker(marker3);
	    CameraUpdate center = CameraUpdateFactory.newLatLng(latlngz);
		gmap.moveCamera(center);
		CameraUpdate zoom=CameraUpdateFactory.zoomTo(13);
    	gmap.animateCamera(zoom);
        
		
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		 	if (requestCode == 1) {
		        if(isLocation()==true)
		        {
		        	 startActivity(new Intent(foodloc.this,home.class));
		        }
		    }
	}
	
	public boolean isLocation()
	{
		boolean enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
		return enabled;
	}
	
	@Override
	public void getArray(List<Map<String, Object>> param) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onGpsStatusChanged(int event) {
		// TODO Auto-generated method stub
		
	}
}
