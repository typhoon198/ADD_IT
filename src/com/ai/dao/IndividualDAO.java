package com.ai.dao;

import com.ai.dto.Account;
import com.ai.dto.Bank;
import com.ai.dto.Individual;
import com.ai.exception.AddException;
import com.ai.exception.FindException;
import com.ai.exception.ModifyException;

public interface IndividualDAO {
	/**
	 * 회원 추가
	 * @param a
	 * @throws AddException
	 */
	void insert(Account a) throws AddException;
	
	/**
	 * 개인 회원 추가
	 * @param i
	 * @throws AddException
	 */
	void insert(Individual i) throws AddException;
	
	/**
	 * 계좌 정보 추가
	 * @param b
	 * @throws AddException
	 */
	void insert(Bank b) throws AddException;
	
	/**
	 * 개인 회원 id로 검색
	 * @param id
	 * @return Individual
	 * @throws FindException
	 */
	Individual selectById(String id) throws FindException;  //applyStateCheck service
	
	/**
	 * 개인 회원 정보 수정
	 * @param c
	 * @throws modifyException
	 */
	void update(Individual i) throws ModifyException;
	
	
	
}
