package com.example;

import java.util.LinkedList;
import java.util.List;

import android.R.integer;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.database.provider.MyProvider;
import com.example.train_android_contentprovider.PhongBan;

public class MySQLiteHelper extends SQLiteOpenHelper {

	ContentResolver myCR;
	static int DATABASE_VERSION = 1;

	static String DATABASE_NAME = "QLPhongBan";
	public static final String PBAN_TABLE = "QLPhongBan_Table";
	public static final String COL_MAPHONG = "_id";
	public static final String COL_NAME = "Name";
	String CREATE_PBAN_TABLE = "create table " + PBAN_TABLE + " ("
			+ COL_MAPHONG + " text primary key, " + COL_NAME + " text);";

	public static final String NVIEN_TABLE = "QLNhanVien_Table";
	String COL_HOTEN = "Hovaten";
	String COL_GIOITINH = "Gioitinh";
	String COL_MASO = "Maso";
	String COL_CHUCVU = "Chucvu";
	public static final String PBAN_COL_MAPHONG = "Maphongban";
	String CREATE_NVIEN_TABLE = "create table " + NVIEN_TABLE + " (" + COL_MASO
			+ " text primary key, " + COL_HOTEN + " text, " + COL_CHUCVU
			+ " text, " + COL_GIOITINH + " integer, " + PBAN_COL_MAPHONG
			+ " text, FOREIGN KEY (" + PBAN_COL_MAPHONG + ") REFERENCES "

			+ PBAN_TABLE + " (" + COL_MAPHONG + "));";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
		myCR = context.getContentResolver();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_PBAN_TABLE);
		db.execSQL(CREATE_NVIEN_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table " + PBAN_TABLE);
		db.execSQL("drop table" + NVIEN_TABLE);
		onCreate(db);

	}

	// Command PBAN_TABLE
	public void addPhongBan(PhongBan phongBan) {
		// SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(COL_NAME, phongBan.getNamePhong());
		contentValues.put(COL_MAPHONG, phongBan.getMaPhong());
		// db.insert(PBAN_TABLE, null, contentValues);
		// db.close();
		myCR.insert(MyProvider.PBAN_URI, contentValues);
	}

	public Cursor getPhongBan() {
		SQLiteDatabase db = this.getReadableDatabase();
		String[] allcolumes = { COL_MAPHONG, COL_NAME };
		Cursor cursor = db.query(PBAN_TABLE, allcolumes, null, null, null,
				null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	public List<PhongBan> getAllPhongBans() {
		List<PhongBan> listPhongBans = new LinkedList<PhongBan>();
		String query = "select * from " + PBAN_TABLE;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		PhongBan pb;
		if (cursor != null && cursor.moveToFirst()) {
			do {
				pb = new PhongBan();
				pb.setMaPhong(cursor.getString(0));
				pb.setNamePhong(cursor.getString(1));
				listPhongBans.add(pb);
			} while (cursor.moveToNext());
		}

		return listPhongBans;
	}

	public int deletePhongBan(PhongBan phongBan) {
		String selection = "_id = '"+phongBan.getMaPhong()+"'";
		int rowDeleted = myCR.delete(MyProvider.PBAN_URI, selection, null);
		return rowDeleted;
		// db.delete(PBAN_TABLE, COL_MAPHONG + " = ?",
		// new String[] { String.valueOf(phongBan.getMaPhong()) });
		// db.delete(NVIEN_TABLE, PBAN_COL_MAPHONG + " = ?",
		// new String[] { String.valueOf(phongBan.getMaPhong()) });
		// db.close();
	}

	public void addNhanVien(NhanVien nVien, String maPhong) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(COL_MASO, nVien.getCode());
		contentValues.put(COL_HOTEN, nVien.getName());
		contentValues.put(COL_CHUCVU, nVien.getChucvu().getChucVu());
		if (nVien.isGioitinh()) {
			contentValues.put(COL_GIOITINH, 1);
		} else {
			contentValues.put(COL_GIOITINH, 0);
		}
		contentValues.put(PBAN_COL_MAPHONG, maPhong);
		myCR.insert(MyProvider.NVIEN_URI, contentValues);
	}

	public void updateNhanVien(NhanVien nvold, NhanVien nvnew) {
		SQLiteDatabase db = this.getWritableDatabase();
		if (nvold != null) {
			String a = String.valueOf(nvold.getCode());
			// myCR.delete(MyProvider.NVIEN_URI, COL_MASO + " = ?",
			// new String[] { String.valueOf(nvold.getCode())});
			ContentValues contentValues = new ContentValues();
			contentValues.put(COL_MASO, a);
			contentValues.put(COL_HOTEN, nvnew.getName());
			contentValues.put(COL_CHUCVU, nvnew.getChucvu().getChucVu());
			if (nvnew.isGioitinh()) {
				contentValues.put(COL_GIOITINH, 1);
			} else {
				contentValues.put(COL_GIOITINH, 0);
			}
			myCR.update(MyProvider.NVIEN_URI, contentValues, COL_MASO + " = ?",
					new String[] { String.valueOf(nvold.getCode()) });

		}

	}
}

