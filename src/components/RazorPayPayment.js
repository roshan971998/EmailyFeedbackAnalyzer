import React from "react";
import { connect } from "react-redux";
import PaymentService from "../services/PaymentService";
import { updateUserCredit } from "../actions/auth";

function loadScript(src) {
  return new Promise((resolve) => {
    const script = document.createElement("script");
    script.src = src;
    script.onload = () => {
      resolve(true);
    };
    script.onerror = () => {
      resolve(false);
    };
    document.body.appendChild(script);
  });
}
const __DEV__ = document.domain === "localhost";

function RazorPayPayment(props) {
  //const [name, setName] = useState("Emaily");

  async function displayRazorpay() {
    //1.loading checkout.js script
    const res = await loadScript(
      "https://checkout.razorpay.com/v1/checkout.js"
    );

    if (!res) {
      alert("Razorpay SDK failed to load. Are you online?");
      return;
    }
    //creating a new order
    const result = await PaymentService.createOrder(499);

    if (!result) {
      alert("Server error. Are you online?");
      return;
    }

    // Getting the order details back
    const { amount, id: order_id, currency } = result.data;
    const { userName } = props.user;
    console.log("token from razorpay:", props.user);

    const options = {
      key: __DEV__ ? "rzp_test_D826Lhf0xYsoAI" : "PRODUCTION_KEY",
      currency: currency,
      amount: amount.toString(),
      name: "Emaily",
      order_id: order_id,
      description: "Thank you for paying us money",
      image:
        "https://as1.ftcdn.net/v2/jpg/02/78/00/26/1000_F_278002600_vdqFzBNCRleoMWY3FDNWZoyrjFjpK6m5.jpg",
      handler: async function(response) {
        alert("payment Success");
        alert(response.razorpay_payment_id);
        const data = {
          orderCreationId: order_id,
          razorpayPaymentId: response.razorpay_payment_id,
          razorpayOrderId: response.razorpay_order_id,
          razorpaySignature: response.razorpay_signature,
        };
        console.log("razorpay user" + response);
        props.dispatch(updateUserCredit(userName, amount / 100));
      },
      prefill: {
        name: "Roshan Kumar",
        email: "sdfdsjfh2@ndsfdf.com",
        phone_number: "9899999999",
      },
      notes: {
        address: "Emaily Corporate Office",
      },
    };
    const paymentObject = new window.Razorpay(options);
    paymentObject.on("payment.failed", function(response) {
      console.log("failed response", response);
    });
    paymentObject.open();
  }

  return (
    <div>
      <button
        className="waves-effect waves-light btn"
        onClick={displayRazorpay}
        target="_blank"
        rel="noopener noreferrer"
        style={{ margin: "0 18px" }}
      >
        RazorPay
      </button>
    </div>
  );
}

function mapStateToProps({ auth }) {
  return {
    user: auth.user,
  };
}
export default connect(mapStateToProps)(RazorPayPayment);
