package com.example.asda;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SlutFas extends Activity {
	Button btnStartProgress;
	ProgressDialog snurra;
	private int snurraStatus = 0;
	private Handler snurraHandler = new Handler();

	private long transDummy = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slut_fas);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		addListenerOnButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_slut_fas, menu);
		return true;
	}

	public void addListenerOnButton() {

		btnStartProgress = (Button) findViewById(R.id.checkaUt);
		btnStartProgress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				snurra = new ProgressDialog(v.getContext());
				snurra.setCancelable(true);
				snurra.setMessage("Transaktion sker: ");
				snurra.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				snurra.setProgress(0);
				snurra.setMax(100);
				snurra.show();

				snurraStatus = 0;

				transDummy = 0;

				new Thread(new Runnable() {
					public void run() {
						while (snurraStatus < 100) {

							snurraStatus = doSomeTasks();

							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

							snurraHandler.post(new Runnable() {
								public void run() {
									snurra.setProgress(snurraStatus);
								}
							});
						}

						if (snurraStatus >= 100) {

							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

							Intent myIntent = new Intent(SlutFas.this,
									ICASjalvscanning.class);
							SlutFas.this.startActivity(myIntent);
							finish();

							snurra.dismiss();
						}
					}
				}).start();

			}

		});

	}

	public int doSomeTasks() {

		while (transDummy <= 1000000) {

			transDummy++;

			if (transDummy == 100000) {
				return 10;
			} else if (transDummy == 200000) {
				return 20;
			} else if (transDummy == 300000) {
				return 30;
			}

		}

		return 100;

	}
}
