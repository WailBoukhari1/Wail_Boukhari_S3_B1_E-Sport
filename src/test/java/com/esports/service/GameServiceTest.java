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

public class GameServiceTest {

    @Mock
    private GameService gameService;

    @Before
    public void setUp() {
        gameService = mock(GameService.class);
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        Game game = new Game();
        game.setId(id);
        when(gameService.findById(id)).thenReturn(game);

        Game result = gameService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(gameService).findById(id);
    }

    @Test
    public void testFindAll() {
        List<Game> games = Arrays.asList(new Game(), new Game());
        when(gameService.findAll()).thenReturn(games);

        List<Game> result = gameService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(gameService).findAll();
    }

    @Test
    public void testSave() {
        Game game = new Game();
        gameService.save(game);
        verify(gameService).save(game);
    }

    @Test
    public void testUpdate() {
        Game game = new Game();
        gameService.update(game);
        verify(gameService).update(game);
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        gameService.delete(id);
        verify(gameService).delete(id);
    }
}