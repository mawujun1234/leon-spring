package com.mawujun.controller.spring.mvc.jackson;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Jackson {
	
	private Integer id;
	private String name;
	
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date createDate;
	
	private Date otherDate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getOtherDate() {
		return otherDate;
	}
	public void setOtherDate(Date otherDate) {
		this.otherDate = otherDate;
	}

}
