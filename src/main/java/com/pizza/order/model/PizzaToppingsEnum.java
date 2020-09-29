package com.pizza.order.model;

import lombok.Getter;

@Getter
public enum PizzaToppingsEnum {
	
	ONION("ON","ONION"),
	TOMATTO("TO","TOMATTO"),
	CAPSICUM("CC","CAPSICUM"),
	GRILLEDCHICKEN("GC","GRILLED CHICKEN"),
	STEAK("ST","STEAK"),
	BLACKOLIVES("BO","BLACK OLIVES"),
	SALT("SA","SALT"),
	PEPPER("PE","PEPPER"),
	JALAPENO("JL","JALAPENO");
	
	private String topCode;
	private String topName;
	
     PizzaToppingsEnum(String topCode,String topName) {
		this.topCode = topCode;
		this.topName = topName;
	}
	
	
	public static String getToppingName(String key) {
		
		String name = null;
		
		for (PizzaToppingsEnum pte : PizzaToppingsEnum.values()) {
			
			if(pte.getTopCode().equals(key)) {
				name = pte.getTopName();
				break;
			}
			
		}
		
		return name;
	}

}
