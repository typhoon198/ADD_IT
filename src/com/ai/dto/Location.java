package com.ai.dto;

public class Location {
	private int loc_code;
	private String loc_name;
	
	public Location() {
		super();
	}
	
	public Location(int loc_code, String loc_name) {
		super();
		this.loc_code = loc_code;
		this.loc_name = loc_name;
	}
	
	public int getLoc_code() {
		return loc_code;
	}
	
	public void setLoc_code(int loc_code) {
		this.loc_code = loc_code;
	}
	
	public String getLoc_name() {
		return loc_name;
	}
	
	public void setLoc_name(String loc_name) {
		this.loc_name = loc_name;
	}
	
	@Override
	public String toString() {
		return "Location [loc_code=" + loc_code + ", loc_name=" + loc_name + "]";
	}
	
}
