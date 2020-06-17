package org.server.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Employee implements Serializable {

	private int id;
	private String name;
	private String gender;
	private String phone;
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "UTC+8")
	private Date birthday;
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "UTC+8")
	private Date hireDate;
	private BigDecimal salary;
	private List<Warehouse> warehouses;

	public Employee() {}

	public Employee(int id, String name, String gender, String phone, Date birthday, Date hireDate, BigDecimal salary) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.phone = phone;
		this.birthday = birthday;
		this.hireDate = hireDate;
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Employee{" +
				"id=" + id +
				", name='" + name + '\'' +
				", gender='" + gender + '\'' +
				", phone='" + phone + '\'' +
				", birthday=" + birthday +
				", hireDate=" + hireDate +
				", salary=" + salary +
				", warehouses=" + warehouses +
				'}';
	}
}
