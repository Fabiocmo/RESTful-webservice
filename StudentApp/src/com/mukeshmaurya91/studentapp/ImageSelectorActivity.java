package com.mukeshmaurya91.studentapp;

import java.util.Locale;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ImageSelectorActivity extends Activity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	static long roll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_selector);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		Intent in = getIntent();
		Bundle b = in.getExtras();
		if (b != null) {
			roll = b.getLong("com.mukeshmaurya91.studentApp.rollNumber");
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_selector, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		if (mViewPager.getCurrentItem() == 0) {
			super.onBackPressed();
		} else {
			// Otherwise, select the previous step.
			mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
		}

	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			// Show 10 total pages.
			return 10;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section0).toUpperCase(l);
			case 1:
				return getString(R.string.title_section1).toUpperCase(l);
			case 2:
				return getString(R.string.title_section2).toUpperCase(l);
			case 3:
				return getString(R.string.title_section3).toUpperCase(l);
			case 4:
				return getString(R.string.title_section4).toUpperCase(l);
			case 5:
				return getString(R.string.title_section5).toUpperCase(l);
			case 6:
				return getString(R.string.title_section6).toUpperCase(l);
			case 7:
				return getString(R.string.title_section7).toUpperCase(l);
			case 8:
				return getString(R.string.title_section8).toUpperCase(l);
			case 9:
				return getString(R.string.title_section9).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		private TextView title;
		private ImageView image;
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_image_selector,
					container, false);
			Bundle bundle = getArguments();
			int section = 0;
			if (bundle != null) {
				section = bundle.getInt(ARG_SECTION_NUMBER);
			}
			final int kid = section - 1;
			int resId = getRes(section);
			image = (ImageView) rootView.findViewById(R.id.image);
			title = (TextView) rootView.findViewById(R.id.section_label);
			image.setImageResource(resId);
			if (section > 1) {
				title.setText("Kid " + kid);
				rootView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						DataBaseAdapter adb = new DataBaseAdapter(getActivity());
						if (adb.updatePic(kid, roll)) {
							Toast.makeText(getActivity(),
									"Your avatar selected ! ",
									Toast.LENGTH_SHORT).show();
							startActivity(new Intent(getActivity(),
									LoginActivity.class));

						}

					}
				});
			}
			return rootView;
		}

		private int getRes(int section) {
			switch (section) {
			case 1:
				return R.drawable.select_msg;
			case 2:
				return R.drawable.kids1;
			case 3:
				return R.drawable.kids2;
			case 4:
				return R.drawable.kids3;
			case 5:
				return R.drawable.kids4;
			case 6:
				return R.drawable.kids5;
			case 7:
				return R.drawable.kids6;
			case 8:
				return R.drawable.kids7;
			case 9:
				return R.drawable.kids8;
			case 10:
				return R.drawable.kids9;

			}
			return 0;
		}
	}

}
