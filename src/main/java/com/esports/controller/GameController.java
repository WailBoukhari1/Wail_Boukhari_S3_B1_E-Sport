package com.esports.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esports.model.Game;
import com.esports.service.GameService;

public class GameController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

    private GameService gameService;

    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    public List<Game> getAllGames() {
        LOGGER.info("Getting all games");
        return gameService.findAll();
    }

    public Game getGameByName(String name) {
        LOGGER.info("Getting game by name: {}", name);
        return gameService.getGameByName(name);
    }

    public void createGame(Game game) {
        LOGGER.info("Creating new game: {}", game.getName());
        gameService.save(game);
    }

    public void updateGame(Game game) {
        LOGGER.info("Updating game: {}", game.getName());
        gameService.update(game);
    }

    public void deleteGameByName(String name) {
        LOGGER.info("Deleting game by name: {}", name);
        gameService.deleteGameByName(name);
    }
}
