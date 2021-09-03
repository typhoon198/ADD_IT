package com.ai.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ai.dto.Account;
import com.ai.dto.Company;
import com.ai.exception.FindException;
import com.ai.service.CompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;

//@WebServlet("/addload")
public class AddPageLoadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper;
		mapper = new ObjectMapper();
		String jsonStr="";
		HttpSession session = request.getSession();
		Map<String, Object> map = new HashMap<>(); /// 비로그인:0 / 개인회원로그인:1 / 기업회원로그인:2 / 업체명,업종 조회 실패-1

		//Account a = new Account();
		//a.setId("cid7");
		//a.setUser_type(1);
		//로그인 정보 확인
		Account a = (Account)session.getAttribute("loginInfo");
		if(a == null){ //비로그인
			map.put("status", 0);
			map.put("msg", "로그인후 이용하세요.");
		} else { 
			if(a.getUser_type()==1) { //개인회원으로 로그인한 경우
				map.put("status", 1);
				map.put("msg", "개인회원은 등록하실수 없습니다.");
			} else if(a.getUser_type()==2) { //기업회원으로 로그인한 경우
				ServletContext sc = getServletContext();
				CompanyService.envProp = sc.getRealPath(sc.getInitParameter("env"));
				CompanyService service;
				service = CompanyService.getInstance();
				try { //기업회원로그인한 경우
					Company com = service.findInfo2(a.getId());
					map.put("status", 2);
					map.put("name",com.getCom_name());
					map.put("bt", String.valueOf(com.getCom_bt()));
				} catch (FindException e) { //조회에 실패 경우
					e.printStackTrace();
					map.put("status", -1);
				}
			}
		}
		jsonStr = mapper.writeValueAsString(map);
		response.setContentType("application/json;charset=utf-8"); //응답형식지정
		response.getWriter().print(jsonStr);

	}
}

