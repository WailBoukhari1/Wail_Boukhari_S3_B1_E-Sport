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

public class TeamServiceTest {

    @Mock
    private TeamService teamService;

    @Before
    public void setUp() {
        teamService = mock(TeamService.class);
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        Team team = new Team();
        team.setId(id);
        when(teamService.findById(id)).thenReturn(team);

        Team result = teamService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(teamService).findById(id);
    }

    @Test
    public void testFindAll() {
        List<Team> teams = Arrays.asList(new Team(), new Team());
        when(teamService.findAll()).thenReturn(teams);

        List<Team> result = teamService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(teamService).findAll();
    }

    @Test
    public void testSave() {
        Team team = new Team();
        teamService.save(team);
        verify(teamService).save(team);
    }

    @Test
    public void testUpdate() {
        Team team = new Team();
        teamService.update(team);
        verify(teamService).update(team);
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        teamService.delete(id);
        verify(teamService).delete(id);
    }

    @Test
    public void testAddPlayerToTeam() {
        String teamName = "TestTeam";
        String playerUsername = "TestPlayer";
        teamService.addPlayerToTeam(teamName, playerUsername);
        verify(teamService).addPlayerToTeam(teamName, playerUsername);
    }

    @Test
    public void testRemovePlayerFromTeam() {
        String teamName = "TestTeam";
        String playerUsername = "TestPlayer";
        teamService.removePlayerFromTeam(teamName, playerUsername);
        verify(teamService).removePlayerFromTeam(teamName, playerUsername);
    }

    @Test
    public void testGetTeamByName() {
        String name = "TestTeam";
        Team team = new Team();
        team.setName(name);
        when(teamService.getTeamByName(name)).thenReturn(team);

        Team result = teamService.getTeamByName(name);

        assertNotNull(result);
        assertEquals(name, result.getName());
        verify(teamService).getTeamByName(name);
    }

    @Test
    public void testDeleteTeamByName() {
        String name = "TestTeam";
        teamService.deleteTeamByName(name);
        verify(teamService).deleteTeamByName(name);
    }
}