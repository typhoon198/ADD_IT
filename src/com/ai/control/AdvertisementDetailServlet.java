package com.ai.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ai.dto.Advertisement;
import com.ai.exception.FindException;
import com.ai.service.AdvertisementService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AdvertisementDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper;
		mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		String jsonStr = "";
		
		String adv_no = request.getParameter("adv_no");
		//int adv_no = request.getParameter("adv_no");
		AdvertisementService service;
		ServletContext sc = getServletContext();
		AdvertisementService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = AdvertisementService.getInstance();
		int adv_no2 = Integer.parseInt(adv_no);
		System.out.println(adv_no2);
		try {
			Advertisement a = service.findByNo(adv_no2);
			jsonStr = mapper.writeValueAsString(a);
		} catch (FindException e) {
			e.printStackTrace();
			Map<String, Object> map = new HashMap<>();
			map.put("status", -1);
			map.put("msg", e.getMessage());
			jsonStr = mapper.writeValueAsString(map);
		}
		response.setContentType("application/json;charset=utf-8"); //응답형식지정
		PrintWriter out = response.getWriter();
		out.print(jsonStr);
	}

}
