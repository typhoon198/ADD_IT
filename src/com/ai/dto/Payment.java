package com.ai.dto;

import java.util.Date;

public class Payment {
	private int pay_no;
	private Apply app;
	private Date pay_date;
	private Date pay_month;
	private int pay_fee;
	
	public Payment() {
		super();
	}

	public Payment(int pay_no, Apply app, Date pay_date, Date pay_month, int pay_fee) {
		super();
		this.pay_no = pay_no;
		this.app = app;
		this.pay_date = pay_date;
		this.pay_month = pay_month;
		this.pay_fee = pay_fee;
	}
	
	public Payment(Apply app, Date pay_month, int pay_fee) {
		super();
		this.app = app;
		this.pay_month = pay_month;
		this.pay_fee = pay_fee;
	}

	public int getPay_no() {
		return pay_no;
	}

	public void setPay_no(int pay_no) {
		this.pay_no = pay_no;
	}

	public Apply getApp() {
		return app;
	}

	public void setApp(Apply app) {
		this.app = app;
	}

	public Date getPay_date() {
		return pay_date;
	}

	public void setPay_date(Date pay_date) {
		this.pay_date = pay_date;
	}

	public Date getPay_month() {
		return pay_month;
	}

	public void setPay_month(Date pay_month) {
		this.pay_month = pay_month;
	}

	public int getPay_fee() {
		return pay_fee;
	}

	public void setPay_fee(int pay_fee) {
		this.pay_fee = pay_fee;
	}

	@Override
	public String toString() {
		return "Payment [pay_no=" + pay_no + ", app=" + app + ", pay_date=" + pay_date + ", pay_month=" + pay_month
				+ ", pay_fee=" + pay_fee + "]";
	}
	
}
