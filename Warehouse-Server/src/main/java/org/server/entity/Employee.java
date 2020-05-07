package org.server.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class Employee {

	private int id;
	private String name;
	private String gender;
	private String phone;
	private Date birthday;
	private Date hireDate;
	private BigDecimal salary;
}
