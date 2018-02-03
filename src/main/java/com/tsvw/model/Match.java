package com.tsvw.model;

import com.tsvw.util.MatchTypeConverter;
import net.formio.validation.constraints.NotEmpty;

import javax.persistence.*;
import java.text.DateFormat;
import java.util.Date;

@Entity(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private long id;

    private Integer number;

    private Date startDate;

    @NotEmpty
    private Status status;

    @ManyToOne(optional = false)
    private Tournament tournament;

    private int goalsTeam1;
    private int goalsTeam2;

    @ManyToOne(optional = true)
    private Team team1;

    @ManyToOne(optional = true)
    private Team team2;

    private String dummyTeam1;
    private String dummyTeam2;

    @Convert(converter = MatchTypeConverter.class)
    private MatchType matchType;

    // ---------------- constructors ------------------

    public Match(Tournament tournament, Integer number, Date date, MatchType matchType, Team team1, Team team2) {
        this(tournament, number, date, matchType, team1, team2, "", "");
    }

    public Match(Tournament tournament, Integer number, Date date, MatchType matchType, Team team1, Team team2, String dummyTeam1, String dummyTeam2) {
        this.tournament = tournament;
        this.number = number;
        this.matchType = matchType;
        this.startDate = date;
        this.team1 = team1;
        this.team2 = team2;
        this.dummyTeam1 = dummyTeam1;
        this.dummyTeam2 = dummyTeam2;
        this.status = Status.NEW;
    }

    public Match() {
    }

    // ---------------- getters and setters ------------------

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getGoalsTeam1() {
        return goalsTeam1;
    }

    public void setGoalsTeam1(int goalsTeam1) {
        this.goalsTeam1 = goalsTeam1;
    }

    public int getGoalsTeam2() {
        return goalsTeam2;
    }

    public void setGoalsTeam2(int goalsTeam2) {
        this.goalsTeam2 = goalsTeam2;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public MatchType getMatchType() {
        return matchType;
    }

    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStartTimeFormatted() {
        return DateFormat.getTimeInstance(DateFormat.SHORT).format(startDate);
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getDummyTeam1() {
        return dummyTeam1;
    }

    public void setDummyTeam1(String dummyTeam1) {
        this.dummyTeam1 = dummyTeam1;
    }

    public String getDummyTeam2() {
        return dummyTeam2;
    }

    public void setDummyTeam2(String dummyTeam2) {
        this.dummyTeam2 = dummyTeam2;
    }
}
