package com.ai.dao;

import java.util.Date;
import java.util.List;

import com.ai.dto.Payment;
import com.ai.exception.AddException;
import com.ai.exception.FindException;

public interface PaymentDAO {
	/**
	 * 지급 내역 추가
	 * @param p
	 * @throws AddException
	 */
	void insert(int pay_no, int app_no, Date pay_month, int pay_fee) throws AddException;
	
	/**
	 * 지급번호로 지급 내역 검색
	 * @param pay_no
	 * @return list
	 * @throws FindException
	 */
	List<Payment>SelectbyPayNo(int pay_no) throws FindException;
	
	/**
	 * 신청번호로 지급 내역 검색
	 * @param pay_app_no
	 * @return list
	 * @throws FindException
	 */
	List<Payment>SelectbyApplyNo(int pay_app_no) throws FindException;
}
