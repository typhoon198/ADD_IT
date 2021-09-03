package com.ai.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.ai.dao.LocationDAO;
import com.ai.dto.Location;
import com.ai.exception.FindException;

public class LocationService {
	private LocationDAO dao;
	private static LocationService service;
	public static String envProp;
	private LocationService() {
		Properties env = new Properties();
		try {
			env.load(new FileInputStream(envProp));
			String className = env.getProperty("locationDAO");
			Class c = Class.forName(className);
			dao = (LocationDAO)c.newInstance();
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
	
	public static LocationService getInstance() {
		if(service == null) {
			service = new LocationService();
		}
		return service;
	}
	
	public List<Location> findAll() throws FindException{
		return dao.selectAll();
	}
	
	public List<Location> findByCityCode(int citycode) throws FindException{
		return dao.selectByCityCode(citycode);
	}
	
}
