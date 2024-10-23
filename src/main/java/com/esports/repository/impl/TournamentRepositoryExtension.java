package com.esports.repository.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esports.model.Tournament;
import com.esports.repository.TournamentRepository;

public class TournamentRepositoryExtension implements TournamentRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TournamentRepositoryExtension.class);
    
    private TournamentRepositoryImpl baseRepository;

    public TournamentRepositoryExtension(TournamentRepositoryImpl baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    public Tournament findById(Long id) {
        return baseRepository.findById(id);
    }

    @Override
    public List<Tournament> findAll() {
        return baseRepository.findAll();
    }

    @Override
    public void save(Tournament tournament) {
        baseRepository.save(tournament);
    }

    @Override
    public void update(Tournament tournament) {
        baseRepository.update(tournament);
    }

    @Override
    public void delete(Long id) {
        baseRepository.delete(id);
    }
    @Override
    public Tournament findByTitle(String title) {
        return baseRepository.findByTitle(title);
    }

    @Override
    public int calculateEstimatedDuration(Long tournamentId) {
        LOGGER.info("Calculating advanced estimated duration for tournament with id: {}", tournamentId);
        Tournament tournament = findById(tournamentId);
        if (tournament == null || tournament.getGame() == null) {
            return 0;
        }
        int numberOfTeams = tournament.getTeams().size();
        int averageMatchDuration = tournament.getGame().getAverageMatchDuration();
        int gameDifficulty = tournament.getGame().getDifficulty();
        return (numberOfTeams * averageMatchDuration * gameDifficulty) + tournament.getMatchBreakTime() + tournament.getCeremonyTime();
    }
}
