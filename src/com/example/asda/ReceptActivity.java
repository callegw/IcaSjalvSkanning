package com.example.asda;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

public class ReceptActivity extends ICASjalvscanning {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recept);

		ImageButton img1 = (ImageButton) findViewById(R.id.imgB1);
		ImageButton img2 = (ImageButton) findViewById(R.id.imgB2);
		ImageButton img3 = (ImageButton) findViewById(R.id.imgB3);
		ImageButton img4 = (ImageButton) findViewById(R.id.imgB4);
		ImageButton img5 = (ImageButton) findViewById(R.id.imgB5);
		ImageButton img6 = (ImageButton) findViewById(R.id.imgB6);

		img1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent myIntent = new Intent(ReceptActivity.this,
						Recept.class);
				ReceptActivity.this.startActivity(myIntent);
			}
		});
		img2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent myIntent = new Intent(ReceptActivity.this,
						Recept.class);
				ReceptActivity.this.startActivity(myIntent);
			}
		});
		img3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent myIntent = new Intent(ReceptActivity.this,
						Recept.class);
				ReceptActivity.this.startActivity(myIntent);
			}
		});
		img4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent myIntent = new Intent(ReceptActivity.this,
						Recept.class);
				ReceptActivity.this.startActivity(myIntent);
			}
		});
		img5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent myIntent = new Intent(ReceptActivity.this,
						Recept.class);
				ReceptActivity.this.startActivity(myIntent);
			}
		});
		img6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent myIntent = new Intent(ReceptActivity.this,
						Recept.class);
				ReceptActivity.this.startActivity(myIntent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
