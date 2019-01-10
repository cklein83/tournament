package com.tsvw.model;

import net.formio.validation.constraints.NotEmpty;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "teams")
public class Team implements Comparable {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private long id;

    @NotEmpty
    private String name;

    @ManyToOne(optional = true)
    private Group group;

    @ManyToMany(targetEntity = Match.class)
    @OrderBy("number ASC")
    private List<Match> matches = new ArrayList<>();

    @Transient
    private Integer points = null;

    @Transient
    private Integer goals = null;

    @Transient
    private Integer contragoals = null;

    private Boolean finalDummyTeam;

    // ---------------- constructors ------------------

    public Team(String name, Group group) {
        this(name, group, false);
    }

    public Team(String name, Group group, Boolean finalDummyTeam) {
        this.name = name;
        this.group = group;
        this.finalDummyTeam = finalDummyTeam;
    }

    public Team() {

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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public List<Match> getMatchesByMatchType(MatchType matchType) {
        Hibernate.initialize(matches);
        List<Match> matches = getMatches().stream()
                .filter(m -> m.getMatchType() == matchType)
                .collect(Collectors.toList());
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    protected boolean hasStartedMatches() {
        return getMatchesByMatchType(MatchType.PRELIM).stream().filter(m -> m.getStatus() != Status.NEW).toArray().length > 0;
    }

    public Integer getPoints() {
        if (points == null && hasStartedMatches()) {
            final List<Match> matchesByMatchType = getMatchesByMatchType(MatchType.PRELIM);
            points = 0;
            List<Match> wins = matchesByMatchType.stream().filter(
                    m ->
                            m.getStatus() != Status.NEW && (
                                m.getTeam1().getId() == id && m.getGoalsTeam1() > m.getGoalsTeam2() ||
                                    m.getTeam2().getId() == id && m.getGoalsTeam2() > m.getGoalsTeam1())

            ).collect(Collectors.toList());
            List<Match> remis = matchesByMatchType.stream().filter(
                    m ->
                            m.getStatus() != Status.NEW &&
                                (m.getTeam1().getId() == id || m.getTeam2().getId() == id) &&
                                    m.getGoalsTeam1() == m.getGoalsTeam2()
            ).collect(Collectors.toList());
            if (!wins.isEmpty() || !remis.isEmpty()) {
                if (!wins.isEmpty()) {
                    points += wins.size()*3;
                }
                if (!remis.isEmpty()) {
                    points += remis.size();
                }
            }
        }
        return points;
    }

    public Integer getGoals() {
        if (goals == null && hasStartedMatches()) {
            final List<Match> matchesByMatchType = getMatchesByMatchType(MatchType.PRELIM);
            goals = 0;
            goals += matchesByMatchType.stream()
                    .filter(m -> m.getStatus() != Status.NEW && (m.getTeam1().getId() == id))
                    .mapToInt(m -> m.getGoalsTeam1()).sum();
            goals += matchesByMatchType.stream()
                    .filter(m -> m.getStatus() != Status.NEW && (m.getTeam2().getId() == id))
                    .mapToInt(m -> m.getGoalsTeam2()).sum();
        }
        return goals;
    }

    public Integer getContragoals() {
        if (contragoals == null && hasStartedMatches()) {
            final List<Match> matchesByMatchType = getMatchesByMatchType(MatchType.PRELIM);
            contragoals = 0;
            contragoals += matchesByMatchType.stream()
                    .filter(m -> m.getStatus() != Status.NEW && (m.getTeam1().getId() == id))
                    .mapToInt(m -> m.getGoalsTeam2()).sum();
            contragoals += matchesByMatchType.stream()
                    .filter(m -> m.getStatus() != Status.NEW && (m.getTeam2().getId() == id))
                    .mapToInt(m -> m.getGoalsTeam1()).sum();
        }
        return contragoals;
    }

    protected Integer getOrZero(Integer i) {
        return i == null ? 0 : i;
    }

    @Override
    public int compareTo(Object o) {

        /*

     //.sorted((t1, t2) -> Integer.compare(getOrZero(t2.getPoints()), getOrZero(t1.getPoints())))
                /*
                .sorted((t1, t2) -> Integer.compare(
                        getOrZero(t2.getGoals()) - getOrZero(t2.getContragoals()), // goals minus contragoals of T2
                        getOrZero(t1.getGoals()) - getOrZero(t1.getContragoals()))) // goals minus contragoals of T1
                        */
        /*
         */

        Team t2 = (Team)o;
        return Comparator.comparingInt(Team::getPoints)
                //.thenComparingInt(t -> getOrZero(t.getGoals()) - getOrZero(t.getContragoals()))
                .compare(this, t2);
    }

    public Boolean getFinalDummyTeam() {
        return finalDummyTeam;
    }

    public void setFinalDummyTeam(Boolean finalDummyTeam) {
        this.finalDummyTeam = finalDummyTeam;
    }
}
