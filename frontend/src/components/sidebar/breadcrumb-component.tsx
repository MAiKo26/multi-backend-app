import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbPage,
  BreadcrumbSeparator,
} from "@/components/ui/breadcrumb";
import { useLocation } from "react-router";
import { Fragment } from "react/jsx-runtime";

function BreadcrumbComponent() {
  const location = useLocation();
  const path = location.pathname
    .replaceAll("-", " ")
    .split("/")
    .filter((p) => p !== "");

  return (
    <Breadcrumb>
      <BreadcrumbList>
        {path.slice(0, -1).map((p, i) => (
          <Fragment key={`breadcrumb-${i}`}>
            <BreadcrumbItem className="hidden md:block">
              <BreadcrumbLink
                href={`/${path.slice(0, i + 1).join("/")}`}
                className="uppercase"
              >
                {p}
              </BreadcrumbLink>
            </BreadcrumbItem>
            <BreadcrumbSeparator className="hidden md:block" />
          </Fragment>
        ))}

        {path.length > 0 && (
          <BreadcrumbItem>
            <BreadcrumbPage className="uppercase">
              {path[path.length - 1].replaceAll(" ", "-")}
            </BreadcrumbPage>
          </BreadcrumbItem>
        )}
      </BreadcrumbList>
    </Breadcrumb>
  );
}
export default BreadcrumbComponent;
