package com.dkit.eventsmanger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SearchActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_activity);
		Button searchBtn = (Button)findViewById(R.id.search_btn);
		searchBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				TextView tv =(TextView)findViewById(R.id.search_name);
				String query = tv.getText().toString();
				Intent i = new Intent(SearchActivity.this, MyEventsActivity.class);
				i.putExtra("type", "search");
				i.putExtra("query", query);
				startActivity(i);
			}
		});
	}
	
	
	
}
