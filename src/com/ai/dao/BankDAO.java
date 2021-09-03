package com.ai.dao;

import com.ai.dto.Bank;
import com.ai.exception.FindException;
import com.ai.exception.ModifyException;

public interface BankDAO {
	/**
	 * 최초 신청 혹은 은행명/계좌번호 변경 후 신청 시 수정
	 * @param b
	 * @throws modifyException
	 */
	void update(Bank b) throws ModifyException;
	
	/**
	 * 고객 아이디로 계좌정보 조회
	 * @param in_id
	 * @throws FindException
	 */
	public Bank selectByid(String in_id) throws FindException;
}
