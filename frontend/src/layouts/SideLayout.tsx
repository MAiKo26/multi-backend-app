import { AppSidebar } from "@/components/sidebar/app-sidebar";
import ThemeSwitch from "@/components/ThemeSwitch";
import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbPage,
  BreadcrumbSeparator,
} from "@/components/ui/breadcrumb";
import { Separator } from "@/components/ui/separator";
import {
  SidebarInset,
  SidebarProvider,
  SidebarTrigger,
} from "@/components/ui/sidebar";
import { useToast } from "@/hooks/use-toast";
import { userInterface } from "@/interfaces/userInterface";
import { logOut } from "@/lib/auth-repo";
import { fetchUserDetails } from "@/lib/user-repo";
import { useEffect, useState } from "react";
import { Outlet } from "react-router";

export default function SideBar() {
  const [user, setUser] = useState<userInterface | null>(null);
  const { toast } = useToast();

  useEffect(() => {
    const fetchData = async () => {
      const session = sessionStorage.getItem("authToken");

      if (!session) {
        setUser(null);
        logOut();
        return;
      }

      const user = await fetchUserDetails();
      if (!user) {
        sessionStorage.removeItem("authToken");
        setUser(null);
        logOut();
      } else {
        setUser(user);
      }
    };

    try {
      fetchData();
    } catch {
      toast({
        title: "Network error. Please try again.",
        variant: "destructive",
      });
    }
  }, []);

  return (
    <SidebarProvider>
      <AppSidebar user={user} />
      <SidebarInset>
        <header className="flex h-16 shrink-0 items-center justify-between gap-2 transition-[width,height] ease-linear group-has-[[data-collapsible=icon]]/sidebar-wrapper:h-12">
          <div className="flex items-center gap-2 px-4">
            <SidebarTrigger className="-ml-1" />
            <Separator orientation="vertical" className="mr-2 h-4" />
            <Breadcrumb>
              <BreadcrumbList>
                <BreadcrumbItem className="hidden md:block">
                  <BreadcrumbLink href="#">Playground</BreadcrumbLink>
                </BreadcrumbItem>
                <BreadcrumbSeparator className="hidden md:block" />
                <BreadcrumbItem>
                  <BreadcrumbPage>Dashboard</BreadcrumbPage>
                </BreadcrumbItem>
              </BreadcrumbList>
            </Breadcrumb>
          </div>
          <div className="flex items-center gap-2 px-4">
            <ThemeSwitch />
          </div>
        </header>
        <div className="flex flex-1 flex-col gap-4 p-4 pt-0">
          <Outlet context={[user]} />
        </div>
      </SidebarInset>
    </SidebarProvider>
  );
}
