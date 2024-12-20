import ThemeSwitch from "@/components/ThemeSwitch";
import { Outlet } from "react-router";

function AuthLayout() {
  return (
    <div>
      <header className="flex h-16 shrink-0 items-center justify-between gap-2 ease-linear">
        <div className="flex w-full items-center justify-end gap-2 px-4">
          <ThemeSwitch />
        </div>
      </header>
      <Outlet />
    </div>
  );
}
export default AuthLayout;
