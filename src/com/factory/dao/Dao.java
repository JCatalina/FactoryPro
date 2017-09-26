package com.factory.dao;
import java.util.LinkedHashMap;
import java.util.List;

import com.factory.common.QueryResult;

/**
 * dao接口
 *
 */
public interface Dao{
	/**
	 * 保存
	 * @param <T>
	 * @param entity 实体bean
	 */
	public <T> void save(Object entity);
	
	/**
	 * 快速保存（适用批量插入数据）
	 * @param entity
	 */
	public <T> void fastSave(Object entity);
	
	/**
	 * 保存 by merge
	 * @param <T>
	 * @param entity 实体bean
	 */
	public <T> void saveForMerge(Object entity);
	
	public <T> void delete(Class<T> entity, String where,Boolean isSql);
	
	/**删除*/
	public <T> void delete(Class cls,String whereql,Object[] queryParme);
	/**
	 * 根据id删除
	 * @param <T>
	 * @param entity 实体bean
	 * @param entityId 实体bean的主键
	 */
	public <T> void delete(Class<T> entity,Object entityId);
	/**
	 * 根据id数组删除
	 * @param <T>
	 * @param entity 实体bean
	 * @param enetityId 实体bean的主键集合
	 */
	public <T> void delete(Class<T> entity,Object[] enetityId);
	/**
	 * 查找
	 * @param <T>
	 * @param entity 实体bean
	 * @param entityId 实体bean的主键
	 * @return 根据id号返回对应的实体bean
	 */
	public <T> T find(Class<T> entity,Object entityId);
	/**
	 * 更新
	 * @param entity 实体bean
	 */
	public void update(Object entity);
	/**
	 * 按分页(起始位，记录数)，条件，排序查询
	 * @param <T>
	 * @param entity 实体bean
	 * @param firstindex 分页起始位置,-1表示不使用分页
	 * @param maxresult 每页记录,-1表示不使分页
	 * @param whereql 条件语句 如:"o.id=? and o.name=? and (o.cn=? or o.en=?)"
	 * @param queryParme 条件中未确定的值 如:new Object[]{1,"tian","cn","en"};
	 * @param orderby 排序 如:new LinkedHashMap<String,String>().put("id","desc");
	 * @return 分页对象
	 */
	public <T> QueryResult<T> getScrollData(Class<T> entity, int firstindex,
			int maxresult,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby);
	
	/**
	 * 按分页(起始位，记录数)，条件，排序查询(具体指定字段)
	 * @param <T>
	 * @param entity 实体bean
	 * @param firstindex 分页起始位置,-1表示不使用分页
	 * @param maxresult 每页记录,-1表示不使分页
	 * @param whereql 条件语句 如:"o.id=? and o.name=? and (o.cn=? or o.en=?)"
	 * @param queryParme 条件中未确定的值 如:new Object[]{1,"tian","cn","en"};
	 * @param orderby 排序 如:new LinkedHashMap<String,String>().put("id","desc");
	 * @return 分页对象
	 * @param ziduan  字段名称   "id,name,type"
	 */
	public <T> QueryResult<T> getScrollDataSpecial(Class<T> entity,String ziduan, int firstindex,
			int maxresult,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby);
	
	/**
	 * 按分页，条件，查询 
	 * @param <T>
	 * @param entity 实体bean
	 * @param firstindex 分页起始位置,-1表示不使用分页
	 * @param maxresult 每页记录,-1表示不使分页
	 * @param whereql 条件语句 如:"o.id=? and o.name=? and (o.cn=? or o.en=?)"
	 * @param queryParme 条件中未确定的值 如:new Object[]{1,"tian","cn","en"};
	 * @return 分页对象
	 */
	public <T> QueryResult<T> getScrollData(Class<T> entity, int firstindex,
			int maxresult,String whereql,Object[] queryParme);
	/**
	 * 按分页(起始位，记录数),查询
	 * @param <T>
	 * @param entity 实体bean
	 * @param firstindex 分页起始位置,-1表示不使用分页
	 * @param maxresult 每页记录,-1表示不使分页
	 * @return 分页对象
	 */
	public <T> QueryResult<T> getScrollData(Class<T> entity, int firstindex,
			int maxresult);
	
	/**
	 * 查询
	 * @param <T>
	 * @param entity 实体bean
	 * @return 分页对象
	 */
	public <T> QueryResult<T> getScrollData(Class<T> entity);
	/**
	 * 排序查询
	 * @param <T>
	 * @param entity 实体bean
	 * @param firstindex 分页起始位置,-1表示不使用分页
	 * @param maxresult 每页记录,-1表示不使分页
	 * @param orderby 排序 o.id desc
	 * @return 分页对象
	 */
	public <T> QueryResult<T> getScrollData(Class<T> entity, int firstindex,
			int maxresult,LinkedHashMap<String, String> orderby);
	/**
	 * 按条件，排序查询
	 * @param <T>
	 * @param entity 实体bean
	 * @param whereql 条件语句 如:"o.id=? and o.name=? and (o.cn=? or o.en=?)"
	 * @param queryParme 条件中未确定的值 如:new Object[]{1,"tian","cn","en"};
	 * @param orderby 排序 如:new LinkedHashMap<String,String>().put("id","desc");
	 * @return 分页对象
	 */
	public <T> QueryResult<T> getScrollData(Class<T> entity,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby);
	
	/**按照自己特定的排序语句来排序*/
	/*public <T> List<T> getListOrderby(Class<T> entity,int firstindex,int maxresult,String whereql,Object[] queryParme,String orderby);*/
	/**
	 * 按条件，排序
	 * @param <T>
	 * @param entity 实体bean
	 * @param firstindex 开始位置
	 * @param firstindex 查询条目数
	 * @param whereql 条件语句 如:"o.id=? and o.name=? and (o.cn=? or o.en=?)"
	 * @param queryParme 条件中未确定的值 如:new Object[]{1,"tian","cn","en"};
	 * @param orderby  排序 如:new LinkedHashMap<String,String>().put("id","desc");
	 * @return List对象
	 */
	public <T> List<T> getList(Class<T> entity,int firstindex,
			int maxresult,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby);
	/**
	 * 按条件，排序
	 * @param <T>
	 * @param entity 实体bean
	 * @param whereql 条件语句 如:"o.id=? and o.name=? and (o.cn=? or o.en=?)"
	 * @param queryParme 条件中未确定的值 如:new Object[]{1,"tian","cn","en"};
	 * @param orderby  排序 如:new LinkedHashMap<String,String>().put("id","desc");
	 * @return List对象
	 */
	public <T> List<T> getList(Class<T> entity,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby);
	
	/**
	 * 按条件，排序
	 * @param <T>
	 * @param entity 实体bean  o1
	 * @param ziduan 要查询的字段       如"o1.id,o1.name,o2.id,o2.name"
	 * @param whereql 条件语句 如:"o.id=? and o.name=? and (o.cn=? or o.en=?)"
	 * @param queryParme 条件中未确定的值 如:new Object[]{1,"tian","cn","en"};
	 * @param orderby  排序 如:new LinkedHashMap<String,String>().put("id","desc");
	 * @return List对象
	 */
	public <T> List<T> getListSpecial(Class<T> entity,String ziduan,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby);
	
