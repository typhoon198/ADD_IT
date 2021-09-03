package com.ai.control;

import java.io.IOException;
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
import com.ai.dto.Advertisement;
import com.ai.dto.AdvertisementLine;
import com.ai.dto.Apply;
import com.ai.exception.FindException;
import com.ai.service.AdvertisementService;
import com.ai.service.ApplyService;
import com.fasterxml.jackson.databind.ObjectMapper;


public class AdvertisementRegisterListServlet extends HttpServlet {

	   private static final long serialVersionUID = 1L;
	   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	      ObjectMapper mapper = new ObjectMapper();
	      mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

	      String jsonStr="";
	      HttpSession session = request.getSession();

	      //0  세션이 끊긴경우 map
	      //-1 지원자 목록이 없을때 map
	      //정상 : 지원자 list
	      Map<String, Object> map = new HashMap<>(); 
	      List<Apply> list =null;
	      
//	      Account a = (Account)session.getAttribute("loginInfo");
//	      if(a == null){
//	         map.put("status", 0);
//	         map.put("msg", "로그인후 이용하세요.");
//	         jsonStr = mapper.writeValueAsString(map);
	//
//	      } else { //기업회원만 마이페이지 접근, 회원유형 확인 필요없음
	      
	          
	         String adv_no_str = request.getParameter("adv_no");
	         int adv_no = Integer.parseInt(adv_no_str);
	         ApplyService service;
	         ServletContext sc = getServletContext();
	         ApplyService.envProp = sc.getRealPath(sc.getInitParameter("env"));
	         service = ApplyService.getInstance();
	         try {
	            list = service.selectApplybyAdvNo(adv_no);
	            jsonStr = mapper.writeValueAsString(list);

	         } catch (FindException e) { //조회에 실패 경우
	            //AdvertisementLineDAOOracle 51번째줄 리스트가 0인경우 new FindExecption("noapplylist")
//	            if(e.getMessage().equals("noapplylist")) {
//	               map.put("status", 1);
//	               map.put("msg","진행한 광고가 없습니다.");
//	               jsonStr = mapper.writeValueAsString(map);
//	            } 
//	            e.printStackTrace();//DB연결 오류는 처리 x
	         }
	      //}end of else
	      response.setContentType("application/json;charset=utf-8");
	      response.getWriter().print(jsonStr);
	      System.out.println(jsonStr);
	   }
	}

