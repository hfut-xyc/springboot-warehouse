package org.server.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Warehouse {
	private int id;
	private String name;
	// from tb_employee_warehouse
	private List<Employee> operators;   // 负责该仓库的操作人员
	// from tb_warehouse_product
	// private List<Product> products;     // 该仓库存放的产品类别


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
				// ", products='" + products.toString() + '\'' +
				'}';
	}
}
