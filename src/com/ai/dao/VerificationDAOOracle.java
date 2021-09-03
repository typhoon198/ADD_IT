package com.ai.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.ai.dto.Advertisement;
import com.ai.dto.Apply;
import com.ai.dto.Verification;
import com.ai.exception.AddException;
import com.ai.exception.FindException;
import com.ai.exception.ModifyException;
import com.ai.sql.MyConnection;

public class VerificationDAOOracle implements VerificationDAO {
	
	public VerificationDAOOracle() throws Exception{
		//JDBC드라이버로드		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("JDBC드라이버로드 성공");		
	}

	@Override
	public void insert(Verification v) throws AddException {
		// TODO Auto-generated method stub

	}
	
	@Override
	public List<Verification> selectByApplyNo(int veri_app_no, java.util.Date veri_month) throws FindException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(int veri_state, int veri_app_no, Date veri_month) throws ModifyException {
		Connection con = null;
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		}
		String updateSQL = "UPDATE verification SET veri_state = ? WHERE veri_app_no = ? AND veri_month = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement(updateSQL);
			pstmt.setInt(1, veri_state);
			pstmt.setInt(2, veri_app_no);
			pstmt.setDate(3, java.sql.Date.valueOf(fm.format(veri_month)));
			rs = pstmt.executeQuery();
			int rowcnt = pstmt.executeUpdate();
			
			System.out.println("총 " + rowcnt + "건이 변경되었습니다.");
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
		
	}
	
	public static void main(String[] args) {
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		VerificationDAOOracle dao;
		
		try {
			dao = new VerificationDAOOracle();
			dao.update(-1, 2, fm.parse("2020-08-01"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
