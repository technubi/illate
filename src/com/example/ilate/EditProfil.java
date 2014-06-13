package com.example.ilate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class EditProfil extends Activity {
	EditText nama;
	EditText about;
	ImageView profpic;
	Button save;
	ImageView change;
	String dapat ="" ;
	Session session;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post);
		//addItemSp1();
		nama = (EditText) findViewById(R.id.inpNama);
		about = (EditText) findViewById(R.id.inpAbout);
		profpic = (ImageView)findViewById(R.id.imgProfpic);
		change = (ImageView)findViewById(R.id.imgChange);
		
		change.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), 3);
			}
        });
		
		save.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				saving();
			}
		});
	}
	
	private void saving()
	{
	}
}