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

    public List<Player> getAllPlayers() {
        return playerService.findAll();
    }

    public Player getPlayerByUsername(String username) {
        return playerService.getPlayerByUsername(username);
    }

    public void createPlayer(Player player) {
        playerService.save(player);
    }

    public void updatePlayer(Player player) {
        playerService.update(player);
    }

    public void deletePlayerByUsername(String username) {
        playerService.deletePlayerByUsername(username);
    }
}
