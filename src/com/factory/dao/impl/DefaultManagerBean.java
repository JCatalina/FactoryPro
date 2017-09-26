package com.factory.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.factory.common.QueryResult;
import com.factory.dao.Dao;
import com.factory.dao.DefaultManager;


@Component("defaultManager")
public class DefaultManagerBean implements DefaultManager {
	protected Dao defaultdDao;
	public Dao getBrandDao() {
		return defaultdDao;
	}
	@Resource(name="defaultDao")
	public void setBrandDao(Dao brandDao) {
		this.defaultdDao = brandDao;
	}
	
	public <T> long getCountByField(Class<T> entity,String Field,String whereql,Object[] queryParme){
		return this.defaultdDao.getCountByField(entity,Field,whereql,queryParme);
	}
	
	public <T> void delete(Class<T> entity, Object entityId) {
		this.defaultdDao.delete(entity, entityId);

	}
	
	public <T> void delete(Class<T> entity, String where,Boolean isWhere) {
		this.defaultdDao.delete(entity, where, isWhere);
	}


	public <T> void delete(Class<T> entity, Object[] enetityId) {
		this.defaultdDao.delete(entity, enetityId);
	}

	public <T> T find(Class<T> entity, Object entityId) {
		return this.defaultdDao.find(entity, entityId);
	}
	

	
	public <T> QueryResult<T> getScrollData(Class<T> entity, int firstindex,
			int maxresult, String whereql, Object[] queryParme,
			LinkedHashMap<String, String> orderby) {
		return this.defaultdDao.getScrollData(entity, firstindex, maxresult, whereql, queryParme, orderby);
	}
	
	public <T> QueryResult<T> getScrollDataSpecial(Class<T> entity,String ziduan, int firstindex,
			int maxresult, String whereql, Object[] queryParme,
			LinkedHashMap<String, String> orderby) {
		return this.defaultdDao.getScrollDataSpecial(entity,ziduan, firstindex, maxresult, whereql, queryParme, orderby);
	}

	public <T> QueryResult<T> getScrollData(Class<T> entity, int firstindex,
			int maxresult, String whereql, Object[] queryParme) {
		return this.defaultdDao.getScrollData(entity, firstindex, maxresult, whereql, queryParme);
	}

	public <T> QueryResult<T> getScrollData(Class<T> entity, int firstindex,
			int maxresult) {
		return this.defaultdDao.getScrollData(entity, firstindex, maxresult);
	}

	public <T> QueryResult<T> getScrollData(Class<T> entity) {
		return this.defaultdDao.getScrollData(entity);
	}

	public <T> QueryResult<T> getScrollData(Class<T> entity, int firstindex,
			int maxresult, LinkedHashMap<String, String> orderby) {
		return this.defaultdDao.getScrollData(entity, firstindex, maxresult, orderby);
	}

	public <T> QueryResult<T> getScrollData(Class<T> entity, String whereql,
			Object[] queryParme, LinkedHashMap<String, String> orderby) {
		return this.defaultdDao.getScrollData(entity, whereql, queryParme, orderby);
	}

	public void save(Object entity) {
		this.defaultdDao.save(entity);
	}
	
	public void saveForMerge(Object entity) {
		this.defaultdDao.saveForMerge(entity);
	}

	public void update(Object entity) {
		this.defaultdDao.update(entity);
	}
	
	public <T> List<T> getList(Class<T> entity,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby){
		return this.defaultdDao.getList(entity, whereql, queryParme, orderby);
	}
	

	public <T> List<T> getList(Class<T> entity){
		return this.defaultdDao.getList(entity);
	}
	
	public <T> List<T> getList(Class<T> entity,String whereql,Object[] queryParme){
		return this.defaultdDao.getList(entity, whereql, queryParme);
	}
	
	public <T> List<T> getList(Class<T> entity,int firstindex,
			int maxresult,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby){
		return this.defaultdDao.getList(entity,firstindex,maxresult, whereql, queryParme, orderby);
	}
	
	public <T> T getT(Class<T> entity,String whereql,Object[] queryParme){
		return this.defaultdDao.getT(entity, whereql, queryParme);
	}
	
	public <T> QueryResult<T> getManyToMany(Class<T> entity,String colname,  int firstindex,
			int maxresult,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby){
		return this.defaultdDao.getManyToMany(entity, colname, firstindex, maxresult, whereql, queryParme, orderby);
	}
	
