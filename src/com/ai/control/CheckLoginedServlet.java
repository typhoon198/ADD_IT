package com.ai.control;

import com.ai.dto.Account;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class CheckLoginedServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper;
        mapper = new ObjectMapper();
        String jsonStr ="";

        HttpSession session = request.getSession();
        Map<String, Object> map = new HashMap<>();
        Account c = (Account) session.getAttribute("loginInfo");
        int status;
        if(c == null) {
            // case logout
            status = 0;
        }else {
            // case login
            status = 1;
        }
        map.put("status", status);
        jsonStr = mapper.writeValueAsString(map);

        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(jsonStr);
    }
}
