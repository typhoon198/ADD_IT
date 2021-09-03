package com.ai.dto;

import java.util.Date;

public class Verification {
	private int ver_app_no;
	private Apply app;
	private Advertisement adv;
	private Date veri_month;
	private int veri_meter;
	private int veri_state;
	
	public Verification() {
		super();
	}


	public Verification(Apply app, Date veri_month, int veri_meter, int veri_state) {
		super();
		this.app = app;
		this.veri_month = veri_month;
		this.veri_meter = veri_meter;
		this.veri_state = veri_state;
	}
	
	public Verification(int ver_app_no, Apply app, Date veri_month, int veri_meter, int veri_state) {
		super();
		this.ver_app_no = ver_app_no;
		this.app = app;
		this.veri_month = veri_month;
		this.veri_meter = veri_meter;
		this.veri_state = veri_state;
	}
	
	public Verification(Apply app, Advertisement adv, Date veri_month, int veri_meter) {
		super();
		this.app = app;
		this.adv = adv;
		this.veri_month = veri_month;
		this.veri_meter = veri_meter;
	}

	public int getVer_app_no() {
		return ver_app_no;
	}

	public void setVer_app_no(int ver_app_no) {
		this.ver_app_no = ver_app_no;
	}

	public Apply getApp() {
		return app;
	}

	public void setApp(Apply app) {
		this.app = app;
	}
	
	public Advertisement getAdv() {
		return adv;
	}

	public void setAdv(Advertisement adv) {
		this.adv = adv;
	}

	public Date getVeri_month() {
		return veri_month;
	}

	public void setVeri_month(Date veri_month) {
		this.veri_month = veri_month;
	}

	public int getVeri_meter() {
		return veri_meter;
	}

	public void setVeri_meter(int veri_meter) {
		this.veri_meter = veri_meter;
	}

	public int getVeri_state() {
		return veri_state;
	}

	public void setVeri_state(int veri_state) {
		this.veri_state = veri_state;
	}

	@Override
	public String toString() {
		return "Verification [ver_app_no=" + ver_app_no + ", app=" + app + ", adv=" + adv + ", veri_month=" + veri_month
				+ ", veri_meter=" + veri_meter + ", veri_state=" + veri_state + "]";
	}

}
