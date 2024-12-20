import { userInterface } from "@/interfaces/userInterface";
import { Input } from "../ui/input";
import { useEffect, useState } from "react";
import { fetchUsers } from "@/lib/user-repo";
import { Skeleton } from "../ui/skeleton";
import { SidebarMenuSubButton, SidebarMenuSubItem } from "../ui/sidebar";

function ChatSearch({ user }: { user: userInterface | null }) {
  const [allUsers, setAllUsers] = useState<userInterface[]>([]);
  const [search, setSearch] = useState<string>("");
  const [filteredUsers, setFilteredUsers] = useState<userInterface[]>([]);
  const [loading, setLoading] = useState<boolean>(false);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      const users = await fetchUsers();
      setAllUsers(users);
      console.log(allUsers);

      setLoading(false);
    };

    fetchData();
  }, []);
  return (
    <div>
      <div>{loading ? <Skeleton /> : <Input />}</div>
      {allUsers.map((user, index) => (
        <SidebarMenuSubItem key={index}>
          <SidebarMenuSubButton asChild>
            <a href={user.email}>
              <span>{user.email}</span>
            </a>
          </SidebarMenuSubButton>
        </SidebarMenuSubItem>
      ))}
    </div>
  );
}
export default ChatSearch;
