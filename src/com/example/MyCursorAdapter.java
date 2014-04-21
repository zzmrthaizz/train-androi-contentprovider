package com.example;

import java.util.zip.Inflater;

import com.example.train_android_contentprovider.PhongBan;

import com.example.train_android_contentprovider.R;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

public class MyCursorAdapter extends CursorAdapter{

	LayoutInflater inflater;
	public MyCursorAdapter(Context context, Cursor c) {
		super(context, c);
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(context);
	}

	@Override
	public void bindView(View arg0, Context arg1, Cursor arg2) {
		// TODO Auto-generated method stub
		TextView phongban = (TextView) arg0.findViewById(R.id.pb);
		TextView truongphong = (TextView) arg0.findViewById(R.id.tphong);
		TextView phophong = (TextView) arg0.findViewById(R.id.pphong);
		phongban.setText(arg2.getString(0)+"-"+arg2.getString(1));
	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.item_phongban, arg2, false);
	}

}
