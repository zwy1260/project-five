package com.abc.rkodemo.domain;

import lombok.Data;

import java.util.List;

@Data
public class Shoppingcart extends ValueObjet {

	private int shoppingcartId;
	private String userPhone;
	private List<ShoppingcartDetails> detailsList;
}
