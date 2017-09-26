package com.factory.action;

import java.io.IOException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.json.JSONException;

import com.factory.common.BaseManagerAction;
import com.factory.common.Constants;
import com.factory.model.Admin;
import com.factory.utils.SimpleToof;
/**
 * 统一登录调用Action
 * @author wenjin.luo
 * @createTime 170423
 */
@ParentPackage(value="admin")
@Namespace("/")
@Action(value = "login",results={
		 @Result(name = "adminLoginUI", location = "/adminLoginUI.html"),
		 @Result(name = "admin_Index", location = "/WEB-INF/page/admin/admin_index.jsp"),
		 @Result(name = "reAdminLoginUI",type="redirectAction",params={"errMsg","${errMsg}"}, location = "login!adminLoginUI.action"),
})
public class LoginAction extends BaseManagerAction{
	private static final long serialVersionUID = -2117222050347314051L;

	private Admin admin;
	private String checkCode;
	private String errMsg;
	
	/*****************************************************************************************/
	
	/**后台登录页面  */
	public String adminLoginUI() throws JSONException, IOException{
		return "adminLoginUI";
	}
	
	
	/**后台登陆验证*/
	public String adminLogin(){
		/*if(!SimpleToof.checkNotNull(checkCode)){
			errMsg="请输入验证码";
			return "reAdminLoginUI";
		}
		String check = (String)getSession().getAttribute("check");
		if(check!=null&& !check.equalsIgnoreCase(checkCode)){
			errMsg="验证码有误";
			return "reAdminLoginUI";
		}*/
		if(!SimpleToof.checkNotNull(admin.getUsername()) || !SimpleToof.checkNotNull(admin.getPassword()) ){
			errMsg="请填入登录信息";
			return "reAdminLoginUI";
		}
		
		Admin vAdmin = dm.getT(Admin.class, " o.username = ? ", new Object[]{admin.getUsername()});
		if(vAdmin==null){
			errMsg="账号或者密码有误";
			return "reAdminLoginUI";
		}
		if(vAdmin!=null&&!vAdmin.getPassword().equals(admin.getPassword())){
			errMsg="账号或者密码有误";
			return "reAdminLoginUI";
		}
		if(SimpleToof.checkNotNull(vAdmin)){
			getSession().setAttribute(Constants.Common.SESSION_ADMIN, vAdmin);
			admin = getLoginAdmin();
			return "admin_Index";
		}else{
			errMsg="账号或者密码有误";
			return "reAdminLoginUI";
		}
	}

	
	/**退出登录*/
	public String loginOut(){
		getSession().invalidate();
		return "adminLoginUI";
	}


	/*****************************************************************************************/
	
	public Admin getAdmin() {
		return admin;
	}


	public void setAdmin(Admin admin) {
		this.admin = admin;
	}


	public String getCheckCode() {
		return checkCode;
	}


	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}


	public String getErrMsg() {
		return errMsg;
	}


	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	
	
	
	
}
