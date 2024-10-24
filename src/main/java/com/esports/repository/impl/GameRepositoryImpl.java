package com.esports.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esports.model.Game;
import com.esports.repository.GameRepository;

public class GameRepositoryImpl implements GameRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameRepositoryImpl.class);

    private EntityManager entityManager;

    private static GameRepositoryImpl instance;

    private GameRepositoryImpl() {
        // Private constructor to prevent instantiation
    }

    public static synchronized GameRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new GameRepositoryImpl();
        }
        return instance;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Game findById(Long id) {
        LOGGER.info("Finding game with id: {}", id);
        return entityManager.find(Game.class, id);
    }

    @Override
    public List<Game> findAll() {
        LOGGER.info("Finding all games");
        TypedQuery<Game> query = entityManager.createQuery("SELECT DISTINCT g FROM Game g", Game.class);
        return query.getResultList();
    }
    @Override
    public void save(Game game) {
        LOGGER.info("Saving game: {}", game.getName());
        entityManager.persist(game);
    }

    @Override
    public void update(Game game) {
        LOGGER.info("Updating game: {}", game.getName());
        entityManager.merge(game);
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Deleting game with id: {}", id);
        Game game = findById(id);
        if (game != null) {
            entityManager.remove(game);
        }
    }

    @Override
    public Game findByName(String name) {
        LOGGER.info("Finding game with name: {}", name);
        TypedQuery<Game> query = entityManager.createQuery("SELECT g FROM Game g WHERE g.name = :name", Game.class);
        query.setParameter("name", name);
        List<Game> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

}
