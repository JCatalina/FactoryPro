package com.factory.common;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.factory.dao.DefaultManager;
import com.factory.model.Admin;
import com.factory.utils.SimpleToof;
import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseManagerAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5419616144306756092L;
	protected HttpServletRequest request = null;
	protected HttpServletResponse response = ServletActionContext.getResponse();
	protected HttpSession session = null;
	protected ServletContext application = null;
	protected String root = null;


	protected DefaultManager dm;

	protected int firstIndex; // 分页起始页面
	protected int maxResult; // 分页取多少条记录
	protected StringBuffer where = new StringBuffer();// 查询条件拼凑
	protected List<Object> values = new ArrayList<Object>();// 条件补充
	protected LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();// 分页排序

	protected String id;
	protected String[] ids = new String[] {};
	protected String SValue;
	protected String queryDate;

	protected String errMsg;
	
	protected Admin loginAdmin;

	public DefaultManager getDm() {
		return dm;
	}

	@Resource(name = "defaultManager")
	public void setDm(DefaultManager dm) {
		this.dm = dm;
	}

	/***************************************************************************************/
	

	
	public Admin getLoginAdmin() {
		loginAdmin = (Admin)getSession().getAttribute(Constants.Common.SESSION_ADMIN);
		return loginAdmin;
	}


	public HttpServletRequest getRequest() {
		request = ServletActionContext.getRequest();
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		response = ServletActionContext.getResponse();
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpSession getSession() {
		session = getRequest().getSession();
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public ServletContext getApplication() {
		application = getSession().getServletContext();
		return application;
	}

	public void setApplication(ServletContext application) {
		this.application = application;
	}

	public String getRoot() {
		root = ServletActionContext.getServletContext().getRealPath("/");
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	/***************************************************************************************/

	public String formatDouble(double s) {
		return SimpleToof.fmt.format(s);
	}

	public int getFirstIndex() {
		return firstIndex < 1 ? 1 : firstIndex;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public int getMaxResult() {
		return maxResult < 1 ? 1 : maxResult;
	}

	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String getSValue() {
		return SValue;
	}

	public void setSValue(String sValue) {
		SValue = sValue;
	}

	public String getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}


	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public void setLoginAdmin(Admin loginAdmin) {
		this.loginAdmin = loginAdmin;
	}
	
	

}
