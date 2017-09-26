package com.factory.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.factory.utils.SimpleToof;

/**
 * 每日数据 ---计算使用
 * @author wenjin.luo
 *
 */
@Entity
@Table(name="machinedailyrecord")
public class MachineDailyRecord implements Serializable{

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
	
	
	
	public MachineDailyRecord() {
		super();
	}
	
	public MachineDailyRecord(Date createTime) {
		this.createTime = createTime;
	}
	
	
	public MachineDailyRecord(Date createTime, Integer no, Double aM,
			Double bM,Double cM,Double dM, Double sumM, String remark,
			Staff staffA, Staff staffB, Staff staffC, Staff staffD,Integer inputNo) {
		this.createTime = createTime;
		this.no = no;
		
		this.AM = aM !=null && aM >=0D ? aM : 0D;
		this.BM = bM !=null && bM >=0D ? bM : 0D;
		this.CM = cM !=null && cM >=0D ? cM : 0D;
		this.DM = dM !=null && dM >=0D ? dM : 0D;
		this.sumM = sumM !=null && sumM >=0D ? sumM : 0D;
		
		this.remark = remark;
		if(staffA!=null)
			this.staffA = staffA;
		if(staffB!=null)
			this.staffB = staffB;
		if(staffC!=null)
			this.staffC = staffC;
		if(staffD!=null)
			this.staffD = staffD;
		this.inputNo=inputNo;
	}

	//导入时使用的构造函数
	public MachineDailyRecord(Date createTime, Integer no, String aM,
			String bM,String cM,String dM, String remark,
			Staff staffA, Staff staffB, Staff staffC, Staff staffD,Integer inputNo) {
		this.createTime = createTime;
		this.no = no;
		this.AM=0D;
		this.BM=0D;
		this.CM=0D;
		this.DM=0D;
		if(SimpleToof.checkNotNull(aM)){
			this.AM = Double.valueOf(aM);
		}
		if(SimpleToof.checkNotNull(bM)){
			this.BM = Double.valueOf(bM);
		}
		if(SimpleToof.checkNotNull(cM)){
			this.CM = Double.valueOf(cM);
		}
		if(SimpleToof.checkNotNull(dM)){
			this.DM = Double.valueOf(dM);
		}
		
		this.sumM = this.AM +this.BM+this.CM + this.DM;

		this.remark = remark;
		if(staffA!=null)
			this.staffA = staffA;
		if(staffB!=null)
			this.staffB = staffB;
		if(staffC!=null)
			this.staffC = staffC;
		if(staffD!=null)
			this.staffD = staffD;
		this.inputNo=inputNo;
		
	}


	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
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
	
	@Column(columnDefinition="DOUBLE default 0")
	public Double getAM() {
		return AM;
	}
	public void setAM(Double aM) {
		AM = aM;
	}
	
	@Column(columnDefinition="DOUBLE default 0")
	public Double getBM() {
		return BM;
	}
	public void setBM(Double bM) {
		BM = bM;
	}
	
	@Column(columnDefinition="DOUBLE default 0")
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
	
	
	@ManyToOne(optional=true)
	@JoinColumn(name="staffA",nullable=true)
	public Staff getStaffA() {
		return staffA;
	}
	public void setStaffA(Staff staffA) {
		this.staffA = staffA;
	}
	
	@ManyToOne(optional=true)
	@JoinColumn(name="staffB",nullable=true)
	public Staff getStaffB() {
		return staffB;
	}
	public void setStaffB(Staff staffB) {
		this.staffB = staffB;
	}
	
	@ManyToOne(optional=true,fetch=FetchType.LAZY)
	@JoinColumn(name="staffC",nullable=true)
	public Staff getStaffC() {
		return staffC;
	}

	public void setStaffC(Staff staffC) {
		this.staffC = staffC;
	}

	@ManyToOne(optional=true,fetch=FetchType.LAZY)
	@JoinColumn(name="staffD",nullable=true)
	public Staff getStaffD() {
		return staffD;
	}
	public void setStaffD(Staff staffD) {
		this.staffD = staffD;
	}

	@Column(columnDefinition="INT default 0")
	public Integer getInputNo() {
		return inputNo;
	}

	public void setInputNo(Integer inputNo) {
		this.inputNo = inputNo;
	}

	@Column(columnDefinition="DOUBLE default 0")
	public Double getCM() {
		return CM;
	}

	public void setCM(Double cM) {
		CM = cM;
	}

	@Column(columnDefinition="DOUBLE default 0")
	public Double getDM() {
		return DM;
	}

	public void setDM(Double dM) {
		DM = dM;
	} 
	
	
	
	
}
