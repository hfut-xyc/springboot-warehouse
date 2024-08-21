package com.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

	private Integer id;
	private Integer eid;
	private Integer wid;
	private Integer pid;
	private Integer amount;
	private Integer status;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
}
