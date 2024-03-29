package source;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler {
		/////////////////////////////////////////////////////////////////////
		//	Constants & Data
		/////////////////////////////////////////////////////////////////////
		// For logging:
		private static final String TAG = "DBAdapter";
		
		// DB Fields
		public static final String KEY_ROWID = "_id";
		public static final int COL_ROWID = 0;

		
		public static final String KEY_PLAYERNAME = "playerName";
		public static final String KEY_SCORE = "score";
		public static final String KEY_TIME = "time";
		
		public static final int COL_PLAYERNAME = 1;
		public static final int COL_SCORE = 2;
		public static final int COL_TIME = 3;

		public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_PLAYERNAME, KEY_SCORE, KEY_TIME};

		
		public static final String DATABASE_NAME = "Highscores";
		public static final String DATABASE_TABLE = "mainTable";
		public static final int DATABASE_VERSION = 1;
		
		private static final String DATABASE_CREATE_SQL = 
				"create table " + DATABASE_TABLE 
				+ " (" + KEY_ROWID + " integer primary key autoincrement, "
				+ KEY_PLAYERNAME + " text not null, "
				+ KEY_SCORE + " integer not null, "
				+ KEY_TIME + " float not null"
				+ ");";
		
		private final Context context;
		private DatabaseHelper myDBHelper;
		private SQLiteDatabase db;

		/////////////////////////////////////////////////////////////////////
		//	Public methods:
		/////////////////////////////////////////////////////////////////////
		
		public DatabaseHandler (Context ctx) {
			this.context = ctx;
			myDBHelper = new DatabaseHelper(context);
		}
		
		// Open the database connection.
		public DatabaseHandler open() {
			db = myDBHelper.getWritableDatabase();
			return this;
		}
		
		// Close the database connection.
		public void close() {
			myDBHelper.close();
		}
		
		// Add a new set of values to the database.
		public long insertRow(String playerName, int score, float time) {
			ContentValues initialValues = new ContentValues();
			initialValues.put(KEY_PLAYERNAME, playerName);
			initialValues.put(KEY_SCORE, score);
			initialValues.put(KEY_TIME, time);
			
			return db.insert(DATABASE_TABLE, null, initialValues);
		}
		
		// Delete a row from the database, by rowId (primary key)
		public boolean deleteRow(long rowId) {
			String where = KEY_ROWID + "=" + rowId;
			return db.delete(DATABASE_TABLE, where, null) != 0;
		}
		
		public void deleteAll() {
			Cursor c = getAllRows();
			long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
			if (c.moveToFirst()) {
				do {
					deleteRow(c.getLong((int) rowId));				
				} while (c.moveToNext());
			}
			c.close();
		}
		
		// Return all data in the database.
		public Cursor getAllRows() {
			String where = null;
			Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
								where, null, null, null, null, null);
			if (c != null) {
				c.moveToFirst();
			}
			return c;
		}

		// Get a specific row (by rowId)
		public Cursor getRow(long rowId) {
			String where = KEY_ROWID + "=" + rowId;
			Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
							where, null, null, null, null, null);
			if (c != null) {
				c.moveToFirst();
			}
			return c;
		}
		
		// Change an existing row to be equal to new data.
		public boolean updateRow(long rowId, String playerName, int score, float time) {
			String where = KEY_ROWID + "=" + rowId;

			ContentValues newValues = new ContentValues();
			newValues.put(KEY_PLAYERNAME, playerName);
			newValues.put(KEY_SCORE, score);
			newValues.put(KEY_TIME, time);
			
			return db.update(DATABASE_TABLE, newValues, where, null) != 0;
		}
		
		
		
		/////////////////////////////////////////////////////////////////////
		//	Private Helper Classes:
		/////////////////////////////////////////////////////////////////////
		
		/**
		 * Private class which handles database creation and upgrading.
		 * Used to handle low-level database access.
		 */
		private static class DatabaseHelper extends SQLiteOpenHelper
		{
			DatabaseHelper(Context context) {
				super(context, DATABASE_NAME, null, DATABASE_VERSION);
			}

			@Override
			public void onCreate(SQLiteDatabase _db) {
				_db.execSQL(DATABASE_CREATE_SQL);			
			}

			@Override
			public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
				Log.w(TAG, "Upgrading application's database from version " + oldVersion
						+ " to " + newVersion + ", which will destroy all old data!");
				
				// Destroy old database:
				_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
				
				// Recreate new database:
				onCreate(_db);
			}
		}
}