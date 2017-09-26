package com.factory.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.factory.dao.impl.DefaultManagerBean;
import com.factory.model.MachineDailyRecord;
import com.factory.model.MachineStaff;
import com.factory.model.Staff;
import com.factory.service.IMachineDailyRecordService;
import com.factory.utils.MyDateUtils;
import com.factory.utils.SimpleToof;

@Service(value="machineDailyRecordService")
public class MachineDailyRecordServiceImpl extends DefaultManagerBean implements
		IMachineDailyRecordService {


	@Override
	public void batchAddMachineDailyRecord(Date createTime,Integer[] nos, Double[] AMs,Double[] BMs,Double[] CMs,Double[] DMs,Double[] sumMs, 
			String[] remarks,String[] staffCIDs,String[] staffDIDs){
		Staff staffC = null;
		Staff staffD = null;
			
		try{
			for (int i = 0; i < nos.length; i++) {
				//要有机台号才保存数据
				if(nos[i]==null || nos[i] <=0)
					continue;
				//根据机台号查出AB班人员
				MachineStaff machineStaff = this.defaultdDao.getT(MachineStaff.class, " o.no = ? ", new Object[]{nos[i]});
				if(machineStaff == null){
					System.out.println("机台号:"+nos[i]+"未设置A B 班人员信息");
					continue;
				}
				
				staffC = null;
				staffD = null;
				if(staffCIDs[i] != null){
					staffC = new Staff(staffCIDs[i],null);
				}
				if(staffDIDs[i] != null){
					staffD = new Staff(staffDIDs[i],null);
				}
				
				MachineDailyRecord record= 
						new MachineDailyRecord(createTime, nos[i], AMs[i], BMs[i], CMs[i], DMs[i], sumMs[i], remarks[i],
								machineStaff.getStaffA(),machineStaff.getStaffB(),staffC,staffD,i);
				this.defaultdDao.save(record);
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		
	}


	@Override
	public void batchEditMachineDailyRecord(String[] ids, Integer[] nos,
			Double[] AMs, Double[] BMs, Double[] CMs, Double[] DMs,
			Double[] sumMs, String[] remarks, String[] staffCIDs,
			String[] staffDIDs) {
		try{
			MachineDailyRecord dailyRecord = this.defaultdDao.find(MachineDailyRecord.class, ids[0]);
			Date createTime=dailyRecord.getCreateTime();
			Staff staffC=null;
			Staff staffD=null;
			//根据id区分修改还是新增
			for (int i = 0; i < ids.length; i++) {
				
				//根据机台号查出AB班人员
				MachineStaff machineStaff = this.defaultdDao.getT(MachineStaff.class, " o.no = ? ", new Object[]{nos[i]});
				if(machineStaff == null){
					System.out.println("机台号:"+nos[i]+"未设置A B 班人员信息");
					continue;
				}
				
				if(!ids[i].equalsIgnoreCase("-1")){//id为编辑
					this.defaultdDao.updateToHQL(MachineDailyRecord.class, 
							"  o.no = ? ,o.AM = ? ,o.BM = ? ,o.CM =? , o.DM = ? ,o.sumM = ? ,o.remark = ?, o.staffA = ? , o.staffB =? ,o.staffC = ? ,o.staffD = ? ", 
							" o.id = ? ",
							new Object[]{nos[i],AMs[i],BMs[i],CMs[i],DMs[i],sumMs[i],remarks[i],machineStaff.getStaffA(),machineStaff.getStaffB(),new Staff(staffCIDs[i],null),new Staff(staffDIDs[i],null),ids[i]});
			
				}else if(ids[i].equalsIgnoreCase("-1") && nos[i] !=null && nos[i] >0){//id为-1,且机台好nos[i]不为空的为新增
					
					staffC=new Staff(staffCIDs[i],null);
					staffD=new Staff(staffDIDs[i],null);
					
					MachineDailyRecord record= 
							new MachineDailyRecord(createTime, nos[i], AMs[i], BMs[i], CMs[i], DMs[i], sumMs[i], remarks[i],
									machineStaff.getStaffA(),machineStaff.getStaffB(),staffC,staffD,i);
					this.defaultdDao.save(record);
					
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}


	@Override
	public Map<Integer,HashMap<String, Double>> calculateStaffMonthlyReport(String staffId, Date month) {
		try{
			List<MachineDailyRecord> list = this.defaultdDao.getList(MachineDailyRecord.class, 
					"DATE_FORMAT(o.createTime,'%Y-%m') = DATE_FORMAT(?,'%Y-%m') and  (o.staffA.id = ? or o.staffB.id = ? or o.staffC.id = ? or o.staffD.id = ? ) ",
					new Object[]{month,staffId,staffId,staffId,staffId});
			Map<Integer,HashMap<String, Double>> rowMap=new TreeMap<Integer, HashMap<String,Double>>();
			
			for (MachineDailyRecord record : list) {
				//获取品种
				String varietyName= record.getVarietyName();
				//获取机台号
				Integer no = record.getNo();
				Double sum =0D;
				//获取工作量（区分 A B 班所织的AB米)
				if(record.getStaffA().getId().equalsIgnoreCase(staffId)){
					sum+=record.getAM();
				}
				if(record.getStaffB().getId().equalsIgnoreCase(staffId)){
					sum+=record.getBM();
				}
				
				//C D 班不一定有人
				if(record.getStaffC() !=null && record.getStaffC().getId().equalsIgnoreCase(staffId)){
					sum+=record.getCM();
				}
				if(record.getStaffD() !=null && record.getStaffD().getId().equalsIgnoreCase(staffId)){
					sum+=record.getDM();
				}
				
				HashMap<String, Double> detailMap = rowMap.get(no);
				if(detailMap==null){
					detailMap=new HashMap<String, Double>();
					detailMap.put(varietyName, sum);
					rowMap.put(no, detailMap);
				}else{
					Double meters = detailMap.get(varietyName);
					if(meters==null){
						detailMap.put(varietyName, sum);
					}else{
						detailMap.put(varietyName, sum+meters);
					}
				}
			}
			return rowMap;
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException("结算出错。");
		}
		
	}



	@Override
	public JSONObject batchEditMachineDailyRecordOnMonth(String yearMonth,String[] dateStrs,String[] ids,String[] varietyNames,Integer[] nos, Double[] AMs,Double[] BMs,Double[] CMs,Double[] DMs,
			Double[] sumMs,String[] remarks,String[] staffCIDs,String[] staffDIDs) {
		JSONObject result = new JSONObject();
		result.put("success", 0);
		result.put("hasNew", false);
		try{
			JSONArray dataArr=new JSONArray();
			
			//根据机台号查出AB班人员
			MachineStaff machineStaff = this.defaultdDao.getT(MachineStaff.class, " o.no = ? ", new Object[]{nos[0]});
			if(machineStaff == null){
				result.put("success", 1);
				result.put("errMsg", "机台号:"+nos[0]+"未设置A B 班人员信息");
				return result;
			}
			
			for (int i = 0; i < ids.length; i++) {
				
				//1、id 为-1,且  日期号 不为空 为新增
				if(ids[i].equals("-1") ){
					if(SimpleToof.checkNotNull(dateStrs[i])){
						Date date=MyDateUtils.formatDateToYYYYMMDD(yearMonth+"-"+dateStrs[i]);
						MachineDailyRecord record= 
								new MachineDailyRecord(date, nos[i], AMs[i], BMs[i], CMs[i], DMs[i], sumMs[i], remarks[i],
										machineStaff.getStaffA(),machineStaff.getStaffB(),new Staff(staffCIDs[i],null),new Staff(staffCIDs[i],null),i);
						this.defaultdDao.save(record);
						//新增返回ID
						JSONObject obj = new JSONObject();
						obj.put("index",i);
						obj.put("id",record.getId());
						dataArr.add(obj);
						result.put("hasNew", true);
					}
					
				}else{
				//2、 id 不为-1为修改
						// 2.1 AM,BM,CM,DM,都为0,删除
						if( (AMs[i]+ BMs[i] +CMs[i] +DMs[i]) == 0D){
							this.defaultdDao.delete(MachineDailyRecord.class, " o.id = ? ", new Object[]{ids[i]});
						}else{
							//2.2  修改
							this.defaultdDao
							.updateToHQL(
									MachineDailyRecord.class,
									"  o.no = ?,o.varietyName = ? ,o.AM = ? ,o.BM = ? ,o.CM =? , o.DM = ? , o.sumM = ? ,o.remark = ?, o.staffA = ? , o.staffB =? , o.staffC = ? ,o.staffD = ? ",
									" o.id = ? ", 
									new Object[]{ nos[i], varietyNames[i], AMs[i], BMs[i], CMs[i], DMs[i], sumMs[i], remarks[i],
										machineStaff.getStaffA(),machineStaff.getStaffB(),new Staff(staffCIDs[i], null),new Staff(staffDIDs[i], null), ids[i] });
						}
				}
				
			}
			result.put("dataArr", dataArr);
			
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", 1);
			result.put("errMsg","更新出错了");
		}
		
		return result;
		
	}


	@Override
	public void saveImportData(List<MachineDailyRecord> dataList,Date createTime) {
		//删除具体日期的数据，重新保存导入的数据
		defaultdDao.delete(MachineDailyRecord.class, " o.createTime = ? ", new Object[]{createTime});
		
		for (MachineDailyRecord machineDailyRecord : dataList) {
			defaultdDao.save(machineDailyRecord);
		}
	}






}
