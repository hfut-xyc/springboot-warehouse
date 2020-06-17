package org.server.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class Warehouse implements Serializable {
	private int id;
	private String name;
	private List<Employee> operators;   // 负责该仓库的操作人员
	
	public Warehouse() {}

    public Warehouse(int id, String name) {
        this.id = id;
        this.name = name;
    }

	@Override
	public String toString() {
		return "Warehouse{" +
				"id=" + id +
				", name='" + name + '\'' +
				", operators='" + operators.toString() + '\'' +
				'}';
	}
}
