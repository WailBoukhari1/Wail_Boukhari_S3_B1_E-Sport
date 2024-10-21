package com.esports.repository.impl;

import com.esports.model.Tournament;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TournamentRepositoryExtension extends TournamentRepositoryImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(TournamentRepositoryExtension.class);

    @Override
    public int calculateEstimatedDuration(Long tournamentId) {
        LOGGER.info("Calculating advanced estimated duration for tournament with id: {}", tournamentId);
        Tournament tournament = findById(tournamentId);
        if (tournament == null) {
            return 0;
        }
        int numberOfTeams = tournament.getTeams().size();
        int averageMatchDuration = tournament.getGame().getAverageMatchDuration();
        int gameDifficulty = tournament.getGame().getDifficulty();
        return (numberOfTeams * averageMatchDuration * gameDifficulty) + tournament.getMatchBreakTime() + tournament.getCeremonyTime();
    }
}
