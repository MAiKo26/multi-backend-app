import {db} from "./db.ts";
import {
  users,
  teams,
  projects,
  tasks,
  teamMembers,
  projectMembers,
  starredTasks,
  notifications,
  userNotifications,
  userSettings,
  activityHistory,
  taskComments,
  UserRole,
} from "./schema.ts";
import bcrypt from "bcrypt";
import crypto from "crypto";

async function hashPassword(password: string) {
  const hashedPassword = await bcrypt.hash(password, 10);
  return hashedPassword;
}

export async function seed() {
  try {
    console.log("Checking if seeding is necessary...");

    const existingUsers = await db.select().from(users).limit(1);
    if (existingUsers.length > 0) {
      console.log("Database already seeded. Skipping...");
      return;
    }

    console.log("Seeding started...");

    const usersData = [
      {
        email: "admin@example.com",
        password: await hashPassword("123456789"),
        name: "Admin User",
        role: UserRole.ADMIN,
        isVerified: true,
        createdAt: new Date(),
      },
      {
        email: "manager@example.com",
        password: await hashPassword("123456789"),
        name: "Project Manager",
        role: UserRole.USER,
        isVerified: true,
        createdAt: new Date(),
      },
      {
        email: "dev1@example.com",
        password: await hashPassword("123456789"),
        name: "Developer One",
        role: UserRole.USER,
        isVerified: true,
        createdAt: new Date(),
      },
      {
        email: "dev2@example.com",
        password: await hashPassword("123456789"),
        name: "Developer Two",
        role: UserRole.USER,
        isVerified: true,
        createdAt: new Date(),
      },
      {
        email: "tester@example.com",
        password: await hashPassword("123456789"),
        name: "QA Tester",
        role: UserRole.USER,
        isVerified: true,
        createdAt: new Date(),
      },
    ];

    await db.insert(users).values(usersData);
    console.log("Users seeded.");

    await db.insert(userSettings).values(
      usersData.map((user) => ({
        userEmail: user.email,
        emailDigest: true,
        taskReminders: true,
      }))
    );
    console.log("User settings seeded.");

    const teamsData = [
      {id: 1, name: "Frontend Team", createdAt: new Date()},
      {id: 2, name: "Backend Team", createdAt: new Date()},
      {id: 3, name: "DevOps Team", createdAt: new Date()},
      {id: 4, name: "QA Team", createdAt: new Date()},
    ];

    await db.insert(teams).values(teamsData);
    console.log("Teams seeded.");

    const teamMembersData = [
      {teamId: 1, email: "admin@example.com"},
      {teamId: 2, email: "admin@example.com"},
      {teamId: 3, email: "admin@example.com"},
      {teamId: 4, email: "admin@example.com"},

      {teamId: 1, email: "manager@example.com"},
      {teamId: 2, email: "manager@example.com"},

      {teamId: 1, email: "dev1@example.com"},

      {teamId: 2, email: "dev2@example.com"},

      {teamId: 4, email: "tester@example.com"},
    ];

    await db.insert(teamMembers).values(teamMembersData);
    console.log("Team members seeded.");

    const projectsData = [
      {
        id: "proj-fe-1",
        name: "Website Redesign",
        teamId: 1,
        createdAt: new Date(),
      },
      {
        id: "proj-be-1",
        name: "API Development",
        teamId: 2,
        createdAt: new Date(),
      },
      {
        id: "proj-qa-1",
        name: "Testing Framework",
        teamId: 4,
        createdAt: new Date(),
      },
    ];

    await db.insert(projects).values(projectsData);
    console.log("Projects seeded.");

    const projectMembersData = [
      {projectId: "proj-fe-1", email: "admin@example.com"},
      {projectId: "proj-fe-1", email: "manager@example.com"},
      {projectId: "proj-fe-1", email: "dev1@example.com"},

      {projectId: "proj-be-1", email: "admin@example.com"},
      {projectId: "proj-be-1", email: "manager@example.com"},
      {projectId: "proj-be-1", email: "dev2@example.com"},

      {projectId: "proj-qa-1", email: "admin@example.com"},
      {projectId: "proj-qa-1", email: "tester@example.com"},
    ];

    await db.insert(projectMembers).values(projectMembersData);
    console.log("Project members seeded.");

    const tasksData = [
      {
        id: "task-1",
        name: "Design Homepage",
        description: "Create new homepage design",
        projectId: "proj-fe-1",
        createdAt: new Date(),
        stars: 2,
      },
      {
        id: "task-2",
        name: "Implement Auth API",
        description: "Develop authentication endpoints",
        projectId: "proj-be-1",
        createdAt: new Date(),
        stars: 1,
      },
      {
        id: "task-3",
        name: "Write E2E Tests",
        description: "Create end-to-end tests for main features",
        projectId: "proj-qa-1",
        createdAt: new Date(),
        stars: 0,
      },
    ];

    await db.insert(tasks).values(tasksData);
    console.log("Tasks seeded.");

    const taskCommentsData = [
      {
        id: crypto.randomBytes(16).toString("hex"),
        taskId: "task-1",
        userId: "dev1@example.com",
        content: "Started working on the mockups",
        createdAt: new Date(),
      },
      {
        id: crypto.randomBytes(16).toString("hex"),
        taskId: "task-2",
        userId: "dev2@example.com",
        content: "JWT implementation completed",
        createdAt: new Date(),
      },
    ];

    await db.insert(taskComments).values(taskCommentsData);
    console.log("Task comments seeded.");

    const starredTasksData = [
      {
        userId: "manager@example.com",
        taskId: "task-1",
        createdAt: new Date(),
      },
      {
        userId: "dev1@example.com",
        taskId: "task-1",
        createdAt: new Date(),
      },
      {
        userId: "dev2@example.com",
        taskId: "task-2",
        createdAt: new Date(),
      },
    ];

    await db.insert(starredTasks).values(starredTasksData);
    console.log("Starred tasks seeded.");

    const notificationsData = [
      {
        id: "notif-1",
        type: "task_assignment",
        title: "New Task Assigned",
        content: "You have been assigned to 'Design Homepage'",
        createdAt: new Date(),
        link: "/tasks/task-1",
      },
      {
        id: "notif-2",
        type: "comment",
        title: "New Comment",
        content: "New comment on 'Implement Auth API'",
        createdAt: new Date(),
        link: "/tasks/task-2",
      },
    ];

    await db.insert(notifications).values(notificationsData);
    console.log("Notifications seeded.");

    const userNotificationsData = [
      {
        userId: "dev1@example.com",
        notificationId: "notif-1",
      },
      {
        userId: "dev2@example.com",
        notificationId: "notif-2",
      },
    ];

    await db.insert(userNotifications).values(userNotificationsData);
    console.log("User notifications seeded.");

    const activityHistoryData = [
      {
        id: crypto.randomBytes(16).toString("hex"),
        userId: "admin@example.com",
        description: "Created Frontend Team",
        doneAt: new Date(),
      },
      {
        id: crypto.randomBytes(16).toString("hex"),
        userId: "manager@example.com",
        description: "Created Website Redesign project",
        doneAt: new Date(),
      },
      {
        id: crypto.randomBytes(16).toString("hex"),
        userId: "dev1@example.com",
        description: "Added comment to Design Homepage task",
        doneAt: new Date(),
      },
    ];

    await db.insert(activityHistory).values(activityHistoryData);
    console.log("Activity history seeded.");

    console.log("Seeding completed successfully.");
  } catch (err) {
    console.error("Error seeding database:", err);
  }
}
