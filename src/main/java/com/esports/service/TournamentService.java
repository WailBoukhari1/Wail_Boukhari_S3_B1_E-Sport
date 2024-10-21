package com.esports.service;

import java.util.List;

import com.esports.model.Tournament;

public interface TournamentService {
    Tournament findById(Long id);
    List<Tournament> findAll();
    void save(Tournament tournament);
    void update(Tournament tournament);
    void delete(Long id);
    void addTeamToTournament(Long tournamentId, Long teamId);
    void removeTeamFromTournament(Long tournamentId, Long teamId);
    int calculateEstimatedDuration(Long tournamentId);
}

