import {desc} from "drizzle-orm";
import * as t from "drizzle-orm/pg-core";

export enum UserRole {
  ADMIN = "admin",
  USER = "user",
}

export const users = t.pgTable("users", {
  email: t.text("email").notNull().unique().primaryKey(),

  // Auth
  password: t.text("password").notNull(),
  resetPasswordToken: t.text("reset_password_token"),
  resetPasswordExpiry: t.timestamp("reset_password_expiry"),
  failedLoginAttempts: t.integer("failed_login_attempts").notNull().default(0),
  accountLockedUntil: t.timestamp("account_locked_until"),
  isVerified: t.boolean("is_verified").notNull().default(false),
  verificationToken: t.text("verification_token").unique(),
  createdAt: t.timestamp("created_at").notNull().defaultNow(),
  lastLogin: t.timestamp("last_login"),

  // Profile
  name: t.text("name"),
  avatar: t.text("avatar_url"),
  phoneNumber: t.text("phone_number"),
  role: t.text("role").notNull().default(UserRole.USER),

  // Billing related fields
  subscriptionStatus: t.text("subscription_status").default("free"),
  subscriptionId: t.text("subscription_id"),
  customerId: t.text("customer_id"),
  mbaPoints: t.integer("mba_points").default(0),

  // Notification preferences
  emailNotifications: t.boolean("email_notifications").default(true),
  pushNotifications: t.boolean("push_notifications").default(true),
});

export const sessions = t.pgTable("sessions", {
  sessionId: t.text("session_id").notNull().primaryKey(),
  email: t
    .text("email")
    .notNull()
    .references(() => users.email, {onDelete: "cascade"}),
  createdAt: t.timestamp("created_at").notNull().defaultNow(),
  expiresAt: t.timestamp("expires_at").notNull(),
});

export const teams = t.pgTable("teams", {
  id: t.text("team_id").notNull().primaryKey(),
  name: t.text("name").notNull(),
  createdAt: t.timestamp("created_at").notNull().defaultNow(),
});

export const teamMembers = t.pgTable("team_members", {
  id: t.text("team_member_id").notNull().primaryKey(),
  teamId: t
    .text("team_id")
    .notNull()
    .references(() => teams.id, {onDelete: "cascade"}),
  email: t
    .text("email")
    .notNull()
    .references(() => users.email, {onDelete: "cascade"}),
  role: t.text("role").notNull().default(UserRole.USER),
});

export const projects = t.pgTable("projects", {
  id: t.text("project_id").notNull().primaryKey(),
  name: t.text("name").notNull(),
  createdAt: t.timestamp("created_at").notNull().defaultNow(),
  stars: t.integer("stars").default(0),
  teamId: t
    .text("team_id")
    .notNull()
    .references(() => teams.id, {onDelete: "cascade"}),
});

export const projectMembers = t.pgTable("project_members", {
  id: t.text("project_member_id").notNull().primaryKey(),
  projectId: t
    .text("project_id")
    .notNull()
    .references(() => projects.id, {onDelete: "cascade"}),
  email: t
    .text("email")
    .notNull()
    .references(() => users.email, {onDelete: "cascade"}),
});

export const tasks = t.pgTable("tasks", {
  id: t.text("task_id").notNull().primaryKey(),
  name: t.text("name").notNull(),
  description: t.text("description").notNull(),
  createdAt: t.timestamp("created_at").notNull().defaultNow(),
  projectId: t
    .text("project_id")
    .notNull()
    .references(() => projects.id, {onDelete: "cascade"}),
  assignee: t
    .text("assignee")
    .notNull()
    .references(() => users.email, {onDelete: "cascade"}),
  status: t.text("status").notNull(),
  priority: t.text("priority").notNull(),
});

export const chatRooms = t.pgTable("chat_rooms", {
  id: t.text("room_id").notNull().primaryKey(),
  name: t.text("name").notNull(),
  type: t.text("type").notNull(), // 'private' or 'group'
  createdAt: t.timestamp("created_at").notNull().defaultNow(),
  createdBy: t
    .text("created_by")
    .notNull()
    .references(() => users.email),
});

export const chatMessages = t.pgTable("chat_messages", {
  id: t.text("message_id").notNull().primaryKey(),
  roomId: t
    .text("room_id")
    .references(() => chatRooms.id, {onDelete: "cascade"}),
  senderId: t.text("sender_id").references(() => users.email),
  content: t.text("content").notNull(),
  createdAt: t.timestamp("created_at").notNull().defaultNow(),
  readBy: t.json("read_by").default([]),
});

export const notifications = t.pgTable("notifications", {
  id: t.text("notification_id").notNull().primaryKey(),
  userId: t
    .text("user_id")
    .references(() => users.email, {onDelete: "cascade"}),
  type: t.text("type").notNull(),
  title: t.text("title").notNull(),
  content: t.text("content").notNull(),
  read: t.boolean("read").default(false),
  createdAt: t.timestamp("created_at").notNull().defaultNow(),
  link: t.text("link"),
});

export const activityHistory = t.pgTable("activity_history", {
  id: t.text("history_id").notNull().primaryKey(),
  userId: t
    .text("user_id")
    .references(() => users.email, {onDelete: "cascade"}),
  type: t.text("type").notNull(),
  action: t.text("action").notNull(),
  resourceId: t.text("resource_id"),
  resourceType: t.text("resource_type"),
  createdAt: t.timestamp("created_at").notNull().defaultNow(),
  metadata: t.json("metadata").default({}),
});

export const subscriptions = t.pgTable("subscriptions", {
  id: t.text("subscription_id").notNull().primaryKey(),
  userId: t
    .text("user_id")
    .references(() => users.email, {onDelete: "cascade"}),
  plan: t.text("plan").notNull(),
  status: t.text("status").notNull(),
  startDate: t.timestamp("start_date").notNull(),
  endDate: t.timestamp("end_date"),
  price: t.decimal("price").notNull(),
  billingCycle: t.text("billing_cycle").notNull(),
});

export const taskComments = t.pgTable("task_comments", {
  id: t.text("comment_id").notNull().primaryKey(),
  taskId: t.text("task_id").references(() => tasks.id, {onDelete: "cascade"}),
  userId: t
    .text("user_id")
    .references(() => users.email, {onDelete: "cascade"}),
  content: t.text("content").notNull(),
  createdAt: t.timestamp("created_at").notNull().defaultNow(),
});

export const starredItems = t.pgTable("starred_items", {
  id: t.text("star_id").notNull().primaryKey(),
  userId: t
    .text("user_id")
    .references(() => users.email, {onDelete: "cascade"}),
  itemType: t.text("item_type").notNull(),
  itemId: t.text("item_id").notNull(),
  createdAt: t.timestamp("created_at").notNull().defaultNow(),
});

export const userSettings = t.pgTable("user_settings", {
  userId: t
    .text("user_id")
    .references(() => users.email, {onDelete: "cascade"})
    .primaryKey(),
  emailDigest: t.boolean("email_digest").default(true),
  taskReminders: t.boolean("task_reminders").default(true),
});
