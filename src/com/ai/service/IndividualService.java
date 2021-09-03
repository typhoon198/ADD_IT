package com.ai.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.ai.dao.IndividualDAO;
import com.ai.dto.Individual;
import com.ai.exception.FindException;
import com.ai.exception.ModifyException;

public class IndividualService {
	private static IndividualService service;
	public static String envProp;
	private IndividualDAO dao;
	
	private IndividualService() {
		Properties env = new Properties();
		try {
			env.load(new FileInputStream(envProp));
			String className = env.getProperty("individualDAO");
			Class c = Class.forName(className);
			dao = (IndividualDAO) c.newInstance();
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
	
	public static IndividualService getInstance() {
		if(service == null) {
			service = new IndividualService();
		}
		return service;
	}
	public Individual findInfo(String id) throws FindException {
		System.out.println("서블릿 위치 4-1 Service 파일  진입:");
		System.out.println("서블릿 위치 4-1 Service 매개변수 id :" + id);
		return dao.selectById(id);
	}
	
	
	public void modify(Individual i) throws ModifyException{
		dao.update(i);
	}
}
