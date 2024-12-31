package tn.maiko26.springboot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import tn.maiko26.springboot.exception.CustomException;
import tn.maiko26.springboot.model.Team;
import tn.maiko26.springboot.model.User;
import tn.maiko26.springboot.model.relations.TeamMember;
import tn.maiko26.springboot.repository.TeamMemberRepository;
import tn.maiko26.springboot.repository.TeamRepository;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@PreAuthorize("hasRole('ROLE_admin')")
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserService userService;
    private final TeamMemberRepository teamMemberRepository;

    public TeamService(TeamRepository teamRepository, UserService userService, TeamMemberRepository teamMemberRepository) {
        this.teamRepository = teamRepository;
        this.userService = userService;
        this.teamMemberRepository = teamMemberRepository;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }


    public Team getTeamById(String id) {
        return teamRepository.findById(id).orElseThrow(() -> new CustomException("No Team Found With Id: " + id, 400));


    }

    public void createTeam(Team team) {
        try {

            Team newTeam = new Team();

            newTeam.setId(team.getId());
            newTeam.setName(team.getName());
            newTeam.setCreatedAt(new Date());

            teamRepository.save(newTeam);

        } catch (Exception e) {

            throw new CustomException("Error Creating the team because " + e.getMessage(), 400);
        }


    }

    public void updateTeam(String teamId, Team newTeam) {
        try {

            Team updatedTeam = this.getTeamById(teamId);

            updatedTeam.setName(newTeam.getName());
            updatedTeam.setMembers(newTeam.getMembers());
            updatedTeam.setProjects(newTeam.getProjects());

            teamRepository.save(updatedTeam);

        } catch (Exception e) {

            throw new CustomException("Error Creating the team because " + e.getMessage(), 400);
        }

    }

    public void deleteTeam(String teamId) {
        teamRepository.deleteById(teamId);

    }

    public void addMemberToTeam(String teamId, String email) {

        Team team = this.getTeamById(teamId);

        User user = userService.getUserByEmail(email);

        TeamMember teamMembership = new TeamMember(team, user);

        teamMemberRepository.save(teamMembership);

    }

    @PreAuthorize("hasAnyRole('ROLE_admin','ROLE_user')")
    public List<Team> getTeamsByUserEmail(String email) {

        return teamRepository.findTeamsByUserEmail(email);


    }


}
