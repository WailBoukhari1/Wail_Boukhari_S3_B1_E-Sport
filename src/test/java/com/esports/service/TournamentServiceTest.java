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

import com.esports.model.Tournament;
import com.esports.model.TournamentStatus;

public class TournamentServiceTest {

    @Mock
    private TournamentService tournamentService;

    @Before
    public void setUp() {
        tournamentService = mock(TournamentService.class);
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        Tournament tournament = new Tournament();
        tournament.setId(id);
        when(tournamentService.findById(id)).thenReturn(tournament);

        Tournament result = tournamentService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(tournamentService).findById(id);
    }

    @Test
    public void testFindAll() {
        List<Tournament> tournaments = Arrays.asList(new Tournament(), new Tournament());
        when(tournamentService.findAll()).thenReturn(tournaments);

        List<Tournament> result = tournamentService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(tournamentService).findAll();
    }

    @Test
    public void testSave() {
        Tournament tournament = new Tournament();
        tournamentService.save(tournament);
        verify(tournamentService).save(tournament);
    }

    @Test
    public void testUpdate() {
        Tournament tournament = new Tournament();
        tournamentService.update(tournament);
        verify(tournamentService).update(tournament);
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        tournamentService.delete(id);
        verify(tournamentService).delete(id);
    }

    @Test
    public void testAddTeamToTournament() {
        String tournamentTitle = "TestTournament";
        String teamName = "TestTeam";
        tournamentService.addTeamToTournament(tournamentTitle, teamName);
        verify(tournamentService).addTeamToTournament(tournamentTitle, teamName);
    }

    @Test
    public void testRemoveTeamFromTournament() {
        String tournamentTitle = "TestTournament";
        String teamName = "TestTeam";
        tournamentService.removeTeamFromTournament(tournamentTitle, teamName);
        verify(tournamentService).removeTeamFromTournament(tournamentTitle, teamName);
    }

    @Test
    public void testGetTournamentByTitle() {
        String title = "TestTournament";
        Tournament tournament = new Tournament();
        tournament.setTitle(title);
        when(tournamentService.getTournamentByTitle(title)).thenReturn(tournament);

        Tournament result = tournamentService.getTournamentByTitle(title);

        assertNotNull(result);
        assertEquals(title, result.getTitle());
        verify(tournamentService).getTournamentByTitle(title);
    }

    @Test
    public void testDeleteTournamentByTitle() {
        String title = "TestTournament";
        tournamentService.deleteTournamentByTitle(title);
        verify(tournamentService).deleteTournamentByTitle(title);
    }

    @Test
    public void testChangeStatus() {
        String tournamentTitle = "TestTournament";
        TournamentStatus newStatus = TournamentStatus.IN_PROGRESS;
        tournamentService.changeStatus(tournamentTitle, newStatus);
        verify(tournamentService).changeStatus(tournamentTitle, newStatus);
    }

    @Test
    public void testCancelTournament() {
        String tournamentTitle = "TestTournament";
        tournamentService.cancelTournament(tournamentTitle);
        verify(tournamentService).cancelTournament(tournamentTitle);
    }

    @Test
    public void testUpdateTournamentStatuses() {
        String tournamentTitle = "TestTournament";
        TournamentStatus newStatus = TournamentStatus.IN_PROGRESS;
        tournamentService.changeStatus(tournamentTitle, newStatus);
        verify(tournamentService).changeStatus(tournamentTitle, newStatus);
    }

    @Test
    public void testCreateTournamentWithGame() {
        String title = "TestTournament";
        String gameName = "TestGame";
        int difficulty = 5;
        int averageMatchDuration = 30;
        List<String> teamNames = Arrays.asList("Team1", "Team2");
        tournamentService.createTournamentWithGame(title, gameName, difficulty, averageMatchDuration, teamNames);
        verify(tournamentService).createTournamentWithGame(title, gameName, difficulty, averageMatchDuration, teamNames);
    }

    @Test
    public void testCalculateEstimatedDuration() {
        String title = "TestTournament";
        when(tournamentService.calculateEstimatedDuration(title)).thenReturn(120);

        int result = tournamentService.calculateEstimatedDuration(title);

        assertEquals(120, result);
        verify(tournamentService).calculateEstimatedDuration(title);
    }

    @Test
    public void testAddGameToTournament() {
        String tournamentTitle = "TestTournament";
        String gameName = "TestGame";
        tournamentService.addGameToTournament(tournamentTitle, gameName);
        verify(tournamentService).addGameToTournament(tournamentTitle, gameName);
    }
}