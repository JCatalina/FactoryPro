package com.factory.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="machinestaff")
public class MachineStaff implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
	private Integer no;//机台号
	private Staff staffA;
	private Staff staffB;
	
	
	public MachineStaff() {
	}
	
	
	public MachineStaff(Integer no, Staff staffA, Staff staffB) {
		this.no = no;
		this.staffA = staffA;
		this.staffB = staffB;
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
	
	
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	
	@ManyToOne(optional=true,fetch=FetchType.LAZY)
	@JoinColumn(name="staffA",nullable=true)
	public Staff getStaffA() {
		return staffA;
	}
	public void setStaffA(Staff staffA) {
		this.staffA = staffA;
	}
	
	
	@ManyToOne(optional=true,fetch=FetchType.LAZY)
	@JoinColumn(name="staffB",nullable=true)
	public Staff getStaffB() {
		return staffB;
	}
	public void setStaffB(Staff staffB) {
		this.staffB = staffB;
	}
	
	
	
	
	
}
