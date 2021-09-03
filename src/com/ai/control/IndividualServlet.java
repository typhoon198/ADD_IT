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
import com.ai.dto.Individual;
import com.ai.exception.FindException;
import com.ai.exception.ModifyException;
import com.ai.service.IndividualService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class IndividualServlet extends HttpServlet {
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

    // doGet
    protected void modify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();

        //1. 세션에서 데이터 얻기
        HttpSession session = request.getSession();


        Individual in = (Individual) session.getAttribute("indiInfo");
        Individual indi = new Individual();

        String in_id = request.getParameter("in_id");
        String in_email = request.getParameter("in_email");
        String in_phone = request.getParameter("in_phone");
        String in_zipcode = request.getParameter("in_zipcode");
        String in_address = request.getParameter("in_address");
        String in_cartype = request.getParameter("in_cartype");
        System.out.println(in_email);
        System.out.println(in_phone);
        System.out.println(in_zipcode);
        System.out.println(in_address);
        System.out.println(in_cartype);


        indi.setIn_id(in_id);
        if (!in.getIn_email().equals(in_email)) {
            indi.setIn_email(in_email);
            in.setIn_email(in_email);
        }
        if (!in.getIn_phone().equals(in_phone)) {
            indi.setIn_phone(in_phone);
            in.setIn_phone(in_phone);
        }
        if (!(in.getIn_zipcode()+"").equals(in_zipcode)) {
            indi.setIn_zipcode(Integer.parseInt(in_zipcode));
            in.setIn_zipcode(Integer.parseInt(in_zipcode));
        }
        if (!in.getIn_address().equals(in_address)) {
            indi.setIn_address(in_address);
            in.setIn_address(in_address);
        }
        if (!(in.getIn_cartype()+"").equals(in_cartype)) {
            indi.setIn_cartype(Integer.parseInt(in_cartype));
            in.setIn_cartype(Integer.parseInt(in_cartype));
        }

        System.out.println("indi :"+ indi);

        IndividualService service;
        Map<String, Integer> map = new HashMap<>();
        if(in == null) {
            map.put("status", 0);
            out.print(mapper.writeValueAsString(map));
        }else {
            //2. 비지니스 로직 호출
            ServletContext sc = getServletContext();
            //IndividualService.envProp = sc.getRealPath(sc.getInitParameter("env"));
            service = IndividualService.getInstance();

            try {
                service.modify(indi);
                map.put("status", 1); // 성공
                session.setAttribute("indiInfo", in);
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

    // doPost
    protected void findInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8"); //응답형식지정
        PrintWriter out = response.getWriter();


        //1. 세션에서 데이터 얻기
        HttpSession session = request.getSession();

        Account ac = (Account) session.getAttribute("loginInfo");
        System.out.println("a.getId() :"+ac.getId());

        IndividualService service;
        if(ac == null) {
            Map<String, Integer> map = new HashMap<>();
            map.put("status", 0);
            out.print(mapper.writeValueAsString(map));
        }else {
            //2. 비지니스 로직 호출
            ServletContext sc = getServletContext();
            IndividualService.envProp = sc.getRealPath(sc.getInitParameter("env"));
            service = IndividualService.getInstance();
            Individual indi = null;
            try {
                System.out.println("서블릿 서비스의 매개변수 :"+ac.getId());

                indi = service.findInfo(ac.getId());
                System.out.println("서블릿 반환값 :"+indi);


            } catch (FindException e) {
                e.printStackTrace();
            }
            String jsonStr = mapper.writeValueAsString(indi);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(jsonStr);

            // 정보 수정에서 사용함
            session.setAttribute("indiInfo", indi);
        }
    }
}


///////////////////// 로그인 대체 과정 ////////////////////////

//IndividualService service;
//Individual info = new Individual();
//info.setIn_id("indi1");
//session.setAttribute("loginInfo", info);

//////////////////////////////////////////////////////////