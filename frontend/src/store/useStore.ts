import { create } from "zustand";
import { persist } from "zustand/middleware";
import { userInterface } from "@/interfaces/userInterface";
import { fetchUserDetails } from "@/lib/user-repo";
import { fetchUsers } from "@/lib/user-repo";

export interface Team {
  id: string;
  name: string;
  createdAt: string;

  // Add other team properties as needed
}

export interface Project {
  id: string;
  name: string;
  createdAt: string;
  teamId: string;
  tasks: Task[];
}

export interface ActivityHistory {
  id: string;
  userId: string;
  description: string;
  doneAt: string;
  users: { email: string; name: string; avatar: string };
}

export interface Task {
  id: string;
  name: string;
  description: string;
  createdAt: string;
  stars: number;
  finished: boolean;
  projectId: string;
  finishedBy: string;
  taskComments: TaskComments[];
  starredTasks: StarredTasks[];
}

export interface StarredTasks {
  taskId: string;
  userId: string;
  createdAt: string;
}

export interface TaskComments {
  id: string;
  content: string;
  createdAt: string;
}

export interface AppState {
  // Auth State
  isAuthenticated: boolean;
  token: string | null;

  // User State
  currentUser: userInterface | null;
  allUsers: userInterface[];

  // Team State
  currentTeam: Team | null;
  teams: Team[];

  // Project State
  projects: Project[];

  // ActivityHistory State
  activityHistory: ActivityHistory[];

  tasks: Task[];

  // Loading States
  isLoading: {
    user: boolean;
    users: boolean;
    projects: boolean;
    teams: boolean;
    activity: boolean;
    tasks: boolean;
  };

  // Actions
  setToken: (token: string | null) => void;
  setCurrentUser: (user: userInterface | null) => void;
  setCurrentTeam: (team: Team | null) => void;
  setAllUsers: (users: userInterface[]) => void;
  setProjects: (projects: Project[]) => void;
  setActivityHistory: (activity: ActivityHistory[]) => void;
  setTeams: (teams: Team[]) => void;
  setTasks: (tasks: Task[]) => void;
  setLoading: (key: keyof AppState["isLoading"], value: boolean) => void;

  // Fetch Actions
  fetchCurrentUser: () => Promise<void>;
  fetchAllUsers: () => Promise<void>;
  fetchUserProjects: () => Promise<void>;
  fetchAllActivities: () => Promise<void>;
  fetchTasks: () => Promise<void>;

  // Auth Actions
  logout: () => void;

  // Add to the AppState interface
  fetchTeams: () => Promise<void>;
}

const useStore = create<AppState>()(
  persist(
    (set, get) => ({
      // Initial State
      isAuthenticated: !!sessionStorage.getItem("authToken"),
      token: sessionStorage.getItem("authToken"),
      currentUser: null,
      allUsers: [],
      currentTeam: null,
      teams: [],
      projects: [],
      tasks: [],
      activityHistory: [],
      isLoading: {
        user: true,
        users: false,
        projects: false,
        teams: false,
        activity: false,
        tasks: false,
      },

      // Basic Setters
      setToken: (token) => {
        if (token) {
          sessionStorage.setItem("authToken", token);
        } else {
          sessionStorage.removeItem("authToken");
        }
        set({ token, isAuthenticated: !!token });
      },
      setCurrentUser: (user) => set({ currentUser: user }),
      setCurrentTeam: (team) => set({ currentTeam: team }),
      setAllUsers: (users) => set({ allUsers: users }),
      setProjects: (projects) => set({ projects }),
      setTeams: (teams) => set({ teams }),
      setTasks: (tasks) => set({ tasks }),
      setActivityHistory: (activity) => set({ activityHistory: activity }),
      setLoading: (key, value) =>
        set((state) => ({
          isLoading: { ...state.isLoading, [key]: value },
        })),

      // Fetch Actions
      fetchCurrentUser: async () => {
        const state = get();
        try {
          state.setLoading("user", true);
          console.log("heyyyy");
          console.log(state.isLoading.user);

          const user = await fetchUserDetails();
          if (!user) {
            state.logout();
            return;
          }
          set({ currentUser: user });
          state.setLoading("user", false);
        } catch (error) {
          console.error("Failed to fetch user:", error);
        }
      },

      fetchAllUsers: async () => {
        const state = get();
        try {
          state.setLoading("users", true);
          if (!state.token) return;
          const users = await fetchUsers(state.token);
          set({ allUsers: users });
        } catch (error) {
          console.error("Failed to fetch users:", error);
        } finally {
          state.setLoading("users", false);
        }
      },

      fetchAllActivities: async () => {
        const state = get();
        if (!state.currentUser?.email || !state.token) return;

        try {
          state.setLoading("activity", true);
          const response = await fetch(`http://localhost:3636/activity`, {
            headers: { Authorization: `Bearer ${state.token}` },
          });
          if (!response.ok) throw new Error("Failed to fetch activities");
          const activityHistory = await response.json();
          set({ activityHistory });
        } catch (error) {
          console.error("Failed to fetch activities:", error);
        } finally {
          state.setLoading("activity", false);
        }
      },

      fetchUserProjects: async () => {
        const state = get();
        if (!state.currentUser?.email || !state.token) return;

        try {
          state.setLoading("projects", true);
          const response = await fetch(
            `http://localhost:3636/projects/${state.currentTeam?.id}`,
            {
              headers: { Authorization: `Bearer ${state.token}` },
            },
          );
          if (!response.ok) throw new Error("Failed to fetch projects");
          const projects = await response.json();
          set({ projects });
        } catch (error) {
          console.error("Failed to fetch projects:", error);
        } finally {
          state.setLoading("projects", false);
        }
      },

      // Auth Actions
      logout: () => {
        sessionStorage.clear();
        set({
          token: null,
          currentUser: null,
          currentTeam: null,
          isAuthenticated: false,
          projects: [],
          teams: [],
        });
      },

      // Add to the store actions
      fetchTeams: async () => {
        const state = get();
        if (!state.currentUser?.email || !state.token) return;

        try {
          state.setLoading("teams", true);
          const response = await fetch(
            `http://localhost:3636/teams/byuser/${state.currentUser.email}`,
            {
              headers: { Authorization: `Bearer ${state.token}` },
            },
          );
          if (!response.ok) throw new Error("Failed to fetch teams");
          const teams = await response.json();
          set({ teams });

          // Set current team if not already set
          if (!state.currentTeam && teams.length > 0) {
            state.setCurrentTeam(teams[0]);
          }
        } catch (error) {
          console.error("Failed to fetch teams:", error);
        } finally {
          state.setLoading("teams", false);
        }
      },

      fetchTasks: async () => {
        const state = get();
        if (!state.currentUser?.email || !state.token) return;

        try {
          state.setLoading("tasks", true);
          const response = await fetch(`http://localhost:3636/tasks`, {
            headers: { Authorization: `Bearer ${state.token}` },
          });
          if (!response.ok) throw new Error("Failed to fetch tasks");
          const tasks = await response.json();
          set({ tasks });
        } catch (error) {
          console.error("Failed to fetch tasks:", error);
        } finally {
          state.setLoading("activity", false);
        }
      },
    }),
    {
      name: "app-storage",
      partialize: (state) => ({
        token: state.token,
        currentUser: state.currentUser,
        currentTeam: state.currentTeam,
        isAuthenticated: state.isAuthenticated,
      }),
    },
  ),
);

export default useStore;
