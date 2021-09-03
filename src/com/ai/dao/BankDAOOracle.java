package com.ai.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ai.dto.Bank;
import com.ai.exception.FindException;
import com.ai.exception.ModifyException;
import com.ai.sql.MyConnection;

public class BankDAOOracle implements BankDAO {

	@Override
	public void update(Bank b) throws ModifyException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		}
		
		String updateSQL = "UPDATE bank SET ";
		String updateSQL1 = " WHERE in_id = ?";

		boolean flag = false; //변경할 값이 있는 경우 true

		String bank = b.getBank();
		if( bank != null && !bank.equals("")) {
			updateSQL += "bank = '" + bank + "'";
			flag = true;
		}		

		String acno = b.getAcno();		
		if( acno != null && !acno.equals("")) {
			if(flag) {
				updateSQL += ",";
			}		
			updateSQL += "acno = '" + acno + "'";
		}
		updateSQL += updateSQL1;

		PreparedStatement pstmt = null;
		//System.out.println(updateSQL);
		
		try {
			pstmt = con.prepareStatement(updateSQL);
			pstmt.setString(1,b.getIn_id());
	         int rowcnt = pstmt.executeUpdate();
	         if(rowcnt==1) {
	            System.out.println("고객의 내용이 변경되었습니다.");
	         }
	      } catch (SQLException e) {
			e.printStackTrace();
		}finally {
			MyConnection.close(con, pstmt, null);
		}
	}
	
	@Override
	public Bank selectByid(String in_id) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}		
		String selectByIdSQL = "SELECT * FROM bank WHERE in_id = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(selectByIdSQL);
			pstmt.setString(1, in_id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String bank = rs.getString("bank");
				String acno = rs.getString("acno");

				Bank b = new Bank(in_id, bank, acno);
				return b;
			}
			throw new FindException("계좌정보가 없습니다");
		} catch (SQLException e) {
			e.printStackTrace();			
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
	}
	
	public static void main(String[] args) {
	

		BankDAOOracle dao = new BankDAOOracle();
		Bank b = new Bank("indi17");
		//b.setIn_id("indi17");
		//b.setBank("우리은행");
		b.setAcno("2121-2222-2121");

		try {
			dao.update(b);
		} catch (ModifyException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
