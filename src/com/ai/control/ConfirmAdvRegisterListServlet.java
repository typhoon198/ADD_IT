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

import com.ai.dto.Location;
import com.ai.exception.FindException;
import com.ai.exception.ModifyException;
import com.ai.service.ApplyService;
import com.ai.service.LocationService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfirmAdvRegisterListServlet extends HttpServlet {

	   private static final long serialVersionUID = 1L;
	   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	      //status -1: 거절 성공
	      //status  1 : 수락성공
	      //status -2 : 상태변경 실패
	      int app_state = Integer.parseInt(request.getParameter("app_state"));
	      int app_no  =  Integer.parseInt(request.getParameter("app_no"));
	      ApplyService service;
	      ServletContext sc = getServletContext();
	      ApplyService.envProp = sc.getRealPath(sc.getInitParameter("env"));
	      service = ApplyService.getInstance();
	      ObjectMapper mapper = new ObjectMapper();
	      String jsonStr ="";
	      Map<String, Object> map = new HashMap<>();
	         try {
	            service.update(app_no, app_state);
	            if(app_state==1) {
	               map.put("status", 1);
	               map.put("msg", "수락");
	            }else{
	               map.put("status", -1);
	               map.put("msg", "거절");
	            }
	         } catch (ModifyException e) {
	            map.put("status", -2);
	            map.put("msg", "실패");
	            e.printStackTrace();
	         }
	         jsonStr = mapper.writeValueAsString(map);
	         response.setContentType("application/json;charset=utf-8");
	         response.getWriter().print(jsonStr);
	   }

	}
