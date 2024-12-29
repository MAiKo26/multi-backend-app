package tn.maiko26.springboot.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import tn.maiko26.springboot.exception.ResourceNotImplementedException;
import tn.maiko26.springboot.model.Team;
import tn.maiko26.springboot.model.User;

import java.util.List;

@Service
@Slf4j
public class TeamService {

    public List<Team> getAllTeams() {
        throw new ResourceNotImplementedException();
    }

    public void createTeam(Team team) {
        throw new ResourceNotImplementedException();
    }

    public void updateTeam(String teamId, Team newTeam) {
        throw new ResourceNotImplementedException();

    }

    public void deleteTeam(String teamId) {
        throw new ResourceNotImplementedException();

    }

    public void addMemberToTeam(User user) {
        throw new ResourceNotImplementedException();

    }

    public void getTeamsByUserEmail(String email) {

        throw new ResourceNotImplementedException();
    }


}
