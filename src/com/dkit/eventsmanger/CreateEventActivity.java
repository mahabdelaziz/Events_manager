package com.dkit.eventsmanger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
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
import android.widget.DatePicker;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class CreateEventActivity extends Activity {
	ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	final String LOGIN_URL = URLs.url + "/events.php";
	Button createBtn;
	TextView eventName, spaces, description, room;
	Spinner depart, building;
	DatePicker date;
	TimePicker time;
	String token;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_event);
		createBtn = (Button) findViewById(R.id.event_create_btn);

		eventName = (TextView) findViewById(R.id.event_create_name);
		spaces = (TextView) findViewById(R.id.event_create_space);
		description = (TextView) findViewById(R.id.event_create_description);
		room = (TextView) findViewById(R.id.event_create_room);
		date = (DatePicker) findViewById(R.id.event_create_date);
		time = (TimePicker) findViewById(R.id.event_create_time);

		depart = (Spinner) findViewById(R.id.event_create_department);
		building = (Spinner) findViewById(R.id.event_create_building);
		depart.getSelectedItem().toString();

		time.setIs24HourView(true);
		final String date_time = date.getYear() + "-"
				+ (date.getMonth() + 1) + "-" + date.getDayOfMonth() + " "
				+ time.getCurrentHour() + ":" + time.getCurrentMinute() + ":00";
		SharedPreferences settings = getSharedPreferences("main", 0);
		token = settings.getString("token", "noLogged");
		createBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new CreateEventTask().execute(
						eventName.getText().toString(),
						spaces.getText().toString(),
						depart.getSelectedItem().toString(),
						building.getSelectedItem().toString(),
						description.getText().toString(),
						date_time,
						room.getText().toString() );
				// time.get
				// Log.d("data",date.);
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
			params.add(new BasicNameValuePair("token", token));
			params.add(new BasicNameValuePair("name", args[0]));
			params.add(new BasicNameValuePair("max_space", args[1]));
			params.add(new BasicNameValuePair("department", args[2]));
			params.add(new BasicNameValuePair("building", args[3]));
			params.add(new BasicNameValuePair("description", args[4]));
			params.add(new BasicNameValuePair("date_time", args[5]));
			params.add(new BasicNameValuePair("room", args[6]));

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
