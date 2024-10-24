package com.esports.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esports.model.Tournament;
import com.esports.repository.TournamentRepository;

public class TournamentRepositoryImpl implements TournamentRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TournamentRepositoryImpl.class);

    private static TournamentRepositoryImpl instance;
    private EntityManager entityManager;

    protected TournamentRepositoryImpl() {
    }

    public static synchronized TournamentRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new TournamentRepositoryImpl();
        }
        return instance;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Tournament findById(Long id) {
        LOGGER.info("Finding tournament with id: {}", id);
        return entityManager.find(Tournament.class, id);
    }

    @Override
    public List<Tournament> findAll() {
        LOGGER.info("Finding all tournaments");
        TypedQuery<Tournament> query = entityManager.createQuery("SELECT DISTINCT t FROM Tournament t LEFT JOIN FETCH t.teams", Tournament.class);
        return query.getResultList();
    }

    @Override
    public void save(Tournament tournament) {
        LOGGER.info("Saving tournament: {}", tournament.getTitle());
        entityManager.persist(tournament);
        entityManager.flush();
    }
    
    @Override
    public void update(Tournament tournament) {
        LOGGER.info("Updating tournament: {}", tournament.getTitle());
        entityManager.merge(tournament);
        entityManager.flush();
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
        Tournament tournament = findById(tournamentId);
        if (tournament == null || tournament.getGame() == null) {
            return 0;
        }
        int numberOfTeams = tournament.getTeams().size();
        int averageMatchDuration = tournament.getGame().getAverageMatchDuration();
        return (numberOfTeams * averageMatchDuration) + tournament.getMatchBreakTime();
    }

    @Override
    public Tournament findByTitle(String title) {
        LOGGER.info("Finding tournament with title: {}", title);
        TypedQuery<Tournament> query = entityManager.createQuery(
            "SELECT DISTINCT t FROM Tournament t LEFT JOIN FETCH t.teams LEFT JOIN FETCH t.game WHERE t.title = :title", Tournament.class);
        query.setParameter("title", title);
        List<Tournament> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
