package com.cts.paymentmicroservice.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.cts.paymentmicroservice.dto.OrderResponse;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

/**
 * Service Implementation for Payment Service
 */

@Service
public class PaymentServiceImpl implements PaymentService{
	
	
	/**
	 * Overriding method to create order request on the RazorPay Server
	 * 
	 * @param keyId, keySecret, amount
	 * @return This returns the order created on the RazorPay Server
	 */
	@Override
	public OrderResponse createOrder(String keyId,String keySecret,Integer amount) throws RazorpayException {
		
		RazorpayClient razorpayClient = new RazorpayClient(keyId, keySecret);
		
		JSONObject options = new JSONObject();
		options.put("amount", amount * 100);
		options.put("currency", "INR");
		options.put("receipt", "txn_123456");
		
		Order order = razorpayClient.orders.create(options);
		OrderResponse orderResponse = new OrderResponse(order);
		return orderResponse;
	}

}
