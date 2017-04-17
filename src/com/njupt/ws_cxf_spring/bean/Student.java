package com.njupt.ws_cxf_spring.bean;

public class Student {
	int ID;
	String name;
	
	public Student(int iD, String name) {
		super();
		ID = iD;
		this.name = name;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
