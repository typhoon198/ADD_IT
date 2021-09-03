package com.ai.dao;

import java.util.List;

import com.ai.dto.ApplyList;
import com.ai.exception.FindException;

public interface ApplyListDAO {
	
	/**
	 * 개인 회원 ID별 광고신청 내역 총 개수
	 * @param inId 개인 회원 아이디
	 * @return int
	 * @throws FindException
	 */
	public int getApplyListCount(String inId) throws FindException;
	
	/**
	 * 개인 회원 ID별 광고신청 내역  광고명 혹은 수락 상태로 검색 후 광고신청 내역 총 개수
	 * @param inId 개인 회원 아이디
	 * @return int
	 * @throws FindException
	 */
	public int getApplyListCount(String inId, String field, String query) throws FindException;
	
	/**
	 * 개인 회원 ID별 광고신청 내역 조회
	 * @param inId 개인 회원 아이디
	 * @return list
	 * @throws FindException
	 */
	public List<ApplyList> getApplyList(String inId)throws FindException;
	
	/**
	 * 개인 회원 ID별 광고신청 내역 에서 하단 페이지 번호에 해당하는 내역 조회 
	 * @param inId 개인 회원 아이디, page 선택한 페이지 번호
	 * @return list
	 * @throws FindException
	 */
	public List<ApplyList> getApplyList(String inId, int page)throws FindException;
	/**
	 * 개인 회원 ID별 광고신청 내역  광고명 혹은 수락 상태로 검색 후 페이지별 조회 
	 * @param inId 개인 회원 아이디, field 검색 기준(광고명, 수락 상태) page 선택한 페이지 번호
	 * @return list
	 * @throws FindException
	 */
	public List<ApplyList> getApplyList(String inId, String field, String query, int page)throws FindException;

}
