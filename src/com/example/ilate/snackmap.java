package com.example.ilate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
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
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class snackmap extends FragmentActivity implements Listener,OnMarkerClickListener, LocationListener,GetArrayList{
	
	private GoogleMap gmap;
	Double latitudeC = 0.0;
	Double longitudeC = 0.0;
	MarkerOptions marker2;
	Marker marker;
	LatLng latlng;
	LocationManager lm;
	Location loc;
	Button bt;
	Context ctx;
	int x;
	
	int urutan;
	
	Location location;
	LatLng latLng;//untuk current location
	private static final long MIN_TIME = 400;
	private static final float MIN_DISTANCE = 1000;
	
	
	List<Map<String,Object>> alllatlng;
	
	HashMap<String,LatLng> obyeks = new HashMap<String,LatLng>();
	List<LatLng> obyek = new ArrayList<LatLng>();
	async_tasked at;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maps);
		this.ctx = this.getApplicationContext();
		
		 	gmap = ((SupportMapFragment) getSupportFragmentManager()
	                .findFragmentById(R.id.map)).getMap();
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
	        	            startActivity(new Intent(snackmap.this,home.class));
	        	            dialog.dismiss();
	        	        }
	        	    });

	        	    AlertDialog alert = builder.create();
	        	    alert.show();
	        }
	         //getsetmarker();
	         gmap.setOnMarkerClickListener(this);
	         lm.requestLocationUpdates(provider, 20000, 0, this);
	         at = new async_tasked(snackmap.this,this);
	         at.execute("x","x","snackmap");
	         
	         
	         /*gmap.setOnMarkerClickListener(new OnMarkerClickListener(){

				@Override
				public boolean onMarkerClick(Marker arg0) {
					
						double jarak ;
						jarak = distance(latitudeC,longitudeC,arg0.getPosition().latitude,arg0.getPosition().longitude);
						Toast.makeText(snackmap.this, "ini jaraknya "+jarak,Toast.LENGTH_LONG).show();
					return true;
				}
	        	 
	         });*/
	        
		        
		        ImageView cl = (ImageView) findViewById(R.id.mylocation);
		        cl.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						latitudeC= location.getLatitude();
						longitudeC = location.getLongitude();
				 
				        if(marker2 == null)
				        {	
				        	latlng = new LatLng(latitudeC, longitudeC);
				        	marker2 = new MarkerOptions().position(new LatLng(latitudeC,longitudeC)).title("my location").snippet("selamat datang2").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
					        gmap.addMarker(marker2);

							//when the location changes, update the map by zooming to the location
							CameraUpdate center = CameraUpdateFactory.newLatLng(latlng);
							gmap.moveCamera(center);
							CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
					    	gmap.animateCamera(zoom);
				        }
						else
						{
							CameraUpdate center = CameraUpdateFactory.newLatLng(latlng);
							gmap.moveCamera(center);
							CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
					    	gmap.animateCamera(zoom);
					        
						}
				 
					}
		        	
		        });

	        
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
			        	 startActivity(new Intent(snackmap.this,home.class));
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


		
		
		public void getCurrentLocation(Location locatio){
			
		}
		
		@Override
		public void onLocationChanged(Location location) {
			latitudeC= location.getLatitude();
			longitudeC = location.getLongitude();
	 
	        if(marker2 == null)
	        {	
	        	latlng = new LatLng(latitudeC, longitudeC);
	        	marker2 = new MarkerOptions().position(new LatLng(latitudeC,longitudeC)).title("my location").snippet("posisi saya ada di lat : "+latitudeC+ " ,long : "+longitudeC).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
		        gmap.addMarker(marker2);

				//when the location changes, update the map by zooming to the location
				CameraUpdate center = CameraUpdateFactory.newLatLng(latlng);
				this.gmap.moveCamera(center);
				CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
		    	this.gmap.animateCamera(zoom);
	        }
			else
			{
				CameraUpdate center = CameraUpdateFactory.newLatLng(latlng);
				this.gmap.moveCamera(center);
				CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
		    	this.gmap.animateCamera(zoom);
		        
			}
		}

		@Override
		public void onProviderDisabled(String provider) {
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
		public void getArray(List<Map<String, Object>> param) {
			alllatlng = new ArrayList<Map<String,Object>>(param);
			System.out.println("ukuran latlng "+alllatlng.size());
			for(int k=0;k<alllatlng.size();k++)
			{
				Double la = Double.parseDouble(alllatlng.get(k).get("latitude").toString()); 
				Double lo = Double.parseDouble(alllatlng.get(k).get("longitude").toString());
				System.out.println("xxx " +k);
				marker = gmap.addMarker(new MarkerOptions().position(new LatLng(la,lo)).title(alllatlng.get(k).get("nmfood").toString()).snippet(alllatlng.get(k).get("nmplace").toString()+"\noke").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
			}
			System.out.println("ukuran obyeg "+obyek.size());
			
			
		}


		@Override
		public boolean onMarkerClick(Marker arg0) {
			
			gmap.setInfoWindowAdapter(new InfoWindowAdapter(){
				
				@Override
				public View getInfoContents(Marker savedInstanceState) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public View getInfoWindow(Marker marker) {
					View myContentView = getLayoutInflater().inflate(
							R.layout.custommarker, null);
					
					char kodemap = marker.getId().charAt(1);
					x = Character.getNumericValue(kodemap);
					
					//System.out.println("Xxxxxxx" + alllatlng.get(3).get("nmplace").toString());
					TextView nmplace = ((TextView) myContentView.findViewById(R.id.nmplaceM));
		            nmplace.setText(alllatlng.get(x).get("nmplace").toString());
		            
		            TextView foodname = ((TextView) myContentView.findViewById(R.id.nmfoodM));
		            foodname.setText(alllatlng.get(x).get("nmfood").toString());
		            
		            TextView location = ((TextView) myContentView.findViewById(R.id.nmalamatM));
		            location.setText(alllatlng.get(x).get("alamat").toString());
		            System.out.println("ini >>>> "+x+" "+alllatlng.get(x).get("foto").toString());
		            
		            ImageView gambarfood = ((ImageView) myContentView.findViewById(R.id.gambarfoodM));
		            byte[] imageAsBytes = Base64.decode(alllatlng.get(x).get("foto").toString().getBytes(),0);
		    		gambarfood.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
		            return myContentView;
				}
				
			});
			
			
			
			return false;
		}
		
		
	 
	 
	
	
}