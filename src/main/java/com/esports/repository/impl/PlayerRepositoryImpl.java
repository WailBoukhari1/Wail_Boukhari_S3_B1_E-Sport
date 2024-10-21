package com.esports.repository.impl;

import com.esports.model.Player;
import com.esports.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class PlayerRepositoryImpl implements PlayerRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

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
