package com.factory.service;

public interface IMachineStaffService {
	
	//批量添加机台AB班员工数据
	void batchAddMachineStaffRecord(String[] noStr,String[] staffAIDs,String[] staffBIDs);
	
	
	//批量编辑机台AB班员工数据
	void batchEditMachineStaffRecord(String[] ids,Integer[] nos,String[] staffAIDs,String[] staffBIDs);
	
}
