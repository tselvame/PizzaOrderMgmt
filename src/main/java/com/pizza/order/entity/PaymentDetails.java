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
@Table(name="payment_details")
public class PaymentDetails implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="payment_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer paymentId;
	
	@Column(name="is_credit")
	private String isCredit;
	
	@Column(name="card_type")
	private String cardType;
	
	@Column(name="card_no")
	private String cardNo;
	
	@Column(name="card_exp_dt")
	private String cardExpDt;
	
	@Column(name="card_secure_cd")
	private String cardSecureCd;
	
	@Column(name="coupon_cd")
	private String couponCd;
	
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
	
	@Column(name="create_dt")
	private Date createDt;
	
	@Column(name="update_dt")
	private Date updateDt;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	private OrderDetails orderDetailsFk;
	

}