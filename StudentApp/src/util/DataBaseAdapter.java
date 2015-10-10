package util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseAdapter {
	private static int VERSION = 1;
	private static String TABLE_NAME = "detail";
	private static String DB_NAME = "student";
	private static String ID = "_ID";
	private static String NAME = "name";
	private static String ROLL = "roll";
	private static String EMAIL = "email";
	private static String PASSWORD = "password";
	private static String MOBILE = "mobile";
	private static String AVATAR = "avatar";
	private static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
			+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME
			+ " VARCHAR(50)," + ROLL + " INTEGER," + EMAIL + " VARCHAR(100),"
			+ PASSWORD + " VARCHAR(20)," + MOBILE + " INTEGER," + AVATAR
			+ " INTEGER)";
	private static final String DROP_TABLE = "DROP TABLE IF EXISTS "
			+ TABLE_NAME;
	private ContentValues cv;
	private Helper help;
	private int avatar;

	public DataBaseAdapter(Context context) {
		help = new Helper(context);
	}

	public long insertData(String name, long roll, String email, String pass,
			long mobile, int av) {
		cv = new ContentValues();
		SQLiteDatabase db = help.getWritableDatabase();
		cv.put(NAME, name);
		cv.put(ROLL, roll);
		cv.put(EMAIL, email);
		cv.put(PASSWORD, pass);
		cv.put(MOBILE, mobile);
		cv.put(AVATAR, av);
		long id = db.insert(TABLE_NAME, null, cv);
		db.close();
		return id;
	}

	public boolean updatePic(int num, long roll) {
		cv = new ContentValues();
		SQLiteDatabase db = help.getWritableDatabase();
		cv.put(AVATAR, num);
		return db.update(TABLE_NAME, cv, ROLL + "=" + roll, null) > 0;
	}

	public String matchPassword(long roll, String pass) {
		String password = "";
		// String[] cols={PASSWORD};
		SQLiteDatabase db = help.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, null, ROLL + "=" + roll, null,
				null, null, null, null);
		if (cursor.getCount() < 1) // UserName Not Exist
		{
			cursor.close();
			return "NOT EXIST";
		}
		cursor.moveToFirst();
		password = cursor.getString(cursor.getColumnIndex(PASSWORD));
		cursor.close();
		return password;

	}

	public String getDetail(long roll) {
		String s = "";
		// String[] cols={NAME,ROLL,EMAIL,MOBILE,AVATAR};
		SQLiteDatabase db = help.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, null, ROLL + "=" + roll, null,
				null, null, null, null);
		if (cursor.getCount() < 1) {
			cursor.close();
			return "NOT EXIST";
		}
		cursor.moveToFirst();
		s = "Name : " + cursor.getString(cursor.getColumnIndex(NAME)) + " \n";
		s += "Email : " + cursor.getString(cursor.getColumnIndex(EMAIL)) + "\n";
		s += "Mobile : " + cursor.getString(cursor.getColumnIndex(MOBILE))
				+ "\n";
		setAvatar(Integer.parseInt(cursor.getString(cursor
				.getColumnIndex(AVATAR))));
		cursor.close();
		return s;

	}

	public String viewAll() {
		String[] columns = { ID, NAME, ROLL, EMAIL, PASSWORD, MOBILE, AVATAR };
		SQLiteDatabase db = help.getReadableDatabase();
		Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);
		String s = "";
		if (c.moveToFirst()) {
			do {
				s += c.getString(0) + " ";
				s += c.getString(1) + " ";
				s += c.getString(2) + " ";
				s += c.getString(3) + " ";
				s += c.getString(4) + " ";
				s += c.getString(5) + " ";
				s += c.getString(6) + "\n ";
			} while (c.moveToNext());
		}
		return s;
	}

	public int getAvatar() {
		return avatar;
	}

	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}

	class Helper extends SQLiteOpenHelper {

		public Helper(Context context) {
			super(context, DB_NAME, null, VERSION);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL(CREATE_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			try {
				db.execSQL(DROP_TABLE);
				onCreate(db);
			} catch (SQLException e) {
			}
		}

	}

}
