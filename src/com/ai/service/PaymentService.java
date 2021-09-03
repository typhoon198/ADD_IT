package com.ai.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import com.ai.dao.PaymentDAO;
import com.ai.dao.VerificationDAO;
import com.ai.exception.AddException;

public class PaymentService {
	private PaymentDAO dao;
	private static PaymentService service;
	public static String envProp;
	private PaymentService() {
		Properties env = new Properties();
		try {
			env.load(new FileInputStream(envProp));
			String className = env.getProperty("paymentDAO");
			Class c = Class.forName(className);
			dao = (PaymentDAO) c.newInstance();
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
	
	public static PaymentService getInstance() {
		if(service == null) {
			service = new PaymentService();
		}
		return service;
	}
	
	public void insert(int pay_no, int app_no, Date pay_month, int pay_fee) throws AddException {
		dao.insert(pay_no, app_no, pay_month, pay_fee);
	}
}
