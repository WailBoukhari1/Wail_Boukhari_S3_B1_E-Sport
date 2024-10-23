package com.esports.repository;

import java.util.List;

import com.esports.model.Team;

public interface TeamRepository {
    Team findById(Long id);
    List<Team> findAll();
    void save(Team team);
    void update(Team team);
    void delete(Long id);
    Team findByName(String name);
}
