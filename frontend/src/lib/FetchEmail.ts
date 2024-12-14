export async function fetchEmails(): Promise<string[]> {
  const res = await fetch("http://localhost:3636/emails");
  if (!res.ok) {
    throw new Error("Failed to fetch emails");
  }
  return res.json();
}