	public <T> QueryResult<T> getManyToManySpecial(Class<T> entity,String ziduan,String colname,String colname2,  int firstindex,
			int maxresult,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby){
		return this.defaultdDao.getManyToManySpecial(entity,ziduan, colname,colname2, firstindex, maxresult, whereql, queryParme, orderby);
	}
	
	public <T> List<T> getManyToManyList(Class<T> entity,String colname,  int firstindex,
			int maxresult,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby){
		return this.defaultdDao.getManyToManyList(entity, colname, firstindex, maxresult, whereql, queryParme, orderby);
	}
	public void update(Object newObject,Object oldObject){
		this.defaultdDao.update(newObject, oldObject);
	}
	public void update(Object newObject,Object oldObject,boolean full,boolean filePath){
		this.defaultdDao.update(newObject);
	}

	public <T> void updateToHQL(Class<T> entity,String updatecol,String wherecol,Object[] values){
		this.defaultdDao.updateToHQL(entity, updatecol, wherecol, values);
	}

	
	public <T> long getCount(Class<T> entity){
		return this.defaultdDao.getCount(entity);
	}

	public <T> long getCount(Class<T> entity,String whereql,Object[] queryParme){
		return this.defaultdDao.getCount(entity,whereql,queryParme);
	}
	
	/**根据条件查询数量（统计ID）*/
	public <T> long getCountId(Class<T> entity,String whereql,Object[] queryParme){
		return this.defaultdDao.getCountId(entity, whereql, queryParme);
	}
	
	public <T> QueryResult<T> getManyToMany(Class<T> entity,String colname,String distinct, int firstindex,
			int maxresult,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby) {
		return this.defaultdDao.getManyToMany(entity, colname, distinct, firstindex, maxresult, whereql, queryParme, orderby);
	}
	

	public <T,U> List<U> getPropertyForEntity(Class<T> entity,Class<U> returnObjType,String selectMsg,String whereql,Object[] queryParme){
		return this.defaultdDao.getPropertyForEntity(entity, returnObjType, selectMsg, whereql, queryParme);
	}
	

	public <T> List<T> getPropertyForEntity(Class<T> entity,String selectMsg,String whereql,Object[] queryParme){
		return this.defaultdDao.getPropertyForEntity(entity, selectMsg, whereql, queryParme);
	}
	
	public Long sum(Class cls,String method,String whereql,Object[] queryParme){
		return this.defaultdDao.sum(cls, method, whereql, queryParme);
	}
	
	public List<Object[]> createSQLQuery(String sql){
		return this.defaultdDao.createSQLQuery(sql);
	}
	
	public <T> List<T> createHQLQuery(Class<T> entity,String hql,Object[] queryParme){
		return this.defaultdDao.createHQLQuery(entity,hql,queryParme);
	}
	
	
	public <T> List<T> queryHQLList(Class<T> entity,Integer firstindex,Integer maxresult,String hql,Object[] queryParme,LinkedHashMap<String, String> orderby){
		return this.defaultdDao.queryHQLList(entity, firstindex, maxresult, hql, queryParme, orderby);
	}

	public <T> QueryResult<T> queryHQLScrollData(Class<T> entity, Integer firstindex,Integer maxresult,String hql,String hqlCount,Object[] queryParme,LinkedHashMap<String, String> orderby){
		return this.defaultdDao.queryHQLScrollData(entity, firstindex, maxresult, hql, hqlCount, queryParme, orderby);
	}
	
	public <T> T queryHQLT(Class<T> entity, String hql,Object[] queryParme){
		return this.defaultdDao.queryHQLT(entity, hql,queryParme);
	}
	/**
	 * 自定义sql查询
	 * 
	 */
	public <T> List<T> querySqlList(String hql){
		return this.defaultdDao.querySqlList(hql);
	}
	
	public <T> double getSumByField(Class<T> entity, String Field,
		String whereql, Object[] queryParme) {
		return this.defaultdDao.getSumByField(entity, Field, whereql,queryParme);
	}
	
	public List<Object[]> createSQLQuery(String sql, Object[] queryParme) {
		return this.defaultdDao.createSQLQuery(sql, queryParme);
	}
	
	
}
