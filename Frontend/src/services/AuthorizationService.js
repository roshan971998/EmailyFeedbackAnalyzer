import axios from "axios";
import {
  getAuthTokenFromLocalStorage,
  getUserNameFromToken,
} from "../utils/Utilityhelper";

const AUTH_API_BASE_URL = "/api/auth/user";

class AuthorizationService {
  saveUser(user) {
    return axios.post(`${AUTH_API_BASE_URL}/register`, user, {
      headers: {
        "Content-Type": "application/json",
      },
    });
  }

  getCurrentUser(token) {
    return axios.get(`${AUTH_API_BASE_URL}/${getUserNameFromToken(token)}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
  }

  loginUser(user) {
    return axios.post(`${AUTH_API_BASE_URL}/login`, user, {
      headers: {
        "Content-Type": "application/json",
      },
    });
  }

  getUsers() {
    return axios.get(AUTH_API_BASE_URL, {
      headers: {
        Authorization: `Bearer ${getAuthTokenFromLocalStorage()}`,
      },
    });
  }
  updateCredits(userName, amount) {
    return axios.put(
      `${AUTH_API_BASE_URL}/${userName}/${amount.toString()}`,
      {},
      {
        headers: {
          Authorization: `Bearer ${getAuthTokenFromLocalStorage()}`,
        },
      }
    );
  }

  decreaseCredits(userName, amount) {
    return axios.put(
      `${AUTH_API_BASE_URL}/balance/${userName}/${amount.toString()}`,
      {},
      {
        headers: {
          Authorization: `Bearer ${getAuthTokenFromLocalStorage()}`,
        },
      }
    );
  }
}

export default new AuthorizationService();
