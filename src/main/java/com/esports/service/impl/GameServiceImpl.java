package com.esports.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.esports.model.Game;
import com.esports.repository.GameRepository;
import com.esports.repository.TeamRepository;
import com.esports.service.GameService;

@Transactional
public class GameServiceImpl implements GameService {

    private GameRepository gameRepository;
    private TeamRepository teamRepository;

    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void setTeamRepository(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public Game findById(Long id) {
        return gameRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    @Override
    public void save(Game game) {
        gameRepository.save(game);
    }

    @Override
    public void update(Game game) {
        gameRepository.update(game);
    }

    @Override
    public void delete(Long id) {
        gameRepository.delete(id);
    }

    @Override
    public Game getGameByName(String name) {
        return gameRepository.findByName(name);
    }

    @Override
    public void deleteGameByName(String name) {
        Game game = getGameByName(name);
        if (game != null) {
            delete(game.getId());
        }
    }
}
