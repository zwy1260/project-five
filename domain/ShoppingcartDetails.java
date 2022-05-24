package com.abc.rkodemo.domain;

import lombok.Data;

@Data
public class ShoppingcartDetails extends ValueObjet {

	private int detailsId;
	private int shoppingcartId;
	private int commodityId;
	private String title;
	private double price;
	private int num;
	private String image;
}
