package com.pizza.order.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.pizza.order.entity.OrderDetails;
import com.pizza.order.entity.PaymentDetails;
import com.pizza.order.entity.PizzaDetails;
import com.pizza.order.model.CustomerAddress;
import com.pizza.order.model.CustomerInfo;
import com.pizza.order.model.OrderConfirmRes;
import com.pizza.order.model.OrderPaymentInfo;
import com.pizza.order.model.OrderStatusRes;
import com.pizza.order.model.PizzaOrderReq;
import com.pizza.order.model.PizzaReq;
import com.pizza.order.model.PizzaToppingsEnum;
import com.pizza.order.model.ProcessStatusEnum;
import com.pizza.order.repository.OrderDetailsRepository;
import com.pizza.order.repository.PaymentDetailsRepository;
import com.pizza.order.repository.PizzaDetailsRepository;
import com.pizza.order.service.OrderDetailsService;
import com.pizza.order.util.OrderUtil;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

	@Autowired
	OrderDetailsRepository orderDetailsRepo;

	@Autowired
	PaymentDetailsRepository paymentDetailsRepo;

	@Autowired
	PizzaDetailsRepository pizzaDetailsRepo;
	
	@PersistenceContext
    protected EntityManager entityManager;

	private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	/*
	 * Below method is to retrieve Complete Order Details with Customer/Pizza
	 * Ordered/Payment for View Purpose based on given Order Id
	 */
	public OrderStatusRes viewCompleteOrderById(String orderId) throws Exception {

		OrderDetails orderDet = null;
		OrderStatusRes orderDetailsRes = null;
		try {
			System.out.println(
					"OrderDetailsServiceImpl -> getOrderStatusById -> START -> Before calling Order Details Table");
			orderDet = orderDetailsRepo.findByOrderId(Integer.parseInt(orderId));

			if (orderDet != null) {

				orderDetailsRes = new OrderStatusRes();
				orderDetailsRes.setOrderId(orderDet.getOrderId());
				orderDetailsRes.setOrderStatus(orderDet.getOrderStatus());
				orderDetailsRes.setEstDeliveryTime(orderDet.getEstTime());

				// To-DO: We can enhance this Waiting time based on current Requested time -
				// Estimated Time to get new Waiting Time
				orderDetailsRes.setWaitingTime(orderDet.getEstTime());

				// To-DO Below Contact is Employee Contact in Case of Client Wants to Reach.
				// Hence hardcoding for now Considering the given timeframe.
				CustomerInfo custInfo = new CustomerInfo();
				custInfo.setFirstName("DELIVERYBOY");
				custInfo.setLastName("SAM");
				custInfo.setPhoneNo("(111)-111-1111");
				custInfo.setSpecialNote(
						"Look for White Color Ford Fiesta for Delivery Vehicle with our Organization Sign printed in the window");

				orderDetailsRes.setDelBoyCntctDetails(custInfo);

				custInfo = new CustomerInfo();
				custInfo.setFirstName(orderDet.getFirstName());
				custInfo.setLastName(orderDet.getLastName());
				custInfo.setMidName(orderDet.getMidName());
				custInfo.setPhoneNo(orderDet.getPhoneNo());
				custInfo.setSpecialNote(orderDet.getSpecialNote());

				CustomerAddress custAddr = new CustomerAddress();
				custAddr.setAddressLine1(orderDet.getAddrLine1());
				custAddr.setAddressLine2(orderDet.getAddrLine2());
				custAddr.setCity(orderDet.getCity());
				custAddr.setState(orderDet.getState());
				custAddr.setZipCodeWithExtn(orderDet.getZipCodeExtn());

				custInfo.setDeliveryAddress(custAddr);
				orderDetailsRes.setCustomerInfo(custInfo);

				if (!CollectionUtils.isEmpty(orderDet.getPizzaDetailsList())) {
					List<PizzaReq> pizzaReq = new ArrayList<PizzaReq>();

					for (PizzaDetails pizzaDetails : orderDet.getPizzaDetailsList()) {
						PizzaReq reqDetails = new PizzaReq();
						reqDetails.setPizzaSize(pizzaDetails.getSize());
						reqDetails.setPizzaStyle(pizzaDetails.getStyle());
						reqDetails.setSpecialNote(pizzaDetails.getSpeclNote());

						reqDetails.setToppingName(PizzaToppingsEnum.getToppingName(pizzaDetails.getTopCd()));
						pizzaReq.add(reqDetails);
					}

					orderDetailsRes.setPizzaReq(pizzaReq);

				}

				if (orderDet.getPaymentDetails() != null) {
					OrderPaymentInfo odrPayInfo = new OrderPaymentInfo();
					odrPayInfo.setIsCredit(orderDet.getPaymentDetails().getIsCredit());
					odrPayInfo.setCardType(orderDet.getPaymentDetails().getCardType());
					odrPayInfo.setCardNo(orderDet.getPaymentDetails().getCardNo());
					odrPayInfo.setCardExp(orderDet.getPaymentDetails().getCardExpDt());
					odrPayInfo.setCardSecureCd(orderDet.getPaymentDetails().getCardSecureCd());
					odrPayInfo.setApplyCoupon(orderDet.getPaymentDetails().getCouponCd());

					custAddr = new CustomerAddress();
					custAddr.setAddressLine1(orderDet.getPaymentDetails().getAddrLine1());
					custAddr.setAddressLine2(orderDet.getPaymentDetails().getAddrLine2());
					custAddr.setCity(orderDet.getPaymentDetails().getCity());
					custAddr.setState(orderDet.getPaymentDetails().getState());
					custAddr.setZipCodeWithExtn(orderDet.getPaymentDetails().getZipCodeExtn());

					odrPayInfo.setBillingAddress(custAddr);

					orderDetailsRes.setPaymentInfo(odrPayInfo);
				}

				orderDetailsRes.setOrderCreatedDt(dateFormat.format(orderDet.getCreateDt()));

				System.out.println("OrderDetailsServiceImpl -> getOrderStatusById -> After calling Order Details Table "
						+ orderDetailsRes.getOrderStatus());

			}
		} catch (Exception ex) {
			System.err.println("Exception -> OrderDetailsServiceImpl -> getOrderStatusById :: " + ex.getMessage());
			throw ex;
		}

		System.out.println("OrderDetailsServiceImpl -> getOrderStatusById -> END!!!");
		return orderDetailsRes;
	}

	/*
	 * Below method is to Save Order Details and generate Order Id Order Id
	 */
	public OrderConfirmRes saveOrderDetails(PizzaOrderReq orderReq) throws Exception {

		OrderConfirmRes orderStatusRes = null;
		OrderDetails orderDet = null;
		try {
			System.out.println(
					"OrderDetailsServiceImpl -> saveOrderDetails -> START -> Before calling Order Details Table");

			if (orderReq.getOrderId() != null && orderReq.getOrderId() > 0) {
				// Existing Order with Payment Order Confirmation
				orderDet = orderDetailsRepo.findByOrderId(orderReq.getOrderId());

				if (orderReq.getPaymentInfo() != null) {

					PaymentDetails paymentDetails = new PaymentDetails();
					paymentDetails.setIsCredit(orderReq.getPaymentInfo().getIsCredit());
					paymentDetails.setCardNo(orderReq.getPaymentInfo().getCardNo());
					paymentDetails.setCardExpDt(orderReq.getPaymentInfo().getCardExp());
					paymentDetails.setCardSecureCd(orderReq.getPaymentInfo().getCardSecureCd());
					paymentDetails.setCardType(orderReq.getPaymentInfo().getCardType());
					paymentDetails.setAddrLine1(orderReq.getPaymentInfo().getBillingAddress().getAddressLine1());
					paymentDetails.setAddrLine2(orderReq.getPaymentInfo().getBillingAddress().getAddressLine2());
					paymentDetails.setCity(orderReq.getPaymentInfo().getBillingAddress().getCity());
					paymentDetails.setZipCodeExtn(orderReq.getPaymentInfo().getBillingAddress().getZipCodeWithExtn());
					paymentDetails.setState(orderReq.getPaymentInfo().getBillingAddress().getState());
					paymentDetails.setCreateDt(OrderUtil.getGMTTimestamp());
					paymentDetails.setUpdateDt(OrderUtil.getGMTTimestamp());
					paymentDetails.setOrderDetailsFk(orderDet);
					paymentDetailsRepo.save(paymentDetails);
					System.out.println(
							"OrderDetailsServiceImpl -> saveOrderDetails -> START -> After calling Payment Details Table");
					
					orderStatusRes = new OrderConfirmRes();
					orderStatusRes.setOrderId(String.valueOf(orderDet.getOrderId()));
					// ordConfRes.setDelBoyCntctDetails(custInfo);
					orderStatusRes.setOrderStatus(ProcessStatusEnum.ACCEPTED.getStName());
					
				}
			} else {
				// New Application
				orderDet = new OrderDetails();
				orderDet.setFirstName(orderReq.getCustomerInfo().getFirstName());
				orderDet.setLastName(orderReq.getCustomerInfo().getLastName());
				orderDet.setMidName(orderReq.getCustomerInfo().getMidName());
				orderDet.setAddrLine1(orderReq.getCustomerInfo().getDeliveryAddress().getAddressLine1());
				orderDet.setAddrLine2(orderReq.getCustomerInfo().getDeliveryAddress().getAddressLine2());
				orderDet.setCity(orderReq.getCustomerInfo().getDeliveryAddress().getCity());
				orderDet.setState(orderReq.getCustomerInfo().getDeliveryAddress().getState());
				orderDet.setZipCodeExtn(orderReq.getCustomerInfo().getDeliveryAddress().getZipCodeWithExtn());
				orderDet.setDob(orderReq.getCustomerInfo().getDob());
				orderDet.setEstTime("30");
				orderDet.setOrderStatus(ProcessStatusEnum.INITIAL.getStCode());
				orderDet.setPhoneNo(orderReq.getCustomerInfo().getPhoneNo());
				orderDet.setSpecialNote(orderReq.getCustomerInfo().getSpecialNote());
				orderDet.setUpdateDt(OrderUtil.getGMTTimestamp());
				orderDet.setCreateDt(OrderUtil.getGMTTimestamp());
				orderDetailsRepo.save(orderDet);
				System.out.println(
						"OrderDetailsServiceImpl -> saveOrderDetails -> START -> After Saving Order Details Table");

				if (!CollectionUtils.isEmpty(orderReq.getPizzaReq())) {

					for (PizzaReq pizReq : orderReq.getPizzaReq()) {

						PizzaDetails pizzaDet = new PizzaDetails();
						pizzaDet.setOrderDetails(orderDet);
						pizzaDet.setCreateDt(OrderUtil.getGMTTimestamp());
						pizzaDet.setUpdateDt(OrderUtil.getGMTTimestamp());
						pizzaDet.setSize(pizReq.getPizzaSize());
						pizzaDet.setStyle(pizReq.getPizzaStyle());

						// To-do: Need to enhance this to customize to save all toppings details
						for (Entry<String, Integer> entry : pizReq.getSelectedToppingsAndNo().entrySet()) {
							pizzaDet.setTopCd(entry.getKey());
							pizzaDet.setTopNo(entry.getValue());
						}

						pizzaDet.setSpeclNote(pizReq.getSpecialNote());
						pizzaDetailsRepo.save(pizzaDet);
						System.out.println(
								"OrderDetailsServiceImpl -> saveOrderDetails -> START -> After Saving Pizza Details Table");
					}

				}
				
				orderStatusRes = new OrderConfirmRes();
				orderStatusRes.setOrderId(String.valueOf(orderDet.getOrderId()));
				// ordConfRes.setDelBoyCntctDetails(custInfo);
				orderStatusRes.setOrderStatus(ProcessStatusEnum.INITIAL.getStName());
			}

			
			System.out.println("OrderDetailsServiceImpl -> saveOrderDetails -> END!!!" + orderStatusRes.getOrderId());

		} catch (Exception ex) {
			System.err.println("Exception -> OrderDetailsServiceImpl -> saveOrderDetails :: " + ex.getMessage());
			throw ex;
		}

		return orderStatusRes;
	}

	/*
	 * Below method is to Delete or Cancel Order Details with current status based
	 * on given Order Id
	 */
	@Transactional
	public OrderConfirmRes cancelOrderConfirmById(String orderId) throws Exception {
		// To-Do: We can enhance the Delete based on Soft Delete so that in future we
		// can see the list of appt cancelled.
		OrderConfirmRes ordConfRes = null;
		OrderDetails orderDet = null;
		Long count = 0L;
		try {
			System.out.println(
					"OrderDetailsServiceImpl -> cancelOrderConfirmById -> START -> Before Deleting Order Details Table");
			orderDet = orderDetailsRepo.findByOrderId(Integer.parseInt(orderId));
			
			paymentDetailsRepo.deleteByOrderDetailsFk(orderDet);
			System.out.println(
					"OrderDetailsServiceImpl -> cancelOrderConfirmById -> START -> After Successful Deleting Order Details Table");
			
			pizzaDetailsRepo.deleteByOrderDetails(orderDet);
			System.out.println(
					"OrderDetailsServiceImpl -> cancelOrderConfirmById -> START -> After Successful Deleting Order Details Table");
			
			count = orderDetailsRepo.deleteByOrderId(Integer.parseInt(orderId));

			if (count > 0) {
				System.out.println(
						"OrderDetailsServiceImpl -> cancelOrderConfirmById -> START -> After Successful Deleting Order Details Table");
				ordConfRes = new OrderConfirmRes();

				ordConfRes.setOrderId(orderId);
				// ordConfRes.setDelBoyCntctDetails(custInfo);
				ordConfRes.setOrderStatus(ProcessStatusEnum.CANCELLED.getStName());
				ordConfRes.setPaymentStatus(ProcessStatusEnum.CANCELPAYMENT.getStName());
				ordConfRes.setConfirmMsg(ProcessStatusEnum.CANCELACCEPTED.getStName());

			}

		} catch (Exception ex) {
			System.err.println("Exception -> OrderDetailsServiceImpl -> cancelOrderConfirmById :: " + ex.getMessage());
			throw ex;
		}

		System.out.println("OrderDetailsServiceImpl -> cancelOrderConfirmById -> END!!!");
		return ordConfRes;
	}

	/*
	 * Below method is to Save Order Details and generate Order Id Order Id
	 */
	public OrderConfirmRes updateOrderDetails(PizzaOrderReq orderReq) throws Exception {

		OrderConfirmRes orderStatusRes = null;
		OrderDetails orderDet = null;
		try {
			System.out.println(
					"OrderDetailsServiceImpl -> updateOrderDetails -> START -> Before calling Order Details Table");

			if (orderReq.getOrderId() != null && orderReq.getOrderId() > 0) {
				// Existing Order with Payment Order Confirmation
				orderDet = orderDetailsRepo.findByOrderId(orderReq.getOrderId());

				orderDet.setFirstName(orderReq.getCustomerInfo().getFirstName());
				orderDet.setLastName(orderReq.getCustomerInfo().getLastName());
				orderDet.setMidName(orderReq.getCustomerInfo().getMidName());
				orderDet.setAddrLine1(orderReq.getCustomerInfo().getDeliveryAddress().getAddressLine1());
				orderDet.setAddrLine2(orderReq.getCustomerInfo().getDeliveryAddress().getAddressLine2());
				orderDet.setCity(orderReq.getCustomerInfo().getDeliveryAddress().getCity());
				orderDet.setState(orderReq.getCustomerInfo().getDeliveryAddress().getState());
				orderDet.setZipCodeExtn(orderReq.getCustomerInfo().getDeliveryAddress().getZipCodeWithExtn());
				orderDet.setDob(orderReq.getCustomerInfo().getDob());
				orderDet.setEstTime("30");
				orderDet.setOrderStatus(ProcessStatusEnum.INITIAL.getStCode());
				orderDet.setPhoneNo(orderReq.getCustomerInfo().getPhoneNo());
				orderDet.setSpecialNote(orderReq.getCustomerInfo().getSpecialNote());
				orderDet.setUpdateDt(OrderUtil.getGMTTimestamp());
				orderDet.setCreateDt(OrderUtil.getGMTTimestamp());
				orderDetailsRepo.save(orderDet);
				System.out.println(
						"OrderDetailsServiceImpl -> updateOrderDetails -> START -> After Saving Order Details Table");

				// To-Do: We can enhance this to Update Existing Record and Add new Record
				if (!CollectionUtils.isEmpty(orderReq.getPizzaReq())) {

					for (PizzaReq pizReq : orderReq.getPizzaReq()) {

						PizzaDetails pizzaDet = new PizzaDetails();
						pizzaDet.setOrderDetails(orderDet);
						pizzaDet.setCreateDt(OrderUtil.getGMTTimestamp());
						pizzaDet.setUpdateDt(OrderUtil.getGMTTimestamp());
						pizzaDet.setSize(pizReq.getPizzaSize());
						pizzaDet.setStyle(pizReq.getPizzaStyle());

						// To-do: Need to enhance this to customize to save all toppings details
						for (Entry<String, Integer> entry : pizReq.getSelectedToppingsAndNo().entrySet()) {
							pizzaDet.setTopCd(entry.getKey());
							pizzaDet.setTopNo(entry.getValue());
						}

						pizzaDet.setSpeclNote(pizReq.getSpecialNote());
						pizzaDetailsRepo.save(pizzaDet);
						System.out.println(
								"OrderDetailsServiceImpl -> updateOrderDetails -> START -> After Saving Pizza Details Table");
					}

				}

				if (orderReq.getPaymentInfo() != null) {

					PaymentDetails paymentDetails = orderDet.getPaymentDetails();
					paymentDetails.setIsCredit(orderReq.getPaymentInfo().getIsCredit());
					paymentDetails.setCardNo(orderReq.getPaymentInfo().getCardNo());
					paymentDetails.setCardExpDt(orderReq.getPaymentInfo().getCardExp());
					paymentDetails.setCardSecureCd(orderReq.getPaymentInfo().getCardSecureCd());
					paymentDetails.setCardType(orderReq.getPaymentInfo().getCardType());
					paymentDetails.setAddrLine1(orderReq.getPaymentInfo().getBillingAddress().getAddressLine1());
					paymentDetails.setAddrLine2(orderReq.getPaymentInfo().getBillingAddress().getAddressLine2());
					paymentDetails.setCity(orderReq.getPaymentInfo().getBillingAddress().getCity());
					paymentDetails.setState(orderReq.getPaymentInfo().getBillingAddress().getState());
					paymentDetails.setZipCodeExtn(orderReq.getPaymentInfo().getBillingAddress().getZipCodeWithExtn());
					paymentDetails.setCreateDt(OrderUtil.getGMTTimestamp());
					paymentDetails.setUpdateDt(OrderUtil.getGMTTimestamp());
					paymentDetails.setOrderDetailsFk(orderDet);
					paymentDetailsRepo.save(paymentDetails);
					System.out.println(
							"OrderDetailsServiceImpl -> updateOrderDetails -> START -> After calling Payment Details Table");
				}
			}

			orderStatusRes = new OrderConfirmRes();
			orderStatusRes.setOrderId(String.valueOf(orderDet.getOrderId()));
			// ordConfRes.setDelBoyCntctDetails(custInfo);
			orderStatusRes.setOrderStatus(ProcessStatusEnum.INITIAL.getStName());
			System.out.println("OrderDetailsServiceImpl -> updateOrderDetails -> END!!!" + orderStatusRes.getOrderId());

		} catch (Exception ex) {
			System.err.println("Exception -> OrderDetailsServiceImpl -> updateOrderDetails :: " + ex.getMessage());
			throw ex;
		}

		return orderStatusRes;
	}

}
