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
import com.ai.dto.Apply;
import com.ai.dto.Individual;
import com.ai.exception.FindException;
import com.ai.service.ApplyService;
import com.ai.service.IndividualService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApplyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("apply servlet");
		String param = (String) request.getParameter("type");
		System.out.println("param - : "+param);
		switch(param) {
			case "1" :
				findApply(request, response);
				break;
			case "2" :
				findAccept(request, response);
				break;
		}
	}
	
	// findApply
	protected void findApply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));//JSON에는 date가 숫자형태로전송 변환
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();

		
		//1. 세션에서 데이터 얻기
		HttpSession session = request.getSession();

		ApplyService service;
		Account ac = (Account) session.getAttribute("loginInfo");
		System.out.println("a.getId() :"+ac.getId());
		
		if(ac == null) {
			Map<String, Integer> map = new HashMap<>();
			map.put("status", 0);
			out.print(mapper.writeValueAsString(map));
			
		}else {
			//2. 비지니스 로직 호출
			ServletContext sc = getServletContext();
			ApplyService.envProp = sc.getRealPath(sc.getInitParameter("env"));
			System.out.println("apply env "+ ApplyService.envProp);
			//IndividualService service;
			service = ApplyService.getInstance();
			List<Apply> ap = null;
			try {
				ap = service.findApply(ac.getId());

			} catch (FindException e) {
				e.printStackTrace();
			}
			String jsonStr = mapper.writeValueAsString(ap);
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(jsonStr);
			System.out.println("syso : "+jsonStr);
		}
	}
	// findAccept
		protected void findAccept(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			//response.getWriter().append("Served at: ").append(request.getContextPath());

			ObjectMapper mapper = new ObjectMapper();
			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));//JSON에는 date가 숫자형태로전송 변환
			response.setContentType("application/json;charset=utf-8");
			PrintWriter out = response.getWriter();

			
			//1. 세션에서 데이터 얻기
			HttpSession session = request.getSession();
			

			ApplyService service;
			Account ac = (Account) session.getAttribute("loginInfo");
			System.out.println("a.getId() :"+ac.getId());
			if(ac == null) {
				Map<String, Integer> map = new HashMap<>();
				map.put("status", 0);
				out.print(mapper.writeValueAsString(map));
				
			}else {
				//2. 비지니스 로직 호출
				ServletContext sc = getServletContext();
				ApplyService.envProp = sc.getRealPath(sc.getInitParameter("env"));
				//IndividualService service;
				service = ApplyService.getInstance();
				List<Apply> ap = null;
				try {
					ap = service.findAccept(ac.getId());

				} catch (FindException e) {
					e.printStackTrace();
				}
				System.out.println("apply 서블릿 _ 객체 : "+ ap);
				String jsonStr = mapper.writeValueAsString(ap);
				response.setContentType("application/json;charset=utf-8");
				response.getWriter().print(jsonStr);
				System.out.println("syso : "+jsonStr);
			}
			
		}

}
