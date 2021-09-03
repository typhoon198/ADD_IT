package com.ai.dto;

import java.util.Date;

public class Individual {
	String in_id;
	String in_name;
	String in_phone;
	String in_email;
	int in_zipcode;
	String in_address;
	String in_birthday;
	int in_cartype;
	
	public Individual() {
		super();
	}

	public Individual(String in_id, String in_name, String in_phone, String in_email, int in_zipcode, String in_address,
			String in_birthday, int in_cartype) {
		super();
		this.in_id = in_id;
		this.in_name = in_name;
		this.in_phone = in_phone;
		this.in_email = in_email;
		this.in_zipcode = in_zipcode;
		this.in_address = in_address;
		this.in_birthday = in_birthday;
		this.in_cartype = in_cartype;
	}

	public String getIn_id() {
		return in_id;
	}

	public void setIn_id(String in_id) {
		this.in_id = in_id;
	}

	public String getIn_name() {
		return in_name;
	}

	public void setIn_name(String in_name) {
		this.in_name = in_name;
	}

	public String getIn_phone() {
		return in_phone;
	}

	public void setIn_phone(String in_phone) {
		this.in_phone = in_phone;
	}

	public String getIn_email() {
		return in_email;
	}

	public void setIn_email(String in_email) {
		this.in_email = in_email;
	}

	public int getIn_zipcode() {
		return in_zipcode;
	}

	public void setIn_zipcode(int in_zipcode) {
		this.in_zipcode = in_zipcode;
	}

	public String getIn_address() {
		return in_address;
	}

	public void setIn_address(String in_address) {
		this.in_address = in_address;
	}

	public String getIn_birthday() {
		return in_birthday;
	}

	public void setIn_birthday(String in_birthday) {
		this.in_birthday = in_birthday;
	}

	public int getIn_cartype() {
		return in_cartype;
	}

	public void setIn_cartype(int in_cartype) {
		this.in_cartype = in_cartype;
	}

	@Override
	public String toString() {
		return "Individual [in_id=" + in_id + ", in_name=" + in_name + ", in_phone=" + in_phone + ", in_email="
				+ in_email + ", in_zipcode=" + in_zipcode + ", in_address=" + in_address + ", in_birthday="
				+ in_birthday + ", in_cartype=" + in_cartype + "]";
	}
	
}
