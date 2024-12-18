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
});

export const sessions = t.pgTable("sessions", {
  sessionId: t.text("session_id").notNull().primaryKey(),
  email: t
    .text("email")
    .notNull()
    .references(() => users.email),
  createdAt: t.timestamp("created_at").notNull().defaultNow(),
  expiresAt: t.timestamp("expires_at").notNull(),
});
