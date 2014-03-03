package com.example.asda;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

@SuppressLint("DefaultLocale")
public class Wizard extends FragmentActivity {
	SectionsPagerAdapter mSectionsPagerAdapter;

	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@SuppressLint("DefaultLocale")
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {

			Fragment fragment = null;
			if (position == 0) {
				fragment = new oneFragment();
			} else if (position == 1) {
				fragment = new twoFragment();
			} else if (position == 2) {
				fragment = new threeFragment();
			}
			Bundle args = new Bundle();
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return 3;
		}

		@SuppressLint("DefaultLocale")
		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase();
			case 1:
				return getString(R.string.title_section2).toUpperCase();
			case 2:
				return getString(R.string.title_section3).toUpperCase();
			}
			return null;
		}
	}

	public static class oneFragment extends Fragment {

		public oneFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);
			View view = inflater.inflate(R.layout.wizard1, container, false);
			return view;
		}
	}

	public static class twoFragment extends Fragment {

		public twoFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			super.onCreateView(inflater, container, savedInstanceState);
			View view = inflater.inflate(R.layout.wizard2, container, false);
			return view;
		}
	}

	public static class threeFragment extends Fragment {

		public threeFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);

			View view = inflater.inflate(R.layout.wizard3, container, false);
			Button back = (Button) view.findViewById(R.id.back);

			back.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {

					Intent myIntent = new Intent(getActivity(),
							ICASjalvscanning.class);
					getActivity().startActivity(myIntent);

					SharedPreferences userDetails = getActivity()
							.getSharedPreferences("first", MODE_PRIVATE);
					final Editor edit = userDetails.edit();
					edit.clear();
					edit.putBoolean("first", false);
					edit.commit();

				}
			});
			return view;
		}
	}

}
