package org.server.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Order {

	private int id;
	private int eid;
	private int wid;
	private int pid;
	private int amount;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "UTC+8")
	private Date createTime;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "UTC+8")
	private Date updateTime;
}
