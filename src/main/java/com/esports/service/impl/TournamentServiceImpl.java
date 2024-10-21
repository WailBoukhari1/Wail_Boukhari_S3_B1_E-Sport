package com.esports.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.esports.model.Team;
import com.esports.model.Tournament;
import com.esports.repository.TeamRepository;
import com.esports.repository.TournamentRepository;
import com.esports.service.TournamentService;


@Transactional
public class TournamentServiceImpl implements TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public Tournament findById(Long id) {
        return tournamentRepository.findById(id);
    }

    @Override
    public List<Tournament> findAll() {
        return tournamentRepository.findAll();
    }

    @Override
    public void save(Tournament tournament) {
        tournamentRepository.save(tournament);
    }

    @Override
    public void update(Tournament tournament) {
        tournamentRepository.update(tournament);
    }

    @Override
    public void delete(Long id) {
        tournamentRepository.delete(id);
    }

    @Override
    public void addTeamToTournament(Long tournamentId, Long teamId) {
        Tournament tournament = tournamentRepository.findById(tournamentId);
        Team team = teamRepository.findById(teamId);
        if (tournament != null && team != null) {
            tournament.getTeams().add(team);
            team.getTournaments().add(tournament);
            tournamentRepository.update(tournament);
            teamRepository.update(team);
        }
    }

    @Override
    public void removeTeamFromTournament(Long tournamentId, Long teamId) {
        Tournament tournament = tournamentRepository.findById(tournamentId);
        Team team = teamRepository.findById(teamId);
        if (tournament != null && team != null && tournament.getTeams().contains(team)) {
            tournament.getTeams().remove(team);
            team.getTournaments().remove(tournament);
            tournamentRepository.update(tournament);
            teamRepository.update(team);
        }
    }

    @Override
    public int calculateEstimatedDuration(Long tournamentId) {
        return tournamentRepository.calculateEstimatedDuration(tournamentId);
    }
}
