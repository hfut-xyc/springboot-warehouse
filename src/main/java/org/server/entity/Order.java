package org.server.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

	private int id;
	private int eid;
	private int wid;
	private int pid;
	private int amount;
	private String status;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "UTC+8")
	private Date createTime;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "UTC+8")
	private Date updateTime;
}
