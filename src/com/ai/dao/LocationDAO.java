package com.ai.dao;

import java.util.List;

import com.ai.dto.Location;
import com.ai.exception.FindException;

public interface LocationDAO {
	/**
	 * 지역코드 앞 2자리 숫자로 검색
	 * @return 지역코드 앞 2자리를 포함하는 5자리 지역코드
	 * @throws FindException
	 */
	public List<Location> selectByCityCode(int citycode) throws FindException;
	
	/**
	 * 전체 지역 검색
	 * @return 지역들
	 * @throws FindException
	 */
	public List<Location> selectAll() throws FindException;
}
