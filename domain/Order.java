package com.abc.rkodemo.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class Order extends ValueObjet {

	private int orderId;
	private String userPhone;
	private Date date;
	private String status = "配送中";
	private double totalPrice;
	private int addressId;
	private List<OrderDetails> detailsList;
}
