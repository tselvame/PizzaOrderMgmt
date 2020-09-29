package com.pizza.order.service;

import org.springframework.stereotype.Service;

import com.pizza.order.model.OrderStatusRes;

@Service
public interface DeliveryManageService {
	
	public OrderStatusRes getOrderStatusById(String orderId) throws Exception;

}
