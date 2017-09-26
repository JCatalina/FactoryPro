package com.factory.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.factory.common.BaseManagerAction;
import com.factory.common.PageList;
import com.factory.model.MachineStaff;
import com.factory.model.Staff;
import com.factory.service.IMachineStaffService;
import com.factory.utils.SimpleToof;


@ParentPackage(value="admin")
@Namespace("/admin")
@Action(value = "/machineStaff",results={
		 @Result(name = "list", location = "/WEB-INF/page/admin/machineStaff/machineStaff_list.jsp"),
		 @Result(name = "addUI", location = "/WEB-INF/page/admin/machineStaff/machineStaff_addUI.jsp")
})
public class MachineStaffAction extends BaseManagerAction{

	
	private static final long serialVersionUID = 1L;
	private MachineStaff machineStaff;
	private PageList<MachineStaff> pl;
	private final Integer PAGESIZE = 20;
	private MachineStaff queryObj;//查询传值对象
	
	private String[] noStr;//批量设置机台号字符串  1-10
	private Integer[] nos;//机台号
	private String[] staffAIds;//A班员工id
	private String[] staffBIds;//A班员工id
	
	private List<Staff> staffList = new ArrayList<Staff>();
	
	@Resource(name="machineStaffService")
	private IMachineStaffService machineStaffService;
	
	public String list(){
		
		where.append(" 1= 1");
		if(queryObj!=null){
			if(SimpleToof.checkNotNull(queryObj.getNo())){
				where.append("and o.no = ? ");
				values.add(queryObj.getNo());
			}
		}
		
		orderby.put("no", "asc");
		pl=new PageList<MachineStaff>(this.PAGESIZE, getFirstIndex());
		pl.setQueryResult(dm.getScrollData(MachineStaff.class, pl.getFirstindex(), pl.getMaxresult(),where.toString(),values.toArray(), orderby));
		
		staffList= dm.getList(Staff.class);
		
		return "list";
	}
	
	
	public String addUI(){
		staffList= dm.getList(Staff.class);
		return "addUI";
	}
	
	
	public String add(){
		machineStaffService.batchAddMachineStaffRecord(noStr, staffAIds, staffBIds);
		return list();
	}
	
	
	public String edit(){
		machineStaffService.batchEditMachineStaffRecord(ids, nos, staffAIds, staffBIds);
		return list();
	}
	
	
	public String delete(){
		if(SimpleToof.checkNotNull(id)){
			
		}
		return list();
	}


	/**********************************setting and getting******************************************************/
	
	public MachineStaff getMachineStaff() {
		return machineStaff;
	}


	public void setMachineStaff(MachineStaff machineStaff) {
		this.machineStaff = machineStaff;
	}


	public PageList<MachineStaff> getPl() {
		return pl;
	}


	public void setPl(PageList<MachineStaff> pl) {
		this.pl = pl;
	}


	public MachineStaff getQueryObj() {
		return queryObj;
	}


	public void setQueryObj(MachineStaff queryObj) {
		this.queryObj = queryObj;
	}


	public Integer[] getNos() {
		return nos;
	}


	public void setNos(Integer[] nos) {
		this.nos = nos;
	}


	public String[] getStaffAIds() {
		return staffAIds;
	}


	public void setStaffAIds(String[] staffAIds) {
		this.staffAIds = staffAIds;
	}


	public String[] getStaffBIds() {
		return staffBIds;
	}


	public void setStaffBIds(String[] staffBIds) {
		this.staffBIds = staffBIds;
	}


	public IMachineStaffService getMachineStaffService() {
		return machineStaffService;
	}


	public void setMachineStaffService(IMachineStaffService machineStaffService) {
		this.machineStaffService = machineStaffService;
	}

	public List<Staff> getStaffList() {
		return staffList;
	}


	public void setStaffList(List<Staff> staffList) {
		this.staffList = staffList;
	}


	public String[] getNoStr() {
		return noStr;
	}


	public void setNoStr(String[] noStr) {
		this.noStr = noStr;
	}


	
	
	
	
}
