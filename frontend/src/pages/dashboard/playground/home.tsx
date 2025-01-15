import FeaturedTask from "@/components/FeaturedTask";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { verifyAuth } from "@/lib/auth-repo";
import useStore from "@/store/useStore";
import { Star } from "lucide-react";
import { Navigate } from "react-router";

function Home() {
  const { allUsers, tasks } = useStore();

  if (verifyAuth()) return <Navigate to="/auth/sign-in" />;

  return (
    <div className="container mx-auto p-6">
      <div className="grid gap-6 md:grid-cols-[2fr_1fr]">
        <Card>
          <CardHeader>
            <CardTitle>Featured Unfinished Tasks</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              {tasks
                .filter((task) => !task.finished)
                .slice(0, 3)
                .map((task) => (
                  <FeaturedTask key={task.id} {...task} />
                ))}
            </div>
          </CardContent>
        </Card>
        <Card>
          <CardHeader>
            <CardTitle>User Leaderboard</CardTitle>
          </CardHeader>
          <CardContent>
            <ul className="space-y-2">
              {allUsers.map((user) => (
                <li
                  key={user.email}
                  className="flex items-center justify-between rounded-lg bg-secondary p-2"
                >
                  <span className="font-medium">{user.name}</span>
                  <span className="flex items-center gap-1">
                    <span className="font-bold">{user.stars || 0}</span>
                    <Star className="h-4 w-4 fill-yellow-400 text-yellow-400" />
                  </span>
                </li>
              ))}
            </ul>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
export default Home;
