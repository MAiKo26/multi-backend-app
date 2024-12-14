import { userInterface } from "@/interfaces/userInterface";

export async function fetchUsers(): Promise<userInterface[]> {
  const res = await fetch("http://localhost:3636/users");
  if (!res.ok) {
    throw new Error("Failed to fetch emails");
  }
  return res.json();
}
