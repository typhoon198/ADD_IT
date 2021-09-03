package com.ai.dto;

import java.util.Date;

public class Apply {
	private int app_no;
	private String app_in_id;
	private Individual i;
	private Advertisement adv;
	private Date app_date;
	private int app_state;
	
	public Apply() {
		super();
		
	}
	
	public Apply(int app_no, Advertisement adv) {
		this.app_no = app_no;
		this.adv = adv;
	}
	

	public Apply(Advertisement adv, int app_no) {
		this.app_no = app_no;
		this.adv = adv;
	}
	
	public Apply(int app_no, Advertisement adv, Date app_date, int app_state) {
		this.app_no = app_no;
		this.adv = adv;
		this.app_date = app_date;
		this.app_state = app_state;
	}
	
	public Apply(int app_no, Individual i, Advertisement adv, Date app_date, int app_state) {
		super();
		this.app_no = app_no;
		this.i = i;
		this.adv = adv;
		this.app_date = app_date;
		this.app_state = app_state;

	}
	
	public Apply(String app_in_id, Individual i) {
		super();
		this.app_in_id = app_in_id;
		this.i = i;
	}
	
	public Apply(int app_no, int app_state) {
		super();
		this.app_no = app_no;
		this.app_state = app_state;
	}

	public int getApp_no() {
		return app_no;
	}

	public void setApp_no(int app_no) {
		this.app_no = app_no;
	}
	
	public String getApp_in_id() {
		return app_in_id;
	}

	public void setApp_in_id(String app_in_id) {
		this.app_in_id = app_in_id;
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
		return "Apply [app_no=" + app_no + ", app_in_id=" + app_in_id + ", i=" + i + ", adv=" + adv + ", app_date="
				+ app_date + ", app_state=" + app_state + "]";
	}

}
