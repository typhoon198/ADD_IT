package com.ai.control;

import com.ai.dto.Account;
import com.ai.exception.FindException;
import com.ai.getBody;
import com.ai.service.AccountService;
import com.ai.service.KakaoApiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


public class LoginServlet extends HttpServlet {

    // 카카오 간편로그인 로직
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //응답헤더 설정
        response.addHeader("Access-Control-Allow-Origin", "*");

        // 요청 전달 데이터 얻기
        String code = request.getParameter("code");

        // 비지니스 로직 호출
        HttpSession session = request.getSession();
        session.removeAttribute("loginInfo");
        // 카카오 관련 비스니스 로직 호출
        KakaoApiService service = KakaoApiService.getInstance();
        String access_token = service.getAccessToken(code);
        HashMap<String, Object> userInfo = service.getUserInfo(access_token);

        // 클라이언트의 이메일이 존재할 경우 세션에 해당 이메일과 토큰을 등록한다.
        if (userInfo.get("nickname") != null) {
            Account loginInfo = new Account((String) userInfo.get("nickname"), 1);
            session.setAttribute("loginInfo", loginInfo);
            session.setAttribute("access_Token", access_token);
            response.sendRedirect("http://localhost:8888/Add_it/");
        } else {
            // 로그인 체크 서블릿으로 보낸다.
            response.sendRedirect("http://localhost:8888/Add_it?login=fail");
        }

        //todo 로그인은 완료, 광고를 신청하려고 할 경우 마이페이지에서 주소지를 입력하게 유도해야한다.
        // 세션에 저장하는 정보가 1이 아니라 3으로 둬서 카카오 간편 로그인 회원임을 알리도록 해야할 수도 있다.
    }

    // 일반 로그인 로직
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //응답헤더 설정
        response.addHeader("Access-Control-Allow-Origin", "*"); // 모든 경로 허용
        response.setContentType("application/json");
        ObjectMapper mapper;
        mapper = new ObjectMapper();
        String jsonStr = "";

        //요청 데이터 얻기 (Http request body로부터 읽어온다.)
        getBody getBody = new getBody();
        JSONObject jsonObj = getBody.getJsonObj(request);

        String id = (String) jsonObj.get("id");
        String pwd = (String) jsonObj.get("pwd");
        getBody = null;

        // 아래는 쿼리 파라미터로 받아오는 방식 (권장하지 않음)
        // String id = request.getParameter("id");
        // String pwd = request.getParameter("pwd");

        ServletContext sc = getServletContext();
        AccountService.envProp = sc.getRealPath(sc.getInitParameter("env"));
        AccountService service = AccountService.getInstance();

        //todo 비즈니스 로직
        // 세션에 로그인 정보를 저장한다.
        HttpSession session = request.getSession();
        session.removeAttribute("loginInfo");
        Map<String, Object> map = new HashMap<>();
        try {
            Account loginInfo = service.login(id, pwd);

            //세션에 로그인 정보를 저장한다.
            session.setAttribute("loginInfo", loginInfo);
            map.put("status", 1);

        } catch (FindException e) {
            e.printStackTrace();
            map.put("status", 0);
            map.put("msg", e.getMessage());
        }
        jsonStr = mapper.writeValueAsString(map);

        //응답
        response.setContentType("application/json;charset=utf-8");
        //response.sendError(200, "isOk");
        PrintWriter out = response.getWriter();
        out.print(jsonStr);
    }

}
