import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import useStore from "@/store/useStore";

function Starred() {
  const { tasks, currentUser } = useStore();

  const tasksFinishedByMe = tasks.filter(
    (task) => task.finished && task.finishedBy === currentUser?.email,
  );

  const tasksIStarred = tasks.filter((task) =>
    task.starredTasks.some((st) => {
      return st.userId === currentUser?.email;
    }),
  );

  return (
    <div className="container mx-auto p-6">
      <div className="grid gap-6 md:grid-cols-2">
        <Card>
          <CardHeader>
            <CardTitle>Stars I got</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              {tasksFinishedByMe.map((task) =>
                task.starredTasks.map((st, index) => (
                  <div key={index}>
                    {st.userId} gave me a star for {st.taskId}
                  </div>
                )),
              )}
            </div>
          </CardContent>
        </Card>
        <Card>
          <CardHeader>
            <CardTitle>Stars I gave</CardTitle>
          </CardHeader>
          <CardContent>
            <ul className="space-y-4">
              {tasksIStarred.map((task, key) => (
                <div key={key}>
                  I gave {task.finishedBy} a star for {task.id}
                </div>
              ))}
            </ul>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
export default Starred;
