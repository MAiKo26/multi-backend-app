import {relations} from "drizzle-orm";
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
  name: t.text("name").notNull(),
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

export const usersRelations = relations(users, ({many, one}) => ({
  activityHistory: one(activityHistory),
  userSettings: one(userSettings, {
    fields: [users.email],
    references: [userSettings.userEmail],
  }),
  sessions: many(sessions),
  teamMembers: many(teamMembers),
  userNotifications: many(userNotifications),
  taskComments: many(taskComments),
  starredTasks: many(starredTasks),
}));

export const userSettings = t.pgTable("user_settings", {
  userEmail: t
    .text("user_email")
    .references(() => users.email, {onDelete: "cascade"})
    .primaryKey(),
  emailDigest: t.boolean("email_digest").default(true),
  taskReminders: t.boolean("task_reminders").default(true),
});

export const userSettingsRelations = relations(userSettings, ({one}) => ({
  users: one(users, {
    fields: [userSettings.userEmail],
    references: [users.email],
  }),
}));

export const sessions = t.pgTable("sessions", {
  sessionId: t.text("session_id").notNull().primaryKey(),
  email: t
    .text("email")
    .notNull()
    .references(() => users.email, {onDelete: "cascade"}),
  createdAt: t.timestamp("created_at").notNull().defaultNow(),
  expiresAt: t.timestamp("expires_at").notNull(),
});

export const sessionsRelations = relations(sessions, ({one}) => ({
  users: one(users, {fields: [sessions.email], references: [users.email]}),
}));

export const teams = t.pgTable("teams", {
  id: t.serial("team_id").notNull().primaryKey(),
  name: t.text("name").notNull(),
  createdAt: t.timestamp("created_at").notNull().defaultNow(),
});

export const teamsRelations = relations(teams, ({many, one}) => ({
  teamMembers: many(teamMembers),
  projects: many(projects),
}));

export const projects = t.pgTable("projects", {
  id: t.text("project_id").notNull().primaryKey(),
  name: t.text("name").notNull(),
  createdAt: t.timestamp("created_at").notNull().defaultNow(),
  teamId: t
    .integer("team_id")
    .notNull()
    .references(() => teams.id, {onDelete: "cascade"}),
});

export const projectsRelations = relations(projects, ({one, many}) => ({
  team: one(teams, {fields: [projects.teamId], references: [teams.id]}),
  tasks: many(tasks),
}));

export const tasks = t.pgTable("tasks", {
  id: t.text("task_id").notNull().primaryKey(),
  name: t.text("name").notNull(),
  description: t.text("description").notNull(),
  createdAt: t.timestamp("created_at").notNull().defaultNow(),
  projectId: t
    .text("project_id")
    .notNull()
    .references(() => projects.id, {onDelete: "cascade"}),
  stars: t.integer("stars").default(0),
  finished: t.boolean("finished").notNull().default(false),
  finishedBy: t
    .text("finished_by")
    .references(() => users.email, {onDelete: "no action"}),
});

export const tasksRelations = relations(tasks, ({one, many}) => ({
  project: one(projects, {
    fields: [tasks.projectId],
    references: [projects.id],
  }),
  users: one(users, {
    fields: [tasks.finishedBy],
    references: [users.email],
  }),
  taskComments: many(taskComments),
  starredTasks: many(starredTasks),
}));

export const notifications = t.pgTable("notifications", {
  id: t.text("notification_id").notNull().primaryKey(),
  type: t.text("type").notNull(),
  title: t.text("title").notNull(),
  content: t.text("content").notNull(),
  read: t.boolean("read").default(false),
  createdAt: t.timestamp("created_at").notNull().defaultNow(),
  link: t.text("link"),
});

export const notificationsRelations = relations(notifications, ({many}) => ({
  userNotifications: many(userNotifications),
}));

export const activityHistory = t.pgTable("activity_history", {
  id: t.text("history_id").notNull().primaryKey(),
  userId: t
    .text("user_id")
    .references(() => users.email, {onDelete: "cascade"}),
  description: t.text("description").notNull(),
  doneAt: t.timestamp("done_at").notNull().defaultNow(),
});

export const activityHistoryRelations = relations(activityHistory, ({one}) => ({
  users: one(users, {
    fields: [activityHistory.userId],
    references: [users.email],
  }),
}));

//  TODO

// export const chatRooms = t.pgTable("chat_rooms", {
//   id: t.text("room_id").notNull().primaryKey(),
//   name: t.text("name").notNull(),
//   type: t.text("type").notNull(),
//   createdAt: t.timestamp("created_at").notNull().defaultNow(),
//   createdBy: t
//     .text("created_by")
//     .notNull()
//     .references(() => users.email),
// });

