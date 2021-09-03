package com.ai.dao;

import com.ai.exception.FindException;
import com.ai.sql.MyConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class testDAO {
    public testDAO() throws Exception {

        // JDBC 드라이버 로드
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("JDBC 드라이버 로드 성공");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }

    public void selectId() throws FindException {
        Connection con = null;
        try {
            con = MyConnection.getConnection();
            System.out.println("------------------------------");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        }

        String query = "SELECT * FROM ACCOUNT";

        ResultSet rs = null;
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            System.out.println("SQL 구문 DB에 송신, 결과 수신");

            while(rs.next()) {
                String id = rs.getString("ID");
                int pwd = rs.getInt("PWD");
                int u_type = rs.getInt("USER_TYPE");
                System.out.println(id + " : " + pwd + " : " + u_type);
            }
            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            MyConnection.close(con, stmt, rs);
        }
    }

    public static void main(String[] args) {
        try {
            testDAO testDAO = new testDAO();
            testDAO.selectId();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}