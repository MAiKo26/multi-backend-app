import { userInterface } from "@/interfaces/userInterface";
import { logOut } from "@/lib/auth-repo";

export async function fetchUserDetails(): Promise<userInterface> {
  const session = sessionStorage.getItem("authToken");
  const response = await fetch(
    "http://localhost:3636/users/bysession/" + session,
    {
      method: "GET",
      headers: { "Content-Type": "application/json" },
    },
  );

  const user = await response.json();

  if (!user) {
    logOut();
  }

  return user;
}

export async function fetchUsers(): Promise<userInterface[]> {
  const res = await fetch("http://localhost:3636/users");
  if (!res.ok) {
    throw new Error("Failed to fetch emails");
  }
  return res.json();
}
