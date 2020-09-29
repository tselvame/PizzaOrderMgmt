package com.pizza.order.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class CustomerInfo {
	
	private String firstName;
	
	private String midName;
	
	private String LastName;
	
	private String dob;
	
	private String phoneNo;
	
	private CustomerAddress deliveryAddress;
	
	private String specialNote;

}