// export const chatMessages = t.pgTable("chat_messages", {
//   id: t.text("message_id").notNull().primaryKey(),
//   roomId: t
//     .text("room_id")
//     .references(() => chatRooms.id, {onDelete: "cascade"}),
//   senderId: t.text("sender_id").references(() => users.email),
//   content: t.text("content").notNull(),
//   createdAt: t.timestamp("created_at").notNull().defaultNow(),
//   readBy: t.text("read_by").references(() => users.email),
// });

// export const subscriptions = t.pgTable("subscriptions", {
//   id: t.text("subscription_id").notNull().primaryKey(),
//   userId: t
//     .text("user_id")
//     .references(() => users.email, {onDelete: "cascade"}),
//   plan: t.text("plan").notNull(),
//   status: t.text("status").notNull(),
//   startDate: t.timestamp("start_date").notNull(),
//   endDate: t.timestamp("end_date"),
//   price: t.decimal("price").notNull(),
//   billingCycle: t.text("billing_cycle").notNull(),
// });

// Many to Many

export const taskComments = t.pgTable("task_comments", {
  id: t.text("comment_id").notNull().primaryKey(),
  taskId: t.text("task_id").references(() => tasks.id, {onDelete: "cascade"}),
  userId: t
    .text("user_id")
    .references(() => users.email, {onDelete: "cascade"}),
  content: t.varchar("content", {length: 255}).notNull(),
  createdAt: t.timestamp("created_at").notNull().defaultNow(),
});

export const taskCommentsRelations = relations(taskComments, ({one}) => ({
  tasks: one(tasks, {fields: [taskComments.taskId], references: [tasks.id]}),
  users: one(users, {fields: [taskComments.userId], references: [users.email]}),
}));

export const teamMembers = t.pgTable(
  "team_members",
  {
    teamId: t
      .integer("team_id")
      .notNull()
      .references(() => teams.id, {onDelete: "cascade"}),
    email: t
      .text("email")
      .notNull()
      .references(() => users.email, {onDelete: "cascade"}),
  },
  (ct) => [{pk: t.primaryKey({columns: [ct.email, ct.teamId]})}]
);

export const teamMembersRelations = relations(teamMembers, ({one}) => ({
  teams: one(teams, {fields: [teamMembers.teamId], references: [teams.id]}),
  users: one(users, {fields: [teamMembers.email], references: [users.email]}),
}));

export const projectMembers = t.pgTable(
  "project_members",
  {
    projectId: t
      .text("project_id")
      .notNull()
      .references(() => projects.id, {onDelete: "cascade"}),
    email: t
      .text("email")
      .notNull()
      .references(() => users.email, {onDelete: "cascade"}),
  },
  (ct) => [{pk: t.primaryKey({columns: [ct.email, ct.projectId]})}]
);

export const projectMembersRelations = relations(projectMembers, ({one}) => ({
  projects: one(projects, {
    fields: [projectMembers.projectId],
    references: [projects.id],
  }),
  users: one(users, {
    fields: [projectMembers.email],
    references: [users.email],
  }),
}));

export const userNotifications = t.pgTable(
  "user_notifications",
  {
    userId: t
      .text("user_id")
      .notNull()
      .references(() => users.email, {onDelete: "cascade"}),
    notificationId: t
      .text("notification_id")
      .notNull()
      .references(() => notifications.id, {onDelete: "cascade"}),
  },
  (ct) => [{pk: t.primaryKey({columns: [ct.userId, ct.notificationId]})}]
);

export const userNotificationsRelations = relations(
  userNotifications,
  ({one}) => ({
    users: one(users, {
      fields: [userNotifications.userId],
      references: [users.email],
    }),
    notifications: one(notifications, {
      fields: [userNotifications.notificationId],
      references: [notifications.id],
    }),
  })
);

export const starredTasks = t.pgTable(
  "starred_tasks",
  {
    userId: t
      .text("user_id")
      .references(() => users.email, {onDelete: "cascade"}),
    taskId: t.text("task_id").references(() => tasks.id, {onDelete: "cascade"}),
    createdAt: t.timestamp("created_at").notNull().defaultNow(),
  },
  (ct) => [{pk: t.primaryKey({columns: [ct.userId, ct.taskId]})}]
);

export const starredTasksRelations = relations(starredTasks, ({one}) => ({
  users: one(users, {fields: [starredTasks.userId], references: [users.email]}),
  tasks: one(tasks, {fields: [starredTasks.taskId], references: [tasks.id]}),
}));
