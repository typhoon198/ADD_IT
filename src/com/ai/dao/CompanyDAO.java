package com.ai.dao;

import java.util.List;

import com.ai.dto.Account;
import com.ai.dto.Advertisement;
import com.ai.dto.Apply;
import com.ai.dto.Company;
import com.ai.exception.AddException;
import com.ai.exception.FindException;
import com.ai.exception.ModifyException;

public interface CompanyDAO {
	
	/**
	 * 기업 회원 id로 검색
	 * @param id
	 * @return Company
	 * @throws FindException
	 */
	Company selectById(String id) throws FindException;
	
	/**
	 * 기업 회원 정보 수정
	 * @param c
	 * @throws modifyException
	 */
	void update(Company c) throws ModifyException;
	
	
	/**추가
	 * 기업 회원 정보 수정
	 * @param c
	 * @throws modifyException
	 */
	Company selectById2(String id) throws FindException;

	
}
