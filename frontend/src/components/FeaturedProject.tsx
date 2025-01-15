import { Badge } from "@/components/ui/badge";
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Calendar, MessageSquare, Star } from "lucide-react";

import { Project } from "@/store/useStore";
import { Link } from "react-router";

function FeaturedProject({ id, name, tasks, createdAt }: Project) {
  return (
    <Link to={`/project/${id}`}>
      <Card className="group max-w-96 overflow-hidden transition-colors hover:bg-accent">
        <CardHeader>
          <CardTitle className="text-lg">{name}</CardTitle>
        </CardHeader>
        <CardContent>
          <p className="text-sm text-muted-foreground"></p>
        </CardContent>
        <CardFooter className="flex justify-between text-sm text-muted-foreground">
          <div className="flex items-center space-x-2">
            <Badge variant="secondary" className="flex items-center space-x-1">
              <Star className="h-3 w-3" />
              <span>
                {tasks.reduce(
                  (acc: number, task) => acc + (task.stars || 0),
                  0,
                )}
              </span>
            </Badge>
            <Badge variant="secondary" className="flex items-center space-x-1">
              <MessageSquare className="h-3 w-3" />
              <span>
                {tasks.reduce(
                  (acc, task) => acc + (task.taskComments?.length || 0),
                  0,
                )}
              </span>
            </Badge>
          </div>
          <div className="flex items-center space-x-1">
            <Calendar className="h-3 w-3" />
            <span>{new Date(createdAt).toLocaleDateString()}</span>
          </div>
        </CardFooter>
      </Card>
    </Link>
  );
}
export default FeaturedProject;
