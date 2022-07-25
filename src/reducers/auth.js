import {
  FETCH_USER_START,
  FETCH_USER_SUCCESS,
  SIGNUP_SUCCESS,
  LOG_OUT,
  SIGNIN_SUCCESS,
  UPDATE_CREDIT,
  DECREASE_CREDIT,
} from "../actions/actionTypes";

const initialAuthState = {
  user: {},
  error: null,
  isLoggedIn: false,
  inProgress: false,
};

export default function auth(state = initialAuthState, action) {
  switch (action.type) {
    case FETCH_USER_START:
      return {
        ...state,
        inProgress: true,
      };
    case SIGNUP_SUCCESS:
    case FETCH_USER_SUCCESS:
      return {
        ...state,
        user: action.user || {},
        inProgress: false,
        isLoggedIn: true,
      };
    case LOG_OUT:
      return {
        ...state,
        user: {},
        isLoggedIn: false,
        error: null,
      };
    case SIGNIN_SUCCESS:
      return {
        ...state,
        isLoggedIn: true,
      };
    case UPDATE_CREDIT:
      return {
        ...state,
        user: {
          ...state.user,
          credits: state.user.credits + action.payload,
        },
      };
    case DECREASE_CREDIT:
      return {
        ...state,
        user: {
          ...state.user,
          credits: state.user.credits - action.payload,
        },
      };
    default:
      return state;
  }
}
