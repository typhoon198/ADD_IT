package com.ai.dto;

import java.util.List;

public class ApplyListPageBean<T> {
	private int currentPage = 1;
	private int totalPage;
	private List<T> list; // 타입 제네릭 : 상품 리스트, 게시글 리스트 등 다양한 리스트 대입 가능
	private int startPage = 1;
	private int endPage;
	private String url;
	/* 페이지별 보여줄 목록 수 */
	public static final int CNT_PER_PAGE = 5;
	/* 페이지 그룹의 페이지수 */
	public static final int CNT_PER_PAGE_GROUP = 5;
	
	
	
	public ApplyListPageBean(int currentPage, int totalPage, List<T> list, String url) {
		this.currentPage = currentPage;
		this.totalPage = totalPage;
		this.list = list;
		this.url = url;
		
	      int endPage = (int)(Math.ceil(currentPage/(double)CNT_PER_PAGE_GROUP))*CNT_PER_PAGE_GROUP;   
	      this.startPage = endPage-CNT_PER_PAGE_GROUP+1;
	      this.endPage = (totalPage-startPage<CNT_PER_PAGE_GROUP)? totalPage : endPage; 

	}
	
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public static int getCntPerPage() {
		return CNT_PER_PAGE;
	}
	public static int getCntPerPageGroup() {
		return CNT_PER_PAGE_GROUP;
	}
	
//	this.startPage = (int) ((Math.ceil(1.0*currentPage/CNT_PER_PAGE_GROUP)-1) * CNT_PER_PAGE_GROUP+1);
//	this.endPage = (int) ((Math.ceil(1.0*currentPage/CNT_PER_PAGE_GROUP)) * CNT_PER_PAGE_GROUP);
}
