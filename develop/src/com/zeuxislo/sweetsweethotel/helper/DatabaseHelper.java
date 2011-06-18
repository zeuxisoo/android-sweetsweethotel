package com.zeuxislo.sweetsweethotel.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.zeuxislo.sweetsweethotel.R;

public class DatabaseHelper {
	
	private Context context;
	private static final String TAG = "DatabaseHelper";
	private SQLiteDatabase sqliteDatabase;
	
	public DatabaseHelper(Context context) {
		this.context = context;
	}

	public SQLiteDatabase open() {
		try {
			//<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
			File database_path = new File(SQLiteDataBaseHelper.DATABASE_PATH);
			if (database_path.exists() == false) {
				database_path.mkdir();
			}
			
			String database_path_with_filename = SQLiteDataBaseHelper.DATABASE_PATH + "/" + SQLiteDataBaseHelper.DATABASE_NAME;
			if (new File(database_path_with_filename).exists() == false) {
				InputStream inputStream = this.context.getResources().openRawResource(R.raw.hotel);
				FileOutputStream fileOutputStream = new FileOutputStream(database_path_with_filename);
				
				byte[] buffer = new byte[8192];
				int read = 0;
				while((read = inputStream.read(buffer)) != -1) {
					fileOutputStream.write(buffer, 0, read);
				}
				
				fileOutputStream.close();
				inputStream.close();
			}
			
			sqliteDatabase = SQLiteDatabase.openOrCreateDatabase(database_path_with_filename, null);
			
			return sqliteDatabase;
		}catch(Exception e) {
			Log.i(TAG, e.getMessage());
		}
		
		return null;
	}
	
	public void close() {
		this.sqliteDatabase.close();
	}
	
}
