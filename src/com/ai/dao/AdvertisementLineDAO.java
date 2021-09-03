package com.ai.dao;

import java.util.List;

import com.ai.dto.AdvertisementLine;
import com.ai.exception.FindException;

public interface AdvertisementLineDAO {
	
	/**
	 * 기업 회원 아이디로 광고 내역 검색 
	 * @param id
	 * @param state
	 * @return list 
	 * @throws FindException
	 */
	List<AdvertisementLine> selectApplybyComId(int state, String id) throws FindException;

}
