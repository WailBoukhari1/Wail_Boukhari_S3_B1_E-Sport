package com.esports.repository.impl;

import com.esports.model.Team;
import com.esports.repository.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class TeamRepositoryImpl implements TeamRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Team findById(Long id) {
        LOGGER.info("Finding team with id: {}", id);
        return entityManager.find(Team.class, id);
    }

    @Override
    public List<Team> findAll() {
        LOGGER.info("Finding all teams");
        TypedQuery<Team> query = entityManager.createQuery("SELECT t FROM Team t", Team.class);
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
