package com.example.asda;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class InkopsLista extends ICASjalvscanning {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inkop_list);
		ListView myListView = (ListView) findViewById(R.id.myListView);

		final EditText myEditText = (EditText) findViewById(R.id.myEditText);

		final ArrayList<String> noteList = new ArrayList<String>();

		final ArrayAdapter<String> aa;

		aa = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, noteList);

		myListView.setAdapter(aa);

		Button btnSimple = (Button) findViewById(R.id.btnSimple);

		btnSimple.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				noteList.add(0, myEditText.getText().toString());
				aa.notifyDataSetChanged();
				myEditText.setText("");
			}
		});

		myListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int pos, long id) {
				AlertDialog.Builder adb = new AlertDialog.Builder(
						InkopsLista.this);
				adb.setTitle("Delete?");
				adb.setMessage("Är du säker att du vill ta bort: \n"
						+ noteList.get(pos));
				final int positionToRemove = pos;
				adb.setNegativeButton("Cancel", null);
				adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						noteList.remove(positionToRemove);
						aa.notifyDataSetChanged();

					}
				});
				adb.show();
				return true;
			}
		});

		myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Toast.makeText(InkopsLista.this, "Håll inne för att ta bort ",
						Toast.LENGTH_LONG).show();

			}

		});
		// @Override
		// public boolean onOptionsItemSelected(MenuItem item) {
		// switch (item.getItemId()) {
		// case android.R.id.home:
		// // This ID represents the Home or Up button. In the case of this
		// // activity, the Up button is shown. Use NavUtils to allow users
		// // to navigate up one level in the application structure. For
		// // more details, see the Navigation pattern on Android Design:
		// //
		// //
		// http://developer.android.com/design/patterns/navigation.html#up-vs-back
		// //
		// NavUtils.navigateUpFromSameTask(this);
		// return true;
		// }
		// return super.onOptionsItemSelected(item);
		// }

	}
}
