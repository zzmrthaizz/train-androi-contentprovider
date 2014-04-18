package com.example.train_android_contentprovider;

import java.io.Serializable;
import java.util.ArrayList;



public class PhongBan implements Serializable {

//	private ArrayList<NhanVien> NhanViens;
	private String namePhong;
	private String maPhong;

	public String getNamePhong() {
		return namePhong;
	}

	public String getMaPhong() {
		return maPhong;
	}

	public void setMaPhong(String maPhong) {
		this.maPhong = maPhong;
	}

	public void setNamePhong(String namePhong) {
		this.namePhong = namePhong;
	}

//	public NhanVien getTphong() {
//		for (int i = 0; i < NhanViens.size(); i++) {
//			NhanVien nv = NhanViens.get(i);
//			if (nv.getChucvu() == ChucVu.TruongPhong) {
//				return nv;
//			}
//		}
//		return null;
//
//	}
//
//	public ArrayList<NhanVien> getPphong() {
//		ArrayList<NhanVien> pPhongs = new ArrayList<NhanVien>();
//		for (int i = 0; i <NhanViens.size(); i ++) {
//			if (NhanViens.get(i).getChucvu() == ChucVu.PhoPhong) {
//				pPhongs.add(NhanViens.get(i));
//			}
//		}
//		return pPhongs;
//
//	}

	public PhongBan() {
		this.maPhong = null;
		this.namePhong = null;
//		this.NhanViens = new ArrayList<NhanVien>();
	};

	PhongBan(String namePhong, String maPhong) {
		this.maPhong = maPhong;
		this.namePhong = namePhong;
//		this.NhanViens = new ArrayList<NhanVien>();
	}

//	public void addnv(NhanVien nVien) {
//		NhanViens.add(nVien);
//	}
//
//	public String coutnv() {
//		String cout = String.valueOf(NhanViens.size());
//		return cout;
//	}
//
//	public ArrayList<NhanVien> getListNhanvien() {
//		return NhanViens;
//	}
//
//	public void addlistNv(ArrayList<NhanVien> arrayList) {
//		this.NhanViens.clear();
//		this.NhanViens.addAll(arrayList);
//	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.namePhong;
	}
}
