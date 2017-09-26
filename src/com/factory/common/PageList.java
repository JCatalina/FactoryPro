package com.factory.common;

import java.util.List;

/**
 * 分页对象
 * @author Administrator
 *
 * @param <T>
 */
public class PageList<T> {
	public static int MAX = 15;
	/**
	 *当前页
	 */
	private int firstindex;

	/**
	 * 每页显示记录数
	 */
	private int maxresult;

	/**
	 * 总页数
	 */
	private int pageCount;

	/**
	 * 列表
	 */
	private List<T> pts;

	/**
	 * 总结果数
	 */
	private int totalResult;
	
	public PageList(){}
	
	public PageList(int maxresult, int firstindex) {
		this.maxresult = maxresult;
		this.firstindex = firstindex;
	}

	public int getFirstindex() {
		return (firstindex-1)*this.maxresult;
	}

	public int getMaxresult() {
		return maxresult;
	}

	public int getPageCount() {
		pageCount = totalResult % maxresult==0?totalResult/maxresult : totalResult/maxresult+1;
		return pageCount;
	}

	public List<T> getPts() {
		return pts;
	}
	public int getTotalResult() {
		return totalResult;
	}
	public void setFirstindex(int firstindex) {
		this.firstindex = firstindex;
	}
	public void setMaxresult(int maxresult) {
		this.maxresult = maxresult;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public void setPts(List<T> pts) {
		this.pts = pts;
	}

	public void setQueryResult(QueryResult<T> qr) {
		this.totalResult=(int) qr.getTatolSize();
		pts = (List<T>) qr.getGetResult();
	}

	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}
}
