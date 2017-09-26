package com.factory.dao;

import java.util.LinkedHashMap;
import java.util.List;

import com.factory.common.QueryResult;



public interface DefaultManager {
	
	public <T> void save(Object entity);
	
	public <T> void saveForMerge(Object entity);
	
	public <T> void delete(Class<T> entity, String where,Boolean isSql);
	
	public <T> void delete(Class<T> entity,Object entityId);
	
	public <T> void delete(Class<T> entity,Object[] enetityId);

	public <T> T find(Class<T> entity,Object entityId);

	public void update(Object entity);

	public <T> List<T> getList(Class<T> entity,int firstindex,
			int maxresult,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby);

	public <T> QueryResult<T> getScrollData(Class<T> entity, int firstindex,
			int maxresult,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby);
	
	public <T> QueryResult<T> getScrollDataSpecial(Class<T> entity,String ziduan, int firstindex,
			int maxresult,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby);
	
	public <T> QueryResult<T> getScrollData(Class<T> entity, int firstindex,
			int maxresult,String whereql,Object[] queryParme);

	public <T> QueryResult<T> getScrollData(Class<T> entity, int firstindex,
			int maxresult);
	
	public <T> QueryResult<T> getScrollData(Class<T> entity);

	public <T> QueryResult<T> getScrollData(Class<T> entity, int firstindex,
			int maxresult,LinkedHashMap<String, String> orderby);

	public <T> QueryResult<T> getScrollData(Class<T> entity,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby);

	public <T> List<T> getList(Class<T> entity,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby);
	
	
	public <T> List<T> getList(Class<T> entity);

	public <T> List<T> getList(Class<T> entity,String whereql,Object[] queryParme);

	public <T> T getT(Class<T> entity,String whereql,Object[] queryParme);


	public <T> QueryResult<T> getManyToMany(Class<T> entity,String colname,  int firstindex,
			int maxresult,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby);
	
	public <T> QueryResult<T> getManyToManySpecial(Class<T> entity,String ziduan,String colname,String colname2,  int firstindex,
			int maxresult,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby);
	
	public <T> List<T> getManyToManyList(Class<T> entity,String colname,  int firstindex,
			int maxresult,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby);

	public void update(Object newObject,Object oldObject);

	public void update(Object newObject,Object oldObject,boolean full,boolean filePath);	

	public <T> void updateToHQL(Class<T> entity,String updatecol,String wherecol,Object[] values);

	
	public <T> long getCount(Class<T> entity);

	public <T> long getCount(Class<T> entity,String whereql,Object[] queryParme);
	/**根据条件查询数量（统计ID）*/
	public <T> long getCountId(Class<T> entity,String whereql,Object[] queryParme);
	
	/**统计表中某个字段相乘的总和**/
	public <T> long getCountByField(Class<T> entity,String Field,String whereql,Object[] queryParme);
	
	/**统计某个字段总和**/
	public <T> double getSumByField(Class<T> entity,String Field,String whereql,Object[] queryParme);
	
	public <T> QueryResult<T> getManyToMany(Class<T> entity,String colname,String distinct, int firstindex,
			int maxresult,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby);

	public <T,U> List<U> getPropertyForEntity(Class<T> entity,Class<U> returnObjType,String selectMsg,String whereql,Object[] queryParme);
	
	public <T> List<T> getPropertyForEntity(Class<T> entity,String selectMsg,String whereql,Object[] queryParme);

	public Long sum(Class cls,String method,String whereql,Object[] queryParme);
	
	public List<Object[]> createSQLQuery(String sql);
	
	public <T> List<T> createHQLQuery(Class<T> entity,String hql,Object[] queryParme);
	
	/**
	 * 自定义查询HQL
	 * 普通查询eg :select o from goods o where o.id = ?
	 * 多对多查询eg：select o from goods o join o.user u  where u.id = ? and o.id = ?
	 * 非*查询eg：(在对应对象添加构造方法)select new goods(o.id,o.name) from goods o where o.id = ?
	 * @return 
	 * */
	public <T> List<T> queryHQLList(Class<T> entity,Integer firstindex,Integer maxresult,String hql,Object[] queryParme,LinkedHashMap<String, String> orderby);

	/**
	 * 自定义查询HQL(分页)
	 * 普通查询eg :select o from goods o where o.id = ?
	 * 多对多查询eg：select o from goods o join o.user u  where u.id = ? and o.id = ?
	 * 非*查询eg：(在对应对象添加构造方法)select new goods(o.id,o.name) from goods o where o.id = ?
	 * @return 
	 * */
	public <T> QueryResult<T> queryHQLScrollData(Class<T> entity, Integer firstindex,Integer maxresult,String hql,String hqlCount,Object[] queryParme,LinkedHashMap<String, String> orderby);

	public <T> T queryHQLT(Class<T> entity, String hql,Object[] queryParme);
	/**
	 * 自定义sql查询
	 * 
	 */
	public <T> List<T> querySqlList(String hql);
	
	/**
	 * 设置占位符参数的SQL查询
	 * @param sql 传递的sql
	 * @param queryParme 参数列表
	 * @return
	 */
	public List<Object[]> createSQLQuery(String sql,Object[] queryParme);
	
}