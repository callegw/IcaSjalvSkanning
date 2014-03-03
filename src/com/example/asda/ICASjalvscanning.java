package com.example.asda;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingActivity;

public class ICASjalvscanning extends SlidingActivity implements
		ActionBar.OnNavigationListener {

	public static TextView txt;
	public View views;
	public ArrayAdapter<String> files;
	public ArrayList<String> values;
	public String varuNamn;
	public ListView lv1;
	public String currenttext;
	public TextView info;
	public EditText antalEdit;
	public int antal, summa = 0, pris;

	NFCForegroundUtil nfcForegroundUtil = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hem);

		SharedPreferences userDetails = ICASjalvscanning.this
				.getSharedPreferences("first", MODE_PRIVATE);

		if (userDetails.getBoolean("first", true)) {
			Intent myIntent = new Intent(ICASjalvscanning.this, Wizard.class);
			ICASjalvscanning.this.startActivity(myIntent);
			finish();
		}

		nfcForegroundUtil = new NFCForegroundUtil(this);

		setBehindContentView(R.layout.menu);

		populate();
		SlidingMenu menu = getSlidingMenu();
		menu.setMode(SlidingMenu.LEFT);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		setSlidingActionBarEnabled(false);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		lv1 = (ListView) findViewById(R.id.list);
		values = new ArrayList<String>(Arrays.asList(""));

		files = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, values);

		lv1.setAdapter(files);

		files.notifyDataSetChanged();
		Button btn = (Button) findViewById(R.id.buttn1);
		info = (TextView) findViewById(R.id.nyVara);
		values.remove(0);

		files.notifyDataSetChanged();
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				currenttext = info.getText().toString();
				if (currenttext != "" || currenttext == "Large Text") {
					antalEdit = (EditText) findViewById(R.id.editText1);
					antalEdit.clearFocus();
					txt = (TextView) findViewById(R.id.summa);

					values.add(
							0,
							currenttext
									+ "\nPris: "
									+ pris(currenttext, antalEdit.getText()
											.toString()) + "\nAntal: " + antal);

					txt.setText("Summa: " + Integer.toString(summa));
					files.notifyDataSetChanged();
				}
			}
		});
		lv1.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int pos, long id) {
				AlertDialog.Builder adb = new AlertDialog.Builder(
						ICASjalvscanning.this);
				adb.setTitle("Delete?");
				adb.setMessage("Är du säker att du vill ta bort: \n"
						+ values.get(pos));
				final int positionToRemove = pos;
				adb.setNegativeButton("Cancel", null);
				adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						values.remove(positionToRemove);
						files.notifyDataSetChanged();
						summa -= pris(currenttext, antalEdit.getText()
								.toString());
						txt.setText("Summa: " + Integer.toString(summa));

					}
				});
				adb.show();
				return true;
			}
		});

		lv1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Toast.makeText(ICASjalvscanning.this,
						"Håll inne för att ta bort ", Toast.LENGTH_LONG).show();

			}

		});

		Button btn2 = (Button) findViewById(R.id.buyDone);
		btn2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(ICASjalvscanning.this,
						SlutFas.class);
				ICASjalvscanning.this.startActivity(myIntent);
				finish();

			}
		});

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
		// .getSelectedNavigationIndex());
	}

	public void onPause() {
		super.onPause();
		nfcForegroundUtil.disableForeground();
	}

	public void onResume() {
		super.onResume();
		nfcForegroundUtil.enableForeground();

		if (!nfcForegroundUtil.getNfc().isEnabled()) {
			Toast.makeText(getApplicationContext(),
					"Var god och sätt på NFC och sedan klicka Tillbaka",
					Toast.LENGTH_LONG).show();
			startActivity(new Intent(
					android.provider.Settings.ACTION_WIRELESS_SETTINGS));
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_ica_sjalvscanning, menu);
		return true;
	}

	public void onNewIntent(Intent intent) {
		// super.setIntent(intent);
		if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())
				|| NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())
				|| NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
			Parcelable[] rawMsgs = intent
					.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			NdefMessage[] msgs = null;
			if (rawMsgs != null) {
				msgs = new NdefMessage[rawMsgs.length];
				for (int i = 0; i < rawMsgs.length; i++) {
					msgs[i] = (NdefMessage) rawMsgs[i];

				}
			}
			varuNamn = new String(msgs[0].getRecords()[0].getPayload());
			TextView hej = (TextView) findViewById(R.id.nyVara);
			hej.setText(varuNamn.substring(3));

		}

	}

	private void populate() {

		ListView lv = (ListView) findViewById(R.id.listView1);
		String[] values = new String[] { "Hem", "Min Sida", "Recept",
				"Inköpslista" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, values);
		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				if (arg2 == 0) {

					Intent myIntent = new Intent(ICASjalvscanning.this,
							ICASjalvscanning.class);
					ICASjalvscanning.this.startActivity(myIntent);
					finish();

				} else if (arg2 == 1) {
					Intent myIntent = new Intent(ICASjalvscanning.this,
							LoginActivity.class);
					ICASjalvscanning.this.startActivity(myIntent);
				} else if (arg2 == 2) {
					Intent myIntent = new Intent(ICASjalvscanning.this,
							ReceptActivity.class);
					ICASjalvscanning.this.startActivity(myIntent);
				} else if (arg2 == 3) {
					Intent myIntent = new Intent(ICASjalvscanning.this,
							InkopsLista.class);
					ICASjalvscanning.this.startActivity(myIntent);
				}

			}

		});

	}

	@Override
	public boolean onNavigationItemSelected(int arg0, long arg1) {
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		case R.id.menu_settings:
			Intent myIntent = new Intent(ICASjalvscanning.this, Wizard.class);
			ICASjalvscanning.this.startActivity(myIntent);
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void attemptLogin() {
		// TODO Auto-generated method stub

	}

	public int pris(String vara, String sAntal) {
		antal = 0;
		if (sAntal.equalsIgnoreCase("")) {
			antal = 0;
		} else {
			antal = Integer.parseInt(sAntal);
		}

		int pris = 0;
		if (antal == 0) {
			antal = 1;
		}
		if (vara.equalsIgnoreCase("Banan")) {
			pris = 10;
		} else if (vara.equalsIgnoreCase("Tomat")) {
			pris = 5;
		} else if (vara.equalsIgnoreCase("Gurka")) {
			pris = 5;
		} else if (vara.equalsIgnoreCase("Schampo")) {
			pris = 25;
		} else if (vara.equalsIgnoreCase("Mjölk")) {
			pris = 11;
		} else if (vara.equalsIgnoreCase("Öl")) {
			pris = 65;
		}
		summa += pris * antal;
		return pris * antal;
	}

}