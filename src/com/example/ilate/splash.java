package com.example.ilate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;



public class splash extends Activity {
	Session session;
    private final int SPLASH_DISPLAY_LENGHT = 1000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash);
        session = new Session(getApplicationContext());
        
        /* New Handler to start the Menu-Activity 
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
            	if(session.loginstatus()==false)
            	{
            		Intent i = new Intent(splash.this, MainActivity.class);
                    // Closing all the Activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     
                    // Add new Flag to start new Activity
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     
                    // Staring Login Activity
                    splash.this.startActivity(i);
                    splash.this.finish();
            	}
            	else
            	{
            		Intent i = new Intent(splash.this, home.class);
                    
                    splash.this.startActivity(i);
                    splash.this.finish();
            	}
                /*Intent mainIntent = new Intent(splash.this,home.class);
                splash.this.startActivity(mainIntent);*/
                
            }
        }, SPLASH_DISPLAY_LENGHT);
    }
}