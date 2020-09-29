package com.pizza.order.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonRootName(value= "orderStatusRes")
@JsonInclude(Include.NON_NULL)
public class OrderStatusRes {
	
	public Integer orderId;
	
	public String orderStatus;
	
	public String orderCreatedDt;
	
	public String estDeliveryTime; 
	
	public String waitingTime; 
	
	@JsonProperty("delvBoyContactInfo")
	public CustomerInfo  delBoyCntctDetails;
	
	@JsonProperty("pizzaList")
	public List<PizzaReq> pizzaReq;
	
	@JsonProperty("customerInfo")
	public CustomerInfo customerInfo;
	
	@JsonProperty("paymentInfo")
	public OrderPaymentInfo paymentInfo;
	

}
