package com.esports.service;

import java.util.List;

import com.esports.model.Player;

public interface PlayerService {
    Player findById(Long id);
    List<Player> findAll();
    void save(Player player);
    void update(Player player);
    void delete(Long id);
    Player getPlayerByUsername(String username);
    void deletePlayerByUsername(String username);

}

