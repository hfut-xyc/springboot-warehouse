package org.server.entity;

public class Role {

	private int id;
	private String name;
	private String remark;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

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
