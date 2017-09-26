package com.factory.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.factory.common.BaseManagerAction;
import com.factory.common.PageList;
import com.factory.model.Staff;
import com.factory.utils.SimpleToof;


@ParentPackage(value="admin")
@Namespace("/admin")
@Action(value = "/staff",results={
		 @Result(name = "list", location = "/WEB-INF/page/admin/staff/staff_list.jsp"),
		 @Result(name = "addUI", location = "/WEB-INF/page/admin/staff/staff_addUI.jsp"),
		 @Result(name = "editUI", location = "/WEB-INF/page/admin/staff/staff_editUI.jsp"),
		 @Result(name = "afterEditUI", params={"id","${id}"},type="redirect",location = "/staff/staff!editUI.action"),
})
public class StaffAction extends BaseManagerAction{

	
	private static final long serialVersionUID = 1L;
	private Staff staff;
	private PageList<Staff> pl;
	private final Integer PAGESIZE = 20;
	private Staff queryObj;//查询传值对象
	
	public String list(){
		
		where.append(" 1= 1");
		
		if(queryObj!=null && SimpleToof.checkNotNull(queryObj.getName())){
			where.append(" and o.name like ? ");
			values.add("%"+queryObj.getName()+"%");
		}
		orderby.put("createTime", "asc");
		pl=new PageList<Staff>(this.PAGESIZE, getFirstIndex());
		pl.setQueryResult(dm.getScrollData(Staff.class, pl.getFirstindex(), pl.getMaxresult(),where.toString(),values.toArray(), orderby));
		return "list";
	}
	
	
	public String addUI(){
		return "addUI";
	}
	
	
	public String add(){
		if(staff!=null && SimpleToof.checkNotNull(staff.getName())){
			staff.setName(staff.getName().trim());
			dm.save(staff);
		}
		return list();
	}
	
	public String editUI(){
		staff=dm.find(Staff.class, id);
		return "editUI";
	}
	
	public String edit(){
		if(staff!=null && staff.getId()!=null){
			dm.updateToHQL(Staff.class, " o.name = ? ", " o.id =? ", new Object[]{staff.getName(),staff.getId()});
		}
		return list();
	}
	
	
	public String delete(){
		if(SimpleToof.checkNotNull(id)){
		}
		return list();
	}


	/**********************************setting and getting******************************************************/
	public Staff getStaff() {
		return staff;
	}


	public void setStaff(Staff staff) {
		this.staff = staff;
	}


	public PageList<Staff> getPl() {
		return pl;
	}


	public void setPl(PageList<Staff> pl) {
		this.pl = pl;
	}


	public Staff getQueryObj() {
		return queryObj;
	}


	public void setQueryObj(Staff queryObj) {
		this.queryObj = queryObj;
	}
	
	
	
	
}
