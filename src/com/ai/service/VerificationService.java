package com.ai.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.ai.dao.ApplyLineDAO;
import com.ai.dao.VerificationDAO;
import com.ai.dto.ApplyLine;
import com.ai.dto.Verification;
import com.ai.exception.FindException;
import com.ai.exception.ModifyException;

public class VerificationService {
	private VerificationDAO dao;
	private static VerificationService service;
	public static String envProp;
	private VerificationService() {
		Properties env = new Properties();
		try {
			env.load(new FileInputStream(envProp));
			String className = env.getProperty("verificationDAO");
			Class c = Class.forName(className);
			dao = (VerificationDAO) c.newInstance();
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
	
	public static VerificationService getInstance() {
		if(service == null) {
			service = new VerificationService();
		}
		return service;
	}
	
	public void update(int veri_state, int veri_app_no, java.util.Date veri_month) throws ModifyException {
		dao.update(veri_state, veri_app_no, veri_month);
	}
	
	/*public List<Verification> selectByApplyNo(int veri_app_no, Date veri_month) throws FindException {
		return dao.selectByappNoMonth(veri_app_no, veri_month);
	}*/
	
}
