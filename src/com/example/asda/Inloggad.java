package com.example.asda;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Inloggad extends ICASjalvscanning {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inloggad);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Button loggaUt = (Button) findViewById(R.id.loggaUt);

		loggaUt.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				SharedPreferences userDetails = Inloggad.this
						.getSharedPreferences("loggedIn", MODE_PRIVATE);
				final Editor edit = userDetails.edit();
				edit.clear();
				edit.putBoolean("loggedIn", false);
				edit.commit();

				Intent myIntent = new Intent(Inloggad.this, LoginActivity.class);
				Inloggad.this.startActivity(myIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_inloggad, menu);
		return true;
	}

}
