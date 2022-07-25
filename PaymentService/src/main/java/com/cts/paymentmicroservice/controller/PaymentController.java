package com.cts.paymentmicroservice.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.paymentmicroservice.dto.OrderResponse;
import com.cts.paymentmicroservice.feign.AuthorisationClient;
import com.cts.paymentmicroservice.service.PaymentService;
import com.razorpay.RazorpayException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller class for Payment service
 */
@RestController
@RequestMapping("/razorpay")
@Slf4j
public class PaymentController {

	@Autowired
	PaymentService paymentService;
	@Autowired
	AuthorisationClient authorisationClient;

	@Value("${key.id}")
	private String key_id;

	@Value("${key.secret}")
	private String key_secret;

	@Operation(summary = "This is to check the health of Authorization controller")
	@ApiResponse(responseCode = "200",description = "Up and running")
	@ApiResponse(responseCode = "403", description = "Request forbidden")
	@GetMapping("/health-check")
	public ResponseEntity<String> healthCheck() {
		return new ResponseEntity<>("Ok", HttpStatus.OK);
	}

	/**
	 * Create Order on RazorPay Server
	 * 
	 * @param data object containing amount to be deducted
	 * @return string version of order created.
	 */
	@PostMapping("/order")
//	@ResponseBody
	@Operation(summary = "This is to create payment request on Razorpay ", description = "Multiple status values can be provided with comma seperated strings", responses = {
			@ApiResponse(responseCode = "200", description = "Order created successfully",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class))),
			@ApiResponse(responseCode = "400", description = "Request invalid"),
			@ApiResponse(responseCode = "403", description = "Invalid token supplied"),
			@ApiResponse(responseCode = "503", description = "Internal Server Error")})
	public ResponseEntity<OrderResponse> createOrder(@RequestHeader(name = "Authorization") String token,
			@RequestBody Map<String, Object> data) {
		Integer amount = Integer.parseInt(data.get("amount").toString());
		OrderResponse orderResponse = null;
		try {
			if (authorisationClient.validate(token)) {
				log.info("Starting Payment Process...");
				orderResponse = paymentService.createOrder(key_id, key_secret, amount);
			} else {
				return new ResponseEntity<>(new OrderResponse(), HttpStatus.FORBIDDEN);
			}
		} catch (RazorpayException ex) {
			return new ResponseEntity<>(new OrderResponse(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new OrderResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(orderResponse, HttpStatus.OK);
	}
}



















