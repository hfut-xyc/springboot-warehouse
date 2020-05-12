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
	private String status;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "UTC+8")
	private Date createTime;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "UTC+8")
	private Date updateTime;

	public Order() {}

	public Order(int id, int eid, int wid, int pid, int amount, Date createTime, Date updateTime) {
		this.id = id;
		this.eid = eid;
		this.wid = wid;
		this.pid = pid;
		this.amount = amount;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "Order{" +
				"id=" + id +
				", eid=" + eid +
				", wid=" + wid +
				", pid=" + pid +
				", amount=" + amount +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}
