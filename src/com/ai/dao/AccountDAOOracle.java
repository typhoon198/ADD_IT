package com.ai.dao;

import com.ai.dto.Account;
import com.ai.exception.AddException;
import com.ai.exception.FindException;
import com.ai.exception.ModifyException;
import com.ai.exception.RemoveException;
import com.ai.sql.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class AccountDAOOracle implements AccountDAO {
    public AccountDAOOracle() throws Exception {
        //JDBC드라이버로드
        Class.forName("oracle.jdbc.driver.OracleDriver");
    }

    //테스트 코드
//    public static void main(String[] args) {
//        AccountDAOOracle accountDAOOracle = null;
//        try {
//            String form = "1988-05-19";
//            accountDAOOracle = new AccountDAOOracle();
//            Individual indi = new Individual("sjabber", "김태호", "01098561765", "sjabber@naver.com", 36155, "서울 성북구 서성동 1번지", form, 1);
//            Account ac = new Account(indi.getIn_id(), "test1234!", 1, indi);
//            accountDAOOracle.insert_individual(ac);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override //개인회원 회원가입
    public void insert_individual(Account account) throws AddException {

        // 회원가입시 패스워드 형식, 중복 아이디 검증
        if (signUpCheck(account)) {

            // Note 회원가입 진행
            Connection con = null;
            try {
                con = MyConnection.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new AddException(e.getMessage());
            }

            String query1 = "INSERT INTO account(id, pwd, user_type) VALUES(?, ?, ?)";
            String query2 = "INSERT INTO individual" +
                    "(in_id, in_name, in_phone, in_email, in_zipcode, in_address, in_birthday, in_car)" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt1 = null;
            PreparedStatement pstmt2 = null;
            try {
                // ACCOUNT 테이블에 추가
                pstmt1 = con.prepareStatement(query1);
                pstmt1.setString(1, account.getId());
                pstmt1.setString(2, account.getPwd());
                pstmt1.setInt(3, account.getUser_type());
                int cnt1 = pstmt1.executeUpdate();

                // INDIVIDUAL 테이블에 추가
                pstmt2 = con.prepareStatement(query2);
                pstmt2.setString(1, account.getIndividual().getIn_id());
                pstmt2.setString(2, account.getIndividual().getIn_name());
                pstmt2.setString(3, account.getIndividual().getIn_phone());
                pstmt2.setString(4, account.getIndividual().getIn_email());
                pstmt2.setInt(5, account.getIndividual().getIn_zipcode());
                pstmt2.setString(6, account.getIndividual().getIn_address());
                pstmt2.setDate(7, java.sql.Date.valueOf(account.getIndividual().getIn_birthday()));
                pstmt2.setInt(8, account.getIndividual().getIn_cartype());
                int cnt2 = pstmt2.executeUpdate();

                if (cnt1 == 0 || cnt2 == 0) {
                    con.rollback();
                    throw new AddException("Sign Up Failed");
                } else {
                    con.commit();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                MyConnection.close(con, pstmt1, null);
                MyConnection.close(con, pstmt2, null);
            }
        }
    }

    @Override
    public void insert_company(Account account) throws AddException {
        // 회원가입시 패스워드 형식, 중복 아이디 검증
        if(signUpCheck(account)) {
			// Note 회원가입 진행
			Connection con = null;
			try {
				con = MyConnection.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AddException(e.getMessage());
			}

			String query1 = "INSERT INTO account(id, pwd, user_type) VALUES(?, ?, ?)";
			String query2 = "INSERT INTO company" +
					"(com_id, com_name, com_phone, com_email, com_zipcode, com_addr, com_rn, com_bt)" +
					"VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt1 = null;
			PreparedStatement pstmt2 = null;
			try {
				// ACCOUNT 테이블에 추가
				pstmt1 = con.prepareStatement(query1);
				pstmt1.setString(1, account.getId());
				pstmt1.setString(2, account.getPwd());
				pstmt1.setInt(3, account.getUser_type());
				int cnt1 = pstmt1.executeUpdate();

				// INDIVIDUAL 테이블에 추가
				pstmt2 = con.prepareStatement(query2);
				pstmt2.setString(1, account.getCompany().getCom_id());
				pstmt2.setString(2, account.getCompany().getCom_name());
				pstmt2.setString(3, account.getCompany().getCom_phone());
				pstmt2.setString(4, account.getCompany().getCom_email());
				pstmt2.setInt(5, account.getCompany().getCom_zipcode());
				pstmt2.setString(6, account.getCompany().getCom_addr());
				pstmt2.setString(7, account.getCompany().getCom_rn());
				pstmt2.setInt(8, account.getCompany().getCom_bt());
				int cnt2 = pstmt2.executeUpdate();

				if (cnt1 == 0 || cnt2 == 0) {
					con.rollback();
					throw new AddException("Sign Up Failed");
				} else {
					con.commit();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
			    MyConnection.close(con, pstmt1, null);
			    MyConnection.close(con, pstmt2, null);
            }
		}
    }

    @Override
    public Account selectById(String id) throws FindException {
        Connection con = null;
        try {
            con = MyConnection.getConnection();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        }

        String query = "SELECT * FROM Account WHERE id=?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                // Account 객체를 반환한다.
                return new Account(rs.getString("id"), rs.getString("pwd"), rs.getInt("user_type"));
            }
            // 해당하는 아이디가 없는 경우
            return null;

        } catch (SQLException e) {
            //e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            MyConnection.close(con, pstmt, rs);
        }

    }

    @Override
    public void update(Account account) throws ModifyException {
        Connection con = null;
        try {
            con = MyConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModifyException(e.getMessage());
        }


        String updateSQL = "UPDATE ACCOUNT SET "; //pwd = 'p1', name = 'n1', buildingno='1'
        String updateSQL1 = " WHERE ID = ?";

        boolean flag = false;

        String pwd = account.getPwd();
        if (pwd != null || pwd.equals("")) {
            System.out.println("pwd : " + pwd);
            updateSQL += "pwd = '" + pwd + "'";
            flag = true;
        }

        System.out.println(updateSQL + updateSQL1);

        if (!flag) throw new ModifyException("수정할 내용이 없습니다.");

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(updateSQL + updateSQL1);
            pstmt.setString(1, account.getId());
            int rowcnt = pstmt.executeUpdate();

            System.out.println("총 " + rowcnt + "건이 변경되었습니다.");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModifyException(e.getMessage());
        } finally {
            MyConnection.close(con, pstmt, rs);
        }
    }

    @Override
    public void Delete(Account account) throws RemoveException {

    }

    public static void modify() { // 2-1) 고객은 비밀번호를 수정한다.
        AccountDAOOracle dao;
        try {
            System.out.println("modify()");
            dao = new AccountDAOOracle();
            Account a = new Account();
            a.setId("indi1");
            a.setPwd("0");
            dao.update(a);

        } catch (FindException e) {

            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean signUpCheck(Account account) throws AddException {
        if (account.getId().equals("")) {
            // 아이디가 공백인 경우
			System.out.println(account.getId());
            System.out.println("The ID is incorrect.");
            return false;
        } else if (!checkPw(account.getPwd())) {
            // 패스워드에는 최소 하나 이상의 숫자, 영문자, 특수문자가 포함되어야 한다.
            System.out.println("The password format is incorrect.");
            return false;
        }
//        else if (!checkId(account.getId())) {
//			// 중복 아이디 인지 검증한다.
//			System.out.println("The ID must already exist.");
//			return false;
//		}
        else {
            return true;
        }
    }

    // 중복된 아이디인지 검증한다.
    public boolean checkId(String id) throws AddException {
        // DB connection
        Connection con = null;
        try {
            con = MyConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AddException(e.getMessage());
        }

        String query = "SELECT id FROM account WHERE id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            // 아이디가 존재하는 경우
            if (rs.next()) {
                //return false;
                System.out.println(rs.getString("id"));
                if (rs.getString("id") != null) {
                    return false;
                }
            }

        } catch (SQLException e) {
            throw new AddException(e.getMessage());
        }

        return true;
    }

    public boolean checkPw(String pwd) {
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!^%*#?&]{8,16}$";
        if (!Pattern.matches(regex, pwd)) {
            return false;
        }

        return true;
    }
}



