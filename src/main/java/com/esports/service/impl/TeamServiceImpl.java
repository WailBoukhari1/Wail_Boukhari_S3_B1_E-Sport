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

    public void setTeamRepository(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void setPlayerRepository(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Team findById(Long id) {
        return teamRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional
    public void addPlayerToTeam(String teamName, String playerUsername) {
        Team team = teamRepository.findByName(teamName);
        if (team == null) {
            throw new IllegalArgumentException("Team not found with name: " + teamName);
        }

        Player player = playerRepository.findByUsername(playerUsername);
        if (player == null) {
            throw new IllegalArgumentException("Player not found with username: " + playerUsername);
        }

        // Remove player from their current team, if any
        if (player.getTeam() != null) {
            player.getTeam().getPlayers().remove(player);
        }

        team.getPlayers().add(player);
        player.setTeam(team);

        teamRepository.update(team);
        playerRepository.update(player);
    }

    @Override
    public void removePlayerFromTeam(String teamName, String playerUsername) {
        Team team = teamRepository.findByName(teamName);
        if (team == null) {
            throw new IllegalArgumentException("Team not found with name: " + teamName);
        }

        Player player = playerRepository.findByUsername(playerUsername);
        if (player == null) {
            throw new IllegalArgumentException("Player not found with username: " + playerUsername);
        }

        if (team.getPlayers().remove(player)) {
            player.setTeam(null);
            teamRepository.update(team);
            playerRepository.update(player);
        } else {
            throw new IllegalArgumentException("Player is not a member of this team");
        }
    }

    @Override
    public Team getTeamByName(String name) {
        return teamRepository.findByName(name);
    }

    @Override
    public void deleteTeamByName(String name) {
        Team team = teamRepository.findByName(name);
        if (team != null) {
            teamRepository.delete(team.getId());
        } else {
            throw new IllegalArgumentException("Team not found with name: " + name);
        }
    }
}
