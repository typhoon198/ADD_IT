package com.ai.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.ai.dao.AdvertisementDAO;
import com.ai.dao.AdvertisementLineDAO;
import com.ai.dto.AdvertisementLine;
import com.ai.exception.FindException;

public class AdvertisementLineService {
	private AdvertisementLineDAO dao;
	private static AdvertisementLineService service;
	public static String envProp;
	private AdvertisementLineService() {
		Properties env = new Properties();
		try {
			env.load(new FileInputStream(envProp));
			String className = env.getProperty("advertisementlineDAO");
			Class c = Class.forName(className);
			dao = (AdvertisementLineDAO) c.newInstance();
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
	public static AdvertisementLineService getInstance() {
		if(service == null) {
			service = new AdvertisementLineService();
		}
		return service;
	}
	
	public List<AdvertisementLine> selectApplybyComId(int state, String id) throws FindException {
		return dao.selectApplybyComId(state, id);
	}
	
	

}
