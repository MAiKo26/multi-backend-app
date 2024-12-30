import {Router} from "express";
import {
  addMembersToProject,
  createProject,
  deleteProject,
  getAllProjects,
} from "../services/projectsServices.ts";

const router = Router();

router.get("/:teamId", getAllProjects);

// Create project
router.post("/", createProject);

// Add member to project
router.post("/members", addMembersToProject);

// Delete project
router.delete("/:projectId", deleteProject);

export default router;
