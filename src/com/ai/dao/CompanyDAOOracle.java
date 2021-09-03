package com.ai.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ai.dto.Account;
import com.ai.dto.Company;
import com.ai.exception.AddException;
import com.ai.exception.FindException;
import com.ai.exception.ModifyException;
import com.ai.sql.MyConnection;

public class CompanyDAOOracle implements CompanyDAO {
	
	public CompanyDAOOracle() throws Exception{
		//JDBC드라이버로드		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("JDBC드라이버로드 성공");		
	}
	
	@Override
	public Company selectById(String id) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		String selectByIdSQL="SELECT * FROM company WHERE com_id = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(selectByIdSQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String com_id = rs.getString("com_id");
				String com_name = rs.getString("com_name");
				String com_phone = rs.getString("com_phone");
				String com_email = rs.getString("com_email");
				int com_zipcode = rs.getInt("com_zipcode");
				String com_addr = rs.getString("com_addr");
				String com_rn = rs.getString("com_rn");
				int com_bt = rs.getInt("com_bt");
				Company com = new Company(com_id, com_name, com_phone, com_email, com_zipcode, com_addr, com_rn, com_bt);
				return com;
			}
			throw new FindException("존재하지 않는 아이디입니다.");
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
	}

	@Override
	public void update(Company c) throws ModifyException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		}
		String updateSQL = "UPDATE company SET ";
		String updateSQL1 = " WHERE com_id = ?";
		
		boolean flag = false;
		
		String com_phone = c.getCom_phone();
		String com_email = c.getCom_email();
		int com_zipcode = c.getCom_zipcode();
		String com_addr = c.getCom_addr();
		
		if(com_phone != null && !com_phone.equals("")) {
			updateSQL += "com_phone = '" + com_phone + "'";
			flag = true;
		}
		
		if(com_email != null && !com_email.equals("")) {
			if(flag) updateSQL += ", ";
			updateSQL += "com_email = '" + com_email + "'";
			flag = true;
		}
		
		if(com_zipcode != 0) {
			if(flag) updateSQL += ", ";
			updateSQL += "com_zipcode = '" + com_zipcode + "'";
			flag = true;
		}
		System.out.println(updateSQL + updateSQL1);
		if(!flag) {
			throw new ModifyException("수정할 내용이 없습니다.");
		}
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement(updateSQL + updateSQL1);
			pstmt.setString(1, c.getCom_id());
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
	public Company selectById2(String id) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		String selectByIdSQL="SELECT com_name, com_bt FROM company WHERE com_id = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(selectByIdSQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String com_name = rs.getString("com_name");
				int com_bt = rs.getInt("com_bt");
				Company com = new Company();
				com.setCom_name(com_name);
				com.setCom_bt(com_bt);
				return com;
			}
			throw new FindException("해당아이디의 업체,업종명을 불러올수 없습니다");
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
	}
	
	public static void main(String[] args) {
//		CompanyDAOOracle com;
//		try {
//			com = new CompanyDAOOracle();
//			Company c = com.selectById("cid1");
//			System.out.println(c);
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println(e.getMessage());
//		}
		
//		CompanyDAOOracle com;
//		try {
//			com = new CompanyDAOOracle();
//			Company c = new Company();
//			c.setCom_id("cid30");
//			c.setCom_phone("01019709269");
//			c.setCom_email("cid30@cid30.com");
//			c.setCom_zipcode(89280);
//			
//			com.update(c);
//		} catch(ModifyException e) {
//			System.out.println(e.getMessage());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		
	}

}
