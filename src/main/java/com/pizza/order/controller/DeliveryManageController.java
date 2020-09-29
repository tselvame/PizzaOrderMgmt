package com.pizza.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.pizza.order.model.OrderStatusRes;
import com.pizza.order.service.DeliveryManageService;
import com.pizza.order.service.impl.DeliveryManageServiceImpl;

@RestController
public class DeliveryManageController {
	
	@Autowired
	DeliveryManageService deliveryManageService;
	
	@GetMapping("/order/status/{orderId}")
	public ResponseEntity<OrderStatusRes> getOrderStatus(@PathVariable String orderId) throws Exception {
		
		OrderStatusRes orderStatusRes = null;
	
	   //Validate the orderId is not empty or null then perform below logic
		System.out.println("DeliveryManageController -> getOrderStatus -> START -> Processing orderId: " + orderId);
		
		if(!StringUtils.isEmpty(orderId)) {
			orderStatusRes = deliveryManageService.getOrderStatusById(orderId);
		}
		System.out.println("DeliveryManageController -> getOrderStatus -> END!!!! ");
	   return new ResponseEntity<OrderStatusRes>(orderStatusRes, HttpStatus.OK);
	  
	}

}
