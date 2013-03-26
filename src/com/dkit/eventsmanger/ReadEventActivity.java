package com.dkit.eventsmanger;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ReadEventActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_view);
		new LoginTask().execute();
	}

	ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	final String LOGIN_URL = "http://10.0.2.2/events/events.php";
	String name = "";
	class LoginTask extends AsyncTask<String, String, String> {
		boolean login = false;

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ReadEventActivity.this);
			pDialog.setMessage("Login in process....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
			// String emailStr = email.getText().toString();
			// String passwordStr = password.getText().toString();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("type", "read"));
			params.add(new BasicNameValuePair("id", "2"));
			params.add(new BasicNameValuePair("token",
					"4167d27aebe5b9cb0e6b765563f85876"));
			// params.add(new BasicNameValuePair("password", passwordStr));

			JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "GET",
					params);
			Log.d("here", "here");
			Log.d("Create Response", json.toString());
			try {
				if (json.getInt("status") != 200) {
					Log.d("Login", "Error");
				} else {
					name = json.getJSONObject("event").getString("created_at");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
			TextView tv= (TextView)ReadEventActivity.this.findViewById(R.id.event_view_name);
			tv.setText(name);
		}

	}
}
