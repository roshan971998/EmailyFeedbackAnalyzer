import { combineReducers } from "redux";
import { reducer as reduxForm } from "redux-form";
import admin from "./admin";
import auth from "./auth";
import surveysReducer from "./surveysReducer";

export default combineReducers({
  auth,
  form: reduxForm,
  surveys: surveysReducer,
  admin: admin,
});
