package com.ai.dao;

import com.ai.dto.Account;
import com.ai.exception.FindException;
import com.ai.exception.ModifyException;
import com.ai.exception.RemoveException;
import com.ai.exception.AddException;

public interface AccountDAO {

	/**
	 * 개인회원 가입
	 * @param account
	 * @throws AddException
	 */
	void insert_individual(Account account) throws AddException;


	/**
	 * 기업회원 가입
	 * @param account
	 * @throws AddException
	 */
	void insert_company(Account account) throws AddException;


	/**
	 * 회원 id로 검색
	 * @param id
	 * @return Account
	 * @throws FindException
	 */
	Account selectById(String id) throws FindException;
	
	/**
	 * 회원 비밀번호 수정
	 * @param account
	 * @throws FindException
	 */
	void update(Account account) throws ModifyException;

	/**
	 * 회원 탈퇴
	 * @param account
	 * @throws RemoveException
	**/
	void Delete(Account account) throws RemoveException;
}
//login(String id, String pwd)은 AccountService
//signup(Individual i) / signup(Company c)은 IndividualService / CompanyService에 넣으면 될 듯 합니다 