package com.esports.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esports.model.Team;
import com.esports.repository.TeamRepository;

public class TeamRepositoryImpl implements TeamRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamRepositoryImpl.class);

    private static TeamRepositoryImpl instance;
    private EntityManager entityManager;

    private TeamRepositoryImpl() {
        // Private constructor to prevent instantiation
    }
    @Override
    public Team findByName(String name) {
        LOGGER.info("Finding team with name: {}", name);
        TypedQuery<Team> query = entityManager.createQuery("SELECT t FROM Team t WHERE t.name = :name", Team.class);
        query.setParameter("name", name);
        List<Team> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
    public static synchronized TeamRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new TeamRepositoryImpl();
        }
        return instance;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Team findById(Long id) {
        LOGGER.info("Finding team with id: {}", id);
        return entityManager.find(Team.class, id);
    }

    @Override
    public List<Team> findAll() {
        LOGGER.info("Finding all teams");
        TypedQuery<Team> query = entityManager.createQuery("SELECT DISTINCT t FROM Team t LEFT JOIN FETCH t.players", Team.class);
        return query.getResultList();
    }

    @Override
    public void save(Team team) {
        LOGGER.info("Saving team: {}", team.getName());
        entityManager.persist(team);
    }

    @Override
    public void update(Team team) {
        LOGGER.info("Updating team: {}", team.getName());
        entityManager.merge(team);
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Deleting team with id: {}", id);
        Team team = findById(id);
        if (team != null) {
            entityManager.remove(team);
        }
    }
}
