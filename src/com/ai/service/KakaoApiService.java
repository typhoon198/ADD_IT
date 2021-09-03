package com.ai.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class KakaoApiService {
    private static KakaoApiService service;

    public static KakaoApiService getInstance() {
        if (service == null) {
            service = new KakaoApiService();
        }
        return service;
    }

    public String getAccessToken(String authorize_code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            //HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            // Post 요청
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=a1be24597949e454b86b2921694fc65b");
            sb.append("&redirect_uri=http://localhost:8888/Add_it/login");
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();

            // 결과코드
            int responseCode = conn.getResponseCode();

            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            // JSON 파싱
            JSONObject jsonObj;
            try {
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(result);
                jsonObj = (JSONObject) obj;

                access_Token = (String) jsonObj.get("access_token");
                refresh_Token = (String) jsonObj.get("refresh_token");

                //System.out.println(access_Token);
            } catch (ParseException p) {
                p.getErrorType();
            } finally {
                br.close();
                bw.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
    }

    // 요청하는 클라이언트마다의 정보가 다를 수 있으므로 HashMap 타입으로 선언한다.
    public HashMap<String, Object> getUserInfo(String access_token) {
        HashMap<String, Object> userInfo = new HashMap<>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            //    요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_token);

            //int responseCode = conn.getResponseCode();
            //System.out.println("responseCode:" + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            /*System.out.println("response body : " + result);*/

            JSONObject jsonObj;
            try {
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(result);
                jsonObj = (JSONObject) obj;

                JSONObject properties = (JSONObject) jsonObj.get("properties");
                JSONObject kakao_account = (JSONObject) jsonObj.get("kakao_account");

                String nickname = (String) properties.get("nickname");
                userInfo.put("nickname", nickname);


            } catch (ParseException p) {
                p.getErrorType();
            }finally {
                br.close();
            }

        }catch (IOException e) {
            e.printStackTrace();
        }

        return userInfo;
    }

    public void kakaoLogout(String access_Token) {
        String reqURL = "https://kapi.kakao.com/v1/user/logout";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String result = "";
            String line = "";

            /*while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println(result);*/
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
