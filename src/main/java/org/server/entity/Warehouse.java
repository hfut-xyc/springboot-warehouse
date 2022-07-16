package org.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Warehouse implements Serializable {
	private int id;
	private String name;
	private List<Employee> operators;   // 负责该仓库的操作人员

    public Warehouse(int id, String name) {
        this.id = id;
        this.name = name;
    }

}
