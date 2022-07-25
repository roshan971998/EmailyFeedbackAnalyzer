export function getAuthTokenFromLocalStorage() {
  if (localStorage.getItem("token")) {
    return localStorage.getItem("token");
  }
  return null;
}

export function removeAuthTokenFromLocalStorage() {
  if (localStorage.getItem("token")) {
    localStorage.removeItem("token");
    localStorage.removeItem("userId");
    localStorage.removeItem("rzp_device_id");
  }
}

export function getUserNameFromToken(token) {
  if (token) {
    var base64Url = token.split(".")[1];
    var decodedValue = JSON.parse(window.atob(base64Url));
    return decodedValue.sub;
  }
  return "";
}
