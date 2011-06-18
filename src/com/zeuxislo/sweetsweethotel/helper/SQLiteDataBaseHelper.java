package com.zeuxislo.sweetsweethotel.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class SQLiteDataBaseHelper extends SQLiteOpenHelper {

	public static final String DATABASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SweetSweetHotel";
	public static final String DATABASE_NAME = "hotel.db";
	public static final int DATABASE_VERSION = 1;
	
	public SQLiteDataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
