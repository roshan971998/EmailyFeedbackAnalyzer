import React from "react";

function SurveyField(props) {
  const { input, label, meta } = props;
  const { error, touched } = meta;
  console.log(props);
  return (
    <div>
      <label>{label}</label>
      <input {...input} style={{ marginBottom: "1px" }} />
      <div className="red-text" style={{ marginBottom: "15px" }}>
        {touched && error}
      </div>
    </div>
  );
}

export default SurveyField;
