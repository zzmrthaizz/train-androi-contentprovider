package com.example.train_android_contentprovider;

import com.example.MySQLiteHelper;
import com.example.train_android_contentprovider.PhongBan;
import com.example.train_android_contentprovider.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
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

public class MainActivity extends Activity {

	EditText maPban;
	EditText tenPban;
	ListView listView;
	Button nhap;
	PhongBan phongbanselected;
	MySQLiteHelper db;
	SimpleCursorAdapter adapter2;
	Cursor cursor;
	long idPhongban;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		db = new MySQLiteHelper(this);
		getWidgets();
		getFromDatabase();
		addEvent();
	}

	private void addEvent() {
		// TODO Auto-generated method stub
		nhap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Creat phongban
				String ten = tenPban.getText() + "";
				String ma = maPban.getText() + "";
				PhongBan phongBan = new PhongBan(ten, ma);
				db.addPhongBan(phongBan);
				adapter2.notifyDataSetChanged();

			}
		});
		registerForContextMenu(listView);
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				idPhongban = arg3;
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
			// dothemnv();
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

	private void doxoa() {
		// TODO Auto-generated method stub
		AlertDialog.Builder xoa = new AlertDialog.Builder(MainActivity.this);
		xoa.setTitle("Xoa du lieu");
		xoa.setMessage("Ban co chac muon xoa phong ban nay");
		xoa.setNegativeButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				phongbanselected = new PhongBan();
				phongbanselected.setMaPhong(String.valueOf(idPhongban));
				Toast.makeText(MainActivity.this, String.valueOf(idPhongban),
						Toast.LENGTH_LONG).show();
				db.deletePhongBan(phongbanselected);
				adapter2.notifyDataSetChanged();

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

		String[] from = { db.COL_NAME };
		int[] to = { R.id.pb };
		cursor = db.getPhongBan();
		adapter2 = new SimpleCursorAdapter(this, R.layout.item_phongban,
				cursor, from, to);
		listView.setAdapter(adapter2);

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

}
