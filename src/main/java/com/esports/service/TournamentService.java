package com.esports.service;

import java.util.List;

import com.esports.model.Tournament;
import com.esports.model.TournamentStatus;

public interface TournamentService {
    Tournament findById(Long id);
    List<Tournament> findAll();
    void save(Tournament tournament);
    void update(Tournament tournament);
    void delete(Long id);
    void addTeamToTournament(String tournamentTitle, String teamName);
    void removeTeamFromTournament(String tournamentTitle, String teamName);
    Tournament getTournamentByTitle(String title);
    void deleteTournamentByTitle(String title);
    void changeStatus(String tournamentTitle, TournamentStatus newStatus);
    void cancelTournament(String tournamentTitle);
    void createTournamentWithGame(String title, String gameName, int difficulty, int averageMatchDuration, List<String> teamNames);
    int calculateEstimatedDuration(String title);
    void addGameToTournament(String tournamentTitle, String gameName);
}
