//SurveyForm shows a form for a user to add input
import React, { Component } from "react";
import { Link } from "react-router-dom";
import { reduxForm, Field } from "redux-form";
import SurveyField from "./SurveyField";

import validateEmails from "../../utils/validateEmails";
import FIELDS from "./formFields";

class SurveyForm extends Component {
  renderFields() {
    return FIELDS.map(({ label, name }, index) => {
      return (
        <Field
          key={index}
          component={SurveyField}
          type="text"
          label={label}
          name={name}
        />
      );
    });
  }
  render() {
    return (
      <div>
        <form
          onSubmit={this.props.handleSubmit((values) => {
            //console.log(values);
            this.props.onSurveySubmit();
          })}
        >
          {this.renderFields()}

          <Link to="/surveys" className="red btn-flat left white-text">
            Cancel
          </Link>
          <button type="submit" className="teal btn-flat right white-text">
            Next
            <i className="material-icons right">done</i>
          </button>
        </form>
      </div>
    );
  }
}

function validate(values) {
  const errors = {};

  errors.recipients = validateEmails(values.recipients || "");

  FIELDS.forEach(({ name }) => {
    if (!values[name]) {
      errors[name] = `you must provide a ${name}`;
    }
  });

  return errors;
}

export default reduxForm({
  form: "surveyForm", //here the value of form will become the key of form reducer state object
  validate,
  destroyOnUnmount: false,
})(SurveyForm);
