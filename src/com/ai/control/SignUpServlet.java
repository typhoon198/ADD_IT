package com.ai.control;

import com.ai.dto.Account;
import com.ai.dto.Company;
import com.ai.dto.Individual;
import com.ai.exception.AddException;
import com.ai.getBody;
import com.ai.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class SignUpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //응답헤더 설정
        response.addHeader("Access-Control-Allow-Origin", "*"); // 모든 경로 허용
        response.setContentType("application/json");
        ObjectMapper mapper;
        mapper = new ObjectMapper();
        String jsonStr = "";

        //1. 요청데이터 얻기
        getBody getBody = new getBody();
        JSONObject jsonObj = getBody.getJsonObj(request);
        getBody = null;

        String id = (String) jsonObj.get("id");
        String pwd = (String) jsonObj.get("pwd");
        int user_type = Integer.parseInt((String) jsonObj.get("user_type"));

        Individual individual;
        Company company;
        Account account = null;

        ServletContext sc = getServletContext();
        AccountService.envProp = sc.getRealPath(sc.getInitParameter("env"));
        AccountService service = AccountService.getInstance();

        //비즈니스 로직 호출
        Map<String, Object> map = new HashMap<>();
        if (user_type == 1) {
            String in_name = (String) jsonObj.get("name");
            String in_phone = (String) jsonObj.get("phone");
            String in_email = (String) jsonObj.get("email");
            int in_zipcode = Integer.parseInt((String) jsonObj.get("zipcode"));
            String in_address = (String) jsonObj.get("address");
            String in_birthday = (String) jsonObj.get("birthday");
            int in_cartype = Integer.parseInt((String) jsonObj.get("car_type"));

            individual = new Individual(id, in_name, in_phone, in_email, in_zipcode,
                    in_address, in_birthday, in_cartype);

            account = new Account(id, pwd, 1, individual);

            try {
                // 개인 OR 기업회원 회원가입 진행
                service.signup_individual(account);
                map.put("status", 1);

            } catch (AddException e) {
                e.printStackTrace();
                map.put("status", 0);
                map.put("msg", e.getMessage());
            }

        } else if (user_type == 2) {
            String com_name = (String) jsonObj.get("name");
            String com_phone = (String) jsonObj.get("phone");
            String com_email = (String) jsonObj.get("email");
            int com_zipcode = Integer.parseInt((String) jsonObj.get("zipcode"));
            String com_address = (String) jsonObj.get("address");
            String com_rn = (String) jsonObj.get("com_rn");
            int com_bt = Integer.parseInt((String) jsonObj.get("com_bt"));


            company = new Company(id, com_name, com_phone, com_email, com_zipcode,
                    com_address, com_rn, com_bt);

            account = new Account(id, pwd, 2, company);

            try {
                // 개인 OR 기업회원 회원가입 진행
                service.signup_company(account);
                map.put("status", 1);

            } catch (AddException e) {
                e.printStackTrace();
                map.put("status", 0);
                map.put("msg", e.getMessage());
            }
        }
        //응답
        jsonStr = mapper.writeValueAsString(map);
        response.setContentType("application/json;charset=utf-8");
        //response.sendError(200, "isOk");
        PrintWriter out = response.getWriter();
        out.print(jsonStr);
    }
}
