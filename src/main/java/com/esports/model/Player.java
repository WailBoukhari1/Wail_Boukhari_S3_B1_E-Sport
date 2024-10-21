package com.esports.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @Min(13)
    private int age;

    @ManyToOne
    private Team team;

    // Getters and setters


public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getUsername() {
    return username;
}

public void setUsername(String username) {
    this.username = username;
}

public int getAge() {
    return age;
}

public void setAge(int age) {
    this.age = age;
}

public Team getTeam() {
    return team;
}

public void setTeam(Team team) {
    this.team = team;
}
}