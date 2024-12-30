import bcrypt from "bcrypt";
import {eq} from "drizzle-orm";
import {NextFunction, Request, Response} from "express";
import {db} from "../db/db.ts";
import {users, userSettings} from "../db/schema.ts";
import {logActivity} from "../lib/activity-logger.ts";

// Get user profile
export async function getUserProfile(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const userEmail = req.user?.email as string;
    const user = await db.query.users.findFirst({
      where: eq(users.email, userEmail),
      columns: {
        email: true,
        name: true,
        avatar: true,
        phoneNumber: true,
        role: true,
        emailNotifications: true,
        pushNotifications: true,
      },
      with: {
        userSettings: true,
      },
    });

    if (!user) {
      res.status(404).json({message: "User not found"});
      return;
    }

    res.status(200).json(user);
  } catch (error) {
    next(error);
  }
}

// Update profile
export async function updateProfile(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const userEmail = req.user?.email as string;
    const {name, phoneNumber} = req.body;
    const avatar = req.file;

    const updatedUser = await db
      .update(users)
      .set({
        name,
        phoneNumber,
        avatar: avatar?.path || undefined,
      })
      .where(eq(users.email, userEmail))
      .returning();

    await logActivity(
      userEmail,
      `Updated profile${avatar ? " and changed avatar" : ""}`
    );

    res.status(200).json(updatedUser[0]);
  } catch (error) {
    next(error);
  }
}

// Update password
export async function updatePassword(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const userEmail = req.user?.email as string;
    const {currentPassword, newPassword} = req.body;

    const user = await db.query.users.findFirst({
      where: eq(users.email, userEmail),
    });

    if (!user || !(await bcrypt.compare(currentPassword, user.password))) {
      res.status(400).json({message: "Current password is incorrect"});
      return;
    }

    const hashedPassword = await bcrypt.hash(newPassword, 10);
    await db
      .update(users)
      .set({password: hashedPassword})
      .where(eq(users.email, userEmail));

    await logActivity(userEmail, "Changed password");

    res.status(200).json({message: "Password updated successfully"});
  } catch (error) {
    next(error);
  }
}

// Update notification settings
export async function updateNotificationSettings(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const userEmail = req.user?.email as string;
    const {emailNotifications, pushNotifications, emailDigest, taskReminders} =
      req.body;

    await db
      .update(users)
      .set({
        emailNotifications,
        pushNotifications,
      })
      .where(eq(users.email, userEmail));

    await db
      .update(userSettings)
      .set({
        emailDigest,
        taskReminders,
      })
      .where(eq(userSettings.userEmail, userEmail));

    await logActivity(userEmail, "Updated notification preferences");

    res.status(200).json({message: "Settings updated successfully"});
  } catch (error) {
    next(error);
  }
}
