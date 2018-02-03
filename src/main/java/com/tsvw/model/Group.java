package com.tsvw.model;

import net.formio.validation.constraints.NotEmpty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private long id;

    @NotEmpty
    private String name;

    @ManyToOne(optional = false)
    private Tournament tournament;

    @OneToMany(targetEntity = Team.class)
    private List<Team> teams = new ArrayList<>();

    // ---------------- constructors ------------------

    public Group(String name, Tournament tournament) {
        this.name = name;
        this.tournament = tournament;
    }

    public Group() {
    }

    // ---------------- getters and setters ------------------

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public List<com.tsvw.model.Team> getTeams() {
        return teams;
    }

    public void setTeams(List<com.tsvw.model.Team> teams) {
        this.teams = teams;
    }

    public void addTeam(Team team) {
        this.teams.add(team);
    }
}
