package com.ai.dao;

import java.util.Date;
import java.util.List;

import com.ai.dto.Verification;
import com.ai.exception.AddException;
import com.ai.exception.FindException;
import com.ai.exception.ModifyException;

public interface VerificationDAO {
	
	/**
	 * 인증 등록
	 * @param v
	 * @throws AddException
	 */
	void insert(Verification v) throws AddException;
	
	/**
	 * 신청번호, 인증월로 인증 검색
	 * @param veri_app_no
	 * @return list
	 * @throws FindException
	 */
	List<Verification> selectByApplyNo(int veri_app_no, Date veri_month) throws FindException;
	
	/**
	 * 인증 상태 수정 
	 * @param veri_state
	 * @param veri_app_no
	 * @throws FindException
	 */
	void update(int veri_state, int veri_app_no, java.util.Date veri_month) throws ModifyException;
	
}
