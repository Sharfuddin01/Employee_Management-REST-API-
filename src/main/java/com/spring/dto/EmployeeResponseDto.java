package com.spring.dto;

import java.util.List;

public class EmployeeResponseDto {
	
	private List<EmployeeDto> content;
	private int pageNo;
	private int pageSize;
	private long totalElement;
	private int totalPage;
	private boolean lastPage;
	private boolean firstPage;
	
	
	public List<EmployeeDto> getContent() {
		return content;
	}
	public void setContent(List<EmployeeDto> content) {
		this.content = content;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public long getTotalElement() {
		return totalElement;
	}
	public void setTotalElement(long totalElement) {
		this.totalElement = totalElement;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public boolean isLastPage() {
		return lastPage;
	}
	public void setLastPage(boolean lastPage) {
		this.lastPage = lastPage;
	}
	public boolean isFirstPage() {
		return firstPage;
	}
	public void setFirstPage(boolean firstPage) {
		this.firstPage = firstPage;
	}
	
	
	

}
