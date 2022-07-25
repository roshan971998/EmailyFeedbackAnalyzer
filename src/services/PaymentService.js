import axios from "axios";
import { getAuthTokenFromLocalStorage } from "../utils/Utilityhelper";

const AUTH_API_BASE_URL = "/api/payment/razorpay";

class PaymentService {
  createOrder(amount) {
    return axios.post(
      `${AUTH_API_BASE_URL}/order`,
      {
        amount: amount,
        info: "order_info",
      },
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${getAuthTokenFromLocalStorage()}`,
        },
      }
    );
  }
}

export default new PaymentService();
