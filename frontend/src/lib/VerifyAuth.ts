export function VerifyAuth() {
  const token = sessionStorage.getItem("authToken");

  return token ? false : true;
}
