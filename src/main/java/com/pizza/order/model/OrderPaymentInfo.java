package com.pizza.order.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class OrderPaymentInfo {
	
	private Integer paymentId;

	private String  isCredit;
	
	private String cardType;
	
	private String cardNo;
	
	private String cardExp;
	
	private String cardSecureCd;
	
	private String applyCoupon;
	
	private CustomerAddress billingAddress;
}
