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

import com.esports.model.Team;
import com.esports.repository.PlayerRepository;
import com.esports.repository.TeamRepository;
import com.esports.service.impl.TeamServiceImpl;

public class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private PlayerRepository playerRepository;

    private TeamService teamService;

    @Before
    public void setUp() {
        teamRepository = mock(TeamRepository.class);
        playerRepository = mock(PlayerRepository.class);
        teamService = new TeamServiceImpl();
        ((TeamServiceImpl) teamService).setTeamRepository(teamRepository);
        ((TeamServiceImpl) teamService).setPlayerRepository(playerRepository);
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        Team team = new Team();
        team.setId(id);
        when(teamRepository.findById(id)).thenReturn(team);

        Team result = teamService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(teamRepository).findById(id);
    }

    @Test
    public void testFindAll() {
        List<Team> teams = Arrays.asList(new Team(), new Team());
        when(teamRepository.findAll()).thenReturn(teams);

        List<Team> result = teamService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(teamRepository).findAll();
    }

    @Test
    public void testSave() {
        Team team = new Team();
        teamService.save(team);
        verify(teamRepository).save(team);
    }

    @Test
    public void testUpdate() {
        Team team = new Team();
        teamService.update(team);
        verify(teamRepository).update(team);
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        teamService.delete(id);
        verify(teamRepository).delete(id);
    }

}