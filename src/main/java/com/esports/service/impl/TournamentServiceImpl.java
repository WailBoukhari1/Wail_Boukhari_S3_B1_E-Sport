package com.esports.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.esports.model.Game;
import com.esports.model.Team;
import com.esports.model.Tournament;
import com.esports.model.TournamentStatus;
import com.esports.repository.GameRepository;
import com.esports.repository.TeamRepository;
import com.esports.repository.TournamentRepository;
import com.esports.service.TournamentService;

@Transactional
public class TournamentServiceImpl implements TournamentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TournamentServiceImpl.class);

    private TournamentRepository tournamentRepository;
    private TeamRepository teamRepository;
    private GameRepository gameRepository;

    public void setTournamentRepository(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public void setTeamRepository(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Tournament findById(Long id) {
        LOGGER.info("Finding tournament with id: {}", id);
        return Optional.ofNullable(tournamentRepository.findById(id))
                .orElseThrow(() -> new IllegalArgumentException("Tournament not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tournament> findAll() {
        LOGGER.info("Finding all tournaments");
        return tournamentRepository.findAll();
    }

    @Override
    public void save(Tournament tournament) {
        LOGGER.info("Saving tournament: {}", tournament.getTitle());
        tournamentRepository.save(tournament);
    }

    @Override
    public void update(Tournament tournament) {
        LOGGER.info("Updating tournament: {}", tournament.getTitle());
        tournamentRepository.update(tournament);
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Deleting tournament with id: {}", id);
        tournamentRepository.delete(id);
    }

    @Override
    public void addTeamToTournament(String tournamentTitle, String teamName) {
        LOGGER.info("Adding team {} to tournament {}", teamName, tournamentTitle);
        Tournament tournament = tournamentRepository.findByTitle(tournamentTitle);
        if (tournament == null) {
            throw new IllegalArgumentException("Tournament not found with title: " + tournamentTitle);
        }
    
        Team team = teamRepository.findByName(teamName);
        if (team == null) {
            throw new IllegalArgumentException("Team not found with name: " + teamName);
        }
    
        tournament.getTeams().add(team);
        team.getTournaments().add(tournament);

        tournamentRepository.update(tournament);
        teamRepository.update(team);
    
        // Recalculate and update the estimated duration
        updateEstimatedDuration(tournament);
    }

    private void updateEstimatedDuration(Tournament tournament) {
        if (tournament == null || tournament.getGame() == null) {
            LOGGER.warn("Unable to calculate duration for tournament '{}'. Tournament or game might be missing.", tournament != null ? tournament.getTitle() : "null");
            return;
        }
        int duration = tournamentRepository.calculateEstimatedDuration(tournament.getId());
        tournament.setEstimatedDuration(duration);
        tournamentRepository.update(tournament);
    }

    @Override
    public void removeTeamFromTournament(String tournamentTitle, String teamName) {
        LOGGER.info("Removing team {} from tournament {}", teamName, tournamentTitle);
        Tournament tournament = tournamentRepository.findByTitle(tournamentTitle);
        if (tournament == null) {
            throw new IllegalArgumentException("Tournament not found with title: " + tournamentTitle);
        }

        Team team = teamRepository.findByName(teamName);
        if (team == null) {
            throw new IllegalArgumentException("Team not found with name: " + teamName);
        }

        if (tournament.getTeams().remove(team)) {
            team.getTournaments().remove(tournament);
            tournamentRepository.update(tournament);
            teamRepository.update(team);
        } else {
            LOGGER.warn("Team {} was not part of tournament {}", teamName, tournamentTitle);
        }
    }

    @Override
    public Tournament getTournamentByTitle(String title) {
        return tournamentRepository.findByTitle(title);
    }

    @Override
    public void deleteTournamentByTitle(String title) {
        Tournament tournament = getTournamentByTitle(title);
        if (tournament != null) {
            delete(tournament.getId());
        }
    }

    @Override
    public void changeStatus(String tournamentTitle, TournamentStatus newStatus) {
        Tournament tournament = getTournamentByTitle(tournamentTitle);
        if (tournament != null) {
            tournament.setStatus(newStatus);
            update(tournament);
        }
    }

    @Override
    public void cancelTournament(String tournamentTitle) {
        changeStatus(tournamentTitle, TournamentStatus.CANCELLED);
    }

    @Override
    public void createTournamentWithGame(String title, String gameName, int difficulty, int averageMatchDuration, List<String> teamNames) {
        Game game = new Game();
        game.setName(gameName);
        game.setDifficulty(difficulty);
        game.setAverageMatchDuration(averageMatchDuration);
        gameRepository.save(game);

        Tournament tournament = new Tournament();
        tournament.setTitle(title);
        tournament.setGame(game);
        tournament.setStatus(TournamentStatus.PLANNED);

        Set<Team> teams = new HashSet<>();
        for (String teamName : teamNames) {
            Team team = teamRepository.findByName(teamName);
            if (team == null) {
                team = new Team();
                team.setName(teamName);
                teamRepository.save(team);
            }
            teams.add(team);
            team.getTournaments().add(tournament); // Add this line
        }
        tournament.setTeams(teams);

        save(tournament);

        // Update teams to establish bidirectional relationship
        for (Team team : teams) {
            teamRepository.update(team);
        }

        updateEstimatedDuration(tournament);
    }

    @Override
    public void addGameToTournament(String tournamentTitle, String gameName) {
        Tournament tournament = tournamentRepository.findByTitle(tournamentTitle);
        if (tournament == null) {
            throw new IllegalArgumentException("Tournament not found with title: " + tournamentTitle);
        }

        Game game = gameRepository.findByName(gameName);
        if (game == null) {
            throw new IllegalArgumentException("Game not found with name: " + gameName);
        }

        tournament.setGame(game);
        tournamentRepository.update(tournament);
    }

    @Override
    public int calculateEstimatedDuration(String title) {
        Tournament tournament = tournamentRepository.findByTitle(title);
        if (tournament == null) {
            throw new IllegalArgumentException("Tournament not found with title: " + title);
        }
        int duration = tournamentRepository.calculateEstimatedDuration(tournament.getId());
        if (duration == 0) {
            LOGGER.warn("Unable to calculate duration for tournament '{}'. Game might be missing.", title);
            return 0;
        }
        tournament.setEstimatedDuration(duration);
        tournamentRepository.update(tournament);
        return duration;
    }
}
