package com.ai.control;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ai.dto.Advertisement;
import com.ai.exception.FindException;
import com.ai.service.AdvertisementService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OrderByAddDateListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdvertisementService service;
		ServletContext sc = getServletContext();
		AdvertisementService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = AdvertisementService.getInstance();
		
		try {
			List<Advertisement> list = service.orderByAddDate();
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
			String jsonStr = mapper.writeValueAsString(list);
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(jsonStr);
		} catch (FindException e) {
			e.printStackTrace();
		}
		
	}

}
