package com.ai.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ai.dto.ApplyLine;
import com.ai.dto.Payment;
import com.ai.dto.Verification;
import com.ai.exception.FindException;
import com.ai.sql.MyConnection;

public class ApplyLineDAOOracle implements ApplyLineDAO {

   public ApplyLine SelectbyNo(int APP_NO) throws FindException {
      
      Connection con = null;
      try {
         con = MyConnection.getConnection();
      } catch (SQLException e) {
         e.printStackTrace();
         throw new FindException(e.getMessage());
      }      

      
      String SQL = // CONNECT BY LEVEL (계층형 쿼리)를 이용하여    남은 활동 기간이 담긴 N행 1열 날짜 표를 만들어    기본 select문과 LEFT JOIN 함.
            "SELECT * " + 
            "FROM " + 
            "( SELECT ADD_MONTHS( (SELECT adv_startmonth from advertisement WHERE adv_no = " + 
            "(SELECT d.ADV_NO FROM ADVERTISEMENT d JOIN APPLY a ON (d.ADV_NO = a.APP_ADV_NO) WHERE a.APP_NO = " + APP_NO + ")),"+
            " LEVEL -1 ) AS MONTHS " + 
            
            "  FROM DUAL" + 
            "  CONNECT BY LEVEL <= MONTHS_BETWEEN( (SELECT adv_endmonth from advertisement WHERE adv_no = " + 
            "  (SELECT d.ADV_NO FROM ADVERTISEMENT d JOIN APPLY a ON (d.ADV_NO = a.APP_ADV_NO) WHERE a.APP_NO = " + APP_NO + ")),"+
            "  (SELECT adv_startmonth from advertisement WHERE adv_no = " + 
            "  (SELECT d.ADV_NO FROM ADVERTISEMENT d JOIN APPLY a ON (d.ADV_NO = a.APP_ADV_NO) WHERE a.APP_NO = " + APP_NO +"))"+
            "  -1)) A " + 
            
            "  LEFT JOIN " + 
            
            "( SELECT v.VERI_MONTH V_MONTH, v.VERI_METER AS V_METER, v.VERI_STATE V_STATE, t.PAY_DATE T_PAYDATE, t.PAY_FEE T_PAYFEE" + 
            "  FROM  VERIFICATION v LEFT JOIN PAYMENT t ON ( v.VERI_APP_NO= t.PAY_APP_NO AND v.VERI_MONTH = t.PAY_MONTH)         " + 
            "  WHERE v.VERI_APP_NO = " + APP_NO + 
            "  ORDER BY v.VERI_MONTH " + 
            ") B " + 
            
            "ON ( A.MONTHS = B.V_MONTH )";
      
//      System.out.println(SQL);
      ApplyLine line = new ApplyLine();
      List<Payment> pays = new ArrayList<>();
      List<Verification> veris= new ArrayList<>();
      
      Statement stmt = null;
      ResultSet rs = null;
      try {
         stmt = con.createStatement(); 
         rs = stmt.executeQuery(SQL);
         while(rs.next()) {
            Payment pay = new Payment();
            Verification veri = new Verification();

            Date veri_month = rs.getDate("MONTHS");
            //Date veri_month = rs.getDate("v.VERI_MONTH");
            int veri_meter = rs.getInt("V_METER");
            int veri_state = rs.getInt("V_STATE");   
            Date pay_date = rs.getDate("T_PAYDATE");
            int pay_fee = rs.getInt("T_PAYFEE");
            
            veri.setVeri_month(veri_month);
            veri.setVeri_meter(veri_meter);
            veri.setVeri_state(veri_state);
            pay.setPay_date(pay_date);
            pay.setPay_fee(pay_fee);
            
            pays.add(pay);
            veris.add(veri);
            
         }
         line.setApp_no(APP_NO);
         line.setPays(pays);
         line.setVerifications(veris);
         System.out.println("return : " +line);
         return line;

      } catch (SQLException e) {
         e.printStackTrace();
         throw new FindException(e.getMessage());
      } finally {
         MyConnection.close(con, stmt, rs);
      }

   }


   public static void main(String[] args) {
      
      //table_two();
      //table_pay();
      //table_veri();
   }
   
