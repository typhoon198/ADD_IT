package com.ai.control;

import com.ai.exception.FindException;
import com.ai.service.AdvertisementService;
import com.oreilly.servlet.MultipartRequest;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AddUploadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//비지니스로직 호출
		AdvertisementService service;
		ServletContext sc = getServletContext();		
		AdvertisementService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = AdvertisementService.getInstance();
		String fileName = null;
		//시퀀스 마지막번호(광고번호) 가져오기
		try {
			fileName = String.valueOf(service.last());
			System.out.println(fileName);
		} catch (FindException e) {
			e.printStackTrace();
		}

		//경로 가져오기(없으면 생성)
		String saveDirectory = getServletContext().getRealPath("public/images/");
		System.out.println(saveDirectory);
		File saveDir = new File(saveDirectory);
		if(!saveDir.exists()) saveDir.mkdirs(); 

		if(fileName!=null) {
			
			int maxPostSize = 1024*1024*5;//5mb
			String encoding = "utf-8";

			MultipartRequest mr= new MultipartRequest(request, saveDirectory, maxPostSize, encoding);
			String info = mr.getParameter("intro");
			File imgFile = mr.getFile("img");

			//텍스트 파일 저장
			File textfile = new File(saveDirectory+fileName+".txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(textfile));
			writer.write(info);
			writer.close();
			
			//이미지파일 저장
			imgFile.getName();
			File oldImgF = new File(saveDirectory, imgFile.getName());
			File newImgF = new File(saveDirectory, fileName+".png");//

			if(oldImgF.renameTo(newImgF)) {
				System.out.println(oldImgF.getName()+"->"+newImgF.getName());
			}

			// 리다이렉트
			response.sendRedirect("http://localhost:8888/Add_it/");
		}
	}
}
