package com.ai.sql;

import java.sql.*;

public class MyConnection {

    public static Connection getConnection() throws SQLException {
//        String url = System.getenv("AWS_HOST");
//        String user =  System.getenv("AWS_ADMIN");
//        String password = System.getenv("AWS_PW");

        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "hr";
        String password = "hr";

        Connection con = null;
        con = DriverManager.getConnection(url, user, password);

        return con;
    }

    public static void close(Connection con, Statement pstmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}

