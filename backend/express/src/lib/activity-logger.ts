import {db} from "../db/db.ts";
import {activityHistory} from "../db/schema.ts";
import crypto from "crypto";

export async function logActivity(userId: string, description: string) {
  try {
    await db.insert(activityHistory).values({
      id: crypto.randomBytes(16).toString("hex"),
      userId,
      description,
      doneAt: new Date(),
    });
  } catch (error) {
    console.error("Failed to log activity:", error);
  }
}
