package com.example.ilate;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class Session {
	private SharedPreferences prefs;
	private Editor editor;
	Context ctx;
	int PRIVATE_MODE = 0;
	
	private static final String PREF_NAME = "ilatte";
	private static final String IS_LOGIN = "loggedIN";
	private static final String username = "name";
	private static final String iduser = "iduser";
	private static final String pass = "pass";
	
	
    public Session(Context cntx) {
        this.ctx = cntx;
        prefs = ctx.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = prefs.edit();
    }

    public void createLoginSession(String name, String password, String id_user){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(username, name);
        editor.putString(pass, password);
        editor.putString(iduser, id_user);
        editor.commit();
    }
    
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(username, prefs.getString(username, null));
         
        // user email id
        user.put(pass, prefs.getString(pass, null));
        
        // return user
        return user;
    }
    public String getIDuser()
    {
    	return prefs.getString(iduser, null);
    }
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(ctx, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             
            // Staring Login Activity
            ctx.startActivity(i);
        }
         
    }
    
    public boolean loginstatus()
    {
    	if(!this.isLoggedIn())
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }
    
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
         
        // After logout redirect user to Loing Activity
        Intent i = new Intent(ctx, MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         
        // Staring Login Activity
        ctx.startActivity(i);
    }
    
    public boolean isLoggedIn(){
        return prefs.getBoolean(IS_LOGIN, false);
    }
   
	
}
