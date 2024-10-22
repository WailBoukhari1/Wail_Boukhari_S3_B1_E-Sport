package com.esports.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esports.model.Player;
import com.esports.service.PlayerService;

public class PlayerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerController.class);

    private PlayerService playerService;

    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    public Player getPlayer(Long id) {
        LOGGER.info("Getting player with id: {}", id);
        return playerService.findById(id);
    }

    public List<Player> getAllPlayers() {
        LOGGER.info("Getting all players");
        return playerService.findAll();
    }

    public void createPlayer(Player player) {
        LOGGER.info("Creating new player: {}", player.getUsername());
        playerService.save(player);
    }

    public void updatePlayer(Player player) {
        LOGGER.info("Updating player: {}", player.getUsername());
        playerService.update(player);
    }

    public void deletePlayer(Long id) {
        LOGGER.info("Deleting player with id: {}", id);
        playerService.delete(id);
    }
}
