package com.esports.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esports.model.Player;
import com.esports.repository.PlayerRepository;

public class PlayerRepositoryImpl implements PlayerRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerRepositoryImpl.class);

    private static PlayerRepositoryImpl instance;
    private EntityManager entityManager;

    private PlayerRepositoryImpl() {
        // Private constructor to prevent instantiation
    }

    public static synchronized PlayerRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new PlayerRepositoryImpl();
        }
        return instance;
    }
    @Override
    public Player findByUsername(String username) {
        LOGGER.info("Finding player with username: {}", username);
        TypedQuery<Player> query = entityManager.createQuery("SELECT p FROM Player p WHERE p.username = :username", Player.class);
        query.setParameter("username", username);
        List<Player> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Player findById(Long id) {
        LOGGER.info("Finding player with id: {}", id);
        return entityManager.find(Player.class, id);
    }

    @Override
    public List<Player> findAll() {
        LOGGER.info("Finding all players");
        TypedQuery<Player> query = entityManager.createQuery("SELECT p FROM Player p", Player.class);
        return query.getResultList();
    }

    @Override
    public void save(Player player) {
        LOGGER.info("Saving player: {}", player.getUsername());
        entityManager.persist(player);
    }

    @Override
    public void update(Player player) {
        LOGGER.info("Updating player: {}", player.getUsername());
        entityManager.merge(player);
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Deleting player with id: {}", id);
        Player player = findById(id);
        if (player != null) {
            entityManager.remove(player);
        }
    }
}
