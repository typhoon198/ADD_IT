package com.ai.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ai.dto.Account;
import com.ai.dto.Bank;
import com.ai.exception.FindException;
import com.ai.service.BankService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AcnoInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper;
		mapper = new ObjectMapper();
		String jsonStr = "";
		
		HttpSession session = request.getSession();
		Map<String, Object> map = new HashMap<>();
		
		Account a = (Account)session.getAttribute("loginInfo");
		String in_id = a.getId();
		System.out.println(in_id);
		if(a.getUser_type() == 1) { //개인회원으로 로그인한 경우
			BankService service;
			ServletContext sc = getServletContext();
			BankService.envProp = sc.getRealPath(sc.getInitParameter("env"));
			service = BankService.getInstance();
			
			try {
				Bank b = service.findById(in_id);
				jsonStr = mapper.writeValueAsString(b);
				System.out.println(b);
				map.put("info", 1);
			} catch (FindException e) {
				e.printStackTrace();
				map.put("info", 0);
			}
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(jsonStr);
			
		}
	}

}
