package com.ai.control;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ai.dto.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;

/**
 * Servlet implementation class CertUploadServlet
 */
public class CertUploadServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet 진입");

		HttpSession session= request.getSession();
		
		Account ac = (Account) session.getAttribute("loginInfo");
		System.out.println("a.getId() :"+ac.getId());
		
		/*
		 * String saveDirectory = getServletContext().getRealPath("public/images/");
		 * System.out.println(saveDirectory); int maxPostSize = 5*1024*1024; String
		 * encoding = "utf-8"; // 업로드될 파일이 한글 이름일 경우 깨짐 방지
		 * 
		 * FileRenamePolicy policy = new DefaultFileRenamePolicy(); // 일련번호 1,2,3.. 추가되어
		 * 업로드 됨
		 * 
		 * MultipartRequest mr = new MultipartRequest(request, saveDirectory,
		 * maxPostSize, encoding, policy);
		 * 
		 * String adv_no = mr.getParameter("adv_no");
		 * System.out.println("adv_no "+adv_no);
		 */
		
		String adv_no = request.getParameter("adv_no");
		System.out.println("adv_no "+adv_no);

		SimpleDateFormat ft = new SimpleDateFormat("yy-MM");
		Date time = new Date();
		String date = ft.format(time);
		System.out.println(date );
		
		/*
		 * File file = mr.getFile("file");
		 * System.out.println("file.getName :"+file.getName());
		 * 
		 * // 파일 생성 File oldF = new File(saveDirectory, file.getName()); File newF = new
		 * File(saveDirectory, adv_no+"_"+ac.getId()+"_"+date+".jpg");
		 * 
		 * // 파일명 변경 if(oldF.renameTo(newF)) {
		 * System.out.println(file.getName()+"->"+newF); }
		 */
		
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json;charset=utf-8"); //응답형식지정
		PrintWriter out = response.getWriter();
		Map<String, Integer> map = new HashMap<>();
		
		
		
		String imag_dir = getServletContext().getRealPath("public/images/") + 
				 adv_no+"_"+ac.getId()+"_"+date+".jpg";
		java.io.File img_file = new java.io.File(imag_dir);

//		if(newF.exists()) {
//			System.out.println("저장한 파일이 존재합니다.");
//		}
		if(img_file.exists()) {
			System.out.println("img_file_저장한 파일이 존재합니다.");
			map.put("status", 1);
			//System.out.println(imag_dir);
		} else {
			map.put("status", 0);

		}
		System.out.println("파일 저장 주소:"+imag_dir);
		System.out.println("map :"+map);
		

		String jsonStr = mapper.writeValueAsString(map);
		response.getWriter().print(jsonStr);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session= request.getSession();
		
		Account ac = (Account) session.getAttribute("loginInfo");
		System.out.println("a.getId() :"+ac.getId());
		
		String saveDirectory = getServletContext().getRealPath("public/images/");
		System.out.println(saveDirectory);
		int maxPostSize = 5*1024*1024;
		String encoding = "utf-8"; // 업로드될 파일이 한글 이름일 경우 깨짐 방지
		
		FileRenamePolicy policy = new DefaultFileRenamePolicy(); // 일련번호 1,2,3.. 추가되어 업로드 됨
		
		MultipartRequest mr = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, policy);
		
		String adv_no = mr.getParameter("adv_no");
		System.out.println("adv_no "+adv_no);
		
		SimpleDateFormat ft = new SimpleDateFormat("yy-MM");
		Date time = new Date();
		String date = ft.format(time);
		System.out.println(date );
		
		
		File file = mr.getFile("file");
		System.out.println("file.getName :"+file.getName());
		
		// 파일 생성
		File oldF = new File(saveDirectory, file.getName());
		File newF = new File(saveDirectory, adv_no+"_"+ac.getId()+"_"+date+".jpg");
		
		// 파일명 변경
		if(oldF.renameTo(newF)) {
			System.out.println(file.getName()+"->"+newF);
		}
		
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json;charset=utf-8"); //응답형식지정
		PrintWriter out = response.getWriter();
		Map<String, Integer> map = new HashMap<>();
		
		
		
		String imag_dir = getServletContext().getRealPath("public/images/") + 
				 adv_no+"_"+ac.getId()+"_"+date+".jpg";
		java.io.File img_file = new java.io.File(imag_dir);

//		if(newF.exists()) {
//			System.out.println("저장한 파일이 존재합니다.");
//		}
		if(img_file.exists()) {
			System.out.println("img_file_저장한 파일이 존재합니다.");
			map.put("status", 1);
			//System.out.println(imag_dir);
		} else {
			map.put("status", 0);

		}
		System.out.println("파일 저장 주소:"+saveDirectory);
		

		String jsonStr = mapper.writeValueAsString(map);
		response.getWriter().print(jsonStr);
	}

}
