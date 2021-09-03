package com.ai.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ai.dto.Advertisement;
import com.ai.dto.Apply;
import com.ai.dto.ApplyList;
import com.ai.dto.Company;
import com.ai.exception.FindException;
import com.ai.sql.MyConnection;

public class ApplyListDAOOracle implements ApplyListDAO {
	
	
	public ApplyListDAOOracle() throws Exception{
		//JDBC드라이버로드		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("JDBC드라이버로드 성공");		
	}

	@Override
	public int getApplyListCount(String inId) throws FindException {		
		return getApplyListCount(inId, "comName", "");
	}

	@Override
	public int getApplyListCount(String inId, String field, String query) throws FindException {
		Connection con = null;
		
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		String selectSQL=
				"SELECT COUNT(*) COUNT  												\r\n " + 
				"FROM APPLY a JOIN ADVERTISEMENT d ON (a.APP_ADV_NO = d.ADV_NO)  		\r\n" + 
				"             JOIN COMPANY c ON (c.COM_ID = d.ADV_COM_ID)  				\r\n" + 
				"WHERE APP_IN_ID = ? ";
		

//		if (query != null && !query.equals("")){
//			selectSQL+= " AND "+ field +" LIKE  '%"+query+"%' ";
//		}
		
		if (query != null && field.equals("com_name") && !query.equals("")){ 
			selectSQL+= " AND "+ field +" LIKE  '%"+query+"%' ";
		}else if (query != null && field.equals("app_state") && !query.equals("")){ 
			selectSQL+= " AND "+ field +" LIKE  '"+query+"' ";
		}
		
		
		System.out.println(selectSQL);
		
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		int count = 0;

		try {
			pstmt = con.prepareStatement(selectSQL);
			pstmt.setString(1, inId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt("COUNT");
			}
			
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
	}

	@Override
	public List<ApplyList> getApplyList(String inId) throws FindException{
		return	getApplyList(inId, "comName", "", 1); 
	}

	@Override
	public List<ApplyList> getApplyList(String inId, int page) throws FindException{
		return	getApplyList(inId, "comName", "", page); 
	}

	@Override
	public List<ApplyList> getApplyList(String inId, String field, String query, int page) throws FindException {
		Connection con = null;
		
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		String selectSQL=
				"SELECT * \r\n" + 
				"FROM \r\n" + 
				"(\r\n" + 
				"SELECT rownum NUM, N.* \r\n" + 
				"FROM (\r\n" + 
				"SELECT a.APP_NO, d.ADV_NO, a.APP_DATE, c.COM_NAME, d.ADV_STARTMONTH, d.ADV_ENDMONTH, a.APP_STATE \r\n" + 
				"FROM APPLY a JOIN ADVERTISEMENT d ON (a.APP_ADV_NO = d.ADV_NO) \r\n" + 
				"                    JOIN COMPANY c ON (c.COM_ID = d.ADV_COM_ID) \r\n" + 
				"WHERE APP_IN_ID = ? "; 
			

		if (query != null && field.equals("com_name") && !query.equals("")){
			selectSQL+= " AND "+ field +" LIKE  '%"+query+"%' ";
		}else if (query != null && field.equals("app_state") && !query.equals("")){ 
			selectSQL+= " AND "+ field +" LIKE  '"+query+"' ";
		}
		
		selectSQL+= "ORDER BY ADV_NO DESC \r\n" +
					") N \r\n" + 
					") \r\n" + 
					"WHERE NUM BETWEEN ? and ? ";
		
		
		System.out.println(selectSQL);
		
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		int pageNum = page;
		int rowPerPage = 5;
		System.out.println("start row :"+((pageNum-1)*rowPerPage+1));
		System.out.println("end row :"+pageNum*rowPerPage );
		List<ApplyList> list = new ArrayList<ApplyList>();
		try {
			pstmt = con.prepareStatement(selectSQL);
			pstmt.setString(1, inId);
			//pstmt.setString(2, "%"+query+"%");
			pstmt.setInt(2, (pageNum-1)*rowPerPage+1);
			pstmt.setInt(3, pageNum*rowPerPage);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Company c = new Company();
				Advertisement adv = new Advertisement();
				int app_no = rs.getInt("app_no");
				Date app_date = rs.getDate("app_date");
				c.setCom_name(rs.getString("com_name"));
				adv.setAdv_no(rs.getInt("adv_no"));
				adv.setAdv_startmonth(rs.getDate("adv_startmonth"));
				adv.setAdv_endmonth(rs.getDate("adv_endmonth"));
				int app_state = rs.getInt("app_state");
				int idx = rs.getInt("NUM");
				ApplyList al = new ApplyList(idx, app_no, app_date, c, adv, app_state);
				
				list.add(al);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
	}
	
	
	
	public static void main(String[] args) {
		//totalCount();
		//getList();
	}
	
	public static void getList() {
		ApplyListDAOOracle dao;
		
		try {
			dao = new ApplyListDAOOracle();
			String inId = "indi7";
			String field = "COM_NAME";
			String query = "회사4";
			int page = 2;
//			field = "APP_STATE";
//			query = "-1";
			//List<ApplyList> al = dao.getApplyList(inId, page);
			List<ApplyList> al = dao.getApplyList(inId, field, query, page);
			
			System.out.println("main - al : "+ al);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void totalCount() {
		ApplyListDAOOracle dao;
		
		try {
			dao = new ApplyListDAOOracle();
			String inId = "indi7";
			String field = "";
			String query = "";
//			field = "APP_STATE";
//			query = "-1"; // 거절 상태 , 수락 상태
//			field = "COM_NAME";
//			query = "회사4"; // 거절 상태 , 수락 상태

//			int count = dao.getApplyListCount(inId);
			int count = dao.getApplyListCount(inId, field, query);
			System.out.println("main - count : "+ count);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
