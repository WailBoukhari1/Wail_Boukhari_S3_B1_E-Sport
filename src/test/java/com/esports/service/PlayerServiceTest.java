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

import com.esports.model.Player;
import com.esports.repository.PlayerRepository;
import com.esports.service.impl.PlayerServiceImpl;

public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    private PlayerService playerService;

    @Before
    public void setUp() {
        playerRepository = mock(PlayerRepository.class);
        playerService = new PlayerServiceImpl();
        ((PlayerServiceImpl) playerService).setPlayerRepository(playerRepository);
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        Player player = new Player();
        player.setId(id);
        when(playerRepository.findById(id)).thenReturn(player);

        Player result = playerService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(playerRepository).findById(id);
    }

    @Test
    public void testFindAll() {
        List<Player> players = Arrays.asList(new Player(), new Player());
        when(playerRepository.findAll()).thenReturn(players);

        List<Player> result = playerService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(playerRepository).findAll();
    }

    @Test
    public void testSave() {
        Player player = new Player();
        playerService.save(player);
        verify(playerRepository).save(player);
    }

    @Test
    public void testUpdate() {
        Player player = new Player();
        playerService.update(player);
        verify(playerRepository).update(player);
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        playerService.delete(id);
        verify(playerRepository).delete(id);
    }
}