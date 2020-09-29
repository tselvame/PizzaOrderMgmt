package com.pizza.order.repository;

import org.springframework.data.repository.CrudRepository;

import com.pizza.order.entity.OrderDetails;

public interface OrderDetailsRepository extends CrudRepository<OrderDetails, Integer>{
	
	public OrderDetails findByOrderId(Integer orderId);
	
	public Long deleteByOrderId(Integer orderId);

}
