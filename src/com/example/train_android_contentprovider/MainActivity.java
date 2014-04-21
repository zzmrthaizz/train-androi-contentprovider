package com.example.train_android_contentprovider;

import com.example.MySQLiteHelper;
import com.example.database.provider.MyProvider;
import com.example.train_android_contentprovider.PhongBan;
import com.example.train_android_contentprovider.R;

import android.os.Bundle;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Loader;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements LoaderCallbacks<Cursor> {

	EditText maPban;
	EditText tenPban;
	ListView listView;
	Button nhap;
	PhongBan phongbanselected;
	MySQLiteHelper db;

	LoaderCallbacks<Cursor> mCallbacks = this;
	int LOADER_ID = 1;
	SimpleCursorAdapter adapter2;
	LoaderManager loaderManager = getLoaderManager();
	CursorLoader cursorLoader;
	private static String TAG = "CursorLoader";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		db = new MySQLiteHelper(this);

		String[] from = { db.COL_MAPHONG, db.COL_NAME };
		int[] to = { R.id.pb };
		adapter2 = new SimpleCursorAdapter(this, R.layout.item_phongban, null,
				from, to, 0);
		getWidgets();
		listView.setAdapter(adapter2);
		loaderManager.initLoader(LOADER_ID, null, mCallbacks);
		// loaderManager = getLoaderManager();
		// startManagingCursor(cursor);

		addEvent();
		loaderManager.restartLoader(LOADER_ID, null, mCallbacks);
	}

	private void addEvent() {
		// TODO Auto-generated method stub
		nhap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Creat phongban
				doThemPB();
			}


		});

		registerForContextMenu(listView);
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Cursor a = (Cursor) arg0.getItemAtPosition(arg2);
				phongbanselected = new PhongBan();
				phongbanselected.setMaPhong(a.getString(0));
				phongbanselected.setNamePhong(a.getString(1));
				return false;
			}
		});
		
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.context_menu_phongban, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
		case R.id.themnv:
			dothemnv();
			break;
		case R.id.dsnhanvien:
			// dohiends();
			break;
		case R.id.lapchucvu:
			// dolapchucvu();
			break;
		case R.id.xoa:
			doxoa();
			break;
		}
		return super.onContextItemSelected(item);
	}

	private void dothemnv() {
		Bundle bundle = new Bundle();
		bundle.putSerializable("Phongban", phongbanselected);
		Intent intent = new Intent(this, ThemNhanVienActivity.class);
		intent.putExtra("data", bundle);
		startActivityForResult(intent, 1);
	}

	private void doxoa() {
		// TODO Auto-generated method stub
		AlertDialog.Builder xoa = new AlertDialog.Builder(MainActivity.this);
		xoa.setTitle("Xoa du lieu");
		xoa.setMessage("Ban co chac muon xoa phong ban nay");
		xoa.setNegativeButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				db.deletePhongBan(phongbanselected);
			}
		});
		xoa.setPositiveButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		xoa.show();

	}

	private void getFromDatabase() {
		// TODO Auto-generated method stub

		// loaderManager.initLoader(1, null, this);

	}

	private void getWidgets() {
		// TODO Auto-generated method stub
		maPban = (EditText) findViewById(R.id.txtMaphong);
		tenPban = (EditText) findViewById(R.id.txtTenphong);
		nhap = (Button) findViewById(R.id.btnnhap);
		listView = (ListView) findViewById(R.id.listPhong);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub
		String[] project = { db.COL_MAPHONG, db.COL_NAME };
		cursorLoader = new CursorLoader(MainActivity.this, MyProvider.PBAN_URI,
				project, null, null, null);
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// TODO Auto-generated method stub
		adapter2.swapCursor(arg1);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		adapter2.swapCursor(null);

	}
	private void doThemPB() {
		// TODO Auto-generated method stub
		String ten = tenPban.getText() + "";
		String ma = maPban.getText() + "";
		PhongBan phongBan = new PhongBan(ten, ma);
		db.addPhongBan(phongBan);
		loaderManager.restartLoader(LOADER_ID, null, mCallbacks);
	}
}
