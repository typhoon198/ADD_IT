package com.ai.dao;

import java.util.List;

import com.ai.dto.Apply;
import com.ai.exception.AddException;
import com.ai.exception.FindException;
import com.ai.exception.ModifyException;

public interface ApplyDAO {
	/**
	 * 광고 신청 추가
	 * @param apply
	 * @throws AddException
	 */
	void insert(Apply apply) throws AddException;
	
	/**
	 * 개인 회원 id별 광고신청 내역 검색
	 * @param id 개인 회원 아이디
	 * @return list
	 * @throws FindException
	 */
	List<Apply> selectApplyById(String id) throws FindException;
	
	/**
	 * 개인 회원 id별 광고활동 내역 검색(수락)
	 * @param id 개인 회원 아이디
	 * @return list
	 * @throws FindException
	 */
	List<Apply> selectAcceptById(String id) throws FindException;
	
	/**
	 * 기업 광고별 신청인원 검색 
	 * @param app_adv_no
	 * @return list 
	 * @throws FindException
	 */
	List<Apply> selectApplybyAdvNo(int no) throws FindException;
	
	/**
	 * 신청상태 업데이트(수락, 거절)
	 * @param app_no
	 * @throws FindException
	 */
	void update(int app_no, int app_state) throws ModifyException;

	/**
	 * 개인 회원 id로 가장 최근 광고신청 내역 검색
	 * @param id 개인 회원 아이디
	 * @return apply
	 * @thrwos FindException
	 */
	Apply selectLatelyApplyById(String id) throws FindException;
}
