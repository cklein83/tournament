package com.tsvw.model;

import net.formio.binding.Ignored;
import net.formio.validation.constraints.NotEmpty;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "tournaments")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotEmpty
    private String name;

    private String subtitle;

    @NotEmpty
    private Date date;

    @NotEmpty
    private Integer qualiCount;

    @NotEmpty
    private Integer matchTimeSeconds;

    @NotEmpty
    private Integer pauseInBetweenSeconds;

    @NotEmpty
    private Integer pauseBeforeFinalSeconds;

    @OneToMany(targetEntity = Match.class)
    @OrderBy("number ASC")
    private List<Match> matches = new ArrayList<>();

    @OneToMany(targetEntity = Group.class)
    private List<Group> groups = new ArrayList<>();

    // ---------------- constructors ------------------

    public Tournament(String name, Date date, Integer matchTimeSeconds, Integer pauseInBetweenSeconds, Integer pauseBeforeFinalSeconds, Integer qualiCount)
    {
        this.name = name;
        this.date = date;
        this.pauseInBetweenSeconds = pauseInBetweenSeconds;
        this.pauseBeforeFinalSeconds = pauseBeforeFinalSeconds;
        this.matchTimeSeconds = matchTimeSeconds;
        this.qualiCount = qualiCount;
    }

    public Tournament() {
    }

    // ---------------- getters and setters ------------------

    @Ignored
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

    public void addMatch(Match match){
        matches.add(match);
    }

    @Ignored
    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Integer getMatchTimeSeconds() {
        return matchTimeSeconds;
    }

    public void setMatchTimeSeconds(Integer matchTimeSeconds) {
        this.matchTimeSeconds = matchTimeSeconds;
    }

    public Integer getQualiCount() {
        return qualiCount;
    }

    public void setQualiCount(Integer qualiCount) {
        this.qualiCount = qualiCount;
    }

    public List<Group> getGroups() {
        return groups.stream().filter(g -> g.getDummyFinalGroup() == false).sorted().collect(Collectors.toList());
    }

    public List<Group> getAllGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public void addGroup(Group group) {
        this.groups.add(group);
    }

    public List<Team> getTeams() {
        ArrayList<Team> teams = new ArrayList<>();
        for (Group g : groups.stream().filter(g -> g.getDummyFinalGroup() == false).collect(Collectors.toList())) {
            teams.addAll(g.getTeams());
        }
        return teams;
    }

    public List<Team> getAllTeams() {
        ArrayList<Team> teams = new ArrayList<>();
        for (Group g : groups) {
            teams.addAll(g.getTeams());
        }
        return teams;
    }

    public Integer getPauseInBetweenSeconds() {
        return pauseInBetweenSeconds;
    }

    public void setPauseInBetweenSeconds(Integer pauseInBetweenSeconds) {
        this.pauseInBetweenSeconds = pauseInBetweenSeconds;
    }

    public Integer getPauseBeforeFinalSeconds() {
        return pauseBeforeFinalSeconds;
    }

    public void setPauseBeforeFinalSeconds(Integer pauseBeforeFinalSeconds) {
        this.pauseBeforeFinalSeconds = pauseBeforeFinalSeconds;
    }

    public boolean isPreliminationDone() {
        return false;
    }

    public String getImagePath() {
        final String encodedName = Base64.getEncoder().encodeToString(getName().getBytes());
        return "/images/ads/" + encodedName + ".jpg";
    }
}
