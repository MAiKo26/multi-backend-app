import { userInterface } from "@/interfaces/userInterface";
import useStore from "@/store/useStore";
import { useEffect, useState } from "react";
import { Link } from "react-router";
import { Input } from "../ui/input";
import { SidebarMenuSubButton, SidebarMenuSubItem } from "../ui/sidebar";
import { Skeleton } from "../ui/skeleton";

function ChatSearch({ currentUser }: { currentUser: userInterface }) {
  const { allUsers, isLoading } = useStore();
  const [search, setSearch] = useState<string>("");
  const [filteredUsers, setFilteredUsers] = useState<userInterface[]>([]);

  useEffect(() => {
    const filtered = allUsers.filter(
      (user) =>
        user.email.toLowerCase().includes(search.toLowerCase()) &&
        user.email !== currentUser.email,
    );
    setFilteredUsers(filtered);
  }, [search, allUsers]);

  return (
    <div className="flex flex-col gap-2 pt-2">
      {isLoading.users ? (
        <Skeleton />
      ) : (
        <>
          <Input
            className="h-6 w-full"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />
          {filteredUsers.map((user, index) => {
            const sortedEmails = [currentUser.email, user.email]
              .sort()
              .join("");
            const hashedHref = Array.from(
              new Uint8Array(new TextEncoder().encode(sortedEmails)),
            )
              .map((b) => b.toString(16).padStart(2, "0"))
              .join("");
            return (
              <SidebarMenuSubItem key={index}>
                <SidebarMenuSubButton asChild>
                  <Link
                    to={"/chat/" + hashedHref}
                    className="flex w-full justify-between"
                  >
                    <span className="shrink truncate">{user.name}</span>
                    {user.online ? (
                      <div className="h-3 w-3 rounded-full bg-gray-600" />
                    ) : (
                      <div className="h-3 w-3 rounded-full bg-gray-600" />
                    )}
                  </Link>
                </SidebarMenuSubButton>
              </SidebarMenuSubItem>
            );
          })}
        </>
      )}
    </div>
  );
}
export default ChatSearch;
