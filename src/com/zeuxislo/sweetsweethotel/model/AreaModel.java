package com.zeuxislo.sweetsweethotel.model;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

import com.zeuxislo.sweetsweethotel.entry.AreaEntry;
import com.zeuxislo.sweetsweethotel.helper.DatabaseHelper;

public class AreaModel extends Model {

	private static final String table_name = "area";
	
	public AreaModel(Context context) {
		this.databaseHelper = new DatabaseHelper(context);
		this.sqliteDatabase = databaseHelper.open();
	}
	
	public ArrayList<AreaEntry> findAll() {
		Cursor cursor = sqliteDatabase.query(table_name, new String[] { "id", "name" }, null, null, null, null, null);
		ArrayList<AreaEntry> areaList = new ArrayList<AreaEntry>();
		
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					areaList.add(new AreaEntry(
						cursor.getInt(cursor.getColumnIndex("id")), 
						cursor.getString(cursor.getColumnIndex("name"))
					));
				}while(cursor.moveToNext());	
			}
		}
		
		cursor.close();
		
		return areaList;
	}
	
	public String[] findAllByNameArray() {
		ArrayList<AreaEntry> areaList = this.findAll();
		
		String[] areas = new String[areaList.size()];
        for(int i=0; i<areaList.size(); i++) {
        	areas[i] = areaList.get(i).name;
        }
        
        return areas;
	}
	
}