   public static void table_veri() {
      ApplyLineDAOOracle dao;
      try {
         dao = new ApplyLineDAOOracle();
         // System.out.println(dao.SelectbyNO(2)); 

         List<Verification> al = dao.selectVerificationByNo(1); // app_no = 14, adv_no = 5 // 16 ~ 10
         System.out.println("return : " +al);

      } catch (FindException e) {
         System.out.println(e.getMessage());
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   public static void table_pay() {
      ApplyLineDAOOracle dao;
      try {
         dao = new ApplyLineDAOOracle();
         // System.out.println(dao.SelectbyNO(2)); 

         List<Payment> al = dao.selectPaymentByNo(1); // app_no = 14, adv_no = 5 // 16 ~ 10
         System.out.println("return : " +al);

      } catch (FindException e) {
         System.out.println(e.getMessage());
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public static void table_two() {
      ApplyLineDAOOracle dao;
      try {
         dao = new ApplyLineDAOOracle();
         // System.out.println(dao.SelectbyNO(2)); 

         ApplyLine al = dao.SelectbyNo(14); // app_no = 14, adv_no = 5 // 16 ~ 10
         System.out.println("return : " +al);

      } catch (FindException e) {
         System.out.println(e.getMessage());
      } catch (Exception e) {
         e.printStackTrace();
      }
   }


   @Override
   public List<Payment> selectPaymentByNo(int app_no) throws FindException {
      Connection con = null;
      try {
         con = MyConnection.getConnection();
      } catch (SQLException e) {
         e.printStackTrace();
         throw new FindException(e.getMessage());
      }      

      
      String SQL = // CONNECT BY LEVEL (계층형 쿼리)를 이용하여    남은 활동 기간이 담긴 N행 1열 날짜 표를 만들어    기본 select문과 LEFT JOIN 함.
            "SELECT *\r\n" + 
            "FROM \r\n" + 
            "( SELECT ADD_MONTHS( (SELECT adv_startmonth from advertisement WHERE adv_no = \r\n" + 
            "(SELECT d.ADV_NO FROM ADVERTISEMENT d JOIN APPLY a ON (d.ADV_NO = a.APP_ADV_NO) WHERE a.APP_NO = "+app_no + 
            ")), LEVEL -1 ) AS MONTHS\r\n" + 
            "  FROM DUAL\r\n" + 
            "  CONNECT BY LEVEL <= MONTHS_BETWEEN( (SELECT adv_endmonth from advertisement WHERE adv_no = \r\n" + 
            "  (SELECT d.ADV_NO FROM ADVERTISEMENT d JOIN APPLY a ON (d.ADV_NO = a.APP_ADV_NO) WHERE a.APP_NO = "+app_no + 
            "  )), (SELECT adv_startmonth from advertisement WHERE adv_no = \r\n" + 
            "  (SELECT d.ADV_NO FROM ADVERTISEMENT d JOIN APPLY a ON (d.ADV_NO = a.APP_ADV_NO) WHERE a.APP_NO = "+app_no + 
            "  ))-1)) A\r\n" + 
            "  LEFT JOIN \r\n" + 
            "  \r\n" + 
            "( SELECT t.PAY_MONTH as T_PAY_MONTH, t.PAY_DATE T_PAYDATE, t.PAY_FEE T_PAYFEE \r\n" + 
            "FROM APPLY p JOIN PAYMENT t ON ( p.APP_NO = t.PAY_APP_NO )\r\n" + 
            "WHERE APP_NO ="+app_no + 
            "ORDER BY PAY_MONTH\r\n" + 
            ") B\r\n" + 
            "ON ( A.MONTHS = B.T_PAY_MONTH )\r\n" + 
            "ORDER BY A.MONTHS";
      
//      System.out.println(SQL);
      ApplyLine line = new ApplyLine();
      List<Payment> pays = new ArrayList<>();
      
      Statement stmt = null;
      ResultSet rs = null;
      try {
         stmt = con.createStatement(); 
         rs = stmt.executeQuery(SQL);
         while(rs.next()) {
            Payment pay = new Payment();
            Verification veri = new Verification();

            Date pay_month = rs.getDate("MONTHS");
            Date pay_date = rs.getDate("T_PAYDATE");
            int pay_fee = rs.getInt("T_PAYFEE");

            pay.setPay_month(pay_month);
            pay.setPay_date(pay_date);
            pay.setPay_fee(pay_fee);
            
            pays.add(pay);
            
         }
         line.setApp_no(app_no);
         line.setPays(pays);
         System.out.println("return : " +line);
         return pays;

      } catch (SQLException e) {
         e.printStackTrace();
         throw new FindException(e.getMessage());
      } finally {
         MyConnection.close(con, stmt, rs);
      }
   }

   @Override
   public List<Verification> selectVerificationByNo(int app_no) throws FindException {
      Connection con = null;
      try {
         con = MyConnection.getConnection();
      } catch (SQLException e) {
         e.printStackTrace();
         throw new FindException(e.getMessage());
      }      

      
      String SQL = // CONNECT BY LEVEL (계층형 쿼리)를 이용하여    남은 활동 기간이 담긴 N행 1열 날짜 표를 만들어    기본 select문과 LEFT JOIN 함.
            "SELECT *\r\n" + 
            "FROM \r\n" + 
            "( SELECT ADD_MONTHS( (SELECT adv_startmonth from advertisement WHERE adv_no = \r\n" + 
            "(SELECT d.ADV_NO FROM ADVERTISEMENT d JOIN APPLY a ON (d.ADV_NO = a.APP_ADV_NO) WHERE a.APP_NO = "+app_no + 
            ")), LEVEL -1 ) AS MONTHS\r\n" + 
            "  FROM DUAL\r\n" + 
            "  CONNECT BY LEVEL <= MONTHS_BETWEEN( (SELECT adv_endmonth from advertisement WHERE adv_no = \r\n" + 
            "  (SELECT d.ADV_NO FROM ADVERTISEMENT d JOIN APPLY a ON (d.ADV_NO = a.APP_ADV_NO) WHERE a.APP_NO = "+app_no + 
            "  )), (SELECT adv_startmonth from advertisement WHERE adv_no = \r\n" + 
            "  (SELECT d.ADV_NO FROM ADVERTISEMENT d JOIN APPLY a ON (d.ADV_NO = a.APP_ADV_NO) WHERE a.APP_NO = "+app_no +
            "  ))-1)) A\r\n" + 
            "  LEFT JOIN \r\n" + 
            "  \r\n" + 
            "( SELECT v.VERI_MONTH as V_MONTH, v.VERI_METER as V_METER, v.VERI_STATE as V_STATE\r\n" + 
            "FROM APPLY p JOIN VERIFICATION v ON ( p.APP_NO = v.VERI_APP_NO )\r\n" + 
            "WHERE APP_NO = "+app_no +
            "  ORDER BY v.VERI_MONTH\r\n" + 
            ") B\r\n" + 
            "ON ( A.MONTHS = B.V_MONTH )";
      
      //System.out.println(SQL);
      ApplyLine line = new ApplyLine();
      List<Verification> veris= new ArrayList<>();
      
      Statement stmt = null;
      ResultSet rs = null;
      try {
         stmt = con.createStatement(); 
         rs = stmt.executeQuery(SQL);
         while(rs.next()) {
            Verification veri = new Verification();

            Date veri_month = rs.getDate("MONTHS");
            //Date veri_month = rs.getDate("v.VERI_MONTH");
            int veri_meter = rs.getInt("V_METER");
            int veri_state = rs.getInt("V_STATE");   
            
            veri.setVeri_month(veri_month);
            veri.setVeri_meter(veri_meter);
            veri.setVeri_state(veri_state);
            
            veris.add(veri);
            
         }
         line.setApp_no(app_no);
         line.setVerifications(veris);
         System.out.println("return : " +veris);
         return veris;

      } catch (SQLException e) {
         e.printStackTrace();
         throw new FindException(e.getMessage());
      } finally {
         MyConnection.close(con, stmt, rs);
      }

   }


  @Override
	public List<ApplyLine> selectByAdvNo(int adv_no) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		String selectByAdvNoSQL = "SELECT ve.veri_app_no, ap.app_in_id, ad.adv_startmonth, ad.adv_endmonth, ve.veri_month, ve.veri_meter\n"
								+ " FROM advertisement ad\n"
								+ " RIGHT JOIN apply ap ON (ad.adv_no= ap.app_adv_no)\n"
								+ " JOIN verification ve ON (ap.app_adv_no = ve.veri_app_no)\n"
								+ " WHERE ad.adv_no = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ApplyLine> list = new ArrayList<>();
		try {
			pstmt = con.prepareStatement(selectByAdvNoSQL);
			pstmt.setInt(1, adv_no);
			rs = pstmt.executeQuery();
            String preId = "indi0";
            List<Verification> veri_list = null;
			while(rs.next()) {
                String id = rs.getString("app_in_id");
                if(!preId.equals(id)){
                    ApplyLine appLine = new ApplyLine();
                    list.add(appLine);
                    appLine.setApp_in_id(id);
                    appLine.setAdv_startmonth(rs.getDate("adv_startmonth"));
                    appLine.setAdv_endmonth(rs.getDate("adv_endmonth"));
                    veri_list = new ArrayList<>();
                    appLine.setVerifications(veri_list);
                    preId = id;
                }
                Verification verify = new Verification();
                verify.setVer_app_no(rs.getInt("veri_app_no"));
                verify.setVeri_month(rs.getDate("veri_month"));
                verify.setVeri_meter(rs.getInt("veri_meter"));
                veri_list.add(verify);
			}
            if(list.size() == 0) {
                throw new FindException("해당 내역이 없습니다.");
            }
            return list;
		} catch (SQLException e) {
			e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            MyConnection.close(con, pstmt, rs);
        }
		
	}

}