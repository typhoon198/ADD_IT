package com.ai.dao;

import java.util.List;

import com.ai.dto.Advertisement;
import com.ai.dto.Apply;
import com.ai.dto.ApplyLine;
import com.ai.dto.Payment;
import com.ai.dto.Verification;
import com.ai.exception.AddException;
import com.ai.exception.FindException;

public interface ApplyLineDAO {

	
	/**
	 * 해당 광고신청의 세부 내역 조회(지급, 인증 관련)
	 * @param 광고신청 번호
	 * @return ApplyLine
	 * @throws FindException
	 */
	ApplyLine SelectbyNo(int app_no) throws FindException;
	
	/**
	 * 기업 회원이 광고 번호로 인증내역 검색 
	 * @param adv_no
	 * @return list
	 * @throws FindException
	 */
	public List<ApplyLine> selectByAdvNo(int adv_no) throws FindException;

	
	/**
	 * 해당 광고신청의 세부 내역 조회(지급 관련)
	 * @param 광고신청 번호
	 * @return ApplyLine
	 * @throws FindException
	 */
	List<Payment> selectPaymentByNo(int app_no) throws FindException;
	
	/**
	 * 해당 광고신청의 세부 내역 조회(인증 관련)
	 * @param 광고신청 번호
	 * @return ApplyLine
	 * @throws FindException
	 */
	List<Verification> selectVerificationByNo(int app_no) throws FindException;
}
