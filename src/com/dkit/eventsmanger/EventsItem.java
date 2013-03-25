package com.dkit.eventsmanger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EventsItem extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;

	public EventsItem(Context context, String[] objects) {
		super(context, R.layout.event_item, objects);
		this.context = context;
		this.values = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.event_item, parent, false);
		TextView tv = ((TextView)rowView.findViewById(R.id.item_event_date));
		tv.setText(values[position]);
		return rowView;
	}

}
