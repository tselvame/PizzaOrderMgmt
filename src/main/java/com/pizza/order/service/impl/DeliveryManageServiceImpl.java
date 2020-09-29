package com.pizza.order.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pizza.order.entity.OrderDetails;
import com.pizza.order.model.CustomerInfo;
import com.pizza.order.model.OrderStatusRes;
import com.pizza.order.repository.OrderDetailsRepository;
import com.pizza.order.service.DeliveryManageService;

@Service
public class DeliveryManageServiceImpl implements DeliveryManageService{
	
	private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	@Autowired
	OrderDetailsRepository orderDetailsRepo;
	
	@PersistenceContext
    protected EntityManager entityManager;
	/*
	 * Below method is to retrieve Order Details with current status based on given
	 * Order Id
	 */
	public OrderStatusRes getOrderStatusById(String orderId) throws Exception {

		OrderDetails orderDet = null;
		OrderStatusRes orderStatusRes = null;
		try {
			System.out.println(
					"OrderDetailsServiceImpl -> getOrderStatusById -> START -> Before calling Order Details Table");
			orderDet = orderDetailsRepo.findByOrderId(Integer.parseInt(orderId));

			if (orderDet != null) {

				orderStatusRes = new OrderStatusRes();
				orderStatusRes.setOrderId(orderDet.getOrderId());
				orderStatusRes.setOrderStatus(orderDet.getOrderStatus());
				orderStatusRes.setEstDeliveryTime(orderDet.getEstTime());

				// To-DO: We can enhance this Waiting time based on current Requested time -
				// Estimated Time to get new Waiting Time
				orderStatusRes.setWaitingTime(orderDet.getEstTime());

				// To-DO Below Contact is Employee Contact in Case of Client Wants to Reach.
				// Hence hardcoding for now Considering the given timeframe.
				CustomerInfo custInfo = new CustomerInfo();
				custInfo.setFirstName("DELIVERYBOY");
				custInfo.setLastName("SAM");
				custInfo.setPhoneNo("(111)-111-1111");
				custInfo.setSpecialNote(
						"Look for White Color Ford Fiesta for Delivery Vehicle with our Organization Sign printed in the window");

				orderStatusRes.setDelBoyCntctDetails(custInfo);

				orderStatusRes.setOrderCreatedDt(dateFormat.format(orderDet.getCreateDt()));

				System.out.println("OrderDetailsServiceImpl -> getOrderStatusById -> After calling Order Details Table "
						+ orderStatusRes.getOrderStatus());

			}
		} catch (Exception ex) {
			System.err.println("Exception -> OrderDetailsServiceImpl -> getOrderStatusById :: " + ex.getMessage());
			throw ex;
		}

		System.out.println("OrderDetailsServiceImpl -> getOrderStatusById -> END!!!");
		return orderStatusRes;
	}

}
