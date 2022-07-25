import React, { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useHistory } from "react-router-dom";
import { fetchUser, startFetchUser } from "../actions/auth";
import { getAuthTokenFromLocalStorage } from "../utils/Utilityhelper";

function Landing(props) {
  let history = useHistory();
  let dispatch = useDispatch();
  useEffect(() => {
    const sfetchUser = async () => {
      const token = getAuthTokenFromLocalStorage();
      if (token) {
        dispatch(startFetchUser());
        dispatch(fetchUser(token, history));
      }
    };
    sfetchUser();
  }, []);
  return (
    <div style={{ textAlign: "center" }}>
      <h1>Emaily!</h1>
      Collect feedback form your users
    </div>
  );
}

export default Landing;
