package com.ai.control;

import java.io.IOException;
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
import com.ai.exception.FindException;
import com.ai.service.ApplyService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CompareWithApplyDateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper;
		mapper = new ObjectMapper();
		String jsonStr = "";
		
		HttpSession session = request.getSession();
		Map<String, Object> map = new HashMap<>();
		Account a = (Account)session.getAttribute("loginInfo");
		//비로그인:0 / 기업회원:2 / 개인회원신청불가:-2 / 개인회원신청가능:2 / DB연결실패:-3
		
		System.out.println("test : " + a);
		if(a == null){ //비로그인
			map.put("status", 0);
			map.put("msg", "로그인하세요");
			
		}	else {
			if(a.getUser_type() == 2) { //기업회원으로 로그인한 경우
				map.put("status", 1);
				map.put("msg", "신청할 수 없는 회원유형입니다.");
				System.out.println(map);

			} else if(a.getUser_type() == 1) { //개인회원으로 로그인한 경우
				ServletContext sc = getServletContext();
				ApplyService.envProp = sc.getRealPath(sc.getInitParameter("env"));
				ApplyService service;
				service = ApplyService.getInstance();
				try {
					String id = a.getId();
					Apply app = service.findLatelyApply(id);
					
					System.out.println(app.getApp_state());
					if(app.getApp_state() == -1) {
						map.put("status", 2); //최근신청상태 거절
					}
					else {
						map.put("status", -2); //최근신청상태 수락,대기
						map.put("msg","수락 대기중인 신청내역이 있습니다");
					}						

				} catch (FindException e) {
					System.out.println(e.getMessage());
					if(e.getMessage().equals("0")) {
						map.put("status", 2); //신청내역이없는경우도 신청가능
					} else {
						e.printStackTrace();
						map.put("status", -3); //DB연결 실패
						map.put("msg", "DB연결실패");
					}
				}
			}

		}
		jsonStr = mapper.writeValueAsString(map);
		System.out.println(jsonStr);
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().print(jsonStr);
		//response.getWriter().print(jsonStr2);

	}
}
