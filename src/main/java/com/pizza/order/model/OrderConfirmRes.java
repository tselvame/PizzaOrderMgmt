package com.pizza.order.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonRootName(value= "orderConfirm")
@JsonInclude(Include.NON_NULL)
public class OrderConfirmRes {
	
	@JsonProperty("orderNo")
	public String orderId;
	
	public String orderStatus;
	
	public String paymentStatus;
	
	public String confirmMsg; 
	
	public CustomerInfo  delBoyCntctDetails;

}
