package com.esports.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.esports.model.Player;
import com.esports.model.Team;
import com.esports.repository.PlayerRepository;
import com.esports.repository.TeamRepository;
import com.esports.service.TeamService;

@Transactional
public class TeamServiceImpl implements TeamService {

    private TeamRepository teamRepository;
    private PlayerRepository playerRepository;

    @Override
    public Team findById(Long id) {
        return teamRepository.findById(id);
    }

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public void save(Team team) {
        teamRepository.save(team);
    }

    @Override
    public void update(Team team) {
        teamRepository.update(team);
    }

    @Override
    public void delete(Long id) {
        teamRepository.delete(id);
    }

    @Override
    public void addPlayerToTeam(Long teamId, Long playerId) {
        Team team = teamRepository.findById(teamId);
        Player player = playerRepository.findById(playerId);
        if (team != null && player != null) {
            team.getPlayers().add(player);
            player.setTeam(team);
            teamRepository.update(team);
            playerRepository.update(player);
        }
    }

    @Override
    public void removePlayerFromTeam(Long teamId, Long playerId) {
        Team team = teamRepository.findById(teamId);
        Player player = playerRepository.findById(playerId);
        if (team != null && player != null && team.getPlayers().contains(player)) {
            team.getPlayers().remove(player);
            player.setTeam(null);
            teamRepository.update(team);
            playerRepository.update(player);
        }
    }

    public void setTeamRepository(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void setPlayerRepository(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }
}
