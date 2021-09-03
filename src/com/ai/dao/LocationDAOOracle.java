package com.ai.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ai.dto.Location;
import com.ai.exception.FindException;
import com.ai.sql.MyConnection;

public class LocationDAOOracle implements LocationDAO {

	@Override
	public List<Location> selectByCityCode(int citycode) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}		
		//String selectByCityCodeSQL = "SELECT * FROM location WHERE loc_code LIKE '" + citycode + "%'";
		//String selectByCityCodeSQL = "SELECT * FROM location WHERE loc_code LIKE ?";
		String selectByCityCodeSQL = "SELECT loc_code, loc_name FROM location WHERE loc_code BETWEEN ? AND ?";
		int min = citycode*1000;
		int max = citycode*1000+500;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Location> list = new ArrayList<Location>();
		try {
			pstmt = con.prepareStatement(selectByCityCodeSQL);
			//pstmt.setString(1, citycode + "%");
			pstmt.setInt(1, min);
			pstmt.setInt(2, max);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int loc_code = rs.getInt("loc_code");
				String loc_name = rs.getString("loc_name");

				Location location = new Location(loc_code, loc_name);
				list.add(location);
			}
			if(list.size() == 0) {
				throw new FindException("지역이 없습니다");
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
	public List<Location> selectAll() throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}		
		//String selectByCityCode = "SELECT * FROM location WHERE loc_code LIKE '" + citycode + "%'";
		String selectAllSQL = "SELECT * FROM location";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Location> list = new ArrayList<Location>();
		try {
			pstmt = con.prepareStatement(selectAllSQL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int loc_code = rs.getInt("loc_code");
				String loc_name = rs.getString("loc_name");

				Location location = new Location(loc_code, loc_name);
				list.add(location);
			}
			if(list.size() == 0) {
				throw new FindException("지역이 없습니다");
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
		LocationDAOOracle dao;
		int citycode = 21;
		try {
			dao = new LocationDAOOracle();
			List<Location> list = dao.selectByCityCode(citycode);
			for(Location loc: list) {
				System.out.println(loc);
			}
//			dao = new LocationDAOOracle();
//			List<Location> list = dao.selectAll();
//			for(Location loc: list) {
//				System.out.println(loc);
//			}
		} catch(FindException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
