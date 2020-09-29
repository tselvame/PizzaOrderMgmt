package com.pizza.order.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class PizzaReq {
	
	private Integer pizza_spec_id;
	
	private String pizzaStyle;
	
	private Integer pizzaSize;
	
	private Map<String, Integer> selectedToppingsAndNo;
	
	private String toppingName;
	
	private String specialNote;
	

}