// public ArrayList<NhanVien> getListNhanVien(PhongBan phongBan) {
// ArrayList<NhanVien> listNhanViens = new ArrayList<NhanVien>();
// String query = "select * from " + NVIEN_TABLE + " left join "
// + PBAN_TABLE + " where " + NVIEN_TABLE + "." + PBAN_COL_MAPHONG
// + " = " + PBAN_TABLE + "." + COL_MAPHONG + " and "
// + NVIEN_TABLE + "." + PBAN_COL_MAPHONG + " = " + "'"
// + phongBan.getMaPhong() + "'";
// // // + " where "
// // + PBAN_TABLE + "." + COL_MAPHONG + " = "
// // + phongBan.getMaPhong();
// SQLiteDatabase db = this.getReadableDatabase();
// Cursor cursor = db.rawQuery(query, null);
// NhanVien nVien;
// if (cursor != null && cursor.moveToFirst()) {
// do {
// nVien = new NhanVien();
// nVien.setCode(cursor.getString(0));
// nVien.setName(cursor.getString(1));
// if (cursor.getInt(3) == 1) {
// nVien.setGioitinh(true);
// } else {
// nVien.setGioitinh(false);
// }
// if (cursor.getString(2).equals("Truong phong")) {
// nVien.setChucvu(ChucVu.TruongPhong);
// } else if (cursor.getString(2).equals("Pho phong")) {
// nVien.setChucvu(ChucVu.PhoPhong);
// } else {
// nVien.setChucvu(ChucVu.NhanVien);
// }
// listNhanViens.add(nVien);
// } while (cursor.moveToNext());
// }
// return listNhanViens;
// }
//
//
//
// // Command Nhan vien

// public void deleteNhanVien(NhanVien nv) {
// SQLiteDatabase dbDatabase = this.getWritableDatabase();
// dbDatabase.delete(NVIEN_TABLE, COL_MASO + " = ?",
// new String[] { String.valueOf(nv.getCode()) });
// dbDatabase.close();
// }
//

// }

// public NhanVien getNhanVien(String name) {
// SQLiteDatabase db = this.getReadableDatabase();
// String[] colume = { ID, NAME, CHUCVU };
// Cursor cursor = db.query(TABLE_NAME, colume, null, null, null, null,
// null);
// NhanVien nVien = new NhanVien();
// nVien.setName(cursor.getString(1));
// return nVien;
//
//
// }

// public Cursor getAllNhanVien(String table) {
// SQLiteDatabase db = this.getReadableDatabase();
// String[] colume = { ID, NAME, CHUCVU };
// Cursor cursor = db.query(TABLE_NAME, colume, null, null, null, null,
// null);
// return cursor;
// }

