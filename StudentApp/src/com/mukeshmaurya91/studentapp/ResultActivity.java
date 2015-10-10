package com.mukeshmaurya91.studentapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import other.StudentResult;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ResultActivity extends Activity {
	private TextView mark1, mark2, mark3, mark4, mark5, mark6, total,
			percentage, rollNo;
	StudentResult result;
	Long roll;
	private String RESOURCE_URL = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		rollNo = (TextView) findViewById(R.id.roll);
		mark1 = (TextView) findViewById(R.id.mark1);
		mark2 = (TextView) findViewById(R.id.mark2);
		mark3 = (TextView) findViewById(R.id.mark3);
		mark4 = (TextView) findViewById(R.id.mark4);
		mark5 = (TextView) findViewById(R.id.mark5);
		mark6 = (TextView) findViewById(R.id.mark6);
		total = (TextView) findViewById(R.id.tot);
		percentage = (TextView) findViewById(R.id.per_m);
		SharedPreferences settingPref = PreferenceManager
				.getDefaultSharedPreferences(this);
		String localhost = settingPref.getString("HOST_PREF", "99.59.19.4");
		RESOURCE_URL = "http://" + localhost
				+ ":8080/MyWebService/rest/result/roll=";

		result = new StudentResult();
		Bundle b = getIntent().getExtras();
		if (b != null) {
			roll = b.getLong("com.mukeshmaurya91.studentApp.roll");
		}

		new WebServiceResult().execute(RESOURCE_URL + roll); // execute
																// background
																// task
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
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

	private class WebServiceResult extends AsyncTask<String, Void, String> {
		ProgressDialog pd = null;

		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(ResultActivity.this);
			pd.setTitle("Loading");
			pd.setMessage("Please wait ...");
			pd.setCancelable(false);
			pd.show();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			String str = "";
			HttpURLConnection urlConnection = null;
			try {
				URL url = new URL(params[0]);
				urlConnection = (HttpURLConnection) url.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream()));
				String line = "";
				while ((line = in.readLine()) != null) {
					str += line;
				}
				in.close();
			} catch (Exception e) {
			}
			try {
				urlConnection.disconnect();
			} catch (Exception e) {
			}
			return str;
		}

		@Override
		protected void onPostExecute(String result) {
			pd.dismiss();

			parseJSON(result);
			super.onPostExecute(result);
		}

	}

	private void parseJSON(String json) {

		try {
			JSONObject obj = new JSONObject(json);
			result.setSub1(Integer.parseInt(obj.getString("subone")));
			result.setSub2(Integer.parseInt(obj.getString("subtwo")));
			result.setSub3(Integer.parseInt(obj.getString("subthree")));
			result.setSub4(Integer.parseInt(obj.getString("subfour")));
			result.setSub5(Integer.parseInt(obj.getString("subfive")));
			result.setSub6(Integer.parseInt(obj.getString("subsix")));
			result.setTotal(result.getSub1() + result.getSub2()
					+ result.getSub3() + result.getSub4() + result.getSub5()
					+ result.getSub6());
			result.setPer(result.getTotal() / 6.00);
			setResultOnScreen();
		} catch (JSONException e) {
		}

	}

	private void setResultOnScreen() {
		rollNo.setText(roll + "");
		mark1.setText(result.getSub1() + "");
		mark2.setText(result.getSub2() + "");
		mark3.setText(result.getSub3() + "");
		mark4.setText(result.getSub4() + "");
		mark5.setText(result.getSub5() + "");
		mark6.setText(result.getSub6() + "");
		total.setText(result.getTotal() + "");
		percentage.setText(result.getPer() + "%");
	}
}
