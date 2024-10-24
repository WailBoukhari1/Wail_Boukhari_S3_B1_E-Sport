package com.esports.service;

import java.util.List;

import com.esports.model.Game;

public interface GameService {
    Game findById(Long id);
    List<Game> findAll();
    void save(Game game);
    void update(Game game);
    void delete(Long id);
    Game getGameByName(String name);
    void deleteGameByName(String name);
}
