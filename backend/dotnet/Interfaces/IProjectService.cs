using dotnet.Models;

namespace dotnet.Interfaces;

public interface IProjectService
{
    IEnumerable<Project> GetAllProjects(string teamId);
    void CreateProject(string name, string teamId);
    void AddMemberToProject(string projectId, string email);
    Project GetProjectById(string projectId);
    void DeleteProject(string projectId);

}