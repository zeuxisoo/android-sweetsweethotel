package com.zeuxislo.sweetsweethotel.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zeuxislo.sweetsweethotel.R;
import com.zeuxislo.sweetsweethotel.entry.HotelEntry;

public class HotelItemAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<HotelEntry> hotelList;
	
	public HotelItemAdapter(Context context, ArrayList<HotelEntry> hotelList) {
		this.context = context;
		this.hotelList = hotelList;
	}
	
	@Override
	public int getCount() {
		return this.hotelList.size();
	}

	@Override
	public Object getItem(int position) {
		return this.hotelList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {		
		if (view == null) {
			view = LayoutInflater.from(this.context).inflate(R.layout.hotel_list_item, null);
		}
		
		HotelEntry hotelEntry = this.hotelList.get(position);
		
		TextView name = (TextView) view.findViewById(R.id.hotel_list_item_name);
		TextView telphone = (TextView) view.findViewById(R.id.hotel_list_item_telephone);
		TextView address = (TextView) view.findViewById(R.id.hotel_list_item_address);
		
		name.setText(hotelEntry.name);
		telphone.setText(hotelEntry.telephone);
		address.setText(hotelEntry.address);
		
		return view;
	}

}
