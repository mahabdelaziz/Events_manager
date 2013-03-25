package com.dkit.eventsmanger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class CreateEventActivity extends Activity{
	ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	final String LOGIN_URL = "http://10.0.2.2/events/events.php";
	Button createBtn;
	TextView eventName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_event);
		createBtn = (Button)findViewById(R.id.event_create_btn);
		eventName = (TextView)findViewById(R.id.event_create_name);
		createBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new CreateEventTask().execute(eventName.getText().toString());
			}
		});
	}
	
	class CreateEventTask extends AsyncTask<String, String, String> {
		boolean login = false;

		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(CreateEventActivity.this);
			pDialog.setMessage("Login in process....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("type", "create"));
			params.add(new BasicNameValuePair("token", "bdbe1e68895adb037646c60ab5850674"));
			params.add(new BasicNameValuePair("name", args[0]));
			
			JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "GET",
					params);
			Log.d("json", json.toString());
			
			return "";
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
		}

	}

}
