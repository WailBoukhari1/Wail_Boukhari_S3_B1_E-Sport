package com.esports.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esports.model.Tournament;
import com.esports.model.TournamentStatus;
import com.esports.service.TournamentService;

public class TournamentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TournamentController.class);

    private TournamentService tournamentService;

    public void setTournamentService(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    public List<Tournament> getAllTournaments() {
        LOGGER.info("Getting all tournaments");
        return tournamentService.findAll();
    }

    public Tournament getTournamentByTitle(String title) {
        LOGGER.info("Getting tournament by title: {}", title);
        return tournamentService.getTournamentByTitle(title);
    }

    public void createTournament(Tournament tournament) {
        LOGGER.info("Creating new tournament: {}", tournament.getTitle());
        tournamentService.save(tournament);
    }

    public void updateTournament(Tournament tournament) {
        LOGGER.info("Updating tournament: {}", tournament.getTitle());
        tournamentService.update(tournament);
    }

    public void deleteTournamentByTitle(String title) {
        LOGGER.info("Deleting tournament by title: {}", title);
        tournamentService.deleteTournamentByTitle(title);
    }

    public void addTeamToTournament(String tournamentTitle, String teamName) {
        LOGGER.info("Adding team {} to tournament {}", teamName, tournamentTitle);
        tournamentService.addTeamToTournament(tournamentTitle, teamName);
    }

    public void removeTeamFromTournament(String tournamentTitle, String teamName) {
        LOGGER.info("Removing team {} from tournament {}", teamName, tournamentTitle);
        tournamentService.removeTeamFromTournament(tournamentTitle, teamName);
    }

    public void changeStatus(String tournamentTitle, TournamentStatus newStatus) {
        LOGGER.info("Changing status of tournament {} to {}", tournamentTitle, newStatus);
        tournamentService.changeStatus(tournamentTitle, newStatus);
    }

    public void cancelTournament(String tournamentTitle) {
        LOGGER.info("Cancelling tournament with title: {}", tournamentTitle);
        tournamentService.cancelTournament(tournamentTitle);
    }

    public void createTournamentWithGame(String title, String gameName, int difficulty, int averageMatchDuration, List<String> teamNames) {
        LOGGER.info("Creating new tournament with game and teams: {}", title);
        tournamentService.createTournamentWithGame(title, gameName, difficulty, averageMatchDuration, teamNames);
    }
    public void addGameToTournament(String tournamentTitle, String gameName) {
        LOGGER.info("Adding game {} to tournament {}", gameName, tournamentTitle);
        tournamentService.addGameToTournament(tournamentTitle, gameName);
    }
    
    
    public int calculateEstimatedDuration(String title) {
        LOGGER.info("Calculating estimated duration for tournament: {}", title);
        return tournamentService.calculateEstimatedDuration(title);
    }
}
