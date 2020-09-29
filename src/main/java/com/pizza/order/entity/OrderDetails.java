package com.pizza.order.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.pizza.order.model.CustomerAddress;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="order_details")
public class OrderDetails implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="order_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer orderId;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="mid_Name")
	private String midName;
	
	@Column(name="last_name")
	private String LastName;
	
	@Column(name="dob")
	private String dob;
	
	@Column(name="phone_no")
	private String phoneNo;
	
	@Column(name="cust_note")
	private String specialNote;
	
	@Column(name="address_line1")
	private String addrLine1;
	
	@Column(name="address_line2")
	private String addrLine2;
	
	@Column(name="city")
	private String city;
	
	@Column(name="state")
	private String state;
	
	@Column(name="zip_code")
	private String zipCodeExtn;
	
	@Column(name="est_delv_time")
	private String estTime;
	
	@Column(name="order_status_cd")
	private String orderStatus;
	  
	
	@Column(name="create_dt")
	private Date createDt;
	
	@Column(name="update_dt")
	private Date updateDt;
	
	@OneToMany(mappedBy="orderDetails")
	List<PizzaDetails> pizzaDetailsList;
	
	@OneToOne(mappedBy="orderDetailsFk")
	PaymentDetails paymentDetails;
	
	
}

