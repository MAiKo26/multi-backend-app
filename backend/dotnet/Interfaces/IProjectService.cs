using dotnet.Models;

namespace dotnet.Interfaces;

public interface IProjectService
{
    IEnumerable<Project> GetAllProjects();
    void CreateProject();
    void AddMemberToProject();
    void DeleteProject();

}