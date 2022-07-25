//SurveyformReview shows users their form inputs for review purpose
import { connect } from "react-redux";
import React, { Component, useState } from "react";
import FIELDS from "./formFields";
import { withRouter } from "react-router-dom";
import { submitSurvey } from "../../actions/survey";
import { decreaseUserCredit } from "../../actions/auth";

const SurveyFormReview = ({
  onCancel,
  formValues,
  dispatch,
  history,
  user,
}) => {
  const [errorMessage, setErrorMessage] = useState("");
  const reviewFields = FIELDS.map(({ name, label }) => {
    return (
      <div key={name}>
        <label>{label}</label>
        <div>{formValues[name]}</div>
      </div>
    );
  });

  return (
    <div>
      <h5>Please confirm your entries</h5>
      {reviewFields}
      <button className="yellow darken-3 btn-flat" onClick={onCancel}>
        Back
      </button>
      <button
        onClick={() => {
          if (user.credits >= 5) {
            setErrorMessage("");
            dispatch(submitSurvey(formValues, user.userId, history));
            dispatch(decreaseUserCredit(user.userName, 5));
          } else {
            setErrorMessage(
              "Unable to send Survey due to low Credit! Please have atleast RS:5 Credit"
            );
          }
        }}
        className="green btn-flat right"
      >
        Send Survey
        <i className="material-icons right">email</i>
      </button>
      {errorMessage !== "" && (
        <div class="col s12 m2">
          <p
            class="z-depth-5"
            style={{ margin: "20px 0", padding: "10px", color: "red" }}
          >
            {errorMessage}
          </p>
        </div>
      )}
    </div>
  );
};

function mapStateToProp(state) {
  return {
    formValues: state.form.surveyForm.values,
    user: state.auth.user,
  };
}

export default connect(mapStateToProp)(withRouter(SurveyFormReview));
