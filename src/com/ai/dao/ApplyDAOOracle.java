package com.ai.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ai.dto.Advertisement;
import com.ai.dto.Apply;
import com.ai.dto.Company;
import com.ai.dto.Individual;
import com.ai.exception.AddException;
import com.ai.exception.FindException;
import com.ai.exception.ModifyException;
import com.ai.sql.MyConnection;

public class ApplyDAOOracle implements ApplyDAO {
	
	public ApplyDAOOracle() throws Exception{
		//JDBC드라이버로드		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("JDBC드라이버로드 성공");		
	}



	@Override
	public void insert(Apply apply) throws AddException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
			con.setAutoCommit(false); //자동커밋 해제
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		}
		try {
			insertApply(con, apply);
			con.commit(); //커밋
		}catch(Exception e) {
			try {
				con.rollback(); //롤백
			} catch (SQLException e1) {
			}
			throw new AddException(e.getMessage());
		}finally {
			MyConnection.close(con, null, null);
		}
	}
	
	private void insertApply(Connection con, Apply apply) throws AddException{
		PreparedStatement pstmt = null;
//		String insertApplySQL = "INSERT INTO apply(app_no, app_in_id, app_adv_no, add_date, app_state)"
//				+ " VALUES (?, ?, ?, SYSDATE, 0)";
		String insertApplySQL = "INSERT INTO apply(app_no, app_in_id, app_adv_no, app_date, app_state)"
				+ " VALUES (APPLY_SEQ.NEXTVAL, ?, ?, SYSDATE, 0)";
		try {
			pstmt = con.prepareStatement(insertApplySQL);
			//pstmt.setInt(1, apply.getApp_no());
			pstmt.setString(1, apply.getI().getIn_id());
			pstmt.setInt(2, apply.getAdv().getAdv_no());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AddException("신청추가실패:" + e.getMessage());
		}finally {
			MyConnection.close(null, pstmt, null);
		}		
	}


	// 3) 고객은 광고신청 내역을 조회한다.
	@Override 
	public List<Apply> selectApplyById(String id) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}

		String selectSQL="";
		selectSQL= "SELECT "
				+ "p.APP_NO, "
				+ "d.ADV_NO, "
				+ "p.APP_DATE, "
				+ "c.COM_NAME, "
				+ "d.ADV_STARTMONTH, "
				+ "d.ADV_ENDMONTH, "
				+ "p.APP_STATE \n" + 
				"FROM APPLY p JOIN ADVERTISEMENT d ON ( p.APP_ADV_NO = d.ADV_NO )\n" + 
				"             JOIN INDIVIDUAL i ON ( p.APP_IN_ID = i.IN_ID )\n" + 
				"             JOIN COMPANY c ON ( c.COM_ID = d.ADV_COM_ID )\n" + 
				"WHERE i.IN_ID = ?" + 
				"ORDER BY p.APP_NO DESC";
		Individual indi = null;
		List<Apply> list = new ArrayList<Apply>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(selectSQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int APP_NO = rs.getInt("APP_NO");
				int ADV_NO = rs.getInt("ADV_NO");
				Date APP_DATE = rs.getDate("APP_DATE");
				String COM_NAME = rs.getString("COM_NAME");
				Date ADV_STARTMONTH = rs.getDate("ADV_STARTMONTH");
				Date ADV_ENDMONTH = rs.getDate("ADV_ENDMONTH");
				int APP_STATE = rs.getInt("APP_STATE");
				Advertisement adv = new Advertisement();
				Company c = new Company();
				c.setCom_name(COM_NAME);
				adv.setC(c);
				adv.setAdv_no(ADV_NO);
				adv.setAdv_startmonth(ADV_STARTMONTH);
				adv.setAdv_endmonth(ADV_ENDMONTH);
				
				Apply app = new Apply(APP_NO, adv, APP_DATE, APP_STATE);
				
				list.add(app);
				//System.out.println("ADV_STARTMONTH+ "+ ADV_STARTMONTH);
			}

			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
	}

	// 4) 고객은 광고활동 내역을 조회한다.(수락)
	@Override 
	public List<Apply> selectAcceptById(String id) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		
		String selectSQL="";
		// 기간 대체
