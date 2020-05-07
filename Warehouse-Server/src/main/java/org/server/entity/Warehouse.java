package org.server.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Warehouse {

	private int id;
	private String name;
	private List<Employee> operators;   // 负责该仓库的操作人员
	private List<Product> products;     // 该仓库存放的产品类别
}
