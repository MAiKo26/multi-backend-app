import { userInterface } from "@/interfaces/userInterface";
import { fetchUsers } from "@/lib/user-repo";
import { formatDate } from "@/lib/utils";
import { verifyAuth } from "@/lib/auth-repo";
import { useEffect, useState } from "react";
import { Navigate } from "react-router";

function Home() {
  const [users, setUsers] = useState<userInterface[]>([]);
  useEffect(() => {
    async function fetching() {
      const usersFetched = await fetchUsers();
      setUsers(usersFetched);
    }
    fetching();
  }, []);
  if (verifyAuth()) return <Navigate to="/auth/sign-in" />;

  return (
    <div className="flex h-full flex-col gap-10 p-10">
      {users.map((user, index) => (
        <div key={index}>
          <div>{user.email}</div>
          <div>{user.name}</div>
          <div>{user.phoneNumber}</div>
          <div>{user.role}</div>
          <div>{user.avatar}</div>
          <div>{formatDate(user.lastLogin)}</div>
        </div>
      ))}
    </div>
  );
}
export default Home;
