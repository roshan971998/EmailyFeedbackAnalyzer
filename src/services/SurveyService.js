import axios from "axios";
import { getAuthTokenFromLocalStorage } from "../utils/Utilityhelper";

const AUTH_API_BASE_URL = "/api/survey/surveys";

class SurveyService {
  saveSurvey(survey) {
    return axios.post(AUTH_API_BASE_URL, survey, {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${getAuthTokenFromLocalStorage()}`,
      },
    });
  }
  updateSurvey(userId, surveyId) {
    return axios.put(
      `${AUTH_API_BASE_URL}/${userId}/${surveyId}`,
      {},
      {
        headers: {
          Authorization: `Bearer ${getAuthTokenFromLocalStorage()}`,
        },
      }
    );
  }

  deleteSurvey(userId, surveyId) {
    return axios.delete(
      `${AUTH_API_BASE_URL}/${parseInt(userId)}/${parseInt(surveyId)}`,
      {
        headers: {
          Authorization: `Bearer ${getAuthTokenFromLocalStorage()}`,
        },
      }
    );
  }

  getUserSurvey(userId, token) {
    return axios.get(`${AUTH_API_BASE_URL}/${userId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
  }

  getSurveyBydate(date, token) {
    return axios.get(`${AUTH_API_BASE_URL}/date/${date}`, {
      headers: {
        Authorization: `Bearer ${getAuthTokenFromLocalStorage()}`,
      },
    });
  }

  getAllSurveys() {
    return axios.get(`${AUTH_API_BASE_URL}`, {
      headers: {
        Authorization: `Bearer ${getAuthTokenFromLocalStorage()}`,
      },
    });
  }
}

export default new SurveyService();
