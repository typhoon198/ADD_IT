package com.ai.dto;

import java.util.Date;

public class AdvertisementLine {
	private int adv_no;
	private Date adv_startmonth;
	private Date adv_endmonth;
	private int adv_fee;
	private int cnt;
	
	public AdvertisementLine() {
		super();
	}

	public AdvertisementLine(int adv_no, Date adv_startmonth, Date adv_endmonth, int adv_fee, int cnt) {
		super();
		this.adv_no = adv_no;
		this.adv_startmonth = adv_startmonth;
		this.adv_endmonth = adv_endmonth;
		this.adv_fee = adv_fee;
		this.cnt = cnt;
	}

	public int getAdv_no() {
		return adv_no;
	}

	public void setAdv_no(int adv_no) {
		this.adv_no = adv_no;
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

	public int getAdv_fee() {
		return adv_fee;
	}

	public void setAdv_fee(int adv_fee) {
		this.adv_fee = adv_fee;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	@Override
	public String toString() {
		return "AdvertisementLine [adv_no=" + adv_no + ", adv_startmonth=" + adv_startmonth + ", adv_endmonth="
				+ adv_endmonth + ", adv_fee=" + adv_fee + ", cnt=" + cnt + "]";
	}
	
}
