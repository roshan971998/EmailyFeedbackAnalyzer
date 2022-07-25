import React from "react";
import Select from "react-select";
import { useForm, Controller } from "react-hook-form";
import { connect, useDispatch } from "react-redux";
import { signup } from "../actions/auth";
const Signup = (props) => {
  const {
    register,
    handleSubmit,
    watch,
    control,
    formState: { errors },
  } = useForm();
  const dispatch = useDispatch();
  const onSubmit = (data) => {
    const {
      firstname,
      lastname,
      email,
      password,
      ROLE_TYPE: { value },
    } = data;
    let role = value === "ADMIN" ? "ADMIN" : "USER";
    const fullName = firstname + "" + lastname;
    if (fullName && email && password && value) {
      dispatch(signup(fullName, email, password, role, props.history));
    }
  };

  return (
    <>
      <form onSubmit={handleSubmit(onSubmit)}>
        <h3>Sign Up</h3>
        <div className="mb-3">
          <label>First name</label>
          <input
            type="text"
            className="form-control"
            placeholder="First name"
            {...register("firstname", {
              required: "First Name is required", // JS only: <p>error message</p> TS only support string
            })}
          />
          {errors.firstname && (
            <small className="text-danger">{errors.firstname.message}</small>
          )}
        </div>
        <div className="mb-3">
          <label>Last name</label>
          <input
            type="text"
            className="form-control"
            placeholder="Last name"
            {...register("lastname", {
              required: "Last Name is required", // JS only: <p>error message</p> TS only support string
            })}
          />
          {errors.lastname && (
            <small className="text-danger">{errors.lastname.message}</small>
          )}
        </div>
        <div className="mb-3">
          <label>Email address</label>
          <input
            type="email"
            className="form-control"
            placeholder="Enter email"
            {...register("email", {
              required: "email is required", // JS only: <p>error message</p> TS only support string
            })}
          />
          {errors.email && (
            <small className="text-danger">{errors.email.message}</small>
          )}
        </div>
        <div className="mb-3">
          <label>Password</label>
          <input
            type="password"
            className="form-control"
            placeholder="Enter password"
            {...register("password", {
              required: "Password is required", // JS only: <p>error message</p> TS only support string
            })}
          />
          {errors.password && (
            <small className="text-danger">{errors.password.message}</small>
          )}
        </div>
        <div className="mb-3">
          <label>Role</label>
          <Controller
            name="ROLE_TYPE"
            render={({ field }) => (
              <Select
                {...field}
                options={[
                  { value: "ADMIN", label: "Admin" },
                  { value: "USER", label: "User" },
                ]}
              />
            )}
            control={control}
            defaultValue=""
          />
        </div>
        <div className="d-grid">
          <button type="submit" className="btn btn-primary">
            Sign Up
          </button>
        </div>
        <p className="forgot-password text-right">
          Already registered <a href="/login">sign in?</a>
        </p>
      </form>
    </>
  );
};
const mapStateToProps = ({ auth }) => ({
  auth,
});

export default connect(mapStateToProps)(Signup);
