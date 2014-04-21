package com.example.database.provider;

import android.R.id;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.example.MySQLiteHelper;;

public class MyProvider extends ContentProvider {
	private MySQLiteHelper mDB;
	private static final String PBAN_TABLE = MySQLiteHelper.PBAN_TABLE;
	private static final String NVIEN_TABLE = MySQLiteHelper.NVIEN_TABLE;
	private static final String AUTHORITY = "com.example.database.provider.MyProvider";
	public static final Uri PBAN_URI = Uri.parse("content://" + AUTHORITY + "/"
			+ PBAN_TABLE);
	public static final Uri NVIEN_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + NVIEN_TABLE);

	static final int PHONGBAN = 1;
	static final int PHONGBAN_ID = 2;
	static final int NHANVIEN = 3;
	static final int NHANVIEN_ID = 4;

	private static final UriMatcher sUriMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);
	static {
		sUriMatcher.addURI(AUTHORITY, PBAN_TABLE, PHONGBAN);
		sUriMatcher.addURI(AUTHORITY, PBAN_TABLE + "/#", PHONGBAN_ID);
		sUriMatcher.addURI(AUTHORITY, NVIEN_TABLE, NHANVIEN);
		sUriMatcher.addURI(AUTHORITY, NVIEN_TABLE + "/#", NHANVIEN_ID);
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		mDB = new MySQLiteHelper(getContext());
		return false;

	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		int uriType = sUriMatcher.match(uri);

		SQLiteDatabase sqlDB = mDB.getWritableDatabase();

		long id = 0;
		switch (uriType) {
		case PHONGBAN:
			id = sqlDB.insert(MySQLiteHelper.PBAN_TABLE, null, values);
			break;

		case NHANVIEN:
			id = sqlDB.insert(MySQLiteHelper.NVIEN_TABLE, null, values);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);

		}
		getContext().getContentResolver().notifyChange(uri, null);
		return Uri.parse(PBAN_TABLE + "/" + id);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(MySQLiteHelper.PBAN_TABLE);
		int uriType = sUriMatcher.match(uri);
		switch (uriType) {
		case PHONGBAN_ID:
			queryBuilder.appendWhere(MySQLiteHelper.COL_MAPHONG + " = "
					+ uri.getLastPathSegment());

			break;
		case PHONGBAN:
			break;
		default:
			throw new IllegalArgumentException("Unknown uri");
		}

		Cursor cursor = queryBuilder.query(mDB.getReadableDatabase(),
				projection, selection, selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		String id;
		int uriType = sUriMatcher.match(uri);
		SQLiteDatabase sqlDB = mDB.getWritableDatabase();
		int rowsUpdate = 0;

		switch (uriType) {
		case PHONGBAN:
			rowsUpdate = sqlDB.update(PBAN_TABLE, values, selection,
					selectionArgs);
			break;
		case PHONGBAN_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdate = sqlDB.update(PBAN_TABLE, values,
						MySQLiteHelper.COL_MAPHONG + " = " + id, null);
			} else {
				rowsUpdate = sqlDB.update(PBAN_TABLE, values,
						MySQLiteHelper.COL_MAPHONG + " = " + id + " and "
								+ selection, selectionArgs);
			}
			break;
		case NHANVIEN:
			rowsUpdate = sqlDB.update(NVIEN_TABLE, values, selection,
					selectionArgs);

			break;
		case NHANVIEN_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdate = sqlDB.update(NVIEN_TABLE, values,
						MySQLiteHelper.PBAN_COL_MAPHONG + " = " + id, null);
			} else {
				rowsUpdate = sqlDB.update(NVIEN_TABLE, values,
						MySQLiteHelper.PBAN_COL_MAPHONG + " = " + id + " and "
								+ selection, selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown uri");
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsUpdate;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int uriType = sUriMatcher.match(uri);
		SQLiteDatabase sqlDB = mDB.getWritableDatabase();
		int rowsDeleted = 0;
		switch (uriType) {
		case PHONGBAN:
			rowsDeleted = sqlDB.delete(PBAN_TABLE, selection, selectionArgs);
			break;
		case PHONGBAN_ID:
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(PBAN_TABLE,
						MySQLiteHelper.COL_MAPHONG + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(PBAN_TABLE,
						MySQLiteHelper.COL_MAPHONG + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown uri");
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsDeleted;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	// private class MySQLite extends SQLiteOpenHelper {
	//
	// static final int DATABASE_VERSION = 1;
	//
	// static final String DATABASE_NAME = "QLPhongBan";
	// String PBAN_TABLE = "QLPhongBan_Table";
	// String COL_MAPHONG = "Maphong";
	// String COL_NAME = "Name";
	// String CREATE_PBAN_TABLE = "create table " + PBAN_TABLE + " ("
	// + COL_MAPHONG + " text primary key, " + COL_NAME + " text);";
	//
	// String NVIEN_TABLE = "QLNhanVien_Table";
	// String COL_HOTEN = "Hovaten";
	// String COL_GIOITINH = "Gioitinh";
	// String COL_MASO = "Maso";
	// String COL_CHUCVU = "Chucvu";
	// String PBAN_COL_MAPHONG = "Maphongban";
	// String CREATE_NVIEN_TABLE = "create table " + NVIEN_TABLE + " ("
	// + COL_MASO + " text primary key, " + COL_HOTEN + " text, "
	// + COL_CHUCVU + " text, " + COL_GIOITINH + " integer, "
	// + PBAN_COL_MAPHONG + " text, FOREIGN KEY  (" + PBAN_COL_MAPHONG
	// + ") REFERENCES "
	//
	// + PBAN_TABLE + " (" + COL_MAPHONG + "));";
	//
	// public MySQLite(Context context) {
	// super(context, DATABASE_NAME, null, DATABASE_VERSION);
	// // TODO Auto-generated constructor stub
	// }
	//
	// @Override
	// public void onCreate(SQLiteDatabase db) {
	// // TODO Auto-generated method stub
	// db.execSQL(CREATE_PBAN_TABLE);
	// db.execSQL(CREATE_NVIEN_TABLE);
	//
	// }
	//
	// @Override
	// public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	// {
	// // TODO Auto-generated method stub
	// db.execSQL("drop table " + PBAN_TABLE);
	// db.execSQL("drop table " + NVIEN_TABLE);
	// onCreate(db);
	// }
	// }
}
