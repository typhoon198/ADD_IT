package com.ai.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.ai.dto.Payment;
import com.ai.exception.AddException;
import com.ai.exception.FindException;
import com.ai.sql.MyConnection;

public class PaymentDAOOracle implements PaymentDAO {

	@Override
	public void insert(int pay_no, int app_no, java.util.Date pay_month, int pay_fee) throws AddException {
		Connection con = null;
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AddException(e.getMessage()); 
		}
		String insertSQL = "INSERT INTO payment(pay_no, pay_app_no, pay_month, pay_fee) VALUES(?, ?, ?, ?)";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement(insertSQL);
			pstmt.setInt(1, pay_no);
			pstmt.setInt(2, app_no);
			pstmt.setDate(3, java.sql.Date.valueOf(fm.format(pay_month)));
			pstmt.setInt(4, pay_fee);
			rs = pstmt.executeQuery();
			System.out.println("지급 완료!!");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
	}

	@Override
	public List<Payment> SelectbyPayNo(int pay_no) throws FindException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Payment> SelectbyApplyNo(int pay_app_no) throws FindException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {
//		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
//		PaymentDAOOracle dao;
//		try {
//			dao = new PaymentDAOOracle();
//			dao.insert(57, 16, fm.parse("21-06-21"), 50000);
//		} catch (AddException e) {
//			e.printStackTrace();
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
	}

}
