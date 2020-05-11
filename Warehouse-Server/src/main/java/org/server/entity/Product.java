package org.server.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

	private int id;
	private String name;
	private String supplier;
	private int total;

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
