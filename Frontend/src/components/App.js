import React from "react";
import { connect } from "react-redux";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import {
  Header,
  Dashboard,
  SurveyNew,
  Landing,
  LogIn,
  Signup,
  Admin,
  Feedback,
  Response,
} from "./index";

//materialize css assumes our toplevel or root  component where we render everything to have className as container
class App extends React.Component {
  render() {
    return (
      <div className="container">
        <BrowserRouter>
          <div>
            <Header />
            <Switch>
              <Route exact path="/" component={Landing} />
              <Route exact path="/surveys" component={Dashboard} />
              <Route path="/surveys/new" component={SurveyNew} />
              <Route exact path="/admin" component={Admin} />
              <Route exact path="/login" component={LogIn} />
              <Route exact path="/signup" component={Signup} />
              <Route exact path="/feedback" component={Feedback} />
              <Route exact path="/response" component={Response} />
            </Switch>
          </div>
        </BrowserRouter>
      </div>
    );
  }
}

function mapStateToProps(state) {
  return {
    auth: state.auth,
  };
}

export default connect(mapStateToProps)(App);
