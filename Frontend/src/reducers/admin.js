import {
  ADMIN_SIGNUP_SUCCESSFULL,
  ALL_SURVEYS_FETCHED,
  SET_SHOW_SURVEYS,
  ALL_USER_FETCHED,
  LOG_OUT,
} from "../actions/actionTypes";

const initialAuthState = {
  user: {},
  userDetails: [],
  surveyDetails: [],
  error: null,
  isLoggedIn: false,
  inProgress: false,
  showSurveys: false,
};

export default function admin(state = initialAuthState, action) {
  switch (action.type) {
    case ADMIN_SIGNUP_SUCCESSFULL:
      return {
        ...state,
        user: action.user,
        isLoggedIn: true,
        inProgress: false,
      };
    case ALL_SURVEYS_FETCHED:
      return {
        ...state,
        surveyDetails: action.payload,
      };
    case ALL_USER_FETCHED:
      return {
        ...state,
        userDetails: action.payload,
      };
    case SET_SHOW_SURVEYS:
      return {
        ...state,
        showSurveys: action.val,
      };
    case LOG_OUT:
      return {
        ...state,
        user: {},
        userDetails: [],
        surveyDetails: [],
        isLoggedIn: false,
        error: null,
      };
    default:
      return state;
  }
}
