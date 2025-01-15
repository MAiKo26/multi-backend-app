import { userInterface } from "@/interfaces/userInterface";
import { logOut } from "@/lib/auth-repo";

export async function fetchUserDetails(): Promise<userInterface> {
  const session = sessionStorage.getItem("authToken");

  if (!session) {
    await logOut();
    return Promise.reject("No session found");
  }

  const response = await fetch(
    "http://localhost:3636/users/bysession/" + session,
    {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${session}`,
      },
    },
  );

  const user = await response.json();

  const status = await response.status;

  if (!user || status !== 200) {
    logOut();
  }

  return user;
}

export async function fetchUsers(token: string): Promise<userInterface[]> {
  const res = await fetch("http://localhost:3636/users", {
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
  });
  if (!res.ok) {
    throw new Error("Failed to fetch emails");
  }
  return res.json();
}
