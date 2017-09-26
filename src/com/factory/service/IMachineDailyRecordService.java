package com.factory.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.factory.model.MachineDailyRecord;

public interface IMachineDailyRecordService {
	
	/**
	 * 
	 * <批量每日数据添加记录>  
	 * @param createTime
	 * @param nos 机台号(必填：决定AB班人员)
	 * @param AMs A班米数
	 * @param BMs B班米数
	 * @param CMs C班米数
	 * @param DMs D班米数
	 * @param sumMs 总米数
	 * @param remarks 备注
	 * @param staffCIDs C班人员
	 * @param staffDIDs D班人员
	 */
	void batchAddMachineDailyRecord(Date createTime,Integer[] nos, Double[] AMs,Double[] BMs,Double[] CMs,Double[] DMs,Double[] sumMs,
			String[] remarks,String[] staffCIDs,String[] staffDIDs);
	
	//批量更新每天的数据记录(没有品种)
	void batchEditMachineDailyRecord(String[] ids,Integer[] nos, Double[] AMs,Double[] BMs,Double[] CMs,Double[] DMs,Double[] sumMs, 
			String[] remarks,String[] staffCIDs,String[] staffDIDs);

	
	//月结汇总批量更新每天的数据记录(有品种名字)
	JSONObject batchEditMachineDailyRecordOnMonth(String createTime,String[] dateStrs,String[] ids,String[] varietyNames,Integer[] nos, Double[] AMs,Double[] BMs,Double[] CMs,Double[] DMs,
			Double[] sumMs,String[] remarks,String[] staffCIDs,String[] staffDIDs);
	
	
	//根据  员工ID 和 月份 汇总个人工作总量
	Map<Integer, HashMap<String, Double>> calculateStaffMonthlyReport(String staffId,Date month);
	
	void saveImportData(List<MachineDailyRecord> dataList,Date createTime);
	
}
