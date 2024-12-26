package tn.maiko26.springboot.services;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import tn.maiko26.springboot.models.Team;
import tn.maiko26.springboot.models.User;

import java.util.List;

@Service
public class TeamService {

    public List<Team> getAllTeams() {
        throw new NotImplementedException();

    }

    public void createTeam(Team team) {
        throw new NotImplementedException();
    }

    public void updateTeam(String teamId,Team newTeam) {
        throw new NotImplementedException();

    }
    public void deleteTeam(String teamId) {
        throw new NotImplementedException();

    }

    public void addMemberToTeam(User user) {
        throw new NotImplementedException();

    }

    public void getTeamsByUserEmail(String email) {

        throw new NotImplementedException();
    }


}
