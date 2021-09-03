package com.ai.control;

import java.io.IOException;
import java.io.PrintWriter;
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

public class AccServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper;
		mapper = new ObjectMapper();
		String jsonStr = "";
		
		HttpSession session = request.getSession();
		Map<String, Object> map = new HashMap<>();
		
		Account a = (Account) session.getAttribute("loginInfo");
		
		
		if (a==null) {
			map.put("userType", -1); // type 없음
		} else {
			String id = a.getId();
			System.out.println(id);
			
			if(a.getUser_type() == 1) { // 개인
				map.put("userType", 1);
			} else if (a.getUser_type() == 2) { // 기업
				map.put("userType", 2);
			} 
		}

		jsonStr = mapper.writeValueAsString(map);
		response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(jsonStr);
		
	}

}
