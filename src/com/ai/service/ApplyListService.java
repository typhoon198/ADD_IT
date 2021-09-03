package com.ai.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.ai.dao.ApplyListDAO;
import com.ai.dto.ApplyList;
import com.ai.exception.FindException;

public class ApplyListService {
	private ApplyListDAO dao;
	private static ApplyListService service;
	public static String envProp;
	private ApplyListService() {
		Properties env = new Properties();
		try {
			env.load(new FileInputStream(envProp));
			String className = env.getProperty("applylistDAO");
			Class c = Class.forName(className);
			dao = (ApplyListDAO) c.newInstance();
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
	
	public static ApplyListService getInstance() {
		if(service == null) {
			service = new ApplyListService();
		}
		return service;
	}
	
	public List<ApplyList> findApplyList(String inId) throws FindException {
		return dao.getApplyList(inId);
	}
	public List<ApplyList> findApplyList(String inId, int page) throws FindException {
		return dao.getApplyList(inId, page);
	}
	public List<ApplyList> findApplyList(String inId, String field, String query, int page) throws FindException {
		return dao.getApplyList(inId, field, query, page);
	}
	

	/*
	 * 총 게시물 건수
	 */	
	public int findApplyListCount(String inId) throws FindException {		
		return dao.getApplyListCount(inId);
	}
	
	public int findApplyListCount(String inId, String field, String query) throws FindException {		
		return dao.getApplyListCount(inId, field, query);
	}
}
