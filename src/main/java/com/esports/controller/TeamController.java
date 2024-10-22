package com.esports.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esports.model.Team;
import com.esports.service.TeamService;

public class TeamController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamController.class);

    private TeamService teamService;

    public void setTeamService(TeamService teamService) {
        this.teamService = teamService;
    }

    public Team getTeam(Long id) {
        LOGGER.info("Getting team with id: {}", id);
        return teamService.findById(id);
    }

    public List<Team> getAllTeams() {
        LOGGER.info("Getting all teams");
        return teamService.findAll();
    }

    public void createTeam(Team team) {
        LOGGER.info("Creating new team: {}", team.getName());
        teamService.save(team);
    }

    public void updateTeam(Team team) {
        LOGGER.info("Updating team: {}", team.getName());
        teamService.update(team);
    }

    public void deleteTeam(Long id) {
        LOGGER.info("Deleting team with id: {}", id);
        teamService.delete(id);
    }

    public void addPlayerToTeam(Long teamId, Long playerId) {
        LOGGER.info("Adding player {} to team {}", playerId, teamId);
        teamService.addPlayerToTeam(teamId, playerId);
    }

    public void removePlayerFromTeam(Long teamId, Long playerId) {
        LOGGER.info("Removing player {} from team {}", playerId, teamId);
        teamService.removePlayerFromTeam(teamId, playerId);
    }
}