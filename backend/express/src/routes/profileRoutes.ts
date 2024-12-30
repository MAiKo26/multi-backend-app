import {Router} from "express";
import {uploadAThing} from "../lib/file-upload-config.ts";
import {
  getUserProfile,
  updateNotificationSettings,
  updatePassword,
  updateProfile,
} from "../services/profileServices.ts";

const router = Router();

// Get user profile
router.get("/", getUserProfile);

// Update profile
router.put("/", uploadAThing.single("avatar"), updateProfile);

// Update password
router.put("/password", updatePassword);

// Update notification settings
router.put("/settings", updateNotificationSettings);

export default router;
