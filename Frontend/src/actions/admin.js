import AuthorizationService from "../services/AuthorizationService";
import SurveyService from "../services/SurveyService";
import {
  ADMIN_SIGNUP_SUCCESSFULL,
  ALL_SURVEYS_FETCHED,
  ALL_USER_FETCHED,
  SET_SHOW_SURVEYS,
} from "./actionTypes";

export function adminSignupSuccessfull(user) {
  return {
    type: ADMIN_SIGNUP_SUCCESSFULL,
    user,
  };
}

export function fetchSurveysForAdmin() {
  return async (dispatch) => {
    SurveyService.getAllSurveys()
      .then((response) => response.data)
      .then((data) => {
        if (data.success == 1) {
          dispatch({ type: ALL_SURVEYS_FETCHED, payload: data.response });
        }
      });
  };
}

export function fetchAllUsers() {
  return async (dispatch) => {
    AuthorizationService.getUsers().then((response) => {
      console.log(response);
      dispatch({ type: ALL_USER_FETCHED, payload: response.data });
    });
  };
}

export function showSurveys(val) {
  return {
    type: SET_SHOW_SURVEYS,
    val,
  };
}
