package com.iona.framework;

import java.sql.Date;

public class Person {
	String name;
	int age;
	boolean isActive;
	Date createdDate;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Person [name=");
		builder.append(name);
		builder.append(", age=");
		builder.append(age);
		builder.append(", isActive=");
		builder.append(isActive);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append("]");
		return builder.toString();
	}	
	
	
}