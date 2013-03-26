package com.dkit.eventsmanger;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	TextView email, password;
	Button loginBtn, registerBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		email = (TextView) findViewById(R.id.login_email);
		password = (TextView) findViewById(R.id.login_password);
		loginBtn = (Button) findViewById(R.id.login_btn);
		registerBtn = (Button) findViewById(R.id.login_register_btn);
		SharedPreferences settings = getSharedPreferences("main", 0);
		Log.d("login", settings.getString("token", "noLogged"));
		loginBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(arg0 == loginBtn)
					new LoginTask().execute();
				
			}
		});
		registerBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stubelse {
				Intent intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(intent);
				
				
			}
		});
	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

	ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	final String LOGIN_URL = "http://events-android.aws.af.cm/users.php";

	class LoginTask extends AsyncTask<String, String, String> {
		boolean login = false;

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(LoginActivity.this);
			pDialog.setMessage("Login in process....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
			String emailStr = email.getText().toString();
			String passwordStr = password.getText().toString();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("type", "login"));
			params.add(new BasicNameValuePair("email", emailStr));
			params.add(new BasicNameValuePair("password", passwordStr));
 
			JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "GET",
					params);
			Log.d("here", "here");
			try {
				if (json.getString("status").compareTo("404") == 0) {
					Log.d("Login", "Login 404");
				} else {
					SharedPreferences settings = getSharedPreferences("main", 0);
					SharedPreferences.Editor editor = settings.edit();
					editor.putString("token", json.getString("token"));
					editor.commit();
					Log.d("Login", "Login 200");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Log.d("Create Response", json.toString());
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
		}

	}
}
