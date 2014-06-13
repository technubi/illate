package com.example.ilate;






import android.app.ActionBar;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class home extends TabActivity{
	   	private String[] drawerListViewItems;
	    private DrawerLayout drawerLayout;
	    private ActionBarDrawerToggle actionBarDrawerToggle;
	    private ListView drawerListView;
	    private ListView drawerRlistView;
	   Session session;
	   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 
		session = new Session(getApplicationContext());
		super.onCreate(savedInstanceState);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.back1));
		if(session.isLoggedIn()==true)
		{
			setContentView(R.layout.home_loged);
			ImageView post = (ImageView) findViewById(R.id.post);
			post.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					startActivity(new Intent(home.this,Post.class));
				}
				
			});
		}
		else
		{
			setContentView(R.layout.home);
			Button lgout = (Button) findViewById(R.id.loginnf);
			lgout.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					startActivity(new Intent(home.this,MainActivity.class));
					finish();
					
				}
				
			});
		}
		
		
		
		TabHost th = getTabHost();
				
			
		
		
				TabSpec newsfeed = th.newTabSpec("nf");
				newsfeed.setIndicator(null,getResources().getDrawable(R.drawable.nhome));
				Intent newsfeedintent = new Intent(this,newsfeed.class);
				newsfeed.setContent(newsfeedintent);
				
				TabSpec newer = th.newTabSpec("favorite");
				newer.setIndicator(null,getResources().getDrawable(R.drawable.nfavorite));
				Intent newerintent = new Intent(this,favorite.class);
				newer.setContent(newerintent);
				
				TabSpec home = th.newTabSpec("home");
				home.setIndicator(null,getResources().getDrawable(R.drawable.nprofile));
				Intent homeintent = new Intent(this,MyProfile.class);
				home.setContent(homeintent);
				
				
				
				
				th.addTab(newsfeed);
				th.addTab(newer);
				th.addTab(home);
				
				 for (int i = 0; i < th.getTabWidget().getChildCount(); i++) {
				     th.getTabWidget().getChildAt(i).setPadding(10,10,10,10); 
				 }
				
				 	drawerListView = (ListView) findViewById(R.id.left_drawer);
			        drawer_adapter draw = new drawer_adapter(this);
			        drawerListView.setAdapter(draw);
			        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
			        actionBarDrawerToggle = new ActionBarDrawerToggle(
			                this,                  /* host Activity */
			                drawerLayout,         /* DrawerLayout object */
			                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
			                R.string.drawer_open,  /* "open drawer" description */
			                R.string.drawer_close  /* "close drawer" description */
			                );
			 
			        drawerLayout.setDrawerListener(actionBarDrawerToggle);
			        getActionBar().setDisplayHomeAsUpEnabled(true); 
			        drawerLayout.setDrawerShadow(R.drawable.drawer_shadoww, GravityCompat.START);
			        drawerListView.setOnItemClickListener(new DrawerItemClickListener());
			        
			        
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        // your code
	    	finish();
	    }

	    return super.onKeyDown(keyCode, event);
	}
	 @Override
	    protected void onPostCreate(Bundle savedInstanceState) {
	        super.onPostCreate(savedInstanceState);
	        actionBarDrawerToggle.syncState();
	    }
	 
	    @Override
	    public void onConfigurationChanged(Configuration newConfig) {
	        super.onConfigurationChanged(newConfig);
	        actionBarDrawerToggle.onConfigurationChanged(newConfig);
	    }
	    
	    @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			super.onCreateOptionsMenu(menu);
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.main, menu);
	        
	        MenuItem menuItem = menu.findItem(R.id.options_menu_main_search);
	        SearchView searchView = (SearchView) menuItem.getActionView();
	        searchView.setQueryHint("Type something...");
	        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
	        View searchPlate = searchView.findViewById(searchPlateId);
	        if (searchPlate!=null) {
	            
	        	System.out.println("dicari");
	        }
	        return true;
		}
	 
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	    	if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
	            return true;
	        }
	    	else
	    	{
	    		switch (item.getItemId()) {
	    		
	            case R.id.logout:
	            	session.logoutUser();
	            	finish();
	                  break;
	            }
	    	}
	    	
	        return super.onOptionsItemSelected(item);
	    }
	 
	    private class DrawerItemClickListener implements ListView.OnItemClickListener {
	        
	    	
	    	@Override
	        public void onItemClick(AdapterView parent, View view, int position, long id) {
	           Toast.makeText(getApplicationContext(), "tertekan", Toast.LENGTH_LONG).show();
	           if(position==0)
	           startActivity(new Intent(home.this,snackmap.class));
	           else if(position == 1)
	           startActivity(new Intent(home.this,maps.class));
	           else if(position == 2)
		           startActivity(new Intent(home.this,maincoursemap.class));
	 
	        }
	    }
	   /* @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			super.onCreateOptionsMenu(menu);
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.main, menu);
			return true;
	        
	       
		}*/
	    
	    

}
