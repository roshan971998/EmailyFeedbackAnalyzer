package com.cts.paymentmicroservice.service;

import org.springframework.stereotype.Service;

import com.cts.paymentmicroservice.dto.OrderResponse;
import com.razorpay.RazorpayException;

/**
 * Payment interface for Payment Management
 */
@Service
public interface PaymentService {

	/*
	 * Method to create order request on the RazorPay Server 
	*/
	public OrderResponse createOrder(String keyId, String keySecret, Integer amount) throws RazorpayException;
}
