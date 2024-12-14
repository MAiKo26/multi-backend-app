import "dotenv/config";
import cors from "cors"; // Import CORS middleware
import express, {json} from "express";
import {error} from "./middleware/error.ts";
import {notFound} from "./middleware/not-found.ts";
import AuthRoutes from "./routes/auth.ts";
import UsersRoutes from "./routes/users.ts";

const app = express();
app.use(
  cors({
    origin: "http://localhost:5173", // Allow requests from this origin
    methods: ["GET", "POST", "PUT", "DELETE"], // Allowed HTTP methods
    credentials: true, // Allow cookies if needed
  })
);

app.use(json());

app.use("/auth", AuthRoutes);
app.use("/users", UsersRoutes);

app.use(notFound);
app.use(error);

export default app;
