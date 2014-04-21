package com.example;

import java.io.Serializable;

public class NhanVien implements Serializable {
	private String name;
	private ChucVu chucvu;
	private String code;
	private String phongBan;
	public String getPhongBan() {
		return phongBan;
	}

	public void setPhongBan(String phongBan) {
		this.phongBan = phongBan;
	}

	private boolean gioitinh;

	public NhanVien() {
	}

	NhanVien(String name, String code, boolean gioitinh) {
		this.name = name;
		this.code = code;
		this.gioitinh = gioitinh;
		this.chucvu = ChucVu.NhanVien;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ChucVu getChucvu() {
		return chucvu;
	}

	public void setChucvu(ChucVu chucvu) {
		this.chucvu = chucvu;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isGioitinh() {
		return gioitinh;
	}

	public void setGioitinh(boolean gioitinh) {
		this.gioitinh = gioitinh;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.code + "-" + this.name;
	}
}