package com.abc.rkodemo.domain;

import lombok.Data;

@Data
public class OrderDetails extends ValueObjet {

	private int detailsId;
	private int orderId;
	private int commodityId;
	private double price;
	private int num;
}
