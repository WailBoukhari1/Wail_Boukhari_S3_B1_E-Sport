package com.esports.repository.impl;

import com.esports.model.Tournament;
import com.esports.repository.TournamentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class TournamentRepositoryImpl implements TournamentRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TournamentRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tournament findById(Long id) {
        LOGGER.info("Finding tournament with id: {}", id);
        return entityManager.find(Tournament.class, id);
    }

    @Override
    public List<Tournament> findAll() {
        LOGGER.info("Finding all tournaments");
        TypedQuery<Tournament> query = entityManager.createQuery("SELECT t FROM Tournament t", Tournament.class);
        return query.getResultList();
    }

    @Override
    public void save(Tournament tournament) {
        LOGGER.info("Saving tournament: {}", tournament.getTitle());
        entityManager.persist(tournament);
    }

    @Override
    public void update(Tournament tournament) {
        LOGGER.info("Updating tournament: {}", tournament.getTitle());
        entityManager.merge(tournament);
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Deleting tournament with id: {}", id);
        Tournament tournament = findById(id);
        if (tournament != null) {
            entityManager.remove(tournament);
        }
    }

    @Override
    public int calculateEstimatedDuration(Long tournamentId) {
        LOGGER.info("Calculating estimated duration for tournament with id: {}", tournamentId);
        Tournament tournament = findById(tournamentId);
        if (tournament == null) {
            return 0;
        }
        int numberOfTeams = tournament.getTeams().size();
        int averageMatchDuration = tournament.getGame().getAverageMatchDuration();
        return (numberOfTeams * averageMatchDuration) + tournament.getMatchBreakTime();
    }
}

