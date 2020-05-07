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
}
