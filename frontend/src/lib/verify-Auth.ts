export function verifyAuth() {
  const token = sessionStorage.getItem("authToken");

  return token ? false : true;
}
