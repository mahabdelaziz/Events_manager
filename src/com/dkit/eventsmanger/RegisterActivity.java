package com.dkit.eventsmanger;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.dkit.eventsmanger.asynctasks.URLs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity{
	
	TextView email, password,rePassword;
	Spinner year, courseName;
	ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	final String LOGIN_URL = URLs.url+"/users.php";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		email = (TextView)findViewById(R.id.regsiter_email);
		password = (TextView)findViewById(R.id.regsiter_password);
		rePassword = (TextView)findViewById(R.id.regsiter_re_password);
		
		year = (Spinner)findViewById(R.id.register_year);
//		year.getSelectedItemPosition();
		courseName = (Spinner)findViewById(R.id.register_course);
		Button regt = (Button)findViewById(R.id.register_finish);
		regt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String passwordStr = password.getText().toString();
				String rePasswordStr = rePassword.getText().toString();
				if(passwordStr.compareTo(rePasswordStr)==0){
					new RegisterTask().execute();
				}else{
					Toast.makeText(getApplicationContext(), "Password not match", Toast.LENGTH_LONG).show();

				}
			}
		});
	}
	
	
	
	class RegisterTask extends AsyncTask<String, String, String> {
		boolean login = false;
		JSONObject json ;
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(RegisterActivity.this);
			pDialog.setMessage("Register in process....");
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
			params.add(new BasicNameValuePair("type", "register"));
			params.add(new BasicNameValuePair("email", emailStr));
			params.add(new BasicNameValuePair("password", passwordStr));
			params.add(new BasicNameValuePair("course", courseName.getSelectedItemId()+""));
			params.add(new BasicNameValuePair("year", year.getSelectedItemId()+""));
			json = jsonParser.makeHttpRequest(LOGIN_URL, "GET",
					params);
			return null;
		}

		protected void onPostExecute(String file_url) {
			try {
				if (json.getString("status").compareTo("404") == 0) {
					Toast.makeText(getApplicationContext(),
							json.getString("error"), Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getApplicationContext(), "User Created",
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			pDialog.dismiss();
		}

	}
}
