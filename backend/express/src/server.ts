import cors from "cors";
import "dotenv/config";
import express, {json} from "express";
import expressWs from "express-ws";
import path from "path";
import {fileURLToPath} from "url";
import {seed} from "./db/seed.ts";
import {authenticateToken} from "./middleware/auth.ts";
import {error} from "./middleware/error.ts";
import {notFound} from "./middleware/not-found.ts";
import {apiLimiter, authLimiter} from "./middleware/rate-limit.ts";
import ActivityRoutes from "./routes/activityRoutes.ts";
import AuthRoutes from "./routes/authRoutes.ts";
import ProfileRoutes from "./routes/profileRoutes.ts";
import ProjectsRoutes from "./routes/projectsRoutes.ts";
import TasksRoutes from "./routes/tasksRoutes.ts";
import TeamsRoutes from "./routes/teamsRoutes.ts";
import UsersRoutes from "./routes/usersRoutes.ts";
import {
  connectGeneralChat,
  connectPrivateChat,
} from "./services/webSocketService.ts";

seed();

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const app = expressWs(express()).app;

app.use(
  cors({
    origin: "http://localhost:5173",
    methods: ["GET", "POST", "PUT", "DELETE"],
    credentials: true,
  })
);

app.use(json());
app.use("/uploads", express.static(path.resolve(__dirname, "../../uploads")));

app.use(apiLimiter);

app.use("/auth", authLimiter, AuthRoutes);
app.use("/users", authenticateToken, UsersRoutes);
app.use("/teams", authenticateToken, TeamsRoutes);
app.use("/projects", authenticateToken, ProjectsRoutes);
app.use("/profile", authenticateToken, ProfileRoutes);
app.use("/tasks", authenticateToken, TasksRoutes);
app.use("/activity", authenticateToken, ActivityRoutes);

app.ws("/chat", connectGeneralChat);
app.ws("/chat/:privateChat", connectPrivateChat);

app.use(notFound);
app.use(error);

export default app;
