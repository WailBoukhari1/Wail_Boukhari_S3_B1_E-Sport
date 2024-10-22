package com.esports.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esports.model.Tournament;
import com.esports.service.TournamentService;

public class TournamentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TournamentController.class);

    private TournamentService tournamentService;

    public void setTournamentService(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    public Tournament getTournament(Long id) {
        LOGGER.info("Getting tournament with id: {}", id);
        return tournamentService.findById(id);
    }

    public List<Tournament> getAllTournaments() {
        LOGGER.info("Getting all tournaments");
        return tournamentService.findAll();
    }

    public void createTournament(Tournament tournament) {
        LOGGER.info("Creating new tournament: {}", tournament.getTitle());
        tournamentService.save(tournament);
    }

    public void updateTournament(Tournament tournament) {
        LOGGER.info("Updating tournament: {}", tournament.getTitle());
        tournamentService.update(tournament);
    }

    public void deleteTournament(Long id) {
        LOGGER.info("Deleting tournament with id: {}", id);
        tournamentService.delete(id);
    }

    public void addTeamToTournament(Long tournamentId, Long teamId) {
        LOGGER.info("Adding team {} to tournament {}", teamId, tournamentId);
        tournamentService.addTeamToTournament(tournamentId, teamId);
    }

    public void removeTeamFromTournament(Long tournamentId, Long teamId) {
        LOGGER.info("Removing team {} from tournament {}", teamId, tournamentId);
        tournamentService.removeTeamFromTournament(tournamentId, teamId);
    }

    public int calculateEstimatedDuration(Long tournamentId) {
        LOGGER.info("Calculating estimated duration for tournament {}", tournamentId);
        return tournamentService.calculateEstimatedDuration(tournamentId);
    }
}
