package com.esports.repository;

import java.util.List;

import com.esports.model.Game;

public interface GameRepository {
    Game findById(Long id);
    List<Game> findAll();
    void save(Game game);
    void update(Game game);
    void delete(Long id);
    Game findByName(String name);
   
}
