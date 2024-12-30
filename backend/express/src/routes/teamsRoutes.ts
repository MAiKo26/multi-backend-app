import {Router} from "express";
import {isAdmin} from "../lib/is-admin.ts";
import {
  addMemberToTeam,
  createTeam,
  deleteTeam,
  getAllTeams,
  getTeamsByUserEmail,
  updateTeam,
} from "../services/teamsServices.ts";

const router = Router();

// Get all teams (admin only)
router.get("/", isAdmin, getAllTeams);

// Create team (admin only)
router.post("/", isAdmin, createTeam);

// Update team (admin only)
router.put("/:id", isAdmin, updateTeam);

// Delete team (admin only)
router.delete("/:id", isAdmin, deleteTeam);

// Add member to team
router.post("/members", isAdmin, addMemberToTeam);

// Get teams by user email
router.get("/byuser/:email", getTeamsByUserEmail);

export default router;
