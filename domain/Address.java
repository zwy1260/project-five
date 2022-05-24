package com.abc.rkodemo.domain;

import lombok.Data;

@Data
public class Address extends ValueObjet {

	private int addressId;
	private String userPhone;
	private String address;
	private int priority = 2;
}
