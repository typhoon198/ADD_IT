package com.ai.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.ai.dao.BankDAO;
import com.ai.dto.Bank;
import com.ai.exception.FindException;
import com.ai.exception.ModifyException;

public class BankService {
	private BankDAO dao;
	private static BankService service;
	public static String envProp;
	private BankService() {
		Properties env = new Properties();
		try {
			env.load(new FileInputStream(envProp));
			String className = env.getProperty("bankDAO");
			Class c = Class.forName(className);
			dao = (BankDAO)c.newInstance();
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
	
	public static BankService getInstance() {
		if(service == null) {
			service = new BankService();
		}
		return service;
	}
	
	public void modify(Bank b) throws ModifyException{
		dao.update(b);
	}
	
	public Bank findById(String in_id) throws FindException{
		return dao.selectByid(in_id);
	}
}
