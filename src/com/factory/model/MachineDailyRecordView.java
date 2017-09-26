package com.factory.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 每日数据 ---历史显示用的，计算使用machinedailyrecord
 * @author		wenjin.luo 
 * @version		[1.0, 2017年9月18日] 
 */
public class MachineDailyRecordView implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private Date createTime;
	private Integer no;//机台号
	
	private Double AM = 0D;//A 班米数
	private Double BM = 0D;//B 班米数
	private Double CM = 0D;//C 班米数
	private Double DM = 0D;//D 班米数
	private Double sumM =0D;

	private String varietyName;//品种名字
	private String remark;//备注
	
	private Staff staffA; //A 班员工
	private Staff staffB; //B 班员工
	private Staff staffC; //C 班员工
	private Staff staffD; //D 班员工
	
	private Integer inputNo ; //输入的顺序编号：排序使用
	
	private MachineDailyRecord record;//关联的对账时的数据--导入时跟本实体数据一样，但对账会修改record，view数据不变

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public Double getAM() {
		return AM;
	}

	public void setAM(Double aM) {
		AM = aM;
	}

	public Double getBM() {
		return BM;
	}

	public void setBM(Double bM) {
		BM = bM;
	}

	public Double getCM() {
		return CM;
	}

	public void setCM(Double cM) {
		CM = cM;
	}

	public Double getDM() {
		return DM;
	}

	public void setDM(Double dM) {
		DM = dM;
	}

	public Double getSumM() {
		return sumM;
	}

	public void setSumM(Double sumM) {
		this.sumM = sumM;
	}

	public String getVarietyName() {
		return varietyName;
	}

	public void setVarietyName(String varietyName) {
		this.varietyName = varietyName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Staff getStaffA() {
		return staffA;
	}

	public void setStaffA(Staff staffA) {
		this.staffA = staffA;
	}

	public Staff getStaffB() {
		return staffB;
	}

	public void setStaffB(Staff staffB) {
		this.staffB = staffB;
	}

	public Staff getStaffC() {
		return staffC;
	}

	public void setStaffC(Staff staffC) {
		this.staffC = staffC;
	}

	public Staff getStaffD() {
		return staffD;
	}

	public void setStaffD(Staff staffD) {
		this.staffD = staffD;
	}

	public Integer getInputNo() {
		return inputNo;
	}

	public void setInputNo(Integer inputNo) {
		this.inputNo = inputNo;
	}

	
	public MachineDailyRecord getRecord() {
		return record;
	}

	public void setRecord(MachineDailyRecord record) {
		this.record = record;
	}

	
	
	
}
