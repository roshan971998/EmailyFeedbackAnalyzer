//SurveyNew shows SurveyForm and SurveyFormReview
import React, { Component } from "react";
import { reduxForm } from "redux-form";
import SurveyForm from "./SurveyForm";
import SurveyFormReview from "./SurveyFormReview";

class SurveyNew extends Component {
  constructor() {
    super();
    this.state = { showFormReview: false };
  }

  render() {
    return (
      <div className="container">
        {this.state.showFormReview ? (
          <SurveyFormReview
            onCancel={() => this.setState({ showFormReview: false })}
          />
        ) : (
          <SurveyForm
            onSurveySubmit={() => this.setState({ showFormReview: true })}
          />
        )}
      </div>
    );
  }
}

export default reduxForm({
  form: "surveyForm",
})(SurveyNew);
