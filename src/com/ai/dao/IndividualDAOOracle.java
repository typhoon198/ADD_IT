package com.ai.dao;

import com.ai.dto.Account;
import com.ai.dto.Bank;
import com.ai.dto.Individual;
import com.ai.exception.AddException;
import com.ai.exception.FindException;
import com.ai.exception.ModifyException;
import com.ai.sql.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class IndividualDAOOracle implements IndividualDAO {
	public IndividualDAOOracle() throws Exception{
		//JDBC드라이버로드		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("JDBC드라이버로드 성공");		
	}




	
	@Override
	public void insert(Account a) throws AddException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insert(Individual i) throws AddException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insert(Bank b) throws AddException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Individual selectById(String id) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		
		String selectSQL="";
		selectSQL = "SELECT * \n" +
				"FROM INDIVIDUAL i, ACCOUNT a \n" + 
				"WHERE i.IN_ID = ? \n" + 
				"AND i.IN_ID = a.ID";
		//selectSQL = "SELECT * FROM INDIVIDUAL i, ACCOUNT a WHERE i.IN_ID = ? AND i.IN_ID = a.ID";
		//selectSQL = "SELECT * FROM INDIVIDUAL WHERE IN_ID=?";

		
		System.out.println(selectSQL);
		Individual indi = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(selectSQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			
			if (rs.next()) {
			String IN_ID = rs.getString("IN_ID");
			String IN_NAME= rs.getString("IN_NAME");
			String IN_PHONE= rs.getString("IN_PHONE");
			String IN_EMAIL= rs.getString("IN_EMAIL");
			int IN_ZIPCODE= rs.getInt("IN_ZIPCODE");
			String IN_ADDRESS= rs.getString("IN_ADDRESS");
			String IN_BIRTHDAY = rs.getString("IN_BIRTHDAY");
			int IN_CAR= rs.getInt("IN_CAR");

			indi = new Individual(IN_ID, IN_NAME, IN_PHONE, IN_EMAIL, IN_ZIPCODE, IN_ADDRESS, IN_BIRTHDAY, IN_CAR);
			
			System.out.println("조회 성공");
			return indi;
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
	public void update(Individual indi) throws ModifyException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		}
		
		String updateSQL = "UPDATE INDIVIDUAL SET "; //pwd = 'p1', name = 'n1', buildingno='1'
		String updateSQL1 = " WHERE IN_ID = ?";
		
		boolean flag = false; //변경할 값이 있는 경우 true
		
		String IN_PHONE = indi.getIn_phone();
		String IN_EMAIL = indi.getIn_email();
		int IN_ZIPCODE = indi.getIn_zipcode();
		String IN_ADDRESS = indi.getIn_address();
		int IN_CAR = indi.getIn_cartype();
		 
		if( IN_PHONE != null) {
			updateSQL += "IN_PHONE = '" + IN_PHONE + "'";
			flag = true;
		}		
		if( IN_EMAIL != null) {
			if(flag) updateSQL += ", ";
			updateSQL += "IN_EMAIL = '" + IN_EMAIL + "'";
			flag = true;
		}	
		if( IN_ZIPCODE != 0) {
			if(flag) updateSQL += ", ";
			updateSQL += "IN_ZIPCODE = '" + IN_ZIPCODE + "'";
			flag = true;
		}	
		if( IN_CAR != 0) {
			if(flag) updateSQL += ", ";
			updateSQL += "IN_CAR = '" + IN_CAR + "'";
			flag = true;
		}
		
		if( IN_ADDRESS != null) {
			if(flag) updateSQL += ", ";
			updateSQL += "IN_ADDRESS = '" + IN_ADDRESS + "'";
			flag = true;
		}
		System.out.println(updateSQL+updateSQL1);
		
		System.out.println("flag "+flag);
		if(!flag) {
			System.out.println("throw new ModifyException(\"수정할 내용이 없습니다.\")");
			throw new ModifyException("수정할 내용이 없습니다.");
		}
		
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(updateSQL+updateSQL1);
			pstmt.setString(1, indi.getIn_id());
			int rowcnt = pstmt.executeUpdate();
			
			
			System.out.println("총 " + rowcnt + "건이 변경되었습니다.");



		} catch (SQLException e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
		
	}
	
	//테스트
	public static void main(String[] args) {
		
		//inquiry(); // 기본 조회
		
		
		//modify();
		
	}


	public static void inquiry() {
		IndividualDAOOracle dao;
		try {
			dao = new IndividualDAOOracle();
			Individual indi = dao.selectById("indi1");
			System.out.println(indi);
		} catch(FindException e) {
			
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void modify() {
		
		IndividualDAOOracle dao = null;
		try {
			dao = new IndividualDAOOracle();
			Individual indi = new Individual();
			indi.setIn_id("indi1");
			indi.setIn_email("@");
			indi.setIn_address("juso");
			indi.setIn_cartype(9);
			indi.setIn_phone("01011110000");
			indi.setIn_zipcode(12345);
			
			System.out.println(indi);
			dao.update(indi);
		} catch(ModifyException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