	/**
	 * 按条件
	 * @param <T>
	 * @param entity 实体bean
	 * @param whereql 条件语句 如:"o.id=? and o.name=? and (o.cn=? or o.en=?)"
	 * @param queryParme 条件中未确定的值 如:new Object[]{1,"tian","cn","en"};
	 * @return List对象
	 */
	public <T> List<T> getList(Class<T> entity,String whereql,Object[] queryParme);
	/**
	 * 查询所有
	 * @param <T>
	 * @param entity 实体bean
	 * @return List对象
	 */
	public <T> List<T> getList(Class<T> entity);
	/**
	 * 按条件查询获取一个对象
	 * @param <T>
	 * @param entity 实体bean
	 * @param whereql 条件语句 如:"o.id=? and o.name=? and (o.cn=? or o.en=?)"
	 * @param queryParme 条件中未确定的值 如:new Object[]{1,"tian","cn","en"};
	 * @return T查询对象
	 */
	public <T> T getT(Class<T> entity,String whereql,Object[] queryParme);
	/**
	 * 获取当前对象的上一个记录
	 * @param <T>
	 * @param entity 实体bean
	 * @param entityId 实体bean的主键
	 * @return
	 */
	public <T> T getPrevious(Class<T> entity, Object entityId);
	/**
	 * 获取当前对象的下一个记录
	 * @param <T>
	 * @param entity 实体bean
	 * @param entityId 实体bean的主键
	 * @return
	 */
	public <T> T getNext(Class<T> entity, Object entityId);
	/**
	 * 多对多查询
	 * @param <T>
	 * @param entity 实体bean
	 * @param colname 关联对象的字段名
	 * @param firstindex 分页起始位置,-1表示不使用分页
	 * @param maxresult 每页记录,-1表示不使分页
	 * @param whereql 条件语句 如:"o.id=? and o.name=? and (o.cn=? or o.en=?)"
	 * @param queryParme 条件中未确定的值 如:new Object[]{1,"tian","cn","en"};
	 * @param orderby 排序 如:new LinkedHashMap<String,String>().put("id","desc");
	 * @return 分页对象
	 */
	public <T> QueryResult<T> getManyToManySpecial(Class<T> entity,String ziduan,String colname,String colname2,  int firstindex,
			int maxresult,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby);
	
	/**
	 * 多对多查询
	 * @param <T>
	 * @param entity 实体bean
	 * @param colname 关联对象的字段名
	 * @param firstindex 分页起始位置,-1表示不使用分页
	 * @param maxresult 每页记录,-1表示不使分页
	 * @param whereql 条件语句 如:"o.id=? and o.name=? and (o.cn=? or o.en=?)"
	 * @param queryParme 条件中未确定的值 如:new Object[]{1,"tian","cn","en"};
	 * @param orderby 排序 如:new LinkedHashMap<String,String>().put("id","desc");
	 * @return 分页对象
	 */
	public <T> QueryResult<T> getManyToMany(Class<T> entity,String colname,  int firstindex,
			int maxresult,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby);
	
	/**
	 * 多对多查询
	 * @param <T>
	 * @param entity 实体bean
	 * @param colname 关联对象的字段名
	 * @param firstindex 分页起始位置,-1表示不使用分页
	 * @param maxresult 每页记录,-1表示不使分页
	 * @param whereql 条件语句 如:"o.id=? and o.name=? and (o.cn=? or o.en=?)"
	 * @param queryParme 条件中未确定的值 如:new Object[]{1,"tian","cn","en"};
	 * @param orderby 排序 如:new LinkedHashMap<String,String>().put("id","desc");
	 * @return List对象
	 */
	public <T> List<T> getManyToManyList(Class<T> entity,String colname,  int firstindex,
			int maxresult,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby);
	/**
	 * 更新，更新全部getAndset字段@Transient除外
	 * @param newObject 更新对象（新对象）
	 * @param oldObject 被更新的对象（旧对象）
	 */
	public void update(Object newObject,Object oldObject);
	/**
	 * 更新updatecol字段(不是更新全部字段)
	 * @param entity 更新的实体对象
	 * @param updatecol 需要更新的字段
	 * @param wherecol 条件字段
	 * @param values 条件值
	 */
	public <T> void updateToHQL(Class<T> entity,String updatecol,String wherecol,Object[] values);
	/**
	 * 保存一个集合
	 * @param ts 保存集合实体对象
	 */
	public <T> void saves(List<T> ts);
	
