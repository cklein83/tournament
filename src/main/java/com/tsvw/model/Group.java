package com.tsvw.model;

import net.formio.validation.constraints.NotEmpty;
import org.apache.commons.collections.comparators.ComparatorChain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Entity(name = "groups")
public class Group implements Comparable {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private long id;

    @NotEmpty
    private String name;

    @ManyToOne(optional = false)
    private Tournament tournament;

    @OneToMany(targetEntity = Team.class)
    private List<Team> teams = new ArrayList<>();

    private Boolean dummyFinalGroup;

    // ---------------- constructors ------------------

    public Group(String name, Tournament tournament) {
        this(name, tournament, false);
    }

    public Group(String name, Tournament tournament, Boolean dummyFinalGroup) {
        this.name = name;
        this.tournament = tournament;
        this.dummyFinalGroup = dummyFinalGroup;
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

    protected Integer getOrZero(Integer i) {
        return i == null ? 0 : i;
    }

    @Transient
    private Comparator<Team> pointComparator = new Comparator<Team>() {
        @Override
        public int compare(Team team1, Team team2) {
            return getOrZero(team2.getPoints()).compareTo(getOrZero(team1.getPoints()));
        }
    };

    @Transient
    private Comparator<Team> goalDiffComparator = new Comparator<Team>() {
        @Override
        public int compare(Team team1, Team team2) {
            Integer diffT1 = getOrZero(team1.getGoals()) - getOrZero(team1.getContragoals());
            Integer diffT2 = getOrZero(team2.getGoals()) - getOrZero(team2.getContragoals());
            return diffT2.compareTo(diffT1);
        }
    };

    @Transient
    private Comparator<Team> mostGoalsComparator = new Comparator<Team>() {
        @Override
        public int compare(Team team1, Team team2) {
            return getOrZero(team2.getGoals()).compareTo(getOrZero(team1.getGoals()));
        }
    };

    /**
     * A) first sort by points
     * C) then sort by goal diff
     * B) then let the most scored goals be better in case two diffs are equal
     * @return
     */
    public List<com.tsvw.model.Team> getRankedTeams() {
        ComparatorChain cc = new ComparatorChain();
        cc.addComparator(pointComparator);
        cc.addComparator(goalDiffComparator);
        cc.addComparator(mostGoalsComparator);
        Collections.sort(teams, cc);
        return teams;
    }

    public void setTeams(List<com.tsvw.model.Team> teams) {
        this.teams = teams;
    }

    public void addTeam(Team team) {
        this.teams.add(team);
    }

    public Boolean getDummyFinalGroup() {
        return dummyFinalGroup;
    }

    public void setDummyFinalGroup(Boolean dummyFinalGroup) {
        this.dummyFinalGroup = dummyFinalGroup;
    }

    @Override
    public int compareTo(Object o) {
        Group g2 = (Group)o;
        return getName().compareTo(g2.getName());
    }


}
