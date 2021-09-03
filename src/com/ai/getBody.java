package com.ai;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

// http request body에 담긴 json 데이터를 읽는데 사용한다.
public class getBody {
    public JSONObject getJsonObj(HttpServletRequest request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder(); // String Builder
        BufferedReader bufferedReader = null;
        JSONObject jsonObj = null;
        try(InputStream inputStream = request.getInputStream()){
            if(inputStream != null){
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[256];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0){
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
                String jsonStr = stringBuilder.toString();

                try {
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(jsonStr);
                    jsonObj = (JSONObject) obj;
                } catch (ParseException p) {
                    p.getErrorType();
                }

            }
        } catch (IOException e){
            throw e;
        } finally {
            if(bufferedReader != null){
                try{
                    bufferedReader.close();
                }catch(IOException e){
                    throw e;
                }
            }
        }

        // json 객체를 반환한다.
        return jsonObj;
    }
}
