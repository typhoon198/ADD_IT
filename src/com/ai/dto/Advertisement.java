package com.ai.dto;

import java.util.Date;

public class Advertisement {
	private int adv_no;
	private Company c;
	private Apply app;
	private int adv_location1;
	private int adv_location2;
	private int adv_location3;
	private int adv_fee;
	private int adv_cartype;
	private Date adv_startmonth;
	private Date adv_endmonth;
	private Date adv_date;
	private int adv_total;
	
	public Advertisement() {
		super();
	}

	public Advertisement(int adv_no, Company c, int adv_location1, int adv_location2, int adv_location3, int adv_fee,
			int adv_cartype, Date adv_startmonth, Date adv_endmonth, Date adv_date, int adv_total) {
		super();
		this.adv_no = adv_no;
		this.c = c;
		this.adv_location1 = adv_location1;
		this.adv_location2 = adv_location2;
		this.adv_location3 = adv_location3;
		this.adv_fee = adv_fee;
		this.adv_cartype = adv_cartype;
		this.adv_startmonth = adv_startmonth;
		this.adv_endmonth = adv_endmonth;
		this.adv_date = adv_date;
		this.adv_total = adv_total;
	}

	public Advertisement(int adv_no, Company c, int adv_location1, int adv_location2, int adv_fee, int adv_cartype,
			Date adv_startmonth, Date adv_endmonth, Date adv_date, int adv_total) {
		super();
		this.adv_no = adv_no;
		this.c = c;
		this.adv_location1 = adv_location1;
		this.adv_location2 = adv_location2;
		this.adv_fee = adv_fee;
		this.adv_cartype = adv_cartype;
		this.adv_startmonth = adv_startmonth;
		this.adv_endmonth = adv_endmonth;
		this.adv_date = adv_date;
		this.adv_total = adv_total;
	}

	public Advertisement(int adv_no, Company c, int adv_location1, int adv_fee, int adv_cartype, Date adv_startmonth,
			Date adv_endmonth, Date adv_date, int adv_total) {
		super();
		this.adv_no = adv_no;
		this.c = c;
		this.adv_location1 = adv_location1;
		this.adv_fee = adv_fee;
		this.adv_cartype = adv_cartype;
		this.adv_startmonth = adv_startmonth;
		this.adv_endmonth = adv_endmonth;
		this.adv_date = adv_date;
		this.adv_total = adv_total;
	}
	
	public Advertisement(int adv_no, Apply app, int adv_fee, Date adv_startmonth, Date adv_endmonth, Date adv_date,
			int adv_total) {
		super();
		this.adv_no = adv_no;
		this.app = app;
		this.adv_fee = adv_fee;
		this.adv_startmonth = adv_startmonth;
		this.adv_endmonth = adv_endmonth;
		this.adv_date = adv_date;
		this.adv_total = adv_total;
	}

	public int getAdv_no() {
		return adv_no;
	}

	public void setAdv_no(int adv_no) {
		this.adv_no = adv_no;
	}

	public Company getC() {
		return c;
	}

	public void setC(Company c) {
		this.c = c;
	}

	public int getAdv_location1() {
		return adv_location1;
	}

	public void setAdv_location1(int adv_location1) {
		this.adv_location1 = adv_location1;
	}

	public int getAdv_location2() {
		return adv_location2;
	}

	public void setAdv_location2(int adv_location2) {
		this.adv_location2 = adv_location2;
	}

	public int getAdv_location3() {
		return adv_location3;
	}

	public void setAdv_location3(int adv_location3) {
		this.adv_location3 = adv_location3;
	}

	public int getAdv_fee() {
		return adv_fee;
	}

	public void setAdv_fee(int adv_fee) {
		this.adv_fee = adv_fee;
	}

	public int getAdv_cartype() {
		return adv_cartype;
	}

	public void setAdv_cartype(int adv_cartype) {
		this.adv_cartype = adv_cartype;
	}

	public Date getAdv_startmonth() {
		return adv_startmonth;
	}

	public void setAdv_startmonth(Date adv_startmonth) {
		this.adv_startmonth = adv_startmonth;
	}

	public Date getAdv_endmonth() {
		return adv_endmonth;
	}

	public void setAdv_endmonth(Date adv_endmonth) {
		this.adv_endmonth = adv_endmonth;
	}

	public Date getAdv_date() {
		return adv_date;
	}

	public void setAdv_date(Date adv_date) {
		this.adv_date = adv_date;
	}

	public int getAdv_total() {
		return adv_total;
	}

	public void setAdv_total(int adv_total) {
		this.adv_total = adv_total;
	}
	
	public Apply getApp() {
		return app;
	}

	public void setApp(Apply app) {
		this.app = app;
	}

	@Override
	public String toString() {
		return "Advertisement [adv_no=" + adv_no + ", c=" + c + ", app=" + app + ", adv_location1=" + adv_location1
				+ ", adv_location2=" + adv_location2 + ", adv_location3=" + adv_location3 + ", adv_fee=" + adv_fee
				+ ", adv_cartype=" + adv_cartype + ", adv_startmonth=" + adv_startmonth + ", adv_endmonth="
				+ adv_endmonth + ", adv_date=" + adv_date + ", adv_total=" + adv_total + "]";
	}

}
