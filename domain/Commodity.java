package com.abc.rkodemo.domain;

import lombok.Data;

@Data
public class Commodity extends ValueObjet {

	private int commodityId;
	private String title;
	private String image;
	private String details;
	private double price;
	private int num;
	private String type;
	private User user;
}
