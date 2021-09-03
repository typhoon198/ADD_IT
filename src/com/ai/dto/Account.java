package com.ai.dto;

public class Account {
	String id;
	String pwd;
	String nickname;
	int user_type;
	Individual individual;
	Company	company;
	
	public Account() {
		super();
	}

	// 일반회원 로그인에 사용할 생성자
	public Account(String id, String pwd, int user_type) {
		//super();
		this.id = id;
		this.pwd = pwd;
		this.user_type = user_type;
	}

	public Account(String id, int user_type) {
		this.id = id;
		this.user_type = user_type;
	}

	// 개인 회원 회원가입시에 사용
	public Account(String id, String pwd, int user_type, Individual individual) {
		this.id = id;
		this.pwd = pwd;
		this.user_type = user_type;
		this.individual = individual;
	}

	public Account(String id, String pwd, int user_type, Company company) {
		this.id = id;
		this.pwd = pwd;
		this.user_type = user_type;
		this.company = company;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getUser_type() {
		return user_type;
	}

	public void setUser_type(int user_type) {
		this.user_type = user_type;
	}

	public Individual getIndividual() {
		return individual;
	}

	public void setIndividual(Individual individual) {
		this.individual = individual;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "Account{" +
				"id='" + id + '\'' +
				", pwd='" + pwd + '\'' +
				", user_type=" + user_type +
				", individual=" + individual +
				", company=" + company +
				'}';
	}
}
