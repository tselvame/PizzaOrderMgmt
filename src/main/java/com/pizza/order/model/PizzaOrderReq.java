package com.pizza.order.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonRootName(value= "orderReq")
@JsonInclude(Include.NON_NULL)
public class PizzaOrderReq {
	
	public Integer orderId;
	
	@JsonProperty("pizzaList")
	public List<PizzaReq> pizzaReq;
	
	@JsonProperty("customerInfo")
	public CustomerInfo customerInfo;
	
	@JsonProperty("paymentInfo")
	public OrderPaymentInfo paymentInfo;
	
}
