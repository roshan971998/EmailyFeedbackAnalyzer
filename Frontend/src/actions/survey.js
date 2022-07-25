import SurveyService from "../services/SurveyService";
import {
  FETCH_SURVEYS,
  SUBMIT_SURVEY,
  DELETE_SURVEY,
  RESPOND_SURVEY,
} from "./actionTypes";

export function fetchSurveys(userId, token) {
  return async (dispatch) => {
    if (token && userId) {
      SurveyService.getUserSurvey(userId, token)
        .then((response) => response.data)
        .then((data) => {
          if (data.success == 1) {
            dispatch({ type: FETCH_SURVEYS, payload: data.response });
          }
        });
    }
  };
}

export function submitSurvey(values, userId, history) {
  return async (dispatch) => {
    let arr = [];
    values.recipients.split(",").forEach((s) => {
      let obj = { email_id: "" };
      obj.email_id = obj.email_id + s;
      arr.push(obj);
    });
    const data = {
      Survey_title: values.title,
      Survey_body: values.body,
      Survey_subject: values.subject,
      Recipients_list: arr,
      Survey_userId: userId,
    };
    SurveyService.saveSurvey(JSON.stringify(data))
      .then((response) => response.data)
      .then((data) => {
        if (data.success == 1) {
          dispatch({ type: SUBMIT_SURVEY, payload: data.response });
          history.push("/surveys");
        }
      });
  };
}

export function deleteSurvey(userId, surveyId) {
  return async (dispatch) => {
    SurveyService.deleteSurvey(userId, surveyId).then((response) => {
      dispatch({ type: DELETE_SURVEY, payload: surveyId });
    });
  };
}

export function respondSurvey(userId, surveyId) {
  return async (dispatch) => {
    SurveyService.updateSurvey(userId, surveyId).then((response) => {
      console.log(response);
      dispatch({ type: RESPOND_SURVEY, payload: surveyId });
    });
  };
}
