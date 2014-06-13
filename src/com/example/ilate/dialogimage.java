package com.example.ilate;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

public class dialogimage extends Activity{
	TextView nmMakanan;
	TextView tempat;
	TextView keterangan;
	ImageView fotobesar;
	@Override
	protected void onCreate(Bundle savedInstanceStace)
	{
		super.onCreate(savedInstanceStace);
		setContentView(R.layout.details);
		Bundle b = getIntent().getExtras();
		String[] kata = b.getStringArray("lempar");
		fotobesar = (ImageView) findViewById(R.id.fotodetails);
		nmMakanan = (TextView) findViewById(R.id.txtNamaMakanan);
		tempat = (TextView) findViewById(R.id.txtTempat);
		keterangan = (TextView) findViewById(R.id.txtKet);
		byte[] imageAsBytes = Base64.decode(kata[0].getBytes(),0);
		
		
		fotobesar.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
		nmMakanan.setText("nama makanan : " + kata[1]);
		tempat.setText("lokasi : "+ kata[2]);
		keterangan.setText("about : "+kata[3]);
		//System.out.println(kata[0]);
	}
}
