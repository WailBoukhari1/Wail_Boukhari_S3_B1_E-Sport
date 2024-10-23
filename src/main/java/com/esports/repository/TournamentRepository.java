package com.esports.repository;

import java.util.List;

import com.esports.model.Tournament;

public interface TournamentRepository {
    Tournament findById(Long id);
    List<Tournament> findAll();
    void save(Tournament tournament);
    void update(Tournament tournament);
    void delete(Long id);
    Tournament findByTitle(String title);
    int calculateEstimatedDuration(Long tournamentId);
}
