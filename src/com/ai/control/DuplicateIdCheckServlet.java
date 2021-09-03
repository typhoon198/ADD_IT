package com.ai.control;

import com.ai.exception.FindException;
import com.ai.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class DuplicateIdCheckServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //응답헤더 설정
        response.addHeader("Access-Control-Allow-Origin", "*"); // 모든 경로 허용
        ObjectMapper mapper;
        mapper = new ObjectMapper();
        String jsonStr = "";

        //요청 데이터 얻기 (Http request body로부터 읽어온다.)
        String id = request.getParameter("id");

        ServletContext sc = getServletContext();
        AccountService.envProp = sc.getRealPath(sc.getInitParameter("env"));
        AccountService service = AccountService.getInstance();

        //todo 비즈니스 로직
        Map<String, Object> map = new HashMap<>();
        try {
            boolean check = service.duplicateIdCheck(id);
            if (!check) { // 중복 아이디가 없는 경우
                map.put("status", 1);
            } else { // 중복 아이디가 있는 경우
                map.put("status", 0);
            }

        } catch (FindException e) { // 에러가 발생한 경우
            e.printStackTrace();
            map.put("status", -1);
            map.put("msg", e.getMessage());
        }
        jsonStr = mapper.writeValueAsString(map);

        //응답
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(jsonStr);
    }
}
