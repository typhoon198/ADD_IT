package com.ai.dto;

import java.util.Date;
import java.util.List;

public class ApplyLine {
	   private int app_no;
	   private String app_in_id;
	   private Date adv_startmonth;
	   private Date adv_endmonth;
	   private List<Payment> pays;
	   private List<Verification> verifications;
	   
   public ApplyLine(int app_no, List<Payment> pays, List<Verification> verifications) {
		super();
		this.app_no = app_no;
		this.pays = pays;
		this.verifications = verifications;
	}
	   
	public ApplyLine(int app_no, String app_in_id, Date adv_startmonth, Date adv_endmonth, List<Payment> pays, List<Verification> verifications) {
		super();
		this.app_no = app_no;
		this.app_in_id = app_in_id;
		this.adv_startmonth = adv_startmonth;
		this.adv_endmonth = adv_endmonth;
		this.pays = pays;
		this.verifications = verifications;
	}
	
	public ApplyLine() {}

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
	public List<Payment> getPays() {
		return pays;
	}
	public void setPays(List<Payment> pays) {
		this.pays = pays;
	}
	public List<Verification> getVerifications() {
		return verifications;
	}
	public void setVerifications(List<Verification> verifications) {
		this.verifications = verifications;
	}

	@Override
	public String toString() {
		return "ApplyLine [app_no=" + app_no + ", app_in_id=" + app_in_id + ", adv_startmonth=" + adv_startmonth
				+ ", adv_endmonth=" + adv_endmonth + ", pays=" + pays + ", verifications=" + verifications + "]";
	}

}
