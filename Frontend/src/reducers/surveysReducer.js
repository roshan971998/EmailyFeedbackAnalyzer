import {
  FETCH_SURVEYS,
  SUBMIT_SURVEY,
  DELETE_SURVEY,
  RESPOND_SURVEY,
} from "../actions/actionTypes";

export default function surveysReducer(state = [], action) {
  switch (action.type) {
    case FETCH_SURVEYS:
      return [...action.payload, ...state].filter(
        (value, index, self) =>
          index === self.findIndex((t) => t.id === value.id)
      );
    case SUBMIT_SURVEY:
      return [action.payload, ...state].filter(
        (value, index, self) =>
          index === self.findIndex((t) => t.id === value.id)
      );
    case DELETE_SURVEY:
      return [...state].filter((item) => item.id !== action.payload);
    case RESPOND_SURVEY:
      let arr = [...state];
      arr.forEach((item) => {
        if (item.id === action.payload) item.responded = true;
      });
      return arr;
    default:
      return state;
  }
}
