package com.ai.control;

import com.ai.service.KakaoApiService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogOutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if ((String)session.getAttribute("access_Token") != null) { // 카카오 간편 로그인인 경우
            KakaoApiService service = KakaoApiService.getInstance();
            service.kakaoLogout((String)session.getAttribute("access_token"));
        }

        session.invalidate(); //세션 삭제
    }
}
