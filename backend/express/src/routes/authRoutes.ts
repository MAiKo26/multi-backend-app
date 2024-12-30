import {Router} from "express";
import {uploadAThing} from "../lib/file-upload-config.ts";
import {
  login,
  logout,
  passwordReset,
  passwordResetConfirmation,
  passwordResetVerification,
  register,
  registerVerification,
} from "../services/authServices.ts";

const router = Router();

// Logging in

router.post("/login", login);

router.post("/logout", logout);

// Registering

router.post("/register", uploadAThing.single("avatar"), register);

router.post("/register/verification", registerVerification);

// Password reset

router.post("/password-reset", passwordReset);

router.post("/password-reset/verification", passwordResetVerification);

router.post("/password-reset/confirmation", passwordResetConfirmation);

export default router;
