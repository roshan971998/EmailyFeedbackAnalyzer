import React from "react";
import { Link } from "react-router-dom";
class Feedback extends React.Component {
  render() {
    return (
      <div>
        <h1>Welcome to the Feedback System</h1>
        <form>
          <label for="name">name:</label>
          <input type="text" id="name" name="name" />
          <br />
          <br />
          <label for="emial">Enter your EmailId:</label>
          <input type="email" id="email" name="email" />
          <br />
          <br />
          <h4>How do you like our service....</h4>
          {/* <a
            class="waves-effect waves-light btn"
            style={{ margin: "5px 15px 0 0" }}
          >
            Like
          </a>
          <a
            class="waves-effect waves-light btn"
            style={{ margin: "5px 15px 0px 10px" }}
          >
            Dislike
          </a> */}

          <textarea id="w3review" name="w3review" rows="8" cols="50" />
          <br />
          <br />
          <br />
          <br />
          <Link to="/response">
            <button
              class="btn waves-effect waves-light"
              type="submit"
              name="action"
            >
              Submit
              <i class="material-icons right">send</i>
            </button>
          </Link>
        </form>
      </div>
    );
  }
}

export default Feedback;
