package com.example.train_android_contentprovider;

import com.example.ChucVu;
import com.example.MySQLiteHelper;
import com.example.NhanVien;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ThemNhanVienActivity extends Activity {

	EditText nten;
	EditText nma;
	Button nhap;
	Button xoa;
	RadioGroup radioGroup;
	RadioButton nam;
	RadioButton nu;
	MySQLiteHelper db;
	NhanVien oldNhanVien;
	PhongBan phongbanselectedBan;
	Cursor cursor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_them_nhan_vien);
		db = new MySQLiteHelper(this);
		getDataFormMain();
		getFormWidgets();
		addEvent();

	}

	public void getFormWidgets() {
		nten = (EditText) findViewById(R.id.txtTennv);
		nma = (EditText) findViewById(R.id.txtManv);
		radioGroup = (RadioGroup) findViewById(R.id.radioG);
		nhap = (Button) findViewById(R.id.btnNhapnv);
		xoa = (Button) findViewById(R.id.btnXoanhap);
		nam = (RadioButton) findViewById(R.id.radioNam);
		nu = (RadioButton) findViewById(R.id.radioNu);
		
	}
	private void getDataFormMain() {
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("data");
		phongbanselectedBan =    (PhongBan) bundle.getSerializable("Phongban");
	}
	public void addEvent() {
		nhap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				doNhapnv();

			}

		});
		xoa.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				xoa();

			}
		});
	}

	public void doNhapnv() {
		String tennv = nten.getText() + "";
		String manv = nma.getText() + "";
		boolean gioitinh;
		if (nam.isChecked()) {
			gioitinh = true;
		}
		else {
			gioitinh = false;
		}
		NhanVien nv = new NhanVien();
		nv.setName(tennv);
		nv.setCode(manv);
		nv.setGioitinh(gioitinh);
		nv.setChucvu(ChucVu.NhanVien);
		db.addNhanVien(nv, phongbanselectedBan.getMaPhong());
//		Intent intent =getIntent();
//		Bundle bundle = new Bundle();
//		bundle.putSerializable("Nhanvien", nv);
//		intent.putExtra("data", bundle);
//		setResult(MainActivity.resultcode_nhapnvthanhcong, intent);
		finish();
		
	}
	public void xoa() {
		nten.setText("");
		nma.setText("");
		nam.setChecked(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.them_nhan_vien, menu);
		return true;
	}

}
