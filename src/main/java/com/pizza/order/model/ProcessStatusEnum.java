package com.pizza.order.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum ProcessStatusEnum {
	
	INITIAL("IN","Order is Queue"),
	ACCEPTED("AC","Your Order is Accepted"),
	CANCELACCEPTED("CP","Your Order Cancel is Accepted"),
	PROGRESS("PR","Chef is Cooking the Special Pizza and its in Oven"),
	PACKING("PA","Packing in Progress"),
	TRAVEL("TR","Pizza is currently in transist"),
	DELIVERED("DE","Order successfully Delivered"),
	CANCELLED("CA","Cancelled the Order"),
	INPERSON("IN","Your Order is Ready for Pickup"),
	PAYMENTCONF("PC","Payment confirmed"),
	PAYMENTERROR("PE","Payment Details not authorized. Please retry."),
	CANCELPAYMENT("CA","Your order deducted Payment will be deposited back to the card. Please allow 1-3 Business Days."),
	INVALID("IV","Pizza Order went wrong. Please Retry."),
	UPDATED("UP","Your Order is Successfully updated!");
	
	private String stCode;
	private String stName;
	
	private ProcessStatusEnum(String stCode,String stName) {
		this.stCode = stCode;
		this.stName = stName;
	}
	
	
public static String getToppingName(String key) {
		
		String name = null;
		
		for (ProcessStatusEnum pte : ProcessStatusEnum.values()) {
			
			if(pte.getStCode().equals(key)) {
				name = pte.getStName();
				break;
			}
			
		}
		
		return name;
	}


}
