import FeaturedProject from "@/components/FeaturedProject";
import useStore from "@/store/useStore";

function Projects() {
  const { projects, currentTeam } = useStore();

  return (
    <div className="flex flex-wrap justify-center gap-6 sm:justify-start">
      {projects
        .filter((project) => project.teamId === currentTeam?.id)
        .some((project, index) => (
          <FeaturedProject key={index} {...project} />
        ))}
    </div>
  );
}
export default Projects;
