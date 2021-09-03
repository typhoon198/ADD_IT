package com.ai.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ai.dto.Account;
import com.ai.dto.ApplyList;
import com.ai.dto.ApplyListPageBean;
import com.ai.exception.FindException;
import com.ai.service.AccountService;
import com.ai.service.ApplyListService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class ApplyListServlet
 */
public class ApplyListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

//	   protected void doGet(HttpServletRequest request, HttpServletResponse response)
//		         throws ServletException, IOException {
//		      // 요청전달데이터(이름:opt)가 전달되지 않는 경우
//		      String opt = request.getParameter("opt");
//		      if (opt.equals("getList")) {
//		    	  getList(request, response);
//		      } else if (opt.equals("redirect")) {
//		         redirect(request, response);
//		      }
//		   }
	   
	

	   //getList
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        //응답헤더 설정
	        response.addHeader("Access-Control-Allow-Origin", "*"); // 모든 경로 허용
	        response.setContentType("application/json");
	        ObjectMapper mapper = new ObjectMapper();
	        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));//JSON에는 date가 숫자형태로전송 변환
	        
	        String jsonStr = "";
			Map<String, Object> map = new HashMap<>();
			HttpSession session = request.getSession();

	        //1. 요청데이터 얻기
			Account ac = (Account) session.getAttribute("loginInfo");
			String id = ac.getId();
			//http://localhost:8888/Add_it/applylist?id=indi7&currentPage=1&field=&query=
			
			String url = "applylist";
			//String id = request.getParameter("id"); // 검색 분류 (회사명 / 수락상태)
			String currentPage_ = request.getParameter("currentPage");
			String field = request.getParameter("field"); // 검색 분류 (회사명 / 수락상태)
			String query = request.getParameter("query"); // 검색어
			
			int currentPage = 1;
			if(currentPage_ != null && !currentPage_.contentEquals("")) {
				currentPage = Integer.parseInt(currentPage_);
			}
			
			if(field != null && field.equals("app_state")) {

				switch(query) {
					case "수락": query = "1"; break;
					case "대기": query = "0"; break;
					case "거절": query = "-1"; break;
				}
				System.out.println("query : "+query);

			}
			

	        ServletContext sc = getServletContext();
	        ApplyListService.envProp = sc.getRealPath(sc.getInitParameter("env"));
	        ApplyListService service = ApplyListService.getInstance();
	        List<ApplyList> list = new ArrayList<>();
	        
	        System.out.println("서블릿 field :"+ field + "/ query : "+query +"/ currentPage : "+currentPage);
	        //비즈니스 로직 호출
	        try {
	        	int totalCnt = service.findApplyListCount(id, field, query);
	        	System.out.println("totalCnt :"+ totalCnt);
				int totalPage = (int) Math.ceil( 1.0*totalCnt / ApplyListPageBean.CNT_PER_PAGE);
				list = service.findApplyList(id, field, query, currentPage);
				ApplyListPageBean<ApplyList> pb 
					= new ApplyListPageBean<ApplyList>(currentPage, totalPage, list, url); 
				
				jsonStr = mapper.writeValueAsString(pb);
				map.put("result", 1); //성공
			} catch (FindException e) {
				e.printStackTrace();
				map.put("result", 0); //실패
			}
	        response.setContentType("application/json;charset=utf-8");
	        response.getWriter().print(jsonStr);
			
	        
		}
		
		
		   
	   private void redirect(HttpServletRequest request, HttpServletResponse response)
		         throws ServletException, IOException {
		      // 응답형식 지정: text/html
		      response.setContentType("text/html;charset=UTF-8");
		      // 응답출력스트림 얻기
		      PrintWriter out = response.getWriter();
		      out.print("before redirect");

		      String location = "first";
		      response.sendRedirect(location);
		      
		      out.print("after redirect");
	   }
	
}
