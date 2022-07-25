import React from "react";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import {
  fetchAllUsers,
  fetchSurveysForAdmin,
  showSurveys,
} from "../actions/admin";
import { fetchUser } from "../actions/auth";
import { getAuthTokenFromLocalStorage } from "../utils/Utilityhelper";

class Admin extends React.Component {
  componentDidMount() {
    let token = getAuthTokenFromLocalStorage();
    if (token) {
      const { dispatch, history } = this.props;
      const userId =
        this.props.admin.user.userId || localStorage.getItem("userId");
      dispatch(fetchUser(token, history));
      dispatch(fetchSurveysForAdmin());
      dispatch(fetchAllUsers());
    }
  }

  changeTab = (val) => {
    this.props.dispatch(showSurveys(val));
  };

  renderSurveys(surveyDetails) {
    return surveyDetails.map((survey) => {
      return (
        <div className="card darken-1" key={survey.Survey_userId}>
          <div className="card-content">
            <span className="card-title">{survey.Survey_title}</span>
            <p>{survey.body}</p>
            <p className="right">
              Sent On: {new Date(survey.date_sent).toLocaleDateString()}
            </p>
          </div>
        </div>
      );
    });
  }

  renderUsers(userDetails) {
    return userDetails.map((user) => {
      return (
        <div className="card darken-1" key={user.userId}>
          <div className="card-content">
            <span className="card-title">{user.userName}</span>
            <p>{user.emailId}</p>
            <p className="right">{"Total credits " + user.credits}</p>
          </div>
        </div>
      );
    });
  }

  render() {
    const { showSurveys, surveyDetails, userDetails } = this.props.admin;
    return (
      <div className="row">
        <div className="col s12">
          {" "}
          <h3>WELCOME TO THE ADMIN DASHBOARD</h3>
        </div>
        <div className="col s12" style={{ margin: "10px 0" }}>
          <ul className="tabs">
            <li
              className={`tab  col s3 ${
                showSurveys ? "" : "waves-effect waves-light btn-small"
              }`}
              onClick={() => this.changeTab(false)}
            >
              Users
            </li>
            <li
              className={`tab col s3 ${
                showSurveys ? "waves-effect waves-light btn-small" : ""
              }`}
              onClick={() => this.changeTab(true)}
            >
              Surveys
            </li>
          </ul>
        </div>

        <div id="list" className="col s12">
          {showSurveys ? (
            <div>
              <div>
                <div className=".left"></div>
                <div className=".right">
                  <form action="https://example.com">
                    <label>
                      Enter the date:
                      <input type="date" name="bday" />
                    </label>
                    <p>
                      <button>Submit</button>
                    </p>
                  </form>
                </div>
              </div>
              {this.renderSurveys(surveyDetails)}
            </div>
          ) : (
            this.renderUsers(userDetails)
          )}
        </div>
      </div>
    );
  }
}
function mapStateToProps({ admin }) {
  return {
    admin,
  };
}
export default connect(mapStateToProps)(withRouter(Admin));
