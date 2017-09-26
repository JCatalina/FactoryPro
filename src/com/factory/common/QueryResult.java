package com.factory.common;

import java.util.List;

/**
 *查询结果集
 * @author lijietian
 *
 * @param <T>
 */
public class QueryResult<T> {
	/**
	 * 查询结果集
	 */
	private List<T> getResult;
	/**结果集个数*/
	private long tatolSize;
	public List<T> getGetResult() {
		return getResult;
	}
	public void setGetResult(List<T> getResult) {
		this.getResult = getResult;
	}
	public long getTatolSize() {
		return tatolSize;
	}
	public void setTatolSize(long tatolSize) {
		this.tatolSize = tatolSize;
	}
}
