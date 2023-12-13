package org.server.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Warehouse implements Serializable {
	private int id;
	private String name;
	private String address;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;

}
