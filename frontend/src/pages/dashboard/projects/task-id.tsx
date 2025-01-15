"use client";

import { useState } from "react";
import { useParams } from "react-router";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { ScrollArea } from "@/components/ui/scroll-area";
import { Textarea } from "@/components/ui/textarea";
import { formatDate } from "@/lib/utils";
import { NotFound } from "@/pages/not-found";
import useStore, { Task, TaskComments } from "@/store/useStore";
import {
  Calendar,
  CheckCircle,
  MessageSquare,
  Send,
  Star,
  XCircle,
  Loader2,
} from "lucide-react";

// Sub-components
const TaskHeader = ({
  task,
  isStarred,
  isLoading,
  onToggleStar,
  onToggleComplete,
}: {
  task: Task;
  isStarred: boolean;
  isLoading: boolean;
  onToggleStar: () => void;
  onToggleComplete: () => void;
}) => (
  <div className="flex items-center justify-between">
    <CardTitle className="text-2xl">{task.name}</CardTitle>
    <div className="flex items-center gap-2">
      <Button
        variant="ghost"
        size="icon"
        onClick={onToggleStar}
        className={isStarred ? "text-yellow-400" : ""}
        disabled={isLoading}
        aria-label={isStarred ? "Unstar task" : "Star task"}
      >
        {isLoading ? (
          <Loader2 className="h-4 w-4 animate-spin" />
        ) : (
          <Star className={isStarred ? "fill-current" : ""} />
        )}
      </Button>
      <Button
        variant="ghost"
        size="icon"
        onClick={onToggleComplete}
        disabled={isLoading}
        aria-label={task.finished ? "Mark as incomplete" : "Mark as complete"}
      >
        {isLoading ? (
          <Loader2 className="h-4 w-4 animate-spin" />
        ) : task.finished ? (
          <CheckCircle className="text-green-500" />
        ) : (
          <XCircle className="text-gray-400" />
        )}
      </Button>
    </div>
  </div>
);

const TaskMetadata = ({
  task,
  commentsCount,
}: {
  task: Task;
  commentsCount: number;
}) => (
  <div className="flex flex-wrap gap-2 text-sm text-muted-foreground">
    <Badge variant="secondary" className="flex items-center gap-1">
      <Calendar className="h-4 w-4" />
      {formatDate(task.createdAt)}
    </Badge>
    <Badge variant="secondary" className="flex items-center gap-1">
      <Star className="h-4 w-4" />
      {task.stars}
    </Badge>
    <Badge variant="secondary" className="flex items-center gap-1">
      <MessageSquare className="h-4 w-4" />
      {commentsCount}
    </Badge>
    {task.finished && (
      <Badge variant="secondary">Completed by {task.finishedBy}</Badge>
    )}
  </div>
);

const CommentItem = ({ comment }: { comment: TaskComments }) => {
  const { allUsers } = useStore();
  const user = allUsers.find((user) => user.email === comment.id);
  return (
    <div className="flex gap-4">
      <Avatar className="h-8 w-8">
        <AvatarImage src="/placeholder-avatar.jpg" alt="User avatar" />
        <AvatarFallback>U</AvatarFallback>
      </Avatar>
      <div className="grid gap-1">
        <div className="flex items-center gap-2">
          <span className="font-semibold">{user?.name || "Username"}</span>
          <span className="text-sm text-muted-foreground">
            {formatDate(comment.createdAt)}
          </span>
        </div>
        <p className="text-sm">{comment.content}</p>
      </div>
    </div>
  );
};

const CommentsList = ({ comments }: { comments: TaskComments[] }) => (
  <ScrollArea className="h-[calc(100vh-20rem)] pr-4">
    <div className="space-y-4">
      {comments.map((comment) => (
        <CommentItem key={comment.id} comment={comment} />
      ))}
    </div>
  </ScrollArea>
);

