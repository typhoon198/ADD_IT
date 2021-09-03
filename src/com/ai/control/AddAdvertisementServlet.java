package com.ai.control;

import com.ai.dto.Account;
import com.ai.dto.Advertisement;
import com.ai.dto.Company;
import com.ai.exception.AddException;
import com.ai.service.AdvertisementService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddAdvertisementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		Account a = (Account)session.getAttribute("loginInfo");

		//1. 요청전달데이터 얻기
		//셋중하나는 0이 아님 (유효성검사)
		int loc1 = (request.getParameter("location1")==null) ? 0 : Integer.parseInt(request.getParameter("location1")); //null 허용
		int loc2 = (request.getParameter("location2")==null) ? 0 : Integer.parseInt(request.getParameter("location2")); //null 허용
		int loc3 = (request.getParameter("location3")==null) ? 0 : Integer.parseInt(request.getParameter("location3")); //null 허용
		int location1 =0;
		int location2 =0;
		int location3 =0;
		if(loc1==0) {		
			if(loc2==0) {
				location1 = loc3;
				location2 = loc2;
				location3 = loc1;
			} else {
				location1 = loc2;
				location2 = loc1;
				location3 = loc3;
			}
		} else {
			if(loc2==0) {
				location1 = loc1;
				location2 = loc3;
				location3 = loc2;
			}else {
				location1 = loc1;
				location2 = loc2;
				location3 = loc3;
			}

		}
		int adv_fee = Integer.parseInt(request.getParameter("fee"));
		int adv_cartype = Integer.parseInt(request.getParameter("cartype"));
		int adv_total = Integer.parseInt(request.getParameter("total"));
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		Date adv_startmonth = null;
		Date adv_endmonth = null;
		try {
			adv_startmonth = fm.parse(request.getParameter("start"));
			adv_endmonth = fm.parse(request.getParameter("end"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		//2. 비지니스로직호출
		AdvertisementService service;
		ServletContext sc = getServletContext();
		AdvertisementService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = AdvertisementService.getInstance();

		ObjectMapper mapper = new ObjectMapper();
		String jsonStr ="";//로그인 안되었거나 세션이 끊기면 상태값 넘겨주기위해
		Map<String, Object> map = new HashMap<>();

		//status -1 : 등록 실패
		//status 0 : 로그인 안된 사용자
		//status 1 : 등록 성공
		if(a == null) {//세션이 끊긴상태(로그인여부는 페이지 로드때 한번확인)
			map.put("status", 0);
			map.put("msg", "로그인을 다시해주세요");
			jsonStr = mapper.writeValueAsString(map);
		} else if(a.getUser_type()==2){//한번더 확인
			Advertisement adv = new Advertisement();
			Company com = new Company();
			com.setCom_id(a.getId());	//세션의 아이디를 기업아이디로 설정
			adv.setC(com);
			adv.setAdv_location1(location1);
			adv.setAdv_location2(location2);
			adv.setAdv_location3(location3);
			adv.setAdv_fee(adv_fee);
			adv.setAdv_cartype(adv_cartype);
			adv.setAdv_startmonth(adv_startmonth);
			adv.setAdv_endmonth(adv_endmonth);
			adv.setAdv_total(adv_total);
			try {
				map.put("status", 1);
				map.put("msg", "광고를 성공적으로 등록하였습니다.(마이페이지에서 확인가능)");
				service.add(adv);
			} catch (AddException e) {
				map.put("status", -1);
				map.put("msg", "예상치 못한 문제로  등록에 실패했습니다.");
				e.printStackTrace();
			}
		}
		jsonStr = mapper.writeValueAsString(map);
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().print(jsonStr);
	}
}

