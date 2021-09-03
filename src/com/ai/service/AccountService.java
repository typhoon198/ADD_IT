package com.ai.service;

import com.ai.dao.AccountDAO;
import com.ai.dto.Account;
import com.ai.exception.AddException;
import com.ai.exception.FindException;

import java.io.FileInputStream;
import java.util.Properties;

public class AccountService {
    private AccountDAO dao;
    private static AccountService service;
    public static String envProp;

    private AccountService() {
        Properties env = new Properties();
        try {
            env.load(new FileInputStream(envProp));
            String className = env.getProperty("AccountDAO");
            Class c = Class.forName(className);
            dao = (AccountDAO) c.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static AccountService getInstance() {
        if (service == null) {
            service = new AccountService();
        }
        return service;
    }

    //Note 개인회원 회원가입
    public void signup_individual(Account c) throws AddException {
        dao.insert_individual(c);
    }

    //Note 기업회원 회원가입
    public void signup_company(Account c) throws AddException {
        dao.insert_company(c);
    }


    //Note 로그인
    public Account login(String id, String pwd) throws FindException {
        Account c = dao.selectById(id);
        if (c == null){
            // 해당하는 아이디가 없는 경우 null을 반환하도록 함.
            throw new FindException("해당하는 계정이 존재하지 않습니다.\n입력한 내용을 다시 확인해 주세요.");
        } else if (!c.getPwd().equals(pwd)) {
            throw new FindException("비밀번호가 일치하지 않습니다.\n입력한 내용을 다시 확인해 주세요.");
        } else
        return c;
    }

    //Note 중복 아이디 검사
    //true => 중복아이디 있음
    //false => 중복아이디 없음
    public boolean duplicateIdCheck(String id) throws FindException {
        if (dao.selectById(id) == null) { //중복 아이디 없음
            return false;
        } else { // 중복 아이디 있음
            return true;
        }
    }

}
