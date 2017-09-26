package com.factory.service.impl;

import org.springframework.stereotype.Service;

import com.factory.dao.impl.DefaultManagerBean;
import com.factory.model.MachineStaff;
import com.factory.model.Staff;
import com.factory.service.IMachineStaffService;
import com.factory.utils.SimpleToof;


@Service(value="machineStaffService")
public class MachineStaffServiceImpl extends DefaultManagerBean implements
		IMachineStaffService {


	@Override
	public void batchAddMachineStaffRecord(String[] noStr, String[] staffAIDs,
			String[] staffBIDs) {
		try{
			Integer start =null;
			Integer end =null;
			
			for (int i = 0; i < noStr.length; i++) {
				String str=noStr[i];
				
				//批量设置 机台员工关系   1-10号机器  AB班人员相同
				if(str.length() > 1 && str.indexOf("-") != -1 ){
					String[] split = str.split("-");
					start= Integer.valueOf(split[0]);
					end= Integer.valueOf(split[1]);
					if(start < end){
						for(int no=start;no<=end;no++){
							MachineStaff machineStaff = new MachineStaff(no,new Staff(staffAIDs[i],null),new Staff(staffBIDs[i],null));
							this.defaultdDao.save(machineStaff);
						}
					}

				}else if(SimpleToof.checkNotNull(str)){
					//单个设置 机台员工关系   1号机器  AB班人员
					MachineStaff machineStaff = new MachineStaff(Integer.valueOf(str),new Staff(staffAIDs[i],null),new Staff(staffBIDs[i],null));
					this.defaultdDao.save(machineStaff);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}


	@Override
	public void batchEditMachineStaffRecord(String[] ids, Integer[] nos,
			String[] staffAIDs, String[] staffBIDs) {
		try{
			
			for (int i = 0; i < ids.length; i++) {
				this.defaultdDao.updateToHQL(MachineStaff.class, 
						" o.no = ? , o.staffA.id = ? , o.staffB.id = ?  ", " o.id = ? ",
						new Object[]{nos[i],staffAIDs[i],staffBIDs[i],ids[i]});
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
	}

}
