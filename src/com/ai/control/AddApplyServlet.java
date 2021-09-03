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
import com.ai.dto.Advertisement;
import com.ai.dto.Apply;
import com.ai.dto.Individual;
import com.ai.exception.AddException;
import com.ai.service.ApplyService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AddApplyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper;
		mapper = new ObjectMapper();
		String jsonStr="";
		
		HttpSession session = request.getSession();
		Map<String, Object> map = new HashMap<>(); //정상처리:1 / 비로그인:0 / 기업회원로그인:-1 / 신청추가실패:-2
		
		//로그인 정보 확인
		Account a = (Account)session.getAttribute("loginInfo");
//		if(a == null){ //비로그인
//			map.put("status", 0);
//		} else { 
//			if(a.getUser_type() == 1) { //기업회원으로 로그인한 경우
//				map.put("status", -1);
//			} else if(a.getUser_type() == 2) { //개인회원으로 로그인한 경우
			//if(a != null) {
		
				//요청전달데이터 얻기
			String adv_noStr = request.getParameter("adv_no");
			System.out.println("광고번호 : " + adv_noStr);
			int adv_no = new Integer(adv_noStr);
			
			Advertisement adv = new Advertisement();
			adv.setAdv_no(adv_no);
			
			Apply app = new Apply();
			app.setAdv(adv);
			Individual i = new Individual();
			i.setIn_id(a.getId());
			app.setI(i);
			//System.out.println(i);
			System.out.println("a : " + a);
			System.out.println("i : " + i);
			
			ServletContext sc = getServletContext();
			ApplyService.envProp = sc.getRealPath(sc.getInitParameter("env"));
			ApplyService service;
			service = ApplyService.getInstance();
			try { //정상처리된 경우
				service.add(app);
				map.put("status", 1);
			} catch (AddException e) { //신청 추가 실패한 경우
				e.printStackTrace();
				map.put("msg", e.getMessage());
				map.put("status", -2);
			}
			
			//성공1
			//DB실패 -2
		//}
	//}
	jsonStr = mapper.writeValueAsString(map);
	response.setContentType("application/json;charset=utf-8"); //응답형식지정
	PrintWriter out = response.getWriter();
	out.print(jsonStr);
	}
	
}
