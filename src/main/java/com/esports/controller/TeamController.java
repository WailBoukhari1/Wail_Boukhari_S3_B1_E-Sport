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

    public List<Team> getAllTeams() {
        LOGGER.info("Getting all teams");
        return teamService.findAll();
    }

    public Team getTeamByName(String name) {
        LOGGER.info("Getting team by name: {}", name);
        return teamService.getTeamByName(name);
    }

    public void createTeam(Team team) {
        LOGGER.info("Creating new team: {}", team.getName());
        teamService.save(team);
    }

    public void updateTeam(Team team) {
        LOGGER.info("Updating team: {}", team.getName());
        teamService.update(team);
    }

    public void deleteTeamByName(String name) {
        LOGGER.info("Deleting team by name: {}", name);
        teamService.deleteTeamByName(name);
    }

    public void addPlayerToTeam(String teamName, String playerUsername) {
        LOGGER.info("Adding player {} to team {}", playerUsername, teamName);
        teamService.addPlayerToTeam(teamName, playerUsername);
    }

    public void removePlayerFromTeam(String teamName, String playerUsername) {
        LOGGER.info("Removing player {} from team {}", playerUsername, teamName);
        teamService.removePlayerFromTeam(teamName, playerUsername);
    }
}
