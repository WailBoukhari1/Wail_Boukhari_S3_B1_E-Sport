package com.esports.repository;

import java.util.List;

import com.esports.model.Player;

public interface PlayerRepository {
    Player findById(Long id);
    List<Player> findAll();
    void save(Player player);
    void update(Player player);
    void delete(Long id);
    Player findByUsername(String username);

}
