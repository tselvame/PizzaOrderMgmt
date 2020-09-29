package com.pizza.order.service;

import org.springframework.stereotype.Service;

import com.pizza.order.model.OrderConfirmRes;
import com.pizza.order.model.OrderStatusRes;
import com.pizza.order.model.PizzaOrderReq;

@Service
public interface OrderDetailsService {
	
	public OrderStatusRes viewCompleteOrderById(String orderId) throws Exception ;
	
	public OrderConfirmRes saveOrderDetails(PizzaOrderReq orderReq) throws Exception;
	
	public OrderConfirmRes cancelOrderConfirmById(String orderId) throws Exception ;
	
	public OrderConfirmRes updateOrderDetails(PizzaOrderReq orderReq) throws Exception;

}
