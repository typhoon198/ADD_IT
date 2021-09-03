package com.ai.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ai.dto.Advertisement;
import com.ai.dto.Apply;
import com.ai.dto.Company;
import com.ai.exception.AddException;
import com.ai.exception.FindException;
import com.ai.sql.MyConnection;

public class AdvertisementDAOOracle implements AdvertisementDAO {
	public AdvertisementDAOOracle() throws Exception{
		//JDBC드라이버로드		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("JDBC드라이버로드 성공");		
	}
	
	@Override
	public List<Advertisement> selectAll() throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		
		String selectALLSQL = "SELECT adv.adv_no, com.com_name, com.com_bt, adv.adv_location1, adv.adv_location2, adv.adv_location3,\r\n" + 
				" adv.adv_fee, adv.adv_cartype, adv.adv_startmonth, adv.adv_endmonth, adv.adv_date, adv.adv_total\r\n" + 
				"FROM advertisement adv JOIN company com ON(adv.adv_com_id = com.com_id) WHERE (adv_startmonth - 7) > SYSDATE";
		//String selectALLSQL = "SELECT * from advertisement";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Advertisement> list = new ArrayList<>();
		try {
			pstmt = con.prepareStatement(selectALLSQL);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				int adv_no = rs.getInt("adv_no");
				Company c = new Company();
				c.setCom_name(rs.getString("com_name"));
				c.setCom_bt(rs.getInt("com_bt"));
				int adv_location1 = rs.getInt("adv_location1");
				int adv_location2 = rs.getInt("adv_location2");
				int adv_location3 = rs.getInt("adv_location3");
				int adv_fee = rs.getInt("adv_fee");
				int adv_cartype = rs.getInt("adv_cartype");
				Date adv_startmonth = rs.getDate("adv_startmonth");
				Date adv_endmonth = rs.getDate("adv_endmonth");
				Date adv_date = rs.getDate("adv_date");
				int adv_total = rs.getInt("adv_total");

				Advertisement adv = new Advertisement(adv_no, c, adv_location1, adv_location2, adv_location3, adv_fee, adv_cartype, adv_startmonth, adv_endmonth, adv_date, adv_total);
				list.add(adv);
			}
			if(list.size() == 0) {
				throw new FindException("광고가 없습니다");
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
	public List<Advertisement> selectAll(int currentPage) throws FindException {
		int cnt_per_page = 8; //페이지별 보여줄 목록수
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Advertisement> list = new ArrayList<>();
		String selectAllPageSQL = "SELECT * FROM (SELECT rownum rn, a.* FROM (SELECT adv.adv_no, com.com_name, com.com_bt, adv.adv_location1, adv.adv_location2, adv.adv_location3,\r\n" + 
				" adv.adv_fee, adv.adv_cartype, adv.adv_startmonth, adv.adv_endmonth, adv.adv_date, adv.adv_total\r\n" + 
				"FROM advertisement adv JOIN company com ON(adv.adv_com_id = com.com_id)\r\n" + 
				"ORDER BY adv.adv_no ASC) a)\r\n" + 
				"WHERE rn BETWEEN START_ROW(?, ?) AND  END_ROW(?, ?) AND (adv_startmonth - 7) > SYSDATE";		
		try {
			pstmt = con.prepareStatement(selectAllPageSQL);
			pstmt.setInt(1, currentPage);
			pstmt.setInt(2, cnt_per_page);
			pstmt.setInt(3, currentPage);
			pstmt.setInt(4, cnt_per_page);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int adv_no = rs.getInt("adv_no");
				Company c = new Company();
				c.setCom_name(rs.getString("com_name"));
				c.setCom_bt(rs.getInt("com_bt"));
				int adv_location1 = rs.getInt("adv_location1");
				int adv_location2 = rs.getInt("adv_location2");
				int adv_location3 = rs.getInt("adv_location3");
				int adv_fee = rs.getInt("adv_fee");
				int adv_cartype = rs.getInt("adv_cartype");
				Date adv_startmonth = rs.getDate("adv_startmonth");
				Date adv_endmonth = rs.getDate("adv_endmonth");
				Date adv_date = rs.getDate("adv_date");
				int adv_total = rs.getInt("adv_total");

				Advertisement adv = new Advertisement(adv_no, c, adv_location1, adv_location2, adv_location3, adv_fee, adv_cartype, adv_startmonth, adv_endmonth, adv_date, adv_total);
				list.add(adv);
			}
			if(list.size() == 0) {
				throw new FindException("광고가 없습니다");
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
	public Advertisement selectByNo(int adv_no) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}		
		String selectByNoSQL = "SELECT adv.adv_no, com.com_name, com.com_bt, adv.adv_location1, adv.adv_location2, adv.adv_location3,\r\n" + 
				" adv.adv_fee, adv.adv_cartype, adv.adv_startmonth, adv.adv_endmonth, adv.adv_date, adv.adv_total\r\n" + 
				"FROM advertisement adv JOIN company com ON(adv.adv_com_id = com.com_id)\r\n" + 
				"WHERE adv.adv_no = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(selectByNoSQL);
			pstmt.setInt(1, adv_no);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Company c = new Company();
				c.setCom_name(rs.getString("com_name"));
				c.setCom_bt(rs.getInt("com_bt"));
				int adv_location1 = rs.getInt("adv_location1");
				int adv_location2 = rs.getInt("adv_location2");
				int adv_location3 = rs.getInt("adv_location3");
				int adv_fee = rs.getInt("adv_fee");
				int adv_cartype = rs.getInt("adv_cartype");
				Date adv_startmonth = rs.getDate("adv_startmonth");
				Date adv_endmonth = rs.getDate("adv_endmonth");
				Date adv_date = rs.getDate("adv_date");
				int adv_total = rs.getInt("adv_total");

				Advertisement adv = new Advertisement(adv_no, c, adv_location1, adv_location2, adv_location3, adv_fee, adv_cartype, adv_startmonth, adv_endmonth, adv_date, adv_total);
				return adv;
			}
			throw new FindException("광고가 없습니다");
		} catch (SQLException e) {
			e.printStackTrace();			
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}		
	}

	@Override
	public List<Advertisement> selectByLocation(int location)
			throws FindException {
				Connection con = null;
				try {
					con = MyConnection.getConnection();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new FindException(e.getMessage());
				}		
				PreparedStatement pstmt = null;
				String selectByNameSQL = 
						"SELECT adv.adv_no, com.com_name, com.com_bt, adv.adv_location1, adv.adv_location2, adv.adv_location3,\r\n" + 
								" adv.adv_fee, adv.adv_cartype, adv.adv_startmonth, adv.adv_endmonth, adv.adv_date, adv.adv_total\r\n" + 
								"FROM advertisement adv JOIN company com ON(adv.adv_com_id = com.com_id)\r\n" + 
								"WHERE (adv_startmonth - 7) > SYSDATE AND (adv.adv_location1 = ? or adv.adv_location2 = ? or adv.adv_location3 = ?)";
				ResultSet rs = null;
				List<Advertisement> list = new ArrayList<>();
				try {
					pstmt = con.prepareStatement(selectByNameSQL);
					pstmt.setInt(1, location);
					pstmt.setInt(2, location);
					pstmt.setInt(3, location);
					rs = pstmt.executeQuery();
					while(rs.next()) {
						int adv_no = rs.getInt("adv_no");
						Company c = new Company();
						c.setCom_name(rs.getString("com_name"));
						c.setCom_bt(rs.getInt("com_bt"));
						int adv_location1 = rs.getInt("adv_location1");
						int adv_location2 = rs.getInt("adv_location2");
						int adv_location3 = rs.getInt("adv_location3");
						int adv_fee = rs.getInt("adv_fee");
						int adv_cartype = rs.getInt("adv_cartype");
						Date adv_startmonth = rs.getDate("adv_startmonth");
						Date adv_endmonth = rs.getDate("adv_endmonth");
						Date adv_date = rs.getDate("adv_date");
						int adv_total = rs.getInt("adv_total");

						Advertisement adv = new Advertisement(adv_no, c, adv_location1, adv_location2, adv_location3, adv_fee, adv_cartype, adv_startmonth, adv_endmonth, adv_date, adv_total);
						list.add(adv);
					}
					if(list.size() == 0) {
						throw new FindException("광고가 없습니다");
					}
				} catch (SQLException e) {
					e.printStackTrace();
					throw new FindException(e.getMessage());
				} finally {
					MyConnection.close(con, pstmt, rs);
				}
				return list;
	}

	@Override
	public List<Advertisement> orderByAddDate() throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}		
		String orderByNoSQL = "SELECT adv.adv_no, com.com_name, com.com_bt, adv.adv_location1, adv.adv_location2, adv.adv_location3,\r\n" + 
				" adv.adv_fee, adv.adv_cartype, adv.adv_startmonth, adv.adv_endmonth, adv.adv_date, adv.adv_total\r\n" + 
				"FROM advertisement adv JOIN company com ON(adv.adv_com_id = com.com_id) WHERE (adv_startmonth - 7) > SYSDATE\r\n" + 
				"ORDER BY adv.adv_no DESC";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(orderByNoSQL);
			rs = pstmt.executeQuery();
			List<Advertisement> list = new ArrayList<>();
			while(rs.next()) {
				int adv_no = rs.getInt("adv_no");
				Company c = new Company();
				c.setCom_name(rs.getString("com_name"));
				c.setCom_bt(rs.getInt("com_bt"));
				int adv_location1 = rs.getInt("adv_location1");
				int adv_location2 = rs.getInt("adv_location2");
				int adv_location3 = rs.getInt("adv_location3");
				int adv_fee = rs.getInt("adv_fee");
				int adv_cartype = rs.getInt("adv_cartype");
				Date adv_startmonth = rs.getDate("adv_startmonth");
				Date adv_endmonth = rs.getDate("adv_endmonth");
				Date adv_date = rs.getDate("adv_date");
				int adv_total = rs.getInt("adv_total");

				Advertisement adv = new Advertisement(adv_no, c, adv_location1, adv_location2, adv_location3, adv_fee, adv_cartype, adv_startmonth, adv_endmonth, adv_date, adv_total);
				list.add(adv);
			}
			if(list.size() == 0) {
				throw new FindException("광고가 없습니다");
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
	public List<Advertisement> orderByStartMonth() throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}		
		String orderByStartMonthSQL = "SELECT adv.adv_no, com.com_name, com.com_bt, adv.adv_location1, adv.adv_location2, adv.adv_location3,\r\n" + 
				" adv.adv_fee, adv.adv_cartype, adv.adv_startmonth, adv.adv_endmonth, adv.adv_date, adv.adv_total\r\n" + 
				"FROM advertisement adv JOIN company com ON(adv.adv_com_id = com.com_id) WHERE (adv_startmonth - 7) > SYSDATE\r\n" + 
				"ORDER BY adv.adv_startmonth ASC";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(orderByStartMonthSQL);
			rs = pstmt.executeQuery();
			List<Advertisement> list = new ArrayList<>();
			while(rs.next()) {
				int adv_no = rs.getInt("adv_no");
				Company c = new Company();
				c.setCom_name(rs.getString("com_name"));
				c.setCom_bt(rs.getInt("com_bt"));
				int adv_location1 = rs.getInt("adv_location1");
				int adv_location2 = rs.getInt("adv_location2");
				int adv_location3 = rs.getInt("adv_location3");
				int adv_fee = rs.getInt("adv_fee");
				int adv_cartype = rs.getInt("adv_cartype");
				Date adv_startmonth = rs.getDate("adv_startmonth");
				Date adv_endmonth = rs.getDate("adv_endmonth");
				Date adv_date = rs.getDate("adv_date");
				int adv_total = rs.getInt("adv_total");

				Advertisement adv = new Advertisement(adv_no, c, adv_location1, adv_location2, adv_location3, adv_fee, adv_cartype, adv_startmonth, adv_endmonth, adv_date, adv_total);
				list.add(adv);
			}
			if(list.size() == 0) {
				throw new FindException("광고가 없습니다");
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
	public List<Advertisement> orderByTerm() throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}		
		String orderByTermSQL = "SELECT adv.adv_no, com.com_name, com.com_bt, adv.adv_location1, adv.adv_location2, adv.adv_location3,\r\n" + 
				" adv.adv_fee, adv.adv_cartype, adv.adv_startmonth, adv.adv_endmonth, adv.adv_date, adv.adv_total\r\n" + 
				"FROM advertisement adv JOIN company com ON(adv.adv_com_id = com.com_id) WHERE (adv_startmonth - 7) > SYSDATE\r\n" + 
				"ORDER BY (adv.adv_endmonth - adv.adv_startmonth) DESC";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(orderByTermSQL);
			rs = pstmt.executeQuery();
			List<Advertisement> list = new ArrayList<>();
			while(rs.next()) {
				int adv_no = rs.getInt("adv_no");
				Company c = new Company();
				c.setCom_name(rs.getString("com_name"));
				c.setCom_bt(rs.getInt("com_bt"));
				int adv_location1 = rs.getInt("adv_location1");
				int adv_location2 = rs.getInt("adv_location2");
				int adv_location3 = rs.getInt("adv_location3");
				int adv_fee = rs.getInt("adv_fee");
				int adv_cartype = rs.getInt("adv_cartype");
				Date adv_startmonth = rs.getDate("adv_startmonth");
				Date adv_endmonth = rs.getDate("adv_endmonth");
				Date adv_date = rs.getDate("adv_date");
				int adv_total = rs.getInt("adv_total");

				Advertisement adv = new Advertisement(adv_no, c, adv_location1, adv_location2, adv_location3, adv_fee, adv_cartype, adv_startmonth, adv_endmonth, adv_date, adv_total);
				list.add(adv);
			}
			if(list.size() == 0) {
				throw new FindException("광고가 없습니다");
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
	public List<Advertisement> orderByFee() throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}		
		String orderByFeeSQL = "SELECT adv.adv_no, com.com_name, com.com_bt, adv.adv_location1, adv.adv_location2, adv.adv_location3,\r\n" + 
				" adv.adv_fee, adv.adv_cartype, adv.adv_startmonth, adv.adv_endmonth, adv.adv_date, adv.adv_total\r\n" + 
				"FROM advertisement adv JOIN company com ON(adv.adv_com_id = com.com_id) WHERE (adv_startmonth - 7) > SYSDATE\r\n" + 
				"ORDER BY adv.adv_fee DESC";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(orderByFeeSQL);
			rs = pstmt.executeQuery();
			List<Advertisement> list = new ArrayList<>();
			while(rs.next()) {
				int adv_no = rs.getInt("adv_no");
				Company c = new Company();
				c.setCom_name(rs.getString("com_name"));
				c.setCom_bt(rs.getInt("com_bt"));
				int adv_location1 = rs.getInt("adv_location1");
				int adv_location2 = rs.getInt("adv_location2");
				int adv_location3 = rs.getInt("adv_location3");
				int adv_fee = rs.getInt("adv_fee");
				int adv_cartype = rs.getInt("adv_cartype");
				Date adv_startmonth = rs.getDate("adv_startmonth");
				Date adv_endmonth = rs.getDate("adv_endmonth");
				Date adv_date = rs.getDate("adv_date");
				int adv_total = rs.getInt("adv_total");

				Advertisement adv = new Advertisement(adv_no, c, adv_location1, adv_location2, adv_location3, adv_fee, adv_cartype, adv_startmonth, adv_endmonth, adv_date, adv_total);
				list.add(adv);
			}
			if(list.size() == 0) {
				throw new FindException("광고가 없습니다");
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
	public List<Advertisement> selectById(String adv_com_id) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}	
		String selectByIdSQL = "SELECT ad.adv_date, ad.adv_no, ad.adv_startmonth, ad.adv_endmonth, ad.adv_fee, ad.adv_total,\n"
				+ "        (SELECT COUNT(*) FROM apply ap WHERE ad.adv_no = ap.app_adv_no) AS cnt \n"
				+ "        FROM advertisement ad\n"
				+ "        WHERE ad.adv_com_id = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(selectByIdSQL);
			pstmt.setString(1, adv_com_id);
			rs = pstmt.executeQuery();
			List<Advertisement> list = new ArrayList<>();
			while(rs.next()) {
				
				Date adv_date = rs.getDate("adv_date");
				int adv_no = rs.getInt("adv_no");
				Date adv_startmonth = rs.getDate("adv_startmonth");
				Date adv_endmonth = rs.getDate("adv_endmonth");
				int adv_fee = rs.getInt("adv_fee");
				int adv_total = rs.getInt("adv_total");
				Apply app = new Apply();
				app.setApp_no(rs.getInt("cnt"));
				
				Advertisement adv = new Advertisement(adv_no, app, adv_fee, adv_startmonth, adv_endmonth, adv_date, adv_total);
				list.add(adv);
			}
				if(list.size() == 0) {
					throw new FindException("광고가 없습니다");
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
	public void insert(Advertisement a) throws AddException {
		Connection con = null;
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		}
		PreparedStatement pstmt = null;
//		String insertSQL = " INSERT INTO advertisement(adv_no, adv_com_id, adv_location1, adv_location2, adv_location3,\r\n" + 
//				" adv_fee,adv_cartype, adv_startmonth,adv_endmonth,adv_total) \r\n" + 
//				"VALUES (? , ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		String insertSQL = " INSERT INTO advertisement(adv_no, adv_com_id, adv_location1, adv_location2, adv_location3,\r\n" + 
				" adv_fee,adv_cartype, adv_startmonth,adv_endmonth,adv_total) \r\n" + 
				"VALUES (ADD_SEQ.NEXTVAL , ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			Date startDate = a.getAdv_startmonth();
			Date endDate = a.getAdv_endmonth();
			String formattedDateStart = fm.format(startDate);
			String formattedDateEnd = fm.format(endDate);
			java.sql.Date startDateSql = java.sql.Date.valueOf(formattedDateStart);
			java.sql.Date endDateSql = java.sql.Date.valueOf(formattedDateEnd);
			pstmt = con.prepareStatement(insertSQL);
	
			pstmt.setString(1, a.getC().getCom_id());
			if(a.getAdv_location1()==0) {
				pstmt.setNull(2,Types.INTEGER);
			} else {
				pstmt.setInt(2,a.getAdv_location1());
			}
			if(a.getAdv_location2()==0) {
				pstmt.setNull(3,Types.INTEGER);
			} else {
				pstmt.setInt(3,a.getAdv_location2());
			}
			if(a.getAdv_location3()==0) {
				pstmt.setNull(4,Types.INTEGER);
			} else {
				pstmt.setInt(4, a.getAdv_location3());
			}
			pstmt.setInt(5, a.getAdv_fee());
			pstmt.setInt(6, a.getAdv_cartype());
			pstmt.setDate(7, startDateSql);
			pstmt.setDate(8, endDateSql);
			pstmt.setInt(9, a.getAdv_total());

			int rowcnt = pstmt.executeUpdate();
			if(rowcnt == 1) {
				System.out.println("광고등록 성공");//테스트
			} else {
				throw new AddException("광고등록 실패");
			}
			return;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, null);
		}
	}

	
	
	@Override
	public List<Advertisement> selectByCityCode(int citycode)
	         throws FindException {
	            Connection con = null;
	            try {
	               con = MyConnection.getConnection();
	            } catch (SQLException e) {
	               e.printStackTrace();
	               throw new FindException(e.getMessage());
	            }      
	            int min = citycode*1000;
	            int max = citycode*1000+500;
	            
	            PreparedStatement pstmt = null;
	            String selectByNameSQL = 
	                  "SELECT adv.adv_no, com.com_name, com.com_bt, adv.adv_location1, adv.adv_location2, adv.adv_location3,  \r\n" + 
	                  "                         adv.adv_fee, adv.adv_cartype, adv.adv_startmonth, adv.adv_endmonth, adv.adv_date, adv.adv_total\r\n" + 
	                  "                        FROM advertisement adv JOIN company com ON(adv.adv_com_id = com.com_id)  \r\n" + 
	                  "                        WHERE (adv_startmonth - 7 > SYSDATE) \r\n" + 
	                  "                                AND (adv.adv_location1 BETWEEN ? AND ? \r\n" + 
	                  "                                     OR adv.adv_location2 BETWEEN ? AND ?\r\n" + 
	                  "                                    OR adv.adv_location3 BETWEEN ? AND ? )";
	            ResultSet rs = null;
	            List<Advertisement> list = new ArrayList<>();
	            try {
	               pstmt = con.prepareStatement(selectByNameSQL);
	               pstmt.setInt(1, min);
	               pstmt.setInt(2, max);
	               pstmt.setInt(3, min);
	               pstmt.setInt(4, max);
	               pstmt.setInt(5, min);
	               pstmt.setInt(6, max);
	               rs = pstmt.executeQuery();
	               while(rs.next()) {
	                  int adv_no = rs.getInt("adv_no");
	                  Company c = new Company();
	                  c.setCom_name(rs.getString("com_name"));
	                  c.setCom_bt(rs.getInt("com_bt"));
	                  int adv_location1 = rs.getInt("adv_location1");
	                  int adv_location2 = rs.getInt("adv_location2");
	                  int adv_location3 = rs.getInt("adv_location3");
	                  int adv_fee = rs.getInt("adv_fee");
	                  int adv_cartype = rs.getInt("adv_cartype");
	                  Date adv_startmonth = rs.getDate("adv_startmonth");
	                  Date adv_endmonth = rs.getDate("adv_endmonth");
	                  Date adv_date = rs.getDate("adv_date");
	                  int adv_total = rs.getInt("adv_total");

	                  Advertisement adv = new Advertisement(adv_no, c, adv_location1, adv_location2, adv_location3, adv_fee, adv_cartype, adv_startmonth, adv_endmonth, adv_date, adv_total);
	                  list.add(adv);
	               }
	               if(list.size() == 0) {
	                  throw new FindException("광고가 없습니다");
	               }
	            } catch (SQLException e) {
	               e.printStackTrace();
	               throw new FindException(e.getMessage());
	            } finally {
	               MyConnection.close(con, pstmt, rs);
	            }
	            return list;
	   }

	@Override
	public int lastAdvNo() throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}	
		String lastAdvNoSQL = "SELECT max(adv_no) as last FROM advertisement";
		//추가이후에 조회
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int last_no = 0;
		try {
			pstmt = con.prepareStatement(lastAdvNoSQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				last_no = rs.getInt("last");
			} else {
				throw new FindException("마지막 광고번호를 못찾았습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();			
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
		return last_no;
	}		


	//테스트
	public static void main(String[] args) {
		
		AdvertisementDAOOracle dao;
		int location = 11000;
		try {
			dao = new AdvertisementDAOOracle();
			
			List<Advertisement> list = dao.selectByLocation(location);
			//Advertisement a = dao.selectByNo(1);
			
			for(Advertisement a: list) {
				System.out.println(a);
			}
		//System.out.println(a);
		//[lastAdvNo 테스트]	
		//	 System.out.println(dao.lastAdvNo());

		/*[insert 테스트]
			Company c = new Company();
			c.setCom_bt(1);
			c.setCom_id("cid10");
			c.setCom_name("회사 테스트 이름");
			Advertisement a = new Advertisement();
			a.setAdv_no(33);
			a.setC(c);
			a.setAdv_location1(11000);
			a.setAdv_location2(22000);
			a.setAdv_cartype(1);
			a.setAdv_total(10);
			a.setAdv_fee(100000);
			a.setAdv_startmonth(new Date());
			a.setAdv_endmonth(new Date());
			dao.insert(a);			
		*/
		} catch(FindException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
