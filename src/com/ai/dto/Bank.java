package com.ai.dto;

public class Bank {
	String in_id;
	String bank;
	String acno;
	
	public Bank() {
		super();
	}

	public Bank(String in_id, String bank, String acno) {
		super();
		this.in_id = in_id;
		this.bank = bank;
		this.acno = acno;
	}

	public Bank(String in_id) {
		super();
		this.in_id = in_id;
	}

	public String getIn_id() {
		return in_id;
	}

	public void setIn_id(String in_id) {
		this.in_id = in_id;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAcno() {
		return acno;
	}

	public void setAcno(String acno) {
		this.acno = acno;
	}

	@Override
	public String toString() {
		return "Bank [in_id=" + in_id + ", bank=" + bank + ", acno=" + acno + "]";
	}
	
}
