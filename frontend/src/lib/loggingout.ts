async function logout() {
  try {
    const sessionId = sessionStorage.getItem("authToken");
    console.log(sessionId);

    if (sessionId) {
      await fetch("http://localhost:3636/auth/logout", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          sessionId: sessionId,
        }),
      });
    }
  } finally {
    sessionStorage.removeItem("authToken");
    window.location.reload();
  }
}

export default logout;
