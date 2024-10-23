package com.esports.service;

import java.util.List;

import com.esports.model.Team;

public interface TeamService {
    Team findById(Long id);
    List<Team> findAll();
    void save(Team team);
    void update(Team team);
    void delete(Long id);
    void addPlayerToTeam(String teamName, String playerUsername);
    void removePlayerFromTeam(String teamName, String playerUsername);
    Team getTeamByName(String name);
    void deleteTeamByName(String name);
}
