package com.dkit.eventsmanger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;

public class EventsItem extends SimpleAdapter implements OnClickListener {
	private ArrayList<HashMap<String, String>> list;
	int pos=0;
	String name = "";

	public EventsItem(Context context,
			ArrayList<HashMap<String, String>> eventsList, int resource,
			String[] from, int[] to) {
		super(context, eventsList, resource, from, to);
		this.list = eventsList;
		pos = this.getCount();
		Log.d("Edit", pos+"");
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		Button edit = (Button) view.findViewById(R.id.item_event_edit);
		edit.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
//		Log.d("Edit", pos + "");

	}

}