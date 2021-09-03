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
import com.ai.exception.ModifyException;
import com.ai.service.BankService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UpdateAcnoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper;
		mapper = new ObjectMapper();
		String jsonStr="";
		
		HttpSession session = request.getSession();
		Map<String, Object> map = new HashMap<>();
		
		Account a = (Account)session.getAttribute("loginInfo");
		
		String bankinfo = request.getParameter("bankinfo");
		String acnoinfo = request.getParameter("acnoinfo");
		
		Bank b = new Bank();
		b.setIn_id(a.getId());
		b.setBank(bankinfo);
		b.setAcno(acnoinfo);
		
		ServletContext sc = getServletContext();
		BankService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		BankService service;
		service = BankService.getInstance();
		try {
			service.modify(b);
			map.put("update", 1);
		} catch (ModifyException e) {
			e.printStackTrace();
			map.put("update", -1);
		}
		jsonStr = mapper.writeValueAsString(map);
		response.setContentType("application/json;charset=utf-8"); //응답형식지정
		PrintWriter out = response.getWriter();
		out.print(jsonStr);
		System.out.println(jsonStr);
	}

}
