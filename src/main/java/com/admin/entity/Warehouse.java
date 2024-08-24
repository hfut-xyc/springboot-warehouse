package com.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Warehouse implements Serializable {
	private Integer id;
	private String name;
	private Integer count;

	private Integer createUserId;
	private String createUserName;
	private LocalDateTime createTime;

	private Integer updateUserId;
	private String updateUserName;
	private LocalDateTime updateTime;

	private List<Product> productList;

}
