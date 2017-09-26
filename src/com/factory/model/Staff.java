package com.factory.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author wenjin.luo
 *
 */
@Entity
@Table(name="staff")
public class Staff implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private Date createTime;
	
	
	public Staff() {
	}
	
	public Staff(String id,String name) {
		this.id = id;
		this.name = name;
		this.createTime=new Date();
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
	
}
