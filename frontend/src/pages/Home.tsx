import { fetchEmails } from "@/lib/FetchEmail";
import { VerifyAuth } from "@/lib/VerifyAuth";
import { useEffect, useState } from "react";
import { Navigate } from "react-router";

function Home() {
  const [emails, setEmails] = useState<string[]>([]);
  useEffect(() => {
    async function fetching() {
      const emails = await fetchEmails();
      setEmails(emails);
    }
    fetching();
  }, []);
  if (VerifyAuth()) return <Navigate to="/auth/login" />;

  return (
    <div className="flex h-full flex-col gap-10 p-10">
      {emails.map((email) => (
        <div>{email}</div>
      ))}
    </div>
  );
}
export default Home;
