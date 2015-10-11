package com.mukeshmaurya91.studentapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends Activity {
	EditText name, roll, pass, mobile, email;
	private String Name, Pass, Email;
	private Long Roll, Mobile;
	private int rollLength, mobileLength;
	int row = 0;
	long row1=0;
	private String RESOURCE_URL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		name = (EditText) findViewById(R.id.sign_name);
		roll = (EditText) findViewById(R.id.sign_roll);
		email = (EditText) findViewById(R.id.sign_email);
		pass = (EditText) findViewById(R.id.sign_pass);
		mobile = (EditText) findViewById(R.id.sign_mobile);
		SharedPreferences settingPref = PreferenceManager
				.getDefaultSharedPreferences(this);
		String localhost = settingPref.getString("HOST_PREF", "99.59.19.4");
		RESOURCE_URL = "http://" + localhost + ":8080/MyWebService/rest/roll=";
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.signup, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void signUp(View v) {
		DataBaseAdapter dba = new DataBaseAdapter(this);
		Name = name.getText().toString();
		Email = email.getText().toString();
		Pass = pass.getText().toString();
		try {
			rollLength = roll.getText().toString().length();
			mobileLength = mobile.getText().toString().length();
			Roll = Long.valueOf(roll.getText().toString());
			Mobile = Long.valueOf(mobile.getText().toString());
		} catch (NumberFormatException e) {
		}
		if (validdateInfo()) {
			 row1 = dba.insertData(Name, Roll, Email, Pass, Mobile, 0);
			try {
				if (isConnected())
					new WebServiceTask().execute(RESOURCE_URL);
			} catch (Exception e) {
			}
		}

	}

	private boolean isConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean connection = activeNetwork != null
				&& activeNetwork.isConnectedOrConnecting();
		return connection;
	}

	private boolean validdateInfo() {
		if (Name.equals("")) {
			Toast.makeText(this, "Name can't be blank!", Toast.LENGTH_SHORT)
					.show();
			return false;
		} else if (rollLength != 10) {
			Toast.makeText(this, "Roll number must be 10 digit!",
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (Email.equals("") || Email.indexOf("@") < 1
				|| Email.indexOf(".") - Email.indexOf("@") < 2) {
			Toast.makeText(this, "Email not valid!", Toast.LENGTH_SHORT).show();
			return false;
		} else if (mobileLength != 10) {
			Toast.makeText(this, "Mobile number must be 10 digit!",
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (Pass.length() < 8) {
			Toast.makeText(this, "Password must be atleast 8 character!",
					Toast.LENGTH_SHORT).show();
			return false;
		}

		return true;
	}

	private class WebServiceTask extends AsyncTask<String, Void, String> {
		ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(SignupActivity.this);
			pd.setTitle("Proccessing");
			pd.setMessage("Please wait while working !");
			pd.setCancelable(false);
			pd.show();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {

			HttpURLConnection urlConnection = null;
			String response = "";
			String query = new Uri.Builder().appendQueryParameter("name", Name)
					.appendQueryParameter("roll", Roll.toString())
					.appendQueryParameter("email", Email)
					.appendQueryParameter("mobile", Mobile.toString())
					.appendQueryParameter("pass", Pass).build()
					.getEncodedQuery();
			try {
				URL url = new URL(params[0]);
				urlConnection = (HttpURLConnection) url.openConnection();
				urlConnection.setRequestMethod("POST");
				urlConnection.setDoOutput(true);
				urlConnection.setDoInput(true);
				OutputStream os = urlConnection.getOutputStream();
				BufferedWriter writer = new BufferedWriter(
						new OutputStreamWriter(os, "UTF-8"));
				writer.write(query);
				writer.close();
				os.close();
				int responseCode = urlConnection.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
					response = br.readLine();
					row = 1;
				br.close();
				}
				urlConnection.disconnect();
			} catch (Exception e) {
				Log.e("Error :", e.toString());
			}

			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			pd.dismiss();
			if(result.equals("SUCCESS"))
				row = 1;
			Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
			if (row > 0) {
				Toast.makeText(SignupActivity.this, "Successfully sign up!",Toast.LENGTH_SHORT).show();
				Bundle b = new Bundle();
				b.putLong("com.mukeshmaurya91.studentApp.rollNumber", Roll);
				startActivity(new Intent(SignupActivity.this, ImageSelectorActivity.class).putExtras(b));
			} else{
				Toast.makeText(SignupActivity.this, "Unsuccessfully ! Please try again.",Toast.LENGTH_SHORT).show();
		}
			super.onPostExecute(result);

	}
	}
}
