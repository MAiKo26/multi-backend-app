import { AppSidebar } from "@/components/sidebar/app-sidebar";
import BreadcrumbComponent from "@/components/sidebar/breadcrumb-component";
import ThemeSwitch from "@/components/ThemeSwitch";
import { Separator } from "@/components/ui/separator";
import {
  SidebarInset,
  SidebarProvider,
  SidebarTrigger,
} from "@/components/ui/sidebar";
import { useGlobalFetch } from "@/hooks/use-global-fetch";
import useStore from "@/store/useStore";
import { Loader2 } from "lucide-react";
import { Outlet } from "react-router";

export default function SideBar() {
  const { currentUser, isLoading } = useStore();

  useGlobalFetch();

  return isLoading.user ? (
    <div className="flex h-full min-h-screen w-full items-center justify-center">
      <Loader2 className="h-72 w-72 animate-spin" />
    </div>
  ) : (
    <>
      {currentUser ? (
        <SidebarProvider>
          <AppSidebar user={currentUser} />
          <SidebarInset className="max-h-screen">
            <header className="flex h-16 shrink-0 items-center justify-between gap-2 transition-[width,height] ease-linear group-has-[[data-collapsible=icon]]/sidebar-wrapper:h-12">
              <div className="flex items-center gap-2 px-4">
                <SidebarTrigger className="-ml-1" />
                <Separator orientation="vertical" className="mr-2 h-4" />
                <BreadcrumbComponent />
              </div>
              <div className="flex items-center gap-2 px-4">
                <ThemeSwitch />
              </div>
            </header>
            <div className="flex flex-1 flex-col gap-4 p-4 pt-0">
              <Outlet context={[currentUser]} />
            </div>
          </SidebarInset>
        </SidebarProvider>
      ) : null}
    </>
  );
}
