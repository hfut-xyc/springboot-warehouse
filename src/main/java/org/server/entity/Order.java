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

	private Integer id;
	private Integer eid;
	private Integer wid;
	private Integer pid;
	private Integer amount;
	private Integer status;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "UTC+8")
	private Date createTime;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "UTC+8")
	private Date updateTime;
}
