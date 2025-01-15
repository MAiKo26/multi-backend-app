import FeaturedTask from "@/components/FeaturedTask";
import useStore from "@/store/useStore";

function Tasks() {
  const { tasks, currentUser } = useStore();

  const filteredTasks = tasks.filter(
    (task) => task.finished && task.finishedBy === currentUser?.email,
  );
  return (
    <div className="flex w-full flex-col gap-6">
      {filteredTasks.length === 0 ? (
        <p className="text-center text-gray-500">You have no finished tasks</p>
      ) : (
        filteredTasks.map((task) => <FeaturedTask {...task} />)
      )}
    </div>
  );
}
export default Tasks;
