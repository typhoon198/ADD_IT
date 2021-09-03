package com.ai.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.ai.dao.ApplyLineDAO;
import com.ai.dto.Apply;
import com.ai.dto.ApplyLine;
import com.ai.dto.Payment;
import com.ai.dto.Verification;
import com.ai.exception.AddException;
import com.ai.exception.FindException;

import com.ai.dao.ApplyDAO;

public class ApplyLineService {
	private ApplyLineDAO dao;
	private static ApplyLineService service;
	public static String envProp;
	private ApplyLineService() {
		Properties env = new Properties();
		try {
			env.load(new FileInputStream(envProp));
			String className = env.getProperty("applylineDAO");
			Class c = Class.forName(className);
			dao = (ApplyLineDAO) c.newInstance();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static ApplyLineService getInstance() {
		if(service == null) {
			service = new ApplyLineService();
		}
		return service;
	}
	
	
	/**
	 * 광고신청 번호로 월별 광고인증 내역 및 입금정보 조회
	 * @param id 고객의 아이디
	 * @return List<Apply> 광고활동 내역
	 * @throws FindException
	 */
	public ApplyLine findAll(int app_no) throws FindException {
		return dao.SelectbyNo(app_no);
	}
	
	/**
	 * 광고신청 번호로 월별 입금정보 조회
	 * @param id 고객의 아이디
	 * @return List<Apply> 광고활동 내역
	 * @throws FindException
	 */
	public List<Payment> findPayment(int app_no) throws FindException {
		return dao.selectPaymentByNo(app_no);
	}
	
	/**
	 * 광고신청 번호로 월별 광고인증 내역
	 * @param id 고객의 아이디
	 * @return List<Apply> 광고활동 내역
	 * @throws FindException
	 */
	public List<Verification> findVerification(int app_no) throws FindException {
		return dao.selectVerificationByNo(app_no);
	}
	
	public List<ApplyLine> selectByAdvNo(int adv_no) throws FindException {
		return dao.selectByAdvNo(adv_no);
	}

	
}
