package com.esports.service;

import java.util.List;

import com.esports.model.Team;

public interface TeamService {
    Team findById(Long id);
    List<Team> findAll();
    void save(Team team);
    void update(Team team);
    void delete(Long id);
    void addPlayerToTeam(Long teamId, Long playerId);
    void removePlayerFromTeam(Long teamId, Long playerId);
}

