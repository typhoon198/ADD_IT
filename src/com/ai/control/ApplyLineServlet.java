package com.ai.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ai.dto.Account;
import com.ai.dto.Payment;
import com.ai.dto.Verification;
import com.ai.exception.FindException;
import com.ai.service.ApplyLineService;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ApplyLineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String type = request.getParameter("type"); //data: {type : 1} 
		System.out.println("type : "+type);

		switch(type) {
			case "1" :
				findPay(request, response);
				break;
			case "2" :
				findVeri(request, response);
				break;
		}
	}
	protected void findVeri(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		System.out.println("findVeri 진입");
		int app_no = Integer.parseInt(request.getParameter("app_no"));
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));//JSON에는 date가 숫자형태로전송 변환
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();

		
		//1. 세션에서 데이터 얻기
		HttpSession session = request.getSession();
		
		ApplyLineService service =null;
		Account a = (Account) session.getAttribute("loginInfo");
		
		if(a == null) {
			System.out.println("서블릿 11 null");
			Map<String, Integer> map = new HashMap<>();
			map.put("status", 0);
			out.print(mapper.writeValueAsString(map));
			
		}else {
			System.out.println("서블릿 111");
			//2. 비지니스 로직 호출
			ServletContext sc = getServletContext();
			ApplyLineService.envProp = sc.getRealPath(sc.getInitParameter("env"));
			service = ApplyLineService.getInstance();
			List<Verification> ap = null;
			System.out.println("서블릿 1111");
			try {
				System.out.println("app_no "+app_no);
				ap = service.findVerification(app_no);

			} catch (FindException e) {
				e.printStackTrace();
			}
			String jsonStr = mapper.writeValueAsString(ap);
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(jsonStr);
			
			System.out.println("Servlet - syso : "+jsonStr);
		}
	}
	
	protected void findPay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		System.out.println("pay 진입");
		int app_no = Integer.parseInt(request.getParameter("app_no"));
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		response.setContentType("application/json;charset=utf-8");

		//1. 세션에서 데이터 얻기
		HttpSession session = request.getSession();

		ApplyLineService service;
		Account a = (Account) session.getAttribute("loginInfo");
		if(a == null) {
			System.out.println("서블릿 11 null");
			Map<String, Integer> map = new HashMap<>();
			map.put("status", 0);
			out.print(mapper.writeValueAsString(map));
			
		}else {
			System.out.println("서블릿 111");
			//2. 비지니스 로직 호출
			ServletContext sc = getServletContext();
			ApplyLineService.envProp = sc.getRealPath(sc.getInitParameter("env"));
			//IndividualService service;
			service = ApplyLineService.getInstance();
			List<Payment> ap = null;
			System.out.println("서블릿 1111");
			try {
				ap = service.findPayment(app_no);

			} catch (FindException e) {
				e.printStackTrace();
			}
			System.out.println("서블릿 _ 객체 : "+ ap);
			String jsonStr = mapper.writeValueAsString(ap);
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(jsonStr);
			
			System.out.println("Servlet - syso : "+jsonStr);
		}
	}

}
	
