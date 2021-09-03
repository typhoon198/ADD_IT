package com.ai.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.ai.dao.CompanyDAO;
import com.ai.dao.IndividualDAO;
import com.ai.dto.Company;
import com.ai.exception.FindException;
import com.ai.exception.ModifyException;

public class CompanyService {
	private static CompanyService service;
	public static String envProp;
	private CompanyDAO dao;
	
	private CompanyService() {
		Properties env = new Properties();
		try {
			env.load(new FileInputStream(envProp));

			String className = env.getProperty("CompanyDAO");

			Class c = Class.forName(className);
			dao = (CompanyDAO) c.newInstance();
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
	
	public static CompanyService getInstance() {
		if(service == null) {
			service = new CompanyService();
		}
		return service;
	}
	
	public Company findInfo(String id) throws FindException {
		return dao.selectById(id);
	}
	
	public void modify(Company c) throws ModifyException {
		dao.update(c);
	}
	
	public Company findInfo2(String id) throws FindException {
		return dao.selectById2(id);
	}
}

