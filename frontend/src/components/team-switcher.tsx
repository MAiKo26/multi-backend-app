import { ChevronsUpDown, GalleryVerticalEnd, Plus } from "lucide-react";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuShortcut,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import {
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
  useSidebar,
} from "@/components/ui/sidebar";
import { useEffect } from "react";
import { Skeleton } from "./ui/skeleton";
import useStore, { Team } from "@/store/useStore";

export function TeamSwitcher() {
  const { isMobile } = useSidebar();
  const {
    token,
    currentUser,
    teams,
    currentTeam,
    isLoading,
    setCurrentTeam,
    setTeams,
    setLoading,
  } = useStore();

  useEffect(() => {
    const fetchTeams = async () => {
      try {
        setLoading("teams", true);

        const response = await fetch(
          `http://localhost:3636/teams/byuser/${currentUser?.email}`,
          {
            method: "GET",
            headers: { Authorization: `Bearer ${token}` },
          },
        );

        if (!response.ok) {
          throw new Error("Failed to fetch teams");
        }

        const fetchedTeams: Team[] = await response.json();
        setTeams(fetchedTeams);

        if (fetchedTeams.length === 0) {
          setLoading("teams", false);

          return;
        }

        // Find active team with proper null checking
        const savedTeamId = currentTeam?.id ? JSON.parse(currentTeam.id) : null;
        const activeTeam = savedTeamId
          ? fetchedTeams.find((team) => team.id === savedTeamId)
          : fetchedTeams[0];

        setCurrentTeam(activeTeam || null);
        sessionStorage.setItem("currentTeamId", JSON.stringify(activeTeam?.id));
        setLoading("teams", false);
      } catch (error) {
        console.error("Error fetching teams:", error);
        setLoading("teams", false);
      }
    };

    fetchTeams();
  }, [token, currentUser]);

  const handleTeamChange = (team: Team) => {
    setCurrentTeam(team);
    sessionStorage.setItem("currentTeamId", JSON.stringify(team.id));
  };

  return (
    <SidebarMenu>
      {isLoading.teams ? (
        <Skeleton className="h-12 w-full" />
      ) : teams.length === 0 ? (
        <div className="p-2 text-sm text-muted-foreground">
          No teams available
        </div>
      ) : (
        <SidebarMenuItem>
          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <SidebarMenuButton
                size="lg"
                className="data-[state=open]:bg-sidebar-accent data-[state=open]:text-sidebar-accent-foreground"
              >
                <div className="flex aspect-square size-8 items-center justify-center rounded-lg bg-sidebar-primary text-sidebar-primary-foreground">
                  <GalleryVerticalEnd className="size-4" />
                </div>
                <div className="grid flex-1 text-left text-sm leading-tight">
                  <span className="truncate font-semibold">
                    {currentTeam?.name}
                  </span>
                </div>
                <ChevronsUpDown className="ml-auto" />
              </SidebarMenuButton>
            </DropdownMenuTrigger>
            <DropdownMenuContent
              className="w-[--radix-dropdown-menu-trigger-width] min-w-56 rounded-lg"
              align="start"
              side={isMobile ? "bottom" : "right"}
              sideOffset={4}
            >
              <DropdownMenuLabel className="text-xs text-muted-foreground">
                Teams
              </DropdownMenuLabel>
              {teams.map((team, index) => (
                <DropdownMenuItem
                  key={team.id}
                  onClick={() => handleTeamChange(team)}
                  className="gap-2 p-2"
                >
                  <div className="flex size-6 items-center justify-center rounded-sm border">
                    <GalleryVerticalEnd className="size-4 shrink-0" />
                  </div>
                  {team.name}
                  <DropdownMenuShortcut>⌘{index + 1}</DropdownMenuShortcut>
                </DropdownMenuItem>
              ))}
              <DropdownMenuSeparator />
              <DropdownMenuItem className="gap-2 p-2" disabled>
                <div className="flex size-6 items-center justify-center rounded-md border bg-background">
                  <Plus className="size-4" />
                </div>
                <div className="font-medium text-muted-foreground">
                  Add team
                </div>
              </DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>
        </SidebarMenuItem>
      )}
    </SidebarMenu>
  );
}
