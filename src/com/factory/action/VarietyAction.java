package com.factory.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.factory.common.BaseManagerAction;
import com.factory.common.PageList;
import com.factory.model.Variety;
import com.factory.utils.SimpleToof;


@ParentPackage(value="admin")
@Namespace("/admin")
@Action(value = "/variety",results={
		 @Result(name = "list", location = "/WEB-INF/page/admin/variety/variety_list.jsp"),
		 @Result(name = "addUI", location = "/WEB-INF/page/admin/variety/variety_addUI.jsp"),
		 @Result(name = "editUI", location = "/WEB-INF/page/admin/variety/variety_editUI.jsp"),
		 @Result(name = "afterEditUI", params={"id","${id}"},type="redirect",location = "/variety/variety!editUI.action"),
})
public class VarietyAction extends BaseManagerAction{

	
	private static final long serialVersionUID = 1L;
	private Variety variety;
	private PageList<Variety> pl;
	private final Integer PAGESIZE = 20;
	private Variety queryObj;//查询传值对象
	
	public String list(){
		where.append(" 1= 1");
		
		if(queryObj!=null && SimpleToof.checkNotNull(queryObj.getName())){
			where.append("and  o.name like ? ");
			values.add("%"+queryObj.getName()+"%");
		}
		 
		pl=new PageList<Variety>(this.PAGESIZE, getFirstIndex());
		pl.setQueryResult(dm.getScrollData(Variety.class, pl.getFirstindex(), pl.getMaxresult(),where.toString(),values.toArray(), orderby));
		return "list";
	}
	
	
	public String addUI(){
		return "addUI";
	}
	
	
	public String add(){
		if(variety!=null && SimpleToof.checkNotNull(variety.getName())){
			variety.setName(variety.getName().trim());
			dm.save(variety);
		}
		return list();
	}
	
	public String editUI(){
		return "editUI";
	}
	
	public String edit(){
		return "afterEditUI";
	}
	
	
	public String delete(){
		if(SimpleToof.checkNotNull(id)){
			//dm.delete(variety.class, id);
		}
		return list();
	}


	/**********************************setting and getting******************************************************/
	public Variety getVariety() {
		return variety;
	}

	public void setVariety(Variety variety) {
		this.variety = variety;
	}

	public PageList<Variety> getPl() {
		return pl;
	}

	public void setPl(PageList<Variety> pl) {
		this.pl = pl;
	}


	public Variety getQueryObj() {
		return queryObj;
	}


	public void setQueryObj(Variety queryObj) {
		this.queryObj = queryObj;
	}
	
	
}
