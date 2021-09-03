package com.ai.dao;

import java.util.List;

import com.ai.dto.Advertisement;
import com.ai.exception.AddException;
import com.ai.exception.FindException;

public interface AdvertisementDAO {
	/**
	 * 광고 전체검색
	 * @return 전체광고
	 * @throws FindException 광고가 없을 경우 또는 광고검색을 실패한 경우 발생
	 */
	public List<Advertisement> selectAll() throws FindException;
	
	/**
	 * 광고 전체검색
	 * @param currentPage 페이지
	 * @return 페이지에 해당하는 광고들
	 * @throws FindException 광고가 없을 경우 또는 광고검색을 실패한 경우 발생
	 */
	public List<Advertisement> selectAll(int currentPage) throws FindException;
	
	/**
	 * 광고번호로 검색
	 * @param adv_no 광고번호
	 * @return 광고번호에 해당하는 광고
	 * @throws FindException 광고가 없을 경우 또는 광고검색을 실패한 경우 발생
	 */
	public Advertisement selectByNo(int adv_no) throws FindException;
	
	/**
	 * 광고 지역으로 검색
	 * @param location
	 * @return 지역에 해당하는 광고들
	 * @throws FindException 광고가 없을 경우 또는 광고검색을 실패한 경우 발생
	 */
	public List<Advertisement> selectByLocation(int location) throws FindException;
	
	/**
	 * 최근 등록된 광고 순으로 정렬한다
	 * @return 최근 등록 순 전체광고
	 * @throws FindException 광고가 없을 경우 또는 광고검색을 실패한 경우 발생
	 */
	public List<Advertisement>orderByAddDate() throws FindException;
	
	/**
	 * 시작월이 빠른 광고 순으로 정렬한다
	 * @return 시작월 순 전체광고
	 * @throws FindException 광고가 없을 경우 또는 광고검색을 실패한 경우 발생
	 */
	public List<Advertisement>orderByStartMonth() throws FindException;
	
	/**
	 * 기간이 긴 광고 순으로 정렬한다
	 * @return 기간 순 전체광고
	 * @throws FindException 광고가 없을 경우 또는 광고검색을 실패한 경우 발생
	 */
	public List<Advertisement>orderByTerm() throws FindException;
	
	/**
	 * 월 광고비가 높은 순으로 정렬한다
	 * @return 월 광고비 순 전체광고
	 * @throws FindException 광고가 없을 경우 또는 광고검색을 실패한 경우 발생
	 */
	public List<Advertisement>orderByFee() throws FindException;
	
	/**
	 * 광고 등록
	 * @param a
	 * @throws AddException
	 */
	void insert(Advertisement a) throws AddException;
	
	/**
	 * 기업 회원이 광고 정보 검색 
	 * @param adv_com_id
	 * @return list
	 * @throws FindException
	 */
	public List<Advertisement> selectById(String adv_com_id) throws FindException;
	
	/**
	 * 지역별 전체 광고 검색
	 * @param citycode
	 * @return list
	 * @throws FindException
	 */
	public List<Advertisement> selectByCityCode(int citycode) throws FindException;

	
	/**
	 * 시퀀스 출력(마지막 등록된 광고번호)
	 * @return int
	 * @throws FindException
	 */
	public int lastAdvNo() throws FindException;

	
	//DB에 추가되지 않는 광고 이미지, 소개글만 수정 가능하므로 광고 수정은 없어도 될것같네요
//	/**
//	 * 광고 수정
//	 * @param a
//	 * @throws ModifyException
//	 */
//	void update(Advertisement a) throws ModifyException;
}
