package com.pizza.order.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="pizza_details")
public class PizzaDetails implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="pizza_spec_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer pizaSpecId;
	
	@Column(name="pizza_style")
	private String style;
	
	@Column(name="pizza_size")
	private Integer size;
	
	@Column(name="topng_cd")
	private String topCd;
	
	@Column(name="topng_no")
	private Integer topNo;
	
	@Column(name="specl_note")
	private String speclNote;
	
	@Column(name="create_dt")
	private Date createDt;
	
	@Column(name="update_dt")
	private Date updateDt;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	private OrderDetails orderDetails;

}
