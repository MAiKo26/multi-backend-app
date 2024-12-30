import {Router} from "express";
import {
  addCommentToTask,
  createTask,
  deleteTask,
  getAllTasksForProject,
  starringTask,
  updateTask,
  getAllTasks,
} from "../services/tasksServices.ts";

const router = Router();

router.get("/", getAllTasks);

// Get all tasks for a project
router.get("/project/:projectId", getAllTasksForProject);

// Create task
router.post("/", createTask);

// Update task
router.put("/:taskId", updateTask);

// Delete task
router.delete("/:taskId", deleteTask);

// Add comment to task
router.post("/comments/:taskId", addCommentToTask);

// Star/unstar task
router.post("/star/:taskId", starringTask);

export default router;
