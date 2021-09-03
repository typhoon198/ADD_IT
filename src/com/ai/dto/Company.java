package com.ai.dto;

public class Company {
	String com_id;
	String com_name;
	String com_phone;
	String com_email;
	int com_zipcode;
	String com_addr;
	String com_rn;
	int com_bt;
	
	public Company() {
		super();
	}
	
//	public Company(String com_name, String com_bt) {
//		this(null, com_name, null, null, 0, null, null, com_bt);
//	}
	
	public Company(String com_id, String com_name, String com_phone, String com_email, int com_zipcode, String com_addr,
			String com_rn, int com_bt) {
		super();
		this.com_id = com_id;
		this.com_name = com_name;
		this.com_phone = com_phone;
		this.com_email = com_email;
		this.com_zipcode = com_zipcode;
		this.com_addr = com_addr;
		this.com_rn = com_rn;
		this.com_bt = com_bt;
	}

	public String getCom_id() {
		return com_id;
	}

	public void setCom_id(String com_id) {
		this.com_id = com_id;
	}

	public String getCom_name() {
		return com_name;
	}

	public void setCom_name(String com_name) {
		this.com_name = com_name;
	}

	public String getCom_phone() {
		return com_phone;
	}

	public void setCom_phone(String com_phone) {
		this.com_phone = com_phone;
	}

	public String getCom_email() {
		return com_email;
	}

	public void setCom_email(String com_email) {
		this.com_email = com_email;
	}

	public int getCom_zipcode() {
		return com_zipcode;
	}

	public void setCom_zipcode(int com_zipcode) {
		this.com_zipcode = com_zipcode;
	}

	public String getCom_addr() {
		return com_addr;
	}

	public void setCom_addr(String com_addr) {
		this.com_addr = com_addr;
	}

	public String getCom_rn() {
		return com_rn;
	}

	public void setCom_rn(String com_rn) {
		this.com_rn = com_rn;
	}

	public int getCom_bt() {
		return com_bt;
	}

	public void setCom_bt(int com_bt) {
		this.com_bt = com_bt;
	}

	@Override
	public String toString() {
		return "Company [com_id=" + com_id + ", com_name=" + com_name + ", com_phone=" + com_phone + ", com_email="
				+ com_email + ", com_zipcode=" + com_zipcode + ", com_addr=" + com_addr + ", com_rn=" + com_rn
				+ ", com_bt=" + com_bt + "]";
	}
	
}
