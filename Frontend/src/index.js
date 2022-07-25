import React from "react";
import ReactDOM from "react-dom";
import { Provider } from "react-redux";
import "materialize-css/dist/css/materialize.min.css";
import "./style.css";
import reportWebVitals from "./reportWebVitals";

import axios from "axios";

import App from "./components/App";
import { configureStore } from "./store";
const store = configureStore();

window.axios = axios;

ReactDOM.render(
  <Provider store={store}>
    <App />
  </Provider>,
  document.getElementById("root")
);
reportWebVitals();
