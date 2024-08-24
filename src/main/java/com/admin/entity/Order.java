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
	private Integer warehouseId;
	private Integer productId;
	private Integer count;

	private Integer createUserId;
	private String createUserName;
	private LocalDateTime createTime;

	private Integer updateUserId;
	private String updateUserName;
	private LocalDateTime updateTime;
}
