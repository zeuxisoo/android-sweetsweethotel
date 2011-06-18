package com.zeuxislo.sweetsweethotel.model;

import android.database.sqlite.SQLiteDatabase;

import com.zeuxislo.sweetsweethotel.helper.DatabaseHelper;

public class Model {

	protected DatabaseHelper databaseHelper;
	protected SQLiteDatabase sqliteDatabase;	
	
	public void close() {
		this.databaseHelper.close();
		this.sqliteDatabase.close();
	}
	
}
