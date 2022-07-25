import React, { Component } from "react";
import { connect } from "react-redux";
import {
  fetchSurveys,
  deleteSurvey,
  respondSurvey,
} from "../../actions/survey";
import { getAuthTokenFromLocalStorage } from "../../utils/Utilityhelper";

class SurveyList extends Component {
  componentDidMount() {
    const token = getAuthTokenFromLocalStorage();
    if (token) {
      const userId =
        this.props.auth.user.userId || localStorage.getItem("userId");
      this.props.dispatch(fetchSurveys(userId, token));
    }
  }

  renderSurveys() {
    const userid = this.props.auth.user.userId;
    const { dispatch } = this.props;
    return this.props.surveys.map((survey) => {
      return (
        <div className="card darken-1" key={survey.Survey_userId}>
          <div className="card-content">
            <span className="card-title">{survey.Survey_title}</span>
            <p>{survey.body}</p>
            <p className="right">
              Sent On: {new Date(survey.date_sent).toLocaleDateString()}
            </p>
          </div>
          <div className="card-action">
            <a
              className="waves-effect waves-light btn-small"
              style={{ margin: "0 10px 0 0" }}
            >
              Recipients
            </a>
            <a
              onClick={() => dispatch(respondSurvey(userid, survey.id))}
              className="waves-effect waves-light btn-small"
              style={{ margin: "0 10px 0 0" }}
            >
              {!survey.responded && "Respond"}
              {survey.responded && "Responded"}
            </a>
            <a
              onClick={() => dispatch(deleteSurvey(userid, survey.id))}
              className="waves-effect waves-light btn-small  red darken-4"
            >
              Delete
            </a>
          </div>
        </div>
      );
    });
  }

  render() {
    return <div>{this.renderSurveys()}</div>;
  }
}

function mapStateToProps(state) {
  return {
    surveys: state.surveys,
    auth: state.auth,
  };
}
export default connect(mapStateToProps)(SurveyList);
