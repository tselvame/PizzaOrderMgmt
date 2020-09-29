package com.pizza.order.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pizza.order.model.CustomerAddress;
import com.pizza.order.model.CustomerInfo;
import com.pizza.order.model.OrderConfirmRes;
import com.pizza.order.model.OrderPaymentInfo;
import com.pizza.order.model.OrderStatusRes;
import com.pizza.order.model.PizzaOrderReq;
import com.pizza.order.model.PizzaReq;
import com.pizza.order.model.ProcessStatusEnum;
import com.pizza.order.service.OrderDetailsService;
import com.pizza.order.service.impl.OrderDetailsServiceImpl;

@RestController
public class OrderManageController {
	
	@Autowired
	OrderDetailsService orderDetailsService;
	
	@GetMapping("/order/print/{orderId}")
	public ResponseEntity<OrderStatusRes> printCompleteOrderDetails(@PathVariable String orderId) throws Exception {
		
		OrderStatusRes pizzaOrderReq = null;
	
	   //Validate the orderId is not empty or null then perform below logic
		System.out.println("OrderManageController -> printCompleteOrderDetails -> START -> Processing orderId: " + orderId);
		
		if(!StringUtils.isEmpty(orderId)) {
			pizzaOrderReq = orderDetailsService.viewCompleteOrderById(orderId);
		}
		System.out.println("OrderManageController -> printCompleteOrderDetails -> END!!!! ");
	   return new ResponseEntity<OrderStatusRes>(pizzaOrderReq, HttpStatus.OK);
	  
	}
	
	
	
	@PostMapping(value = "/order/create", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<OrderConfirmRes> saveOrderDetails(@RequestBody PizzaOrderReq orderReq) throws Exception {
		OrderConfirmRes saveOrderRes = null;
		//Validate the orderId is not empty or null then perform below logic
				System.out.println("OrderManageController -> saveOrderDetails -> START -> Processing orderId: ");
				
				if(orderReq != null) {
					saveOrderRes = orderDetailsService.saveOrderDetails(orderReq);
				}
				System.out.println("OrderManageController -> saveOrderDetails -> END!!!! ");
				 return new ResponseEntity<OrderConfirmRes>(saveOrderRes, HttpStatus.OK);
	}
	
	
	
	@PostMapping(value = "/order/confirm/{orderId}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<OrderConfirmRes> confirmOrderDetails(@PathVariable String orderId, @RequestBody PizzaOrderReq orderReq) throws Exception {
		
		        OrderConfirmRes saveOrderRes = null;
		        
		        //Validate the orderId is not empty or null then perform below logic
				System.out.println("OrderManageController -> confirmOrderDetails -> START -> Processing orderId: ");
				
				if(orderReq != null) {
					saveOrderRes = orderDetailsService.saveOrderDetails(orderReq);
				}
				System.out.println("OrderManageController -> confirmOrderDetails -> END!!!! ");
				 return new ResponseEntity<OrderConfirmRes>(saveOrderRes, HttpStatus.OK);
	}


	
	@PutMapping(value = "/order/update/{orderId}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<OrderConfirmRes> changeExistingOrder(@PathVariable String orderId, @RequestBody PizzaOrderReq orderReq) throws Exception{
		 
		/*
		 * OrderConfirmRes ordConfRes = new OrderConfirmRes();
		 * ordConfRes.setOrderId("PIZA000123456987");
		 * //ordConfRes.setDelBoyCntctDetails(custInfo);
		 * ordConfRes.setOrderStatus(ProcessStatusEnum.UPDATED.getStName());
		 * ordConfRes.setPaymentStatus(ProcessStatusEnum.PAYMENTCONF.getStName());
		 * ordConfRes.setConfirmMsg(ProcessStatusEnum.ACCEPTED.getStName());
		 */
		   OrderConfirmRes cancelOrderRes = null;
			
		   //Validate the orderId is not empty or null then perform below logic
			System.out.println("OrderManageController -> changeExistingOrder -> START -> Processing orderId: " + orderId);
			
			if(!StringUtils.isEmpty(orderId)) {
				cancelOrderRes = orderDetailsService.updateOrderDetails(orderReq);
			}
			System.out.println("OrderManageController -> changeExistingOrder -> END!!!! ");
		   return new ResponseEntity<OrderConfirmRes>(cancelOrderRes, HttpStatus.OK);
	  
	}

	@DeleteMapping(value = "/order/delete/{orderId}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<OrderConfirmRes> cancelExistingOrder(@PathVariable String orderId) throws Exception {
		     
	        OrderConfirmRes cancelOrderRes = null;
		
		   //Validate the orderId is not empty or null then perform below logic
			System.out.println("OrderManageController -> cancelExistingOrder -> START -> Processing orderId: " + orderId);
			
			if(!StringUtils.isEmpty(orderId)) {
				cancelOrderRes = orderDetailsService.cancelOrderConfirmById(orderId);
			}
			System.out.println("OrderManageController -> cancelExistingOrder -> END!!!! ");
		   return new ResponseEntity<OrderConfirmRes>(cancelOrderRes, HttpStatus.OK);
	}
	
	
}
