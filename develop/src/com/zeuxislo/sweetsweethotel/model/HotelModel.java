package com.zeuxislo.sweetsweethotel.model;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

import com.zeuxislo.sweetsweethotel.entry.HotelEntry;
import com.zeuxislo.sweetsweethotel.helper.DatabaseHelper;

public class HotelModel extends Model {

	private static final String table_name = "hotel";
	
	public HotelModel(Context context) {
		this.databaseHelper = new DatabaseHelper(context);
		this.sqliteDatabase = databaseHelper.open();
	}
	
	public ArrayList<HotelEntry> findAll() {
		Cursor cursor = sqliteDatabase.query(table_name, new String[] { "id", "area_id", "name", "address", "telephone", "lat", "lng" }, null, null, null, null, null);
		ArrayList<HotelEntry> areaList = new ArrayList<HotelEntry>();
		
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					areaList.add(new HotelEntry(
						cursor.getInt(cursor.getColumnIndex("id")), 
						cursor.getInt(cursor.getColumnIndex("area_id")),
						cursor.getString(cursor.getColumnIndex("name")),
						cursor.getString(cursor.getColumnIndex("address")),
						cursor.getString(cursor.getColumnIndex("telephone")),
						cursor.getString(cursor.getColumnIndex("lat")),
						cursor.getString(cursor.getColumnIndex("lng"))
					));
				}while(cursor.moveToNext());
			}
		}
		
		cursor.close();
		
		return areaList;
	}
	
	public ArrayList<HotelEntry> findByAreaId(int area_id) {
		Cursor cursor = sqliteDatabase.query(
			table_name, 
			new String[] { "id", "area_id", "name", "address", "telephone", "lat", "lng" }, 
			"area_id = ?",
			new String[] { String.valueOf(area_id) }, 
			null, null, null
		);
		ArrayList<HotelEntry> hotelList = new ArrayList<HotelEntry>();

		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					hotelList.add(new HotelEntry(
						cursor.getInt(cursor.getColumnIndex("id")), 
						cursor.getInt(cursor.getColumnIndex("area_id")),
						cursor.getString(cursor.getColumnIndex("name")),
						cursor.getString(cursor.getColumnIndex("address")),
						cursor.getString(cursor.getColumnIndex("telephone")),
						cursor.getString(cursor.getColumnIndex("lat")),
						cursor.getString(cursor.getColumnIndex("lng"))
					));
				}while(cursor.moveToNext());
			}
		}
		
		cursor.close();

		return hotelList;
	}
	
	public String[] findByAreaIdAndNameArray(int area_id) {
		ArrayList<HotelEntry> areaList = this.findByAreaId(area_id);
		
		String[] areas = new String[areaList.size()];
        for(int i=0; i<areaList.size(); i++) {
        	areas[i] = areaList.get(i).name;
        }
        
        return areas;
	}
	
}
