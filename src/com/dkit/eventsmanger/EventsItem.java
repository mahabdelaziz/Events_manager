package com.dkit.eventsmanger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class EventsItem extends SimpleAdapter implements OnClickListener {
	private ArrayList<HashMap<String, String>> list;
	int pos = 0;
	String name = "";
	Context myContext;
	public EventsItem(Context context,
			ArrayList<HashMap<String, String>> eventsList, int resource,
			String[] from, int[] to) {
		
		super(context, eventsList, resource, from, to);
		myContext = context;
		this.list = eventsList;
		pos = this.getCount();
		Log.d("Edit", pos + "");

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		view.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View view) {
		TextView tv = (TextView) view.findViewById(R.id.item_event_id);
		name = tv.getText().toString();
		Intent i = new Intent(myContext,
				ReadEventActivity.class);
		i.putExtra("id", name);
		myContext.startActivity(i);
		Log.d("data", name);

	}

}