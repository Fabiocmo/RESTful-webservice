package com.mukeshmaurya91.studentapp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import util.CircularImageView;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends Activity {
	private CircularImageView rv;
	private TextView tv;
	private String detail = "";
	private String s = "";
	long roll = 0;
	private String RESOURCE_URL;
	Button result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		rv = (CircularImageView) findViewById(R.id.circular_img);
		tv = (TextView) findViewById(R.id.tv);
		result = (Button) findViewById(R.id.button_result);
		Bundle b = getIntent().getExtras();
		if (b != null) {
			roll = b.getLong("com.mukeshmaurya91.studentapp.roll_number");
		}

		result.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isConnected()) {
					Bundle b = new Bundle();
					b.putLong("com.mukeshmaurya91.studentApp.roll", roll);
					Log.i("check :", roll + " ");
					startActivity(new Intent(getBaseContext(),
							ResultActivity.class).putExtras(b));
				} else {
					Toast.makeText(getBaseContext(), "No Internet Connection!",
							Toast.LENGTH_LONG).show();
				}

			}
		});

		setDetail();
	}

	private void setDetail() {
		SharedPreferences settingPref = PreferenceManager
				.getDefaultSharedPreferences(this);
		String localhost = settingPref.getString("HOST_PREF", "99.59.19.4");
		RESOURCE_URL = "http://" + localhost + ":8080/MyWebService/rest/roll=";
		DataBaseAdapter dba = new DataBaseAdapter(this);
		s = dba.getDetail(roll);
		rv.setImageResource(getResId(dba.getAvatar()));
		try {
			new WebServiceTask().execute(RESOURCE_URL + roll); // execute
																// background
																// task
		} catch (Exception e) {

		}

	}

	private boolean isConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean connection = activeNetwork != null
				&& activeNetwork.isConnectedOrConnecting();
		return connection;
	}

	private int getResId(int res) {
		switch (res) {
		case 1:
			return R.drawable.kids1;
		case 2:
			return R.drawable.kids2;
		case 3:
			return R.drawable.kids3;
		case 4:
			return R.drawable.kids4;
		case 5:
			return R.drawable.kids5;
		case 6:
			return R.drawable.kids6;
		case 7:
			return R.drawable.kids7;
		case 8:
			return R.drawable.kids8;
		case 9:
			return R.drawable.kids9;

		}
		return R.drawable.kids9;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action) {
			setDetail();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class WebServiceTask extends AsyncTask<String, Void, String> {
		ProgressDialog pd = null;

		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(DetailActivity.this);
			pd.setTitle("Loading");
			pd.setMessage("Please wait ...");
			pd.setCancelable(false);
			pd.show();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {

			StringBuilder sb = new StringBuilder();
			HttpURLConnection urlConnection = null;

			try {
				URL url = new URL(params[0]);
				urlConnection = (HttpURLConnection) url.openConnection();
				InputStream is = new BufferedInputStream(
						urlConnection.getInputStream());
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "UTF-8"));
				String line;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				reader.close();
			} catch (Exception e) {
				Log.e("Connection error", e.toString());
			}
			try {
				urlConnection.disconnect();
			} catch (Exception e) {
			}
			return sb.toString();
		}

		@Override
		protected void onPostExecute(String result) {
			pd.dismiss();
			parseJsonResult(result);
			super.onPostExecute(result);
		}

	}

	private void parseJsonResult(String result) {
		try {
			JSONObject obj = new JSONObject(result);
			detail = "Name : " + obj.getString("name") + "\n";
			detail += "Email : " + obj.getString("email") + "\n";
			detail += "Mobile : " + obj.getString("mobile") + "\n";
		} catch (Exception e) {
			Log.e("ERROR :", e.toString());
		}
		if (!detail.equals("")) {
			tv.setText(roll + "\n" + detail);
		} else {
			Toast.makeText(this, "You are offline !", Toast.LENGTH_LONG).show();
			tv.setText("Roll No.: " + roll + "\n" + s);
		}

	}
}
