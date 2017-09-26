package com.factory.dao;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Entity;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.factory.common.QueryResult;




/**
 * DAO抽象实现
 * @author lijietian
 *
 */
public abstract class DaoSupport implements Dao{
	
	@Resource(name = "sessionFactory")
	protected SessionFactory sf;
	
	public Session getSession(){
		return sf.getCurrentSession();
	}
	
	
	public <T,U> List<U> getPropertyForEntity(Class<T> entity,Class<U> returnObjType,String selectMsg,String whereql,Object[] queryParme){
		// select max(m.orderNumber) from Message m where m.messageFlag=1
		String entityName = getEntityName(entity);
		/*if(entity.equals(User.class))
			entityName = "base_user";*/
		Query query = (Query) getSession().createSQLQuery("select "+selectMsg+" from "+entityName+" o "+ (whereql==null ? "" :"where " + whereql));
		setWhereQl(query,queryParme);
		return query.list();
	}
	
	public <T> List<T> getPropertyForEntity(Class<T> entity,String selectMsg,String whereql,Object[] queryParme){
		//select new Problem(pid,score,title,totalAccept,totalSubmission,unSee) from Problem order by pid
		String entityName = getEntityName(entity);
		String hql = "select new "+entityName+"("+selectMsg+") from "+entityName+" o "+ (whereql==null ? "" :"where " + whereql);
		Query query = (Query) getSession().createQuery(hql);
		setWhereQl(query,queryParme);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public <T> void delete(Class<T> entity, Object entityId) {
		delete(entity,new Object[]{entityId});
	}
	
	public <T> void delete(Class<T> entity, String where,Boolean isSql) {
		String entityName = getEntityName(entity);
		String hql = "delete "+entityName+" o where "+where;
		//System.err.println(hql);
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}
	
	/**删除*/
	public <T> void delete(Class cls,String whereql,Object[] queryParme){
		String entityName = getEntityName(cls);
		Query query = (Query) getSession().createQuery("delete "+entityName+" o "+ (whereql==null ? "" :"where " + whereql));
		setWhereQl(query,queryParme);
		query.executeUpdate();
	}
	

	public <T> void delete(Class<T> entity, Object[] enetityIds) {
		for(Object id : enetityIds) {
			Object object = getSession().get(entity, (Serializable) id);
			getSession().delete(object);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> T find(Class<T> entity, Object entityId) {
		return (T) getSession().get(entity, (Serializable) entityId);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> List<T> getList(Class<T> entity,int firstindex,int maxresult,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby) {
		String entityName = getEntityName(entity);
		String orderbyql = getOrderBy(orderby);
		//System.out.println(whereql);
		Query query = (Query) getSession().createQuery("select o from "+entityName+" o "+ (whereql==null ? "" :"where " + whereql)  + orderbyql);
		setWhereQl(query,queryParme);
		//System.err.println("select o from "+entityName+" o "+ (whereql==null ? "" :"where " + whereql));
		if(firstindex!=-1 && maxresult!=-1) {
			if(firstindex!=0) {
				query.setFirstResult(firstindex);
			}
			query.setMaxResults(maxresult);
		}
		return query.list();
	}
	
	/**自由组编排序**//*
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> List<T> getListOrderby(Class<T> entity,int firstindex,int maxresult,String whereql,Object[] queryParme,String orderby) {
		String entityName = getEntityName(entity);
		//System.out.println(whereql);
		Query query = (Query) getSession().createQuery("select o from "+entityName+" o "+ (whereql==null ? "" :"where " + whereql)  + orderby);
		setWhereQl(query,queryParme);
		System.err.println("select o from "+entityName+" o "+ (whereql==null ? "" :"where " + whereql)  + orderby);
		if(firstindex!=-1 && maxresult!=-1) {
			if(firstindex!=0) {
				query.setFirstResult(firstindex);
			}
			query.setMaxResults(maxresult);
		}
		return query.list();
	}*/
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> List<T> getListSpecial(Class<T> entity,String ziduan,int firstindex,int maxresult,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby) {
		String entityName = getEntityName(entity);
		String orderbyql = getOrderBy(orderby);
		//System.out.println(whereql);
//		Query query = (Query) getSession().createQuery("select o from "+entityName+" o "+ (whereql==null ? "" :"where " + whereql)  + orderbyql);
//		Query query = (Query) getSession().createQuery("select o from "+entityName+" o "+ (whereql==null ? "" :"where " + whereql)  + orderbyql);
		Query query = (Query) getSession().createQuery("select new "+entityName+"("+ziduan+") from "+entityName+" o "+ (whereql==null ? "" :"where " + whereql)  + orderbyql);
//		Query query = (Query) getSession().createQuery("select new UserOrder(g.id,g.payType,g.user) from UserOrder g");//,Product p where g.product.id=p.id
		setWhereQl(query,queryParme);
		if(firstindex!=-1 && maxresult!=-1) {
			if(firstindex!=0) {
				query.setFirstResult(firstindex);
			}
			query.setMaxResults(maxresult);
		}
		return query.list();
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> List<T> getList(Class<T> entity,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby) {
		return getList(entity,-1,-1,whereql,queryParme,orderby);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> List<T> getList(Class<T> entity) {
		return getList(entity,-1,-1,null,null,null);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> List<T> getList(Class<T> entity,String whereql,Object[] queryParme) {
		return getList(entity,-1,-1,whereql,queryParme,null);
	}
	
	/*@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> List<T> getListOrderby(Class<T> entity,String whereql,Object[] queryParme,String orderby) {
		return getListOrderby(entity,-1,-1,whereql,queryParme,orderby);
	}*/
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> List<T> getListSpecial(Class<T> entity,String ziduan,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby) {
		return getListSpecial(entity,ziduan,-1,-1,whereql,queryParme,orderby);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> T getT(Class<T> entity,String whereql,Object[] queryParme) {
		List<T> lists = getList(entity,0,1,whereql,queryParme,null);//这里改为从第0条开始拿1条，以前那样做法是获取所有的出来，再拿一条很�?
		if(null!=lists && lists.size()>0){
			return lists.get(0);
		}else{
			return null;
		}
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> T getPrevious(Class<T> entity, Object entityId) {
		T t = this.find(entity, entityId);
		List<T> lists = getList(entity);
		if(null!=lists && lists.size()>1){
			for(int i=0;i<lists.size();i++){
				if((i+1)<lists.size() && lists.get(i+1).equals(t)) return lists.get(i);
			}
		}
		return null;
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> T getNext(Class<T> entity, Object entityId) {
		T t = this.find(entity, entityId);
		List<T> lists = getList(entity);
		if(null!=lists && lists.size()>1){
			for(int i=1;i<lists.size();i++){
				if(lists.get(i-1).equals(t)) return lists.get(i);
			}
		}
		return null;
	}
	
	public <T> void save(Object entity) {
		getSession().save(entity);
		getSession().flush();
		getSession().clear();
	}
	
	/**
	 * @author �? （用于批量插入数据，及时清空session缓存，避免循环插入大量数据时越来越慢的问题，尚不清楚副作用）
	 * @param entity
	 */
	public <T> void fastSave(Object entity) {
		getSession().save(entity);
		getSession().flush();
		getSession().clear();
	}
	
	/**�? 14.01.03**/
	public <T> void saveForMerge(Object entity){
		getSession().merge(entity);
	}
	
	
	public void update(Object entity) {
		getSession().update(entity);
	}
	
	public void update(Object newObject,Object oldObject){
		update(newObject,oldObject,true,true);
	}
		
	@SuppressWarnings("unchecked")
	public <T> void update(Object newObject,Object oldObject,boolean full,boolean filePath){
		getSession().update(oldObject);
	}
	

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getScrollData(Class<T> entity, int firstindex,
			int maxresult,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby) {
		
		QueryResult qr = new QueryResult<T>();
		String entityName = getEntityName(entity);
		String orderbyql = getOrderBy(orderby);
		
		Query query = (Query) getSession().createQuery("select o from "+entityName+" o "+ (whereql==null ? "" :"where " + whereql)  + orderbyql);
		setWhereQl(query,queryParme);
		if(firstindex!=-1 && maxresult!=-1) {
			if(firstindex!=0) {
				query.setFirstResult(firstindex);
			}
			query.setMaxResults(maxresult);
		}
		qr.setGetResult(query.list());
		Query query1 = (Query) getSession().createQuery("select count(o) from "+entityName+" o "+ (whereql==null ? "" :"where " + whereql));
		setWhereQl(query1,queryParme);
		qr.setTatolSize((Long)query1.uniqueResult());
		return qr;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getScrollDataSpecial(Class<T> entity, String ziduan, int firstindex,
			int maxresult,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby) {
		
		QueryResult qr = new QueryResult<T>();
		String entityName = getEntityName(entity);
		String orderbyql = getOrderBy(orderby);
//		hql2="select new "+entityName+"("+ziduan+")"+"from "+entityName+" o join o." + colname + " oo " + (whereql==null ? "" :"where " + whereql)  + orderbyql;
//		Query query = (Query) getSession().createQuery("select o from "+entityName+" o "+ (whereql==null ? "" :"where " + whereql)  + orderbyql);
		Query query = (Query) getSession().createQuery("select new "+entityName+"("+ziduan+")"+" from "+entityName+" o "+ (whereql==null ? "" :"where " + whereql)  + orderbyql);
		setWhereQl(query,queryParme);
		if(firstindex!=-1 && maxresult!=-1) {
			if(firstindex!=0) {
				query.setFirstResult(firstindex);
			}
			query.setMaxResults(maxresult);
		}
		qr.setGetResult(query.list());
		Query query1 = (Query) getSession().createQuery("select count(o) from "+entityName+" o "+ (whereql==null ? "" :"where " + whereql));
		setWhereQl(query1,queryParme);
		qr.setTatolSize((Long)query1.uniqueResult());
		return qr;
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getScrollData(Class<T> entity, int firstindex,
			int maxresult, LinkedHashMap<String, String> orderby) {
		return getScrollData(entity,firstindex,maxresult,null,null,orderby);
	}
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getScrollData(Class<T> entity, int firstindex,
			int maxresult, String whereql, Object[] queryParme) {
		return getScrollData(entity,firstindex,maxresult,whereql,queryParme,null);
	}
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getScrollData(Class<T> entity, int firstindex,
			int maxresult) {
		return getScrollData(entity,firstindex,maxresult,null,null,null);
	}
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getScrollData(Class<T> entity, String whereql,
			Object[] queryParme, LinkedHashMap<String, String> orderby) {
		return getScrollData(entity,-1,-1,whereql,queryParme,orderby);
	}
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getScrollData(Class<T> entity) {
		return getScrollData(entity,-1,-1,null,null,null);
	}
	
	public <T> long getManyToManyCount(Class<T> entity,String colname,String distinct,String whereql,Object[] queryParme){
		String entityName = getEntityName(entity);
		Query query1 = (Query) getSession().createQuery("select count(distinct o) from "+entityName+" o join o." + colname + " oo " + (whereql==null ? "" :"where " + whereql));
		setWhereQl(query1,queryParme);
		return((Long)query1.uniqueResult());
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getManyToMany(Class<T> entity,String colname,  int firstindex,
			int maxresult,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby) {
		//"select o from "+leiming+" o join o.user u  where u.id ="+ id
		QueryResult qr = new QueryResult<T>();
		String entityName = getEntityName(entity);
		String orderbyql = getOrderBy(orderby);
		String hql = "select o from "+entityName+" o join o." + colname + " oo " + (whereql==null ? "" :"where " + whereql)  + orderbyql;
		//System.err.println(hql);
		Query query = (Query) getSession().createQuery(hql);
		setWhereQl(query,queryParme);
		if(firstindex!=-1 && maxresult!=-1) {
			if(firstindex!=0) {
				query.setFirstResult(firstindex);
			}
			query.setMaxResults(maxresult);
		}
		qr.setGetResult(query.list());
		Query query1 = (Query) getSession().createQuery("select count(o) from "+entityName+" o join o." + colname + " oo " + (whereql==null ? "" :"where " + whereql));
		setWhereQl(query1,queryParme);
		qr.setTatolSize((Long)query1.uniqueResult());
		
		return qr;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getManyToManySpecial(Class<T> entity,String ziduan,String colname,String colname2,  int firstindex,
			int maxresult,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby) {
		//"select o from "+leiming+" o join o.user u  where u.id ="+ id
		QueryResult qr = new QueryResult<T>();
		String entityName = getEntityName(entity);
		String orderbyql = getOrderBy(orderby);
		String hql = "select o from "+entityName+" o join o." + colname + " oo " + (whereql==null ? "" :"where " + whereql)  + orderbyql;
		String hql2="select new "+entityName+"("+ziduan+")"+"from "+entityName+" o join o." + colname + " oo " + (whereql==null ? "" :"where " + whereql)  + orderbyql;
		//System.err.println(hql);
		Query query = (Query) getSession().createQuery(hql2);
		setWhereQl(query,queryParme);
		if(firstindex!=-1 && maxresult!=-1) {
			if(firstindex!=0) {
				query.setFirstResult(firstindex);
			}
			query.setMaxResults(maxresult);
		}
		qr.setGetResult(query.list());
		Query query1 = (Query) getSession().createQuery("select count(o) from "+entityName+" o join o." + colname + " oo " + (whereql==null ? "" :"where " + whereql));
		setWhereQl(query1,queryParme);
		qr.setTatolSize((Long)query1.uniqueResult());
		
		return qr;
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getManyToMany(Class<T> entity,String colname,String distinct, int firstindex,
			int maxresult,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby) {
		//"select o from "+leiming+" o join o.user u  where u.id ="+ id
		QueryResult qr = new QueryResult<T>();
		String entityName = getEntityName(entity);
		String orderbyql = getOrderBy(orderby);
		Query query = (Query) getSession().createQuery("select distinct o from "+entityName+" o join o." + colname + " oo " + (whereql==null ? "" :"where " + whereql)  + orderbyql);
		setWhereQl(query,queryParme);
		if(firstindex!=-1 && maxresult!=-1) {
			if(firstindex!=0) {
				query.setFirstResult(firstindex);
			}
			query.setMaxResults(maxresult);
		}
		qr.setGetResult(query.list());
		Query query1 = (Query) getSession().createQuery("select count(distinct o) from "+entityName+" o join o." + colname + " oo " + (whereql==null ? "" :"where " + whereql));
		setWhereQl(query1,queryParme);
		qr.setTatolSize((Long)query1.uniqueResult());
		
		return qr;
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> List<T> getManyToManyList(Class<T> entity,String colname,  int firstindex,
			int maxresult,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby) {
		//"select o from "+leiming+" o join o.user u  where u.id ="+ id
		String entityName = getEntityName(entity);
		String orderbyql = getOrderBy(orderby);
		Query query = (Query) getSession().createQuery("select o from "+entityName+" o join o." + colname + " oo " + (whereql==null ? "" :"where " + whereql)  + orderbyql);
		setWhereQl(query,queryParme);
		
		if(firstindex!=-1 && maxresult!=-1) {
			if(firstindex!=0) {
				query.setFirstResult(firstindex);
			}
			query.setMaxResults(maxresult);
		}
		return query.list();
	}

	protected void setWhereQl(Query query,Object[] queryParme){
		if(queryParme!=null && queryParme.length>0) {
			for(int i=0;i<queryParme.length;i++){
				query.setParameter(i, queryParme[i]);
			}
		}
	}
	
	protected <T> String getEntityName(Class<T> entity){
		String entityName = entity.getSimpleName();
		Entity en = entity.getAnnotation(Entity.class);
		if(en.name()!=null && !"".equals(en.name())){
			entityName = en.name();
		}
		return entityName;
	}
	//order by o.key1 desc,o.key2 asc
	protected String getOrderBy(LinkedHashMap<String, String> orderby) {
		StringBuffer sb = new StringBuffer("");
		if(orderby!=null &&orderby.size()>0) {
			sb.append(" order by ");
			for(String key: orderby.keySet()) {
				sb.append("o.").append(key).append(" ").append(orderby.get(key)).append(",");
			}
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
	
	/**更新forHql*/
	public <T> void updateToHQL(Class<T> entity,String updatecol,String wherecol,Object[] values){
		String entityName = getEntityName(entity);
		Query query = (Query) getSession().createQuery("update "+entityName+" o set "+ (updatecol==null ? "" : updatecol)+ (wherecol==null ? "" :" where " + wherecol));
		setWhereQl(query,values);
		query.executeUpdate();
	}
	
	public <T> long getCountByField(Class<T> entity,String Field,String whereql,Object[] queryParme){
		String entityName = getEntityName(entity);
		Query query =  getSession().createQuery("select sum("+Field+") from "+entityName+" o " + (whereql==null ? "" :"where " + whereql));
		setWhereQl(query,queryParme);		return (Long) query.uniqueResult();
	}
	
	public <T> double getSumByField(Class<T> entity, String Field,
		String whereql, Object[] queryParme) {
		String entityName = getEntityName(entity);
		Query query = getSession().createQuery(
			"select sum(" + Field + ") from " + entityName + " o "
				+ (whereql == null ? "" : "where " + whereql));
		setWhereQl(query, queryParme);
		return (Double) (query.uniqueResult() == null ? 0D : query
		.uniqueResult());
	}
	
	
	public <T> long getCount(Class<T> entity) {
		String entityName = getEntityName(entity);
		Query query =  getSession().createQuery("select count(*) from "+entityName);
		return (Long) query.uniqueResult();
	}
	public <T> long getCount(Class<T> entity,String whereql,Object[] queryParme) {
		String entityName = getEntityName(entity);
		Query query = (Query) getSession().createQuery("select count(o) from "+entityName+" o "+ (whereql==null ? "" :"where " + whereql));
		setWhereQl(query,queryParme);
		return (Long) query.uniqueResult();
	}
	
	public <T> long getCountId(Class<T> entity,String whereql,Object[] queryParme) {
		String entityName = getEntityName(entity);
		Query query = (Query) getSession().createQuery("select count(o.id) from "+entityName+" o "+ (whereql==null ? "" :"where " + whereql));
		setWhereQl(query,queryParme);
		return (Long) query.uniqueResult();
	}
	
	
	public <T> void saves(List<T> ts){
		for(T t : ts){
			save(t);
		}
	}
	
	public <T> List<T> participlesQuery(Class<T> entity,int firstindex,
			int maxresult,String[] colsname,String value,LinkedHashMap<String, String> orderby){
		String entityName = getEntityName(entity);
		String orderbyql = getOrderBy(orderby);
		String where = setWhereQl(colsname,value);
		Query query = (Query) getSession().createQuery("select o from "+entityName+" o where "+ where + orderbyql);
		if(firstindex!=-1 && maxresult!=-1) {
			if(firstindex!=0) {
				query.setFirstResult(firstindex);
			}
			query.setMaxResults(maxresult);
		}
		return query.list();
	}

	private String setWhereQl(String[] colsname, String value) {
		StringBuffer where = new StringBuffer("(");
		//-- start 2013-08-29 tian 修复忽略全词查询
		for(String colname : colsname){
			where.append("o.").append(colname).append(" like '%").append(value).append("%'").append(" or ");
		}
		//-- start 2013-08-29 tian 
		for(String str : value.split(" ")){
			for(String colname : colsname){
				where.append("o.").append(colname).append(" like '%").append(str).append("%'").append(" or ");
			}
		}
		where.delete(where.length()-4,where.length());
		where.append(")");
		return where.toString();
	}
	public int deleteHQL(String hql){
		return getSession().createQuery(hql).executeUpdate();
	}
	

	public Long sum(Class cls,String method,String whereql,Object[] queryParme){
		String entityName = getEntityName(cls);
		Query query = (Query) getSession().createQuery("select sum(o."+method+") from "+entityName+" o "+ (whereql==null ? "" :"where " + whereql));
		setWhereQl(query,queryParme);
		return (Long) query.uniqueResult();
	}
	
	public List<Object[]> createSQLQuery(String sql){
		Query query = (Query) getSession().createSQLQuery(sql);
		return query.list();
	}
	
	public <T> List<T> createHQLQuery(Class<T> entity,String hql,Object[] queryParme){
		Query query = (Query) getSession().createQuery(hql);
		setWhereQl(query,queryParme);
		return query.list();
	}
	
	/**执行更新sql*/
	public int executeSQLUpdate(String sql){
		Query query = (Query) getSession().createSQLQuery(sql);
		return query.executeUpdate();
	}
	
	/**
	 * 自定义查询HQL = getT
	 * 普�?查询eg :select o from goods o where o.id = ?
	 * 多对多查询eg：select o from goods o join o.user u  where u.id = ? and o.id = ?
	 * �?查询eg�?在对应对象添加构造方�?select new goods(o.id,o.name) from goods o where o.id = ?
	 * @return 
	 * */
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> T queryHQLT(Class<T> entity, String hql,Object[] queryParme){
		List<T> lists = queryHQLList(entity, null, null, hql, queryParme, null);
		if(null!=lists && lists.size()>0){
			return lists.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 自定义查询HQL
	 * 普�?查询eg :select o from goods o where o.id = ?
	 * 多对多查询eg：select o from goods o join o.user u  where u.id = ? and o.id = ?
	 * �?查询eg�?在对应对象添加构造方�?select new goods(o.id,o.name) from goods o where o.id = ?
	 * @return 
	 * */
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> List<T> queryHQLList(Class<T> entity, Integer firstindex,Integer maxresult,String hql,Object[] queryParme,LinkedHashMap<String, String> orderby){
		
		String orderbyql = getOrderBy(orderby);
		Query query = (Query) getSession().createQuery(hql  + orderbyql);
		setWhereQl(query,queryParme);
		
		if(firstindex!=null && maxresult!=null){//如果没有分页参数就不分页
			if(firstindex!=-1 && maxresult!=-1) {
				if(firstindex!=0) {
					query.setFirstResult(firstindex);
				}
				query.setMaxResults(maxresult);
			}
		}
		return query.list();
	}
	
	/**
	 * 自定义sql查询
	 * 默认返回List Object类型
	 * tip：可通过把Object 转换 成map后再赋�?给实�?
	 * eg:
	 * List<OrderReportBean> list = new ArrayList<OrderReportBean>();
		List o =dm.querySqlList("SELECT goodsName , SUM(count) as count FROM orderreport where shopNo='"+getLoginShop().getNo()+"' and orderTime>='"+sf.format(queryStartTime)+"' and orderTime <='"+sf.format(queryEndTime)+"' GROUP BY goodsName ORDER BY count desc limit 0,10");
		for(Object ob : o){
			OrderReportBean oBean  = new OrderReportBean();
			Map map = (Map) ob;
			oBean.setGoodsName(map.get("goodsName").toString());
			oBean.setCount(Integer.parseInt(map.get("count").toString()));
			list.add(oBean);
		}
	 * 
	 */
	public <T> List<T> querySqlList(String sql){
		List query =  getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return query;
	}
	
	/**
	 * 自定义查询HQL(分页)
	 * 普�?查询eg :select o from goods o where o.id = ?
	 * 多对多查询eg：select o from goods o join o.user u  where u.id = ? and o.id = ?
	 * �?查询eg�?在对应对象添加构造方�?select new goods(o.id,o.name) from goods o where o.id = ?
	 * @return 
	 * */
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> queryHQLScrollData(Class<T> entity, Integer firstindex,Integer maxresult,String hql,String hqlCount,Object[] queryParme,LinkedHashMap<String, String> orderby) {
		
		QueryResult qr = new QueryResult<T>();
		String orderbyql = getOrderBy(orderby);
		
		Query query = (Query) getSession().createQuery(hql  + orderbyql);
		setWhereQl(query,queryParme);
		
		if(firstindex!=null && maxresult!=null){//如果没有分页参数就不分页
			if(firstindex!=-1 && maxresult!=-1) {
				if(firstindex!=0) {
					query.setFirstResult(firstindex);
				}
				query.setMaxResults(maxresult);
			}
		}
		
		qr.setGetResult(query.list());
		
		Query query1 = (Query) getSession().createQuery(hqlCount);
		setWhereQl(query1,queryParme);
		qr.setTatolSize((Long)query1.uniqueResult());
		return qr;
	}
	
	public List<Object[]> createSQLQuery(String sql,Object[] queryParme){
		Query query = (Query) getSession().createSQLQuery(sql);
		setWhereQl(query, queryParme);
		return query.list();
	}
	
	
}