	/**
	 * 获取实体某个字段相乘的总和
	 * @param <T>
	 * @param entity
	 * @param Field 字段名称  
	 * @param whereql
	 * @param queryParme
	 * @return
	 */
	public <T> long getCountByField(Class<T> entity,String Field,String whereql,Object[] queryParme);
	
	/**
	 * 
	 * 获取实体某个字段的总和 
	 * @param entity
	 * @param Field entity
	 * @param whereql
	 * @param queryParme
	 * @return 
	 * @see [Class、Class#Method、Class#Field]
	 */
	public <T> double getSumByField(Class<T> entity,String Field,String whereql,Object[] queryParme);
	
	/**
	 * 获取实体记录数
	 * @param <T>
	 * @param entity 实体
	 * @return 实体记录数
	 */
	public <T> long getCount(Class<T> entity);
	/**
	 * 根据条件查询数量
	 * @param <T>
	 * @param entity 实体
	 * @param whereql 查询条件
	 * @param queryParme 条件值
	 * @return
	 */
	public <T> long getCount(Class<T> entity,String whereql,Object[] queryParme);
	/**根据条件查询数量（统计ID）*/
	public <T> long getCountId(Class<T> entity,String whereql,Object[] queryParme);

	
	/**
	 * 获取某对象具体某字段属性
	 * max(m.orderNumber) :selectMsg  你想查得具体语句
	 * select max(m.orderNumber) from Message m where m.messageFlag=1
	 * */
	public <T,U> List<U> getPropertyForEntity(Class<T> entity,Class<U> returnObjType,String selectMsg,String whereql,Object[] queryParme);
	/**
	 * 获取某对象构造函数对象出来
	 * @param <T>
	 * @param entity
	 * @param selectMsg   pid,score,title,totalAccept,totalSubmission,unSee
	 * @param whereql
	 * @param queryParme
	 * @return
	 */
	public <T> List<T> getPropertyForEntity(Class<T> entity,String selectMsg,String whereql,Object[] queryParme);
	
	/**
	 * 多对多查询
	 * @param <T>
	 * @param entity 实体bean
	 * @param colname 关联对象的字段名
	 * @param distinct 去掉重复对象
	 * @param firstindex 分页起始位置,-1表示不使用分页
	 * @param maxresult 每页记录,-1表示不使分页
	 * @param whereql 条件语句 如:"o.id=? and o.name=? and (o.cn=? or o.en=?)"
	 * @param queryParme 条件中未确定的值 如:new Object[]{1,"tian","cn","en"};
	 * @param orderby 排序 如:new LinkedHashMap<String,String>().put("id","desc");
	 * @return List对象
	 * */
	public <T> QueryResult<T> getManyToMany(Class<T> entity,String colname,String distinct, int firstindex,
			int maxresult,String whereql,Object[] queryParme,LinkedHashMap<String, String> orderby) ;
	/**
	 * 执行删除hql
	 */
	public int deleteHQL(String hql);

	/**
	 * 使用函数统计查询
	 * @param cls
	 * @param method SQL 函数名
	 * @param whereql
	 * @param queryParme
	 * @return
	 */
	public Long sum(Class cls,String method,String whereql,Object[] queryParme);
	/**
	 * SQL 查询
	 */
	public List<Object[]> createSQLQuery(String sql);
	/**
	 * HQL 查询
	 */
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

	/**执行更新sql*/
	public int executeSQLUpdate(String sql);
	
	public <T> T queryHQLT(Class<T> entity, String hql,Object[] queryParme);
	/**
	 * 自定义sql查询
	 * 
	 */
	public <T> List<T> querySqlList(String hql);
	
	public List<Object[]> createSQLQuery(String sql,Object[] queryParme);
}