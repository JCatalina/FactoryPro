package com.factory.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 记录机台计算工资时的倍率以及机台的大小
 * @author wenjin.luo
 *
 */
@Entity
@Table(name="salaryPercentage")
public class SalaryPercentage implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
	private String machineNos;//机台号数组字符串(,分割)
	private double percentage;//对应倍率
	
	
	
	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Lob
	@Column(columnDefinition="TEXT")
	public String getMachineNos() {
		return machineNos;
	}
	public void setMachineNos(String machineNos) {
		this.machineNos = machineNos;
	}
	
	@Column(columnDefinition="DOUBLE DEFAULT 0")
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	
	
	
	
	
	
}
