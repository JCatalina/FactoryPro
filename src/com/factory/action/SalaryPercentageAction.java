package com.factory.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.factory.common.BaseManagerAction;
import com.factory.common.PageList;
import com.factory.model.SalaryPercentage;
import com.factory.utils.SimpleToof;


@ParentPackage(value="admin")
@Namespace("/admin")
@Action(value = "/salaryPercentage",results={
		 @Result(name = "list", location = "/WEB-INF/page/admin/salaryPercentage/salaryPercentage_list.jsp"),
})
public class SalaryPercentageAction extends BaseManagerAction{

	
	private static final long serialVersionUID = 1L;
	private PageList<SalaryPercentage> pl;
	private final Integer PAGESIZE = 20;
	private String[] machineNos;
	private Double[] percentages;
	
	public String list(){
		where.append(" 1= 1");
		 
		pl=new PageList<SalaryPercentage>(this.PAGESIZE, getFirstIndex());
		pl.setQueryResult(dm.getScrollData(SalaryPercentage.class, pl.getFirstindex(), pl.getMaxresult(),where.toString(),values.toArray(), orderby));
		return "list";
	}
	
	
	public String edit(){
		
		for(int i = 0 ;i < ids.length ;i++){
			dm.updateToHQL(SalaryPercentage.class,
					" o.machineNos =? , o.percentage = ? ", 
					" o.id = ? ", 
					new Object[]{machineNos[i],percentages[i],ids[i]});
		}
		return list();
	}


	/**********************************setting and getting******************************************************/
	public PageList<SalaryPercentage> getPl() {
		return pl;
	}


	public void setPl(PageList<SalaryPercentage> pl) {
		this.pl = pl;
	}


	public String[] getMachineNos() {
		return machineNos;
	}


	public void setMachineNos(String[] machineNos) {
		this.machineNos = machineNos;
	}


	public Double[] getPercentages() {
		return percentages;
	}


	public void setPercentages(Double[] percentages) {
		this.percentages = percentages;
	}
	
	
	
	
}
