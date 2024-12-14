import { House, Power } from "lucide-react";
import ThemeSwitch from "./ThemeSwitch";
import logout from "@/lib/loggingout";
import { Link } from "react-router";

function Header() {
  return (
    <nav className="absolute top-0 flex w-full items-center justify-center gap-10 p-1">
      <Link to="/">
        <House className="h-6 w-6 hover:cursor-pointer hover:text-slate-500" />
      </Link>
      <ThemeSwitch />
      <Power
        className="h-6 w-6 hover:cursor-pointer hover:text-slate-500"
        onClick={() => logout()}
      />
    </nav>
  );
}
export default Header;
