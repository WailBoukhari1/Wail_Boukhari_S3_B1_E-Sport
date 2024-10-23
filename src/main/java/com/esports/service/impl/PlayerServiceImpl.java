package com.esports.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.esports.model.Player;
import com.esports.repository.PlayerRepository;
import com.esports.service.PlayerService;

@Transactional
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository playerRepository;

    public void setPlayerRepository(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player findById(Long id) {
        return playerRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    @Override
    public void save(Player player) {
        playerRepository.save(player);
    }

    @Override
    public void update(Player player) {
        playerRepository.update(player);
    }

    @Override
    public void delete(Long id) {
        playerRepository.delete(id);
    }

    @Override
    public Player getPlayerByUsername(String username) {
        return playerRepository.findByUsername(username);
    }

    @Override
    public void deletePlayerByUsername(String username) {
        Player player = getPlayerByUsername(username);
        if (player != null) {
            delete(player.getId());
        }
    }
}
