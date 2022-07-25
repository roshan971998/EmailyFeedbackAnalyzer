import React, { Component } from "react";
import { connect } from "react-redux";
import { Link, withRouter } from "react-router-dom";
import { removeAuthTokenFromLocalStorage } from "../utils/Utilityhelper";
import RazorPayPayment from "./RazorPayPayment";
import { logoutUser } from "../actions/auth";

class Header extends Component {
  logOut = (e) => {
    e.preventDefault();
    removeAuthTokenFromLocalStorage();
    this.props.dispatch(logoutUser());
    // this.props.history.push("/");
  };
  render() {
    const { auth, admin } = this.props;
    return (
      <nav>
        <div className="nav-wrapper">
          <li className="brand-logo" style={{ margin: "0 10px" }}>
            <Link
              to={
                auth.isLoggedIn ? "/surveys" : admin.isLoggedIn ? "/admin" : "/"
              }
            >
              Emaily
            </Link>
          </li>
          <ul id="nav-mobile" className="right">
            {!auth.isLoggedIn &&
              !admin.isLoggedIn && [
                <li key="a">
                  <Link to="/login">Login</Link>
                </li>,
                <li key="b">
                  <Link to="/signup">Signup</Link>
                </li>,
              ]}
            {auth.isLoggedIn &&
              !admin.isLoggedIn && [
                <li key="4">
                  <form>
                    <script
                      src="https://checkout.razorpay.com/v1/payment-button.js"
                      data-payment_button_id="pl_Itmr5PyaDMgixP"
                      async
                    ></script>
                  </form>
                </li>,
                <li key="5">
                  <RazorPayPayment />
                </li>,
                <li key="3" style={{ margin: "0 20px" }}>
                  Credits{" " + this.props.auth.user.credits}
                </li>,
                <li key="2" onClick={this.logOut} style={{ margin: "0 20px" }}>
                  <Link to="/">Log out</Link>
                </li>,
                <li key="1" style={{ margin: "0 20px" }}>
                  {" " + this.props.auth.user.userName.toUpperCase()}
                </li>,
              ]}
            {!auth.isLoggedIn &&
              admin.isLoggedIn && [
                <li key="1" style={{ margin: "0 20px" }}>
                  {" " + this.props.admin.user.userName.toUpperCase()}
                </li>,
                <li key="b" onClick={this.logOut} style={{ margin: "0 20px" }}>
                  <Link to="/">Log out</Link>
                </li>,
              ]}
          </ul>
        </div>
      </nav>
    );
  }
}
function mapStateToProps({ auth, admin }) {
  return {
    auth,
    admin,
  };
}
export default connect(mapStateToProps)(withRouter(Header));
