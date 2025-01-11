import * as React from "react";

import { NavMain } from "@/components/sidebar/nav-main.tsx";
import { NavUser } from "@/components/sidebar/nav-user.tsx";
import { TeamSwitcher } from "@/components/team-switcher.tsx";
import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarHeader,
  SidebarRail,
} from "@/components/ui/sidebar";
import { data } from "@/constants/place-holder-data";
import { userInterface } from "@/interfaces/userInterface";

export function AppSidebar({
  user,
  ...props
}: { user: userInterface } & React.ComponentProps<typeof Sidebar>) {
  return (
    <Sidebar
      collapsible="icon"
      {...props}
      className="bg-slate-50 text-slate-950 dark:bg-slate-950 dark:text-slate-50"
    >
      <SidebarHeader className="bg-slate-50 text-slate-950 dark:bg-slate-950 dark:text-slate-50">
        <TeamSwitcher {...user} />
      </SidebarHeader>
      <SidebarContent className="bg-slate-50 text-slate-950 dark:bg-slate-950 dark:text-slate-50">
        <NavMain items={data.navMain} user={user} />
      </SidebarContent>
      <SidebarFooter className="bg-slate-50 text-slate-950 dark:bg-slate-950 dark:text-slate-50">
        <NavUser {...user} />
      </SidebarFooter>
      <SidebarRail />
    </Sidebar>
  );
}
