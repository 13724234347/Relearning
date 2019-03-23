package com.tzh.entity;

import java.util.List;

public class Page<T> {
	private Integer pageNum;// 当前条数
	private Integer pageSize;// 设置每页多少条数据
	private Integer totalCount;// 总条数
	private Integer pageCount;// 总页数
	private List<T> dataList;// 用来存储数据,返回给页面
	private T t;//实体类

	// 根据输入的页数查看所需页数，所以需要运算才能实现limit达到的效果,limit所需的参数是当前条数和往后多少条来查询,所以,得出规律（当前页数-1）*当前条数
	public Integer getLimitNum() {
		return (pageNum - 1) * pageSize;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	// 在设置总条数的时候完成总页数设置
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
		setPageCount(getPageCount());// 将计算好的总页数加入
	}

	// 总页数需要计算 总页数 = 总条数/每页条数
	public Integer getPageCount() {
		pageCount = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;// 将总条数取模每页条数如果被整除则为0,否则不为0
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	@Override
	public String toString() {
		return "Page [pageNum=" + pageNum + ", pageSize=" + pageSize + ", totalCount=" + totalCount + ", pageCount="
				+ pageCount + ", dataList=" + dataList + ", t=" + t + "]";
	}

}
