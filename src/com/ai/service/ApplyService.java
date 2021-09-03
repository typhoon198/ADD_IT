package com.ai.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.ai.dao.ApplyDAO;
import com.ai.dto.Apply;
import com.ai.exception.AddException;
import com.ai.exception.FindException;
import com.ai.exception.ModifyException;

public class ApplyService {
	private ApplyDAO dao;
	private static ApplyService service;
	public static String envProp;
	private ApplyService() {
		Properties env = new Properties();
		try {
			env.load(new FileInputStream(envProp));
			String className = env.getProperty("applyDAO");
			Class c = Class.forName(className);
			dao = (ApplyDAO)c.newInstance();
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
	
	public static ApplyService getInstance() {
		if(service == null) {
			service = new ApplyService();
		}
		return service;
	}
	
	public void add(Apply apply) throws AddException{
		dao.insert(apply);
	}
	
	/**
	 * 개인 회원 id로 광고신청 내역 조회
	 * @param id 고객의 아이디
	 * @return List<Apply> 광고신청 내역
	 * @throws FindException
	 */
	public List<Apply> findApply(String id) throws FindException {
		return dao.selectApplyById(id);
	}
	
	
	/**
	 * 개인 회원 id로 광고활동 내역 조회 (수락 상태)
	 * @param id 고객의 아이디
	 * @return List<Apply> 광고활동 내역
	 * @throws FindException
	 */
	public List<Apply> findAccept(String id) throws FindException {
		return dao.selectAcceptById(id);
	}
	
	public Apply findLatelyApply(String id) throws FindException {
		return dao.selectLatelyApplyById(id);
	}
	
	public List<Apply> selectApplybyAdvNo(int no) throws FindException {
		return dao.selectApplybyAdvNo(no);
	}
	
	public void update(int app_no, int app_state) throws ModifyException {
		dao.update(app_no, app_state);
	}

}
