package org.server.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Employee {

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
}
