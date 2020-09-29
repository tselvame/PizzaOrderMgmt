package com.pizza.order.repository;

import org.springframework.data.repository.CrudRepository;

import com.pizza.order.entity.OrderDetails;
import com.pizza.order.entity.PizzaDetails;

public interface PizzaDetailsRepository extends CrudRepository<PizzaDetails, Integer>{
	
	public Long deleteByOrderDetails(OrderDetails orderDetails);

}
