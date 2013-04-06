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
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MyEventsActivity extends ListActivity {

	String token;
	ArrayList<HashMap<String, String>> eventsList;
	String Qtype,query ;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		eventsList = new ArrayList<HashMap<String, String>>();

		this.setListAdapter(new SimpleAdapter(MyEventsActivity.this,
				eventsList, R.layout.event_item, null, null));
		SharedPreferences settings = getSharedPreferences("main", 0);
		token = settings.getString("token", "noLogged");

		Intent intent = getIntent();
		// int type = intent.getIntExtra("type", -1);
		// if(type == -1 )
		Qtype = intent.getStringExtra("type");
		query = intent.getStringExtra("query");
//		new LoginTask().execute(intent.getStringExtra("type"),
//				intent.getStringExtra("query"));
	}

	@Override
	protected void onResume() {
		MyEventsActivity.this.setListAdapter(null);
		new LoginTask().execute(Qtype,
				query);
		Log.d("data", "OnResume");
		super.onResume();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i;
		switch (item.getItemId()) {
		case R.id.search_event:
			i = new Intent(MyEventsActivity.this, SearchActivity.class);
			startActivity(i);
			break;

		case R.id.create_event:
			i = new Intent(MyEventsActivity.this, CreateEventActivity.class);
			startActivity(i);
			break;
		default:
			break;
		}
		return true;
	}

	ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	final String LOGIN_URL = URLs.url+"/events.php";

	class LoginTask extends AsyncTask<String, String, String> {
		boolean login = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MyEventsActivity.this);
			pDialog.setMessage("Login in process....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
			eventsList.clear();
		}

		@Override
		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("type", args[0]));
			params.add(new BasicNameValuePair("name", args[1]));
			params.add(new BasicNameValuePair("token", token));
			Log.d("MYTOKEN", token);
			JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "GET",
					params);
			Log.d("json", json.toString());
			try {
				JSONArray events = json.getJSONArray("events");
				for (int i = 0; i < events.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject event = events.getJSONObject(i);
					map.put("name", event.getString("name"));
					Log.d("data", event.getString("name"));
					// the rest goes here
					eventsList.add(map);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String file_url) {
			
			MyEventsActivity.this
					.setListAdapter(new EventsItem(MyEventsActivity.this,
							eventsList, R.layout.event_item,
							new String[] { "name" },
							new int[] { R.id.item_event_name }));
			pDialog.dismiss();
		}

	}
}
