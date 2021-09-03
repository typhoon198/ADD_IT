package com.ai.dto;

import java.util.Date;

public class ApplyList {
	private int idx;
	private Individual i;
	private int app_no;
	private Date app_date;
	private Company c;
	private Advertisement adv;
	private int app_state;

	
	public ApplyList(int idx, int app_no, Date app_date, Company c, Advertisement adv, int app_state) {
		super();
		this.idx = idx;
		this.app_no = app_no;
		this.app_date = app_date;
		this.c = c;
		this.adv = adv;
		this.app_state = app_state;
	}

	
	public int getIdx() {
		return idx;
	}


	public void setIdx(int idx) {
		this.idx = idx;
	}


	public Company getC() {
		return c;
	}

	public void setC(Company c) {
		this.c = c;
	}

	public int getApp_no() {
		return app_no;
	}

	public void setApp_no(int app_no) {
		this.app_no = app_no;
	}

	public Individual getI() {
		return i;
	}

	public void setI(Individual i) {
		this.i = i;
	}

	public Advertisement getAdv() {
		return adv;
	}

	public void setAdv(Advertisement adv) {
		this.adv = adv;
	}

	public Date getApp_date() {
		return app_date;
	}

	public void setApp_date(Date app_date) {
		this.app_date = app_date;
	}

	public int getApp_state() {
		return app_state;
	}

	public void setApp_state(int app_state) {
		this.app_state = app_state;
	}


	@Override
	public String toString() {
		return "ApplyList [idx=" + idx + ", i=" + i + ", app_no=" + app_no + ", app_date=" + app_date + ", c=" + c
				+ ", adv=" + adv + ", app_state=" + app_state + "]";
	}



}
