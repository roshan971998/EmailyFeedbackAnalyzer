package com.cts.paymentmicroservice.dto;

import com.razorpay.Order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * POJO class for Order response
 * 
 */
//
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Model class for Order response")
public class OrderResponse {

	@Schema(description = "The amount for which the order was created, in currency subunits. For example, for an amount of ₹295, enter 29500")
	private Integer amount;
	
	@Schema(description = "The currency associated with the order's amount. The default length is 3 characters")
	private String currency;
	
	@Schema(description = "The unique identifier of the order.")
	private String id;
	
	@Schema(description = " The amount paid against the order.")
	private Integer amount_paid;
	
	@Schema(description = " The amount pending against the order.")
	private Integer amount_due;
	
	@Schema(description = " Receipt number that corresponds to this order. Can have a maximum length of 40 characters and has to be unique.")
	private String receipt;
	
	@Schema(description = "The status of the order. Possible values:"
			+ "created: When you create an order it is in the created state. It stays in this state till a payment is attempted on it."
			+ "attempted: An order moves from created to attempted state when a payment is first attempted on it. It remains in the "
			+ "attempted state till one payment associated with that order is captured."
			+ "paid: After the successful capture of the payment, the order moves to the paid state. No further payment requests "
			+ "are permitted once the order moves to the paid state. The order stays in the paid state even if the payment "
			+ "associated with the order is refunded.")
	private String status;
	
	@Schema(description = " The number of payment attempts, successful and failed, that have been made against this order.")
	private Integer attempts;
	
	@Schema(description = " Indicates the Unix timestamp when this order was created.")
	private Integer created_at;
	
	public OrderResponse(Order order) {
		this.amount =(Integer) order.get("amount");
		this.currency = order.get("currency").toString();
		this.id = order.get("id").toString();
		this.amount_paid=(Integer) order.get("amount_paid");
		this.amount_due=(Integer) order.get("amount_due");
		this.attempts=(Integer) order.get("attempts");
		this.receipt=order.get("receipt").toString();
		this.status=order.get("status").toString();
		this.created_at=order.get("created_at");
		
	}
}
//{
//"amount": 49900,
//"amount_paid": 0,
//"notes": [],
//"created_at": 1657117441,
//"amount_due": 49900,
//"currency": "INR",
//"receipt": "txn_123456",
//"id": "order_Jq714K2sILzOtb",
//"entity": "order",
//"offer_id": null,
//"status": "created",
//"attempts": 0
//}

//https://razorpay.com/docs/api/orders/