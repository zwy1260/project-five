package com.abc.rkodemo.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 *	基础实体类，用lang3包重写toString方法
 */
public class ValueObjet {

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
}
