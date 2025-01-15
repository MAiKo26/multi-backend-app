import { useEffect } from "react";
import useStore from "../store/useStore";

export function useGlobalFetch() {
  const {
    fetchAllActivities,
    fetchAllUsers,
    fetchCurrentUser,
    fetchTasks,
    fetchTeams,
    fetchUserProjects,
    currentTeam,
    fetchConnectedUsers,
  } = useStore();

  useEffect(() => {
    fetchCurrentUser();
    fetchAllUsers();
    fetchAllActivities();
    fetchTasks();
    fetchTeams();
    fetchUserProjects();
    fetchConnectedUsers();
  }, []);

  useEffect(() => {
    fetchUserProjects();
  }, [currentTeam?.id]);
}
