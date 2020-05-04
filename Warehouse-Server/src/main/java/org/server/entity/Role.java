package org.server.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Role {

	private int id;
	private String name;
	private String remark;

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
