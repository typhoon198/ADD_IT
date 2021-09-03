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
import com.ai.dto.Company;
import com.ai.exception.FindException;
import com.ai.exception.ModifyException;
import com.ai.service.CompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;


public class CompanyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	      
	      
	      String type = request.getParameter("type"); //data: {type : 1} 
	      System.out.println("type : "+type);

	      switch(type) {
	         case "1" :
	            findInfo(request, response);
	            break;
	         case "2" :
	            modify(request, response);
	            break;
	      }
	   }

	//doPost
	protected void findInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
	    response.setContentType("application/json;charset=utf-8"); //응답형식지정
	    PrintWriter out = response.getWriter();
	    
	    //1. 세션에서 데이터 얻기
        HttpSession session = request.getSession();
      
        Account ac = (Account) session.getAttribute("loginInfo");
        System.out.println("ac.getId() :" + ac.getId());
        
        CompanyService service;
        if(ac == null) {
            Map<String, Integer> map = new HashMap<>();
            map.put("status", 0);
            out.print(mapper.writeValueAsString(map));
         }else {
        	//2. 비지니스 로직 호출
             ServletContext sc = getServletContext();
             CompanyService.envProp = sc.getRealPath(sc.getInitParameter("env"));
             service = CompanyService.getInstance();
             Company com = null;
             try {
                 System.out.println("서블릿 서비스의 매개변수 :" + ac.getId());
                 com = service.findInfo(ac.getId());
                 System.out.println("서블릿 반환값 :" + com);
             } catch (FindException e) {
                 e.printStackTrace();
             }
             String jsonStr = mapper.writeValueAsString(com);
             response.setContentType("application/json;charset=utf-8");
             response.getWriter().print(jsonStr);
             
             // 정보 수정에서 사용함
             session.setAttribute("cInfo", com);
         }
		
	}

	//doGet
	protected void modify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		ObjectMapper mapper = new ObjectMapper();
	    response.setContentType("application/json;charset=utf-8");
	    PrintWriter out = response.getWriter();
	    
	    //1. 세션에서 데이터 얻기
	    HttpSession session = request.getSession();
	    
	    Company com = (Company) session.getAttribute("cInfo");
	    Company cm = new Company();
	    
	    String com_id = request.getParameter("com_id");
	    cm.setCom_id(com_id);
	    String com_email = request.getParameter("com_email");
	    String com_phone = request.getParameter("com_phone");
	    String com_zipcode = request.getParameter("com_zipcode");
	    String com_address = request.getParameter("com_address");
	    
	    if(!com.getCom_email().equals(com_email)) {
	    	cm.setCom_email(com_email);
	    }
	    if(!com.getCom_phone().equals(com_phone)) {
	    	cm.setCom_phone(com_phone);
	    }
	    if(!(com.getCom_zipcode()+"").equals(com_zipcode)) {
	    	cm.setCom_zipcode(Integer.parseInt(com_zipcode));
	    }
	    if(!com.getCom_addr().equals(com_address)) {
	    	cm.setCom_addr(com_address);
	    }
	    
	    System.out.println("com = " + cm);
	    
	    CompanyService service;
	    Map<String, Integer> map = new HashMap<>();
	    if(com == null) {
	         map.put("status", 0);
	         out.print(mapper.writeValueAsString(map));
	      }else {
	    	//2. 비지니스 로직 호출
	          ServletContext sc = getServletContext();
	          service = CompanyService.getInstance();
	          
	          try {
				service.modify(cm);
				map.put("status", 1); // 성공
			} catch (ModifyException e) {
				e.printStackTrace();
				map.put("status", 2); // 실패
			}
	          String jsonStr = mapper.writeValueAsString(map);
	          response.setContentType("application/json;charset=utf-8");
	          response.getWriter().print(jsonStr);
	          System.out.println("syso : "+jsonStr);
	      }
	}
}
