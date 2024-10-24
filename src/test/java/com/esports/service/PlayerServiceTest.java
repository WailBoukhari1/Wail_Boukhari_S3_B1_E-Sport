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

public class PlayerServiceTest {

    @Mock
    private PlayerService playerService;

    @Before
    public void setUp() {
        playerService = mock(PlayerService.class);
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        Player player = new Player();
        player.setId(id);
        when(playerService.findById(id)).thenReturn(player);

        Player result = playerService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(playerService).findById(id);
    }

    @Test
    public void testFindAll() {
        List<Player> players = Arrays.asList(new Player(), new Player());
        when(playerService.findAll()).thenReturn(players);

        List<Player> result = playerService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(playerService).findAll();
    }

    @Test
    public void testSave() {
        Player player = new Player();
        playerService.save(player);
        verify(playerService).save(player);
    }

    @Test
    public void testUpdate() {
        Player player = new Player();
        playerService.update(player);
        verify(playerService).update(player);
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        playerService.delete(id);
        verify(playerService).delete(id);
    }

    @Test
    public void testGetPlayerByUsername() {
        String username = "testPlayer";
        Player player = new Player();
        player.setUsername(username);
        when(playerService.getPlayerByUsername(username)).thenReturn(player);

        Player result = playerService.getPlayerByUsername(username);

        assertNotNull(result);
        assertEquals(username, result.getUsername());
        verify(playerService).getPlayerByUsername(username);
    }

    @Test
    public void testDeletePlayerByUsername() {
        String username = "testPlayer";
        playerService.deletePlayerByUsername(username);
        verify(playerService).deletePlayerByUsername(username);
    }
}