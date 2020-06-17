package org.server.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Role implements Serializable {

	private int id;
	private String name;
	private String remark;

	public Role() {}

	public Role(int id, String name, String remark) {
		this.id = id;
		this.name = name;
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "Role{" +
				"id=" + id +
				", name='" + name + '\'' +
				", remark='" + remark + '\'' +
				'}';
	}
}