const CommentInput = ({
  value,
  onChange,
  onSubmit,
  isLoading,
}: {
  value: string;
  onChange: (value: string) => void;
  onSubmit: () => void;
  isLoading: boolean;
}) => (
  <div className="flex w-full gap-2">
    <Textarea
      placeholder="Add a comment... (max 500 characters)"
      value={value}
      onChange={(e) => onChange(e.target.value)}
      className="flex-1"
      maxLength={500}
      disabled={isLoading}
    />
    <Button
      size="icon"
      onClick={onSubmit}
      disabled={isLoading || !value.trim()}
      aria-label="Send comment"
    >
      {isLoading ? (
        <Loader2 className="h-4 w-4 animate-spin" />
      ) : (
        <Send className="h-4 w-4" />
      )}
    </Button>
  </div>
);

// Main component
export default function TaskDetails() {
  const { tasks, currentUser } = useStore();
  const { taskId } = useParams();

  const task = tasks.find((task) => task.id === taskId);

  const [isLoading, setIsLoading] = useState({
    star: false,
    complete: false,
    comment: false,
  });
  const [error, setError] = useState<string | null>(null);
  const [isStarred, setIsStarred] = useState(
    task?.starredTasks.some((star) => star.userId === currentUser?.email) ??
      false,
  );
  const [newComment, setNewComment] = useState("");
  const [comments, setComments] = useState<TaskComments[]>(
    task?.taskComments ?? [],
  );

  if (!task) return <NotFound />;
  if (!currentUser) return <div>Please log in to view task details.</div>;

  const handleToggleStar = async () => {
    try {
      setIsLoading((prev) => ({ ...prev, star: true }));
      setError(null);

      // TODO: Implement star/unstar API call
      // const response = await api.toggleTaskStar(taskId);

      // Optimistic update
      setIsStarred(!isStarred);
    } catch {
      setError("Failed to update task star status. Please try again.");
      // Revert optimistic update
      setIsStarred(isStarred);
    } finally {
      setIsLoading((prev) => ({ ...prev, star: false }));
    }
  };

  const handleToggleComplete = async () => {
    try {
      setIsLoading((prev) => ({ ...prev, complete: true }));
      setError(null);

      // TODO: Implement complete/uncomplete API call
      // const response = await api.toggleTaskComplete(taskId);
    } catch {
      setError("Failed to update task completion status. Please try again.");
    } finally {
      setIsLoading((prev) => ({ ...prev, complete: false }));
    }
  };

  const handleAddComment = async () => {
    if (!newComment.trim() || newComment.length > 500) return;

    try {
      setIsLoading((prev) => ({ ...prev, comment: true }));
      setError(null);

      const comment: TaskComments = {
        id: currentUser.email,
        content: newComment,
        createdAt: new Date().toISOString(),
      };

      // Optimistic update
      setComments([...comments, comment]);
      setNewComment("");

      // TODO: Implement add comment API call
      // const response = await api.addTaskComment(taskId, comment);
    } catch {
      setError("Failed to add comment. Please try again.");
      // Revert optimistic update
      setComments(comments);
    } finally {
      setIsLoading((prev) => ({ ...prev, comment: false }));
    }
  };

  return (
    <div className="container mx-auto p-6">
      {error && (
        <div className="mb-4 rounded-md bg-red-50 p-4 text-red-700">
          {error}
        </div>
      )}
      <div className="grid gap-6 lg:grid-cols-3">
        {/* Main Task Details */}
        <div className="lg:col-span-2">
          <Card>
            <CardHeader className="space-y-4">
              <TaskHeader
                task={task}
                isStarred={isStarred}
                isLoading={isLoading.star || isLoading.complete}
                onToggleStar={handleToggleStar}
                onToggleComplete={handleToggleComplete}
              />
              <TaskMetadata task={task} commentsCount={comments.length} />
            </CardHeader>
            <CardContent>
              <p className="whitespace-pre-wrap text-muted-foreground">
                {task.description}
              </p>
            </CardContent>
          </Card>
        </div>

        {/* Comments Section */}
        <Card className="lg:h-[calc(100vh-8rem)]">
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              <MessageSquare className="h-5 w-5" />
              Comments
            </CardTitle>
          </CardHeader>
          <CardContent>
            <CommentsList comments={comments} />
          </CardContent>
          <CardFooter>
            <CommentInput
              value={newComment}
              onChange={setNewComment}
              onSubmit={handleAddComment}
              isLoading={isLoading.comment}
            />
          </CardFooter>
        </Card>
      </div>
    </div>
  );
}
