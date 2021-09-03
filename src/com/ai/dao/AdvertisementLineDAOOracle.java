package com.ai.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ai.dto.Advertisement;
import com.ai.dto.AdvertisementLine;
import com.ai.dto.Apply;
import com.ai.exception.FindException;
import com.ai.sql.MyConnection;

public class AdvertisementLineDAOOracle implements AdvertisementLineDAO {

	@Override
	public List<AdvertisementLine> selectApplybyComId(int state, String id) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		String selectApplybyComIdSQL = "SELECT ad.adv_no, ad.adv_startmonth, ad.adv_endmonth, ad.adv_fee,"
									+ " (SELECT COUNT(*) FROM apply ap WHERE ad.adv_no = ap.app_adv_no AND ap.app_state = ?) AS cnt\n"
									+ " FROM advertisement ad\n"
									+ " WHERE ad.adv_com_id = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AdvertisementLine> list = new ArrayList<>();
		try {
			pstmt = con.prepareStatement(selectApplybyComIdSQL);
			pstmt.setInt(1, state);
			pstmt.setString(2, id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int adv_no = rs.getInt("adv_no");
				Date adv_startmonth = rs.getDate("adv_startmonth");
				Date adv_endmonth = rs.getDate("adv_endmonth");
				int adv_fee = rs.getInt("adv_fee");
				int cnt = rs.getInt("cnt");
				
				AdvertisementLine advLine = new AdvertisementLine(adv_no, adv_startmonth, adv_endmonth, adv_fee, cnt);
				list.add(advLine);
			}
			if(list.size() == 0) {
				throw new FindException("진행한 광고가 없습니다.");
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
		
		AdvertisementLineDAOOracle dao;
		try {
			dao = new AdvertisementLineDAOOracle();
			List<AdvertisementLine> ap = dao.selectApplybyComId(1, "cid1");
			for(AdvertisementLine ls: ap) {
				System.out.println(ls);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
