import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Star, MessageSquare, Calendar } from "lucide-react";

import { Task } from "@/store/useStore";
import { Link } from "react-router";

function FeaturedTask({
  stars,
  taskComments,
  createdAt,
  description,
  name,
  id,
  projectId,
}: Task) {
  return (
    <Link to={`/projects/${projectId}/${id}`}>
      <Card className="group overflow-hidden transition-colors hover:bg-accent">
        <CardHeader>
          <CardTitle className="text-lg">{name}</CardTitle>
        </CardHeader>
        <CardContent>
          <p className="text-sm text-muted-foreground">{description}</p>
        </CardContent>
        <CardFooter className="flex justify-between text-sm text-muted-foreground">
          <div className="flex items-center space-x-2">
            <Badge variant="secondary" className="flex items-center space-x-1">
              <Star className="h-3 w-3" />
              <span>{stars}</span>
            </Badge>
            <Badge variant="secondary" className="flex items-center space-x-1">
              <MessageSquare className="h-3 w-3" />
              <span>{taskComments ? taskComments.length : 0}</span>
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
export default FeaturedTask;
