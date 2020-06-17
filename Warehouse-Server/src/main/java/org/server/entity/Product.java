package org.server.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Product implements Serializable {

	private int id;
	private String name;
	private String supplier;
	private int total;

	public Product() {}

	public Product(int id, String name, String supplier, int total) {
		this.id = id;
		this.name = name;
		this.supplier = supplier;
		this.total = total;
	}

	@Override
	public String toString() {
		return "Product{" +
				"id=" + id +
				", name='" + name + '\'' +
				", supplier='" + supplier + '\'' +
				", total=" + total +
				'}';
	}
}