//		selectSQL = "SELECT  p.APP_NO, d.ADV_NO, c.COM_NAME, d.ADV_FEE, "+
//					"        CONCAT(TO_CHAR(ADV_STARTMONTH, 'yy/MM/mm ~ '), "+
//					"               TO_CHAR(ADV_ENDMONTH, 'yy/MM/mm')) TERM 		\n" + 
//					"FROM APPLY p JOIN ADVERTISEMENT d ON ( p.APP_NO = d.ADV_NO )	\n" + 
//					"             JOIN INDIVIDUAL i ON ( p.APP_IN_ID = i.IN_ID )	\n" + 
//					"             JOIN COMPANY c ON ( c.COM_ID = d.ADV_COM_ID )		\n" + 
//					"WHERE i.IN_ID = ? AND p.APP_STATE = 1							\n" + 
//					"ORDER BY p.APP_NO DESC";

		// 시작일 | 종료일 구분 표시
		selectSQL = "SELECT    														  "+
							"p.APP_NO,  		 								      "+
							"d.ADV_NO, 		 										  "+
							"c.COM_NAME,	 									      "+
							"ADV_STARTMONTH, 										  "+
							"ADV_ENDMONTH,											  "+
							"ADV_FEE                                				  "+ 
					"FROM APPLY p JOIN ADVERTISEMENT d ON ( p.APP_ADV_NO = d.ADV_NO ) "+ 
					"             JOIN COMPANY c ON ( c.COM_ID = d.ADV_COM_ID )       "+ 
					"WHERE p.APP_IN_ID = ?   AND   p.APP_STATE = 1                    "+ 
					"ORDER BY p.APP_NO DESC";
		
		
		System.out.println(selectSQL);
		Individual indi = null;
		List<Apply> list = new ArrayList<Apply>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(selectSQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int APP_NO = rs.getInt("APP_NO");
				int ADV_NO = rs.getInt("ADV_NO");
				String COM_NAME = rs.getString("COM_NAME");
				int ADV_FEE = rs.getInt("ADV_FEE");
				// String APP_Bank = rs.getString("TERM"); ~ 기간 테스트용
				
				Date ADV_STARTMONTH = rs.getDate("ADV_STARTMONTH");
				Date ADV_ENDMONTH = rs.getDate("ADV_ENDMONTH");
				
				Advertisement adv = new Advertisement();
				Company c = new Company();
				c.setCom_name(COM_NAME);
				adv.setC(c);
				adv.setAdv_no(ADV_NO);
				adv.setAdv_startmonth(ADV_STARTMONTH);
				adv.setAdv_endmonth(ADV_ENDMONTH);
				adv.setAdv_fee(ADV_FEE);
				
				//Apply app = new Apply(APP_NO, adv, APP_Bank);
				Apply app = new Apply(APP_NO, adv);
				
				list.add(app);
			}
			
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
	}
	
	@Override
	public List<Apply> selectApplybyAdvNo(int no) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		String selectApplybyAdvNoSQL = "SELECT ap.app_no, ap.app_state, ap.app_in_id, i.in_address, i.in_birthday, i.in_car\n"
									+ " FROM apply ap JOIN individual i ON(ap.app_in_id = i.in_id)\n"
									+ " WHERE ap.app_adv_no = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Apply> list = new ArrayList<>();
		try {
			pstmt = con.prepareStatement(selectApplybyAdvNoSQL);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String app_in_id = rs.getString("app_in_id");
				int app_no = rs.getInt("app_no");
				int app_state = rs.getInt("app_state");
				Individual indi = new Individual();
				indi.setIn_address(rs.getString("in_address"));
				indi.setIn_birthday(rs.getString("in_birthday"));
				indi.setIn_cartype(rs.getInt("in_car"));
				
				Apply app = new Apply(app_in_id, indi);
				app.setApp_no(app_no);
				app.setApp_state(app_state);
				list.add(app);
			}
			if(list.size() == 0) {
				throw new FindException("신청인원이 없습니다.");
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
	}

	@Override
	public void update(int app_no, int app_state) throws ModifyException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		}
		String updateSQL = "UPDATE apply SET app_state = ? WHERE app_no = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(updateSQL);
			pstmt.setInt(1, app_state);
			pstmt.setInt(2, app_no);
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
	public Apply selectLatelyApplyById(String id) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		
		String selectLatelyApplySQL="";
		selectLatelyApplySQL = "SELECT * FROM (SELECT * FROM APPLY WHERE app_in_id = ? ORDER BY app_no DESC) WHERE ROWNUM = 1";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(selectLatelyApplySQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int APP_NO = rs.getInt("APP_NO");
				int APP_ADV_NO = rs.getInt("APP_ADV_NO");
				Date APP_DATE = rs.getDate("APP_DATE");
				int APP_STATE = rs.getInt("APP_STATE");
				
				Advertisement adv = new Advertisement();
				Company c = new Company();
				adv.setC(c);
				adv.setAdv_no(APP_ADV_NO);
				
				Apply app = new Apply(APP_NO, adv, APP_DATE, APP_STATE);
				return app;
			}
			throw new FindException("0");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
	}

	//테스트
	public static void main(String[] args) {
		
//		ApplyDAOOracle dao;
//		
//		try {
//			dao = new ApplyDAOOracle();
//			List<Apply> list = dao.selectApplybyAdvNo(1);
//			for(Apply ls: list) {
//				System.out.println(ls);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		try {
//			dao = new ApplyDAOOracle();
//			dao.update(45, 0);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
//		inquiry1(); // 3) 고객은 광고신청 내역을 조회한다.
		//inquiry2(); // 4) 고객은 광고활동 내역을 조회한다.
//		modify();
		
	}

	public static void inquiry1() { // 3) 고객은 광고신청 내역을 조회한다.
		ApplyDAOOracle dao;
		try {
			System.out.println("inquiry1()");
			dao = new ApplyDAOOracle();
			List<Apply> ap = dao.selectApplyById("indi1");
			System.out.println(ap.get(0).getApp_no());
			System.out.println(ap.get(0).getAdv().getAdv_no());
			System.out.println(ap.get(0).getApp_date());
			System.out.println(ap.get(0).getAdv().getC().getCom_name());
			System.out.println(ap.get(0).getAdv().getAdv_startmonth());
			System.out.println(ap.get(0).getAdv().getAdv_endmonth());
			System.out.println(ap.get(0).getApp_state());
		} catch(FindException e) {
			
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void inquiry2() { // 4) 고객은 광고활동 내역을 조회한다.
		ApplyDAOOracle dao;
		try {
			System.out.println("inquiry2()");
			dao = new ApplyDAOOracle();
			List<Apply> ap = dao.selectAcceptById("indi1");
			System.out.println(ap.get(0).getApp_no());
			System.out.println(ap.get(0).getAdv().getAdv_no());
			System.out.println(ap.get(0).getAdv().getC().getCom_name());
			System.out.println(ap.get(0).getAdv().getAdv_startmonth());
			System.out.println(ap.get(0).getAdv().getAdv_endmonth());
			System.out.println(ap.get(0).getAdv().getAdv_fee());
			//System.out.println("기간 대체:"+ap.get(0).getApp_bank());
		} catch(FindException e) {
			
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}



