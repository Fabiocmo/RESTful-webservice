package com.mukeshmaurya91.studentapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	EditText roll, pass;
	Button login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		roll = (EditText) findViewById(R.id.roll_no);
		pass = (EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.button_login);
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String roll_no = roll.getText().toString();
				String password = pass.getText().toString();
				DataBaseAdapter dba = new DataBaseAdapter(LoginActivity.this);
				// String all=dba.viewAll();
				// Toast.makeText(getBaseContext(),"Details "
				// +all,Toast.LENGTH_SHORT).show();
				if (!roll_no.equals(null) && !password.equals(null)) {
					try {
						String db_pass = dba.matchPassword(Long
								.valueOf(roll_no), pass.getText().toString());
						if (password.equals(db_pass)) {
							Bundle b = new Bundle();
							b.putLong(
									"com.mukeshmaurya91.studentapp.roll_number",
									Long.valueOf(roll_no));
							startActivity(new Intent(getBaseContext(),
									DetailActivity.class).putExtras(b));
						} else {
							Toast.makeText(getBaseContext(),
									"Roll number OR Password dose not match!",
									Toast.LENGTH_SHORT).show();
						}
					} catch (NumberFormatException e) {
						Toast.makeText(getBaseContext(),
								"Roll number and Password can't be blank!",
								Toast.LENGTH_SHORT).show();
					}

				} else {
					Toast.makeText(getBaseContext(),
							"Roll number and Password can't be blank!",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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

	public void forgotPassword(View v) {
		startActivity(new Intent(this, OTPSendActivity.class));
	}
}
