package org.server.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Serializable {

	private int id;
	private String name;
	private String gender;
	private String phone;
	private BigDecimal salary;

	@JsonFormat(pattern="yyyy-MM-dd",timezone = "UTC+8")
	private Date createTime;
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "UTC+8")
	private Date updateTime;

	private List<Warehouse> warehouses;

}
