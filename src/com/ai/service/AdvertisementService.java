package com.ai.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.ai.dao.AdvertisementDAO;
import com.ai.dto.Advertisement;
import com.ai.exception.AddException;
import com.ai.exception.FindException;

public class AdvertisementService {
	private AdvertisementDAO dao;
	private static AdvertisementService service;
	public static String envProp;
	private AdvertisementService() {
		Properties env = new Properties();
		try {
			env.load(new FileInputStream(envProp));
			String className = env.getProperty("advertisementDAO");
			Class c = Class.forName(className);
			dao = (AdvertisementDAO)c.newInstance();
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
	
	public static AdvertisementService getInstance() {
		if(service == null) {
			service = new AdvertisementService();
		}
		return service;
	}
	
	public List<Advertisement> findAll() throws FindException{
		return dao.selectAll();
	}
	
	public List<Advertisement> findAll(int currentPage) throws FindException{
		return dao.selectAll(currentPage);
	}
	
	public Advertisement findByNo(int adv_no) throws FindException{
		return dao.selectByNo(adv_no);
	}
	
	public List<Advertisement> findByLocation(int location) throws FindException{
		return dao.selectByLocation(location);
	}
	
	public List<Advertisement> orderByAddDate() throws FindException{
		return dao.orderByAddDate();
	}
	
	public List<Advertisement> orderByStartMonth() throws FindException {
		return dao.orderByStartMonth();
	}
	
	public List<Advertisement> orderByTerm() throws FindException{
		return dao.orderByTerm();
	}
	
	public List<Advertisement> orderByFee() throws FindException{
		return dao.orderByFee();
	}
	
	public List<Advertisement> findByCityCode(int citycode) throws FindException{
		return dao.selectByCityCode(citycode);
	}

	public void add(Advertisement adv) throws AddException{
		dao.insert(adv);
	}
	
	public List<Advertisement> selectById(String adv_com_id) throws FindException{
		return dao.selectById(adv_com_id);
	}
	
	public int last() throws FindException{
	      return dao.lastAdvNo();
	}
}
