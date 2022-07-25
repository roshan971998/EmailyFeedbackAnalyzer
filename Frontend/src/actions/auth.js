import AuthorizationService from "../services/AuthorizationService";

import {
  FETCH_USER_START,
  FETCH_USER_SUCCESS,
  SIGNUP_SUCCESS,
  LOG_OUT,
  UPDATE_CREDIT,
  DECREASE_CREDIT,
} from "./actionTypes";
import { adminSignupSuccessfull } from "./admin";

export function login(username, password, history) {
  return async (dispatch) => {
    const data = {
      userName: username,
      password: password,
    };
    AuthorizationService.loginUser(JSON.stringify(data))
      .then((response) => response.data)
      .then((dataResponse) => {
        if (dataResponse && dataResponse.token) {
          localStorage.setItem("token", dataResponse.token);
          dispatch(fetchUser(dataResponse.token, history));
          // history.push("/surveys");
        }
      });
  };
}

export function signup(fullName, email, password, role, history) {
  return async (dispatch) => {
    const data = {
      userName: fullName,
      emailId: email,
      password: password,
      roleName: role,
    };
    AuthorizationService.saveUser(JSON.stringify(data))
      .then((response) => response.data)
      .then((dataResponse) => {
        if (dataResponse && dataResponse.token) {
          localStorage.setItem("token", dataResponse.token);
          dispatch(fetchUser(dataResponse.token, history));
        }
      });
  };
}

export function fetchUser(token, history) {
  return async (dispatch) => {
    AuthorizationService.getCurrentUser(token).then((response) => {
      localStorage.setItem("userId", response.data.userId);
      if (response.data.roleName === "Admin") {
        dispatch(adminSignupSuccessfull(response.data));
        history.push("/admin");
      } else {
        dispatch(signupSuccessfull(response.data));
        history.push("/surveys");
      }
    });
  };
}

export function signupSuccessfull(user) {
  return {
    type: SIGNUP_SUCCESS,
    user,
  };
}

export function logoutUser() {
  return {
    type: LOG_OUT,
  };
}

export function startFetchUser() {
  return {
    type: FETCH_USER_START,
  };
}

export function fetchUserSuccess(user) {
  return {
    type: FETCH_USER_SUCCESS,
    payload: user,
  };
}

export function updateUserCredit(userName, amount) {
  return async (dispatch) => {
    AuthorizationService.updateCredits(userName, amount).then((response) => {
      console.log(response);
      dispatch({ type: UPDATE_CREDIT, payload: amount });
    });
  };
}

export function decreaseUserCredit(userName, amount) {
  return async (dispatch) => {
    AuthorizationService.decreaseCredits(userName, amount).then((response) => {
      dispatch({ type: DECREASE_CREDIT, payload: amount });
    });
  };
}
