package com.esports.service;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.esports.model.Game;
import com.esports.repository.GameRepository;
import com.esports.service.impl.GameServiceImpl;

public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    private GameService gameService;

    @Before
    public void setUp() {
        gameRepository = mock(GameRepository.class);
        gameService = new GameServiceImpl();
        ((GameServiceImpl) gameService).setGameRepository(gameRepository);
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        Game game = new Game();
        game.setId(id);
        when(gameRepository.findById(id)).thenReturn(game);

        Game result = gameService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(gameRepository).findById(id);
    }

    @Test
    public void testFindAll() {
        List<Game> games = Arrays.asList(new Game(), new Game());
        when(gameRepository.findAll()).thenReturn(games);

        List<Game> result = gameService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(gameRepository).findAll();
    }

    @Test
    public void testSave() {
        Game game = new Game();
        gameService.save(game);
        verify(gameRepository).save(game);
    }

    @Test
    public void testUpdate() {
        Game game = new Game();
        gameService.update(game);
        verify(gameRepository).update(game);
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        gameService.delete(id);
        verify(gameRepository).delete(id);
    }
}
