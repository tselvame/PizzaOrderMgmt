package com.pizza.order.repository;

import org.springframework.data.repository.CrudRepository;

import com.pizza.order.entity.OrderDetails;
import com.pizza.order.entity.PaymentDetails;

public interface PaymentDetailsRepository extends CrudRepository<PaymentDetails, Integer>{
	
	public Long deleteByOrderDetailsFk(OrderDetails orderDetailsFk);

}
