package com.tsvw.service;


import com.tsvw.model.*;
import org.apache.commons.lang.time.DateUtils;

import javax.persistence.EntityManager;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TournamentService extends Service {

    public TournamentService(EntityManager em) {
        super(em);
    }

    public List<Tournament> getAllTournaments() {
        List<Tournament> tournaments = em.createQuery("select t from tournaments as t").getResultList();
        return tournaments;
    }

    public Tournament getTournament(Long id) {
        Tournament tournament = em.find(Tournament.class, id);
        return tournament;
    }


    public List<Match> getMatchesByMatchType(Tournament tournament, MatchType matchType) {
        List<Match> matches = tournament.getMatches().stream()
                .filter(m -> m.getMatchType() == matchType)
                .collect(Collectors.toList());
        return matches;
    }

    public Optional<Match> getCurrentMatch(Tournament tournament) {
        return tournament.getMatches().stream()
                .filter(m -> m.getStatus() == Status.STARTED).findFirst();
    }

    public List<Team> getAllTournamentTeams() {
        List<Team> teams = em.createQuery("select t from tournaments as t").getResultList();
        return teams;
    }

    public void deleteTournamentById(Long id) {
        em.getTransaction().begin();
        em.remove(getTournament(id));
        em.flush();
        em.getTransaction().commit();
    }

    //

    public void createTournament2018() {
        EntityManager entityManager = em;
        entityManager.getTransaction().begin();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date startDate;
        try {
            startDate = df.parse("2018-02-10 18:00");
        } catch (ParseException e) {
            throw new InvalidParameterException("Wrong date format: " + e.getMessage());
        }

        int minsToPlay = 8;
        int minsPauseInBetween = 1;
        int minsToPlayPlusPause = minsToPlay + 1;
        int minsPauseBeforeFinal = 10;

        Tournament t = new Tournament(
                "Hallenzauber 2018",
                startDate,
                minsToPlay * 60, minsPauseInBetween * 60, minsPauseBeforeFinal * 60,
                2);
        t.setSubtitle("Firmenturnier in der Weibertreuhalle Weinsberg");
        entityManager.persist(t);

        // groups + teams

        ArrayList<Team> teams = new ArrayList<>();

        Group groupA = new Group("A", t);
        entityManager.persist(groupA);

        Team teamA1 = new Team("FC Bierdurst", groupA);
        teams.add(teamA1);
        groupA.addTeam(teamA1);
        Team teamA2 = new Team("Kaufland", groupA);
        teams.add(teamA2);
        groupA.addTeam(teamA2);
        Team teamA3 = new Team("Kardex Mlog", groupA);
        teams.add(teamA3);
        groupA.addTeam(teamA3);
        Team teamA4 = new Team("Bechtle", groupA);
        teams.add(teamA4);
        groupA.addTeam(teamA4);

        t.addGroup(groupA);

        Group groupB = new Group("B", t);
        entityManager.persist(groupB);

        Team teamB1 = new Team("Therapiezentrum", groupB);
        teams.add(teamB1);
        groupB.addTeam(teamB1);
        Team teamB2 = new Team("AS Tralkörper", groupB);
        teams.add(teamB2);
        groupB.addTeam(teamB2);
        Team teamB3 = new Team("Magna I", groupB);
        teams.add(teamB3);
        groupB.addTeam(teamB3);
        Team teamB4 = new Team("Amos", groupB);
        teams.add(teamB4);
        groupB.addTeam(teamB4);

        t.addGroup(groupB);

        Group groupC = new Group("C", t);
        entityManager.persist(groupC);

        Team teamC1 = new Team("Campina", groupC);
        teams.add(teamC1);
        groupC.addTeam(teamC1);
        Team teamC2 = new Team("Telekom", groupC);
        teams.add(teamC2);
        groupC.addTeam(teamC2);
        Team teamC3 = new Team("DMDLJZW", groupC);
        teams.add(teamC3);
        groupC.addTeam(teamC3);
        Team teamC4 = new Team("mybet", groupC);
        teams.add(teamC4);
        groupC.addTeam(teamC4);

        t.addGroup(groupC);

        Group groupD = new Group("D", t);
        entityManager.persist(groupD);

        Team teamD1 = new Team("El Fuego", groupD);
        teams.add(teamD1);
        groupD.addTeam(teamD1);
        Team teamD2 = new Team("IT Works", groupD);
        teams.add(teamD2);
        groupD.addTeam(teamD2);
        Team teamD3 = new Team("Reisebüro Dogru", groupD);
        teams.add(teamD3);
        groupD.addTeam(teamD3);
        Team teamD4 = new Team("Magna II", groupD);
        teams.add(teamD4);
        groupD.addTeam(teamD4);

        t.addGroup(groupD);

        for (Team team : teams) {
            entityManager.persist(team);
        }

        // matches

        /*
        Random random = new Random();
        for (Group g : t.getGroups()) {
            int r1 = random.nextInt(g.getTeams().size());
            int r2 = random.nextInt(g.getTeams().size());
            Match m = new Match(t, new Date(), MatchType.PRELIM, g.getTeams().get(r1), g.getTeams().get(r1));
            entityManager.persist(m);
            t.addMatch(m);
        }
        */

        Integer matchNo = 0;

        Date workDate = startDate;

        // round 1
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA1, teamA2);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamB1, teamB2);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamC1, teamC2);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamD1, teamD2);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // round 2
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA3, teamA4);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamB3, teamB4);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamC3, teamC4);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamD3, teamD4);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // round 3
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA4, teamA1);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamB4, teamB1);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamC4, teamC1);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamD4, teamD1);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // round 4
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA2, teamA3);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamB2, teamB3);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamC2, teamC3);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamD2, teamD3);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // round 5
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA1, teamA3);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamB1, teamB3);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamC1, teamC3);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamD1, teamD3);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // round 6
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA2, teamA4);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamB2, teamB4);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamC2, teamC4);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamD2, teamD4);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlay);

        // pause
        workDate = DateUtils.addMinutes(workDate, minsPauseBeforeFinal);

        /**
         * finals
         */

        ArrayList<Team> finalTeams = new ArrayList<>();

        Group finalGroup = new Group("-", t, true);
        entityManager.persist(finalGroup);

        t.addGroup(finalGroup);
        entityManager.persist(t);

        // quarter-teams

        Team finalTeam1A = new Team("Erster A", null);
        finalTeams.add(finalTeam1A);
        Team finalTeam2A = new Team("Zweiter A", null);
        finalTeams.add(finalTeam2A);

        Team finalTeam1B = new Team("Erster B", null);
        finalTeams.add(finalTeam1B);
        Team finalTeam2B = new Team("Zweiter B", null);
        finalTeams.add(finalTeam2B);

        Team finalTeam1C = new Team("Erster C", null);
        finalTeams.add(finalTeam1C);
        Team finalTeam2C = new Team("Zweiter C", null);
        finalTeams.add(finalTeam2C);

        Team finalTeam1D = new Team("Erster D", null);
        finalTeams.add(finalTeam1D);
        Team finalTeam2D = new Team("Zweiter D", null);
        finalTeams.add(finalTeam2D);

        // semi-teams
        Team finalTeamWinner26 = new Team("Sieger 1/4-Finale Spiel 26", null);
        finalTeams.add(finalTeamWinner26);
        Team finalTeamWinner27 = new Team("Sieger 1/4-Finale Spiel 27", null);
        finalTeams.add(finalTeamWinner27);
        Team finalTeamWinner25 = new Team("Sieger 1/4-Finale Spiel 25", null);
        finalTeams.add(finalTeamWinner25);
        Team finalTeamWinner28 = new Team("Sieger 1/4-Finale Spiel 28", null);
        finalTeams.add(finalTeamWinner28);

        // small final teams
        Team finalTeamLoser29 = new Team("Verlierer 1/2-Finale Spiel 29", null);
        finalTeams.add(finalTeamLoser29);
        Team finalTeamLoser30 = new Team("Verlierer 1/2-Finale Spiel 30", null);
        finalTeams.add(finalTeamLoser30);

        // final teams
        Team finalTeamWinner29 = new Team("Sieger 1/2-Finale Spiel 29", null);
        finalTeams.add(finalTeamWinner29);
        Team finalTeamWinner30 = new Team("Sieger 1/2-Finale Spiel 30", null);
        finalTeams.add(finalTeamWinner30);

        for (Team team : finalTeams) {
            entityManager.persist(team);
            finalGroup.addTeam(team);
            entityManager.persist(finalGroup);
        }

        // quarterfinals
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.QUARTERFINAL, finalTeam1B, finalTeam2A);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.QUARTERFINAL,finalTeam1A, finalTeam2B);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.QUARTERFINAL, finalTeam1D, finalTeam2C);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.QUARTERFINAL, finalTeam1C, finalTeam2D);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlay);

        // pause
        workDate = DateUtils.addMinutes(workDate, minsPauseBeforeFinal);

        // semifinals
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.SEMIFINAL, finalTeamWinner26, finalTeamWinner27);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.SEMIFINAL, finalTeamWinner25, finalTeamWinner28);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlay);

        // pause
        workDate = DateUtils.addMinutes(workDate, minsPauseBeforeFinal);

        // smallfinal + final
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.SMALLFINAL, finalTeamLoser29, finalTeamLoser30);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.FINAL, finalTeamWinner29, finalTeamWinner30);
            entityManager.persist(m);
            t.addMatch(m);
        }

        //

        for (Team team : teams) {
            team.setMatches(
                    t.getMatches().stream()
                            .filter(
                                    m -> m.getTeam1() == team || m.getTeam2() == team)
                            .collect(Collectors.toList()));
            entityManager.persist(team);
        }

        //

        entityManager.persist(t);

        entityManager.getTransaction().commit();
    }

    //

    public void createTournament2019() {
        EntityManager entityManager = em;
        entityManager.getTransaction().begin();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date startDate;
        try {
            startDate = df.parse("2018-02-10 18:00");
        } catch (ParseException e) {
            throw new InvalidParameterException("Wrong date format: " + e.getMessage());
        }

        int minsToPlay = 8;
        int minsPauseInBetween = 2;
        int minsToPlayPlusPause = minsToPlay + 2;
        int minsPauseBeforeFinal = 10;

        Tournament t = new Tournament(
                "Hallenzauber 2019",
                startDate,
                minsToPlay * 60, minsPauseInBetween * 60, minsPauseBeforeFinal * 60,
                2);
        t.setSubtitle("Firmenturnier in der Weibertreuhalle Weinsberg");
        entityManager.persist(t);

        // groups + teams

        ArrayList<Team> teams = new ArrayList<>();

        Group groupA = new Group("A", t);
        entityManager.persist(groupA);

        Team teamA1 = new Team("IT Works", groupA);
        teams.add(teamA1);
        groupA.addTeam(teamA1);
        Team teamA2 = new Team("Amos", groupA);
        teams.add(teamA2);
        groupA.addTeam(teamA2);
        Team teamA3 = new Team("El Fuego", groupA);
        teams.add(teamA3);
        groupA.addTeam(teamA3);
        Team teamA4 = new Team("Bechtle", groupA);
        teams.add(teamA4);
        groupA.addTeam(teamA4);

        t.addGroup(groupA);

        Group groupB = new Group("B", t);
        entityManager.persist(groupB);

        Team teamB1 = new Team("Reisebüro Dogru I", groupB);
        teams.add(teamB1);
        groupB.addTeam(teamB1);
        Team teamB2 = new Team("Campina", groupB);
        teams.add(teamB2);
        groupB.addTeam(teamB2);
        Team teamB3 = new Team("Kardex Mlog", groupB);
        teams.add(teamB3);
        groupB.addTeam(teamB3);
        Team teamB4 = new Team("Meine Favoriten", groupB);
        teams.add(teamB4);
        groupB.addTeam(teamB4);

        t.addGroup(groupB);

        Group groupC = new Group("C", t);
        entityManager.persist(groupC);

        Team teamC1 = new Team("Therapie Zentrum", groupC);
        teams.add(teamC1);
        groupC.addTeam(teamC1);
        Team teamC2 = new Team("mybet", groupC);
        teams.add(teamC2);
        groupC.addTeam(teamC2);
        Team teamC3 = new Team("Magna Steyr", groupC);
        teams.add(teamC3);
        groupC.addTeam(teamC3);
        Team teamC4 = new Team("Reisebüro Dogru II", groupC);
        teams.add(teamC4);
        groupC.addTeam(teamC4);

        t.addGroup(groupC);

        Group groupD = new Group("D", t);
        entityManager.persist(groupD);

        Team teamD1 = new Team("Kohler GmbH", groupD);
        teams.add(teamD1);
        groupD.addTeam(teamD1);
        Team teamD2 = new Team("Telekom", groupD);
        teams.add(teamD2);
        groupD.addTeam(teamD2);
        Team teamD3 = new Team("Immohouse GmbH", groupD);
        teams.add(teamD3);
        groupD.addTeam(teamD3);
        Team teamD4 = new Team("FC Bierdurst", groupD);
        teams.add(teamD4);
        groupD.addTeam(teamD4);

        t.addGroup(groupD);

        for (Team team : teams) {
            entityManager.persist(team);
        }

        // matches

        /*
        Random random = new Random();
        for (Group g : t.getGroups()) {
            int r1 = random.nextInt(g.getTeams().size());
            int r2 = random.nextInt(g.getTeams().size());
            Match m = new Match(t, new Date(), MatchType.PRELIM, g.getTeams().get(r1), g.getTeams().get(r1));
            entityManager.persist(m);
            t.addMatch(m);
        }
        */

        Integer matchNo = 0;

        Date workDate = startDate;

        // round 1
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA1, teamA2);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamB1, teamB2);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamC1, teamC2);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamD1, teamD2);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // round 2
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA3, teamA4);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamB3, teamB4);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamC3, teamC4);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamD3, teamD4);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // round 3
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA4, teamA1);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamB4, teamB1);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamC4, teamC1);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamD4, teamD1);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // round 4
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA2, teamA3);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamB2, teamB3);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamC2, teamC3);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamD2, teamD3);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // round 5
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA1, teamA3);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamB1, teamB3);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamC1, teamC3);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamD1, teamD3);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // round 6
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA2, teamA4);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamB2, teamB4);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamC2, teamC4);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamD2, teamD4);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlay);

        // pause
        workDate = DateUtils.addMinutes(workDate, minsPauseBeforeFinal);

        /**
         * finals
         */

        ArrayList<Team> finalTeams = new ArrayList<>();

        Group finalGroup = new Group("-", t, true);
        entityManager.persist(finalGroup);

        t.addGroup(finalGroup);
        entityManager.persist(t);

        // quarter-teams

        Team finalTeam1A = new Team("Erster A", null);
        finalTeams.add(finalTeam1A);
        Team finalTeam2A = new Team("Zweiter A", null);
        finalTeams.add(finalTeam2A);

        Team finalTeam1B = new Team("Erster B", null);
        finalTeams.add(finalTeam1B);
        Team finalTeam2B = new Team("Zweiter B", null);
        finalTeams.add(finalTeam2B);

        Team finalTeam1C = new Team("Erster C", null);
        finalTeams.add(finalTeam1C);
        Team finalTeam2C = new Team("Zweiter C", null);
        finalTeams.add(finalTeam2C);

        Team finalTeam1D = new Team("Erster D", null);
        finalTeams.add(finalTeam1D);
        Team finalTeam2D = new Team("Zweiter D", null);
        finalTeams.add(finalTeam2D);

        // semi-teams
        Team finalTeamWinner26 = new Team("Sieger 1/4-Finale Spiel 26", null);
        finalTeams.add(finalTeamWinner26);
        Team finalTeamWinner27 = new Team("Sieger 1/4-Finale Spiel 27", null);
        finalTeams.add(finalTeamWinner27);
        Team finalTeamWinner25 = new Team("Sieger 1/4-Finale Spiel 25", null);
        finalTeams.add(finalTeamWinner25);
        Team finalTeamWinner28 = new Team("Sieger 1/4-Finale Spiel 28", null);
        finalTeams.add(finalTeamWinner28);

        // small final teams
        Team finalTeamLoser29 = new Team("Verlierer 1/2-Finale Spiel 29", null);
        finalTeams.add(finalTeamLoser29);
        Team finalTeamLoser30 = new Team("Verlierer 1/2-Finale Spiel 30", null);
        finalTeams.add(finalTeamLoser30);

        // final teams
        Team finalTeamWinner29 = new Team("Sieger 1/2-Finale Spiel 29", null);
        finalTeams.add(finalTeamWinner29);
        Team finalTeamWinner30 = new Team("Sieger 1/2-Finale Spiel 30", null);
        finalTeams.add(finalTeamWinner30);

        for (Team team : finalTeams) {
            entityManager.persist(team);
            finalGroup.addTeam(team);
            entityManager.persist(finalGroup);
        }

        // quarterfinals
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.QUARTERFINAL, finalTeam1B, finalTeam2A);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.QUARTERFINAL,finalTeam1A, finalTeam2B);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.QUARTERFINAL, finalTeam1D, finalTeam2C);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.QUARTERFINAL, finalTeam1C, finalTeam2D);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlay);

        // pause
        workDate = DateUtils.addMinutes(workDate, minsPauseBeforeFinal);

        // semifinals
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.SEMIFINAL, finalTeamWinner26, finalTeamWinner27);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.SEMIFINAL, finalTeamWinner25, finalTeamWinner28);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlay);

        // pause
        workDate = DateUtils.addMinutes(workDate, minsPauseBeforeFinal);

        // smallfinal + final
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.SMALLFINAL, finalTeamLoser29, finalTeamLoser30);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.FINAL, finalTeamWinner29, finalTeamWinner30);
            entityManager.persist(m);
            t.addMatch(m);
        }

        //

        for (Team team : teams) {
            team.setMatches(
                    t.getMatches().stream()
                            .filter(
                                    m -> m.getTeam1() == team || m.getTeam2() == team)
                            .collect(Collectors.toList()));
            entityManager.persist(team);
        }

        //

        entityManager.persist(t);

        entityManager.getTransaction().commit();
    }

    public void cleanupTournament2019() {

        final Tournament tournament = getTournament(71L);

        // fix group names
        tournament.getGroups().stream().forEach(g -> {
            em.getTransaction().begin();
            g.setName(g.getName().replace("Gruppe ", ""));
            em.persist(g);
            em.flush();
            em.getTransaction().commit();
        });

        // fix therapiezentrum
        final Optional<Team> therapiezentrum = tournament.getTeams().stream().filter(t -> t.getName().startsWith("Therapiezentrum")).findFirst();
        if (therapiezentrum.isPresent()) {
            em.getTransaction().begin();
            therapiezentrum.get().setName("Therapie Zentrum");
            em.persist(therapiezentrum.get());
            em.flush();
            em.getTransaction().commit();
        }

    }

    //

    public void createTournament2020() {
        EntityManager entityManager = em;
        entityManager.getTransaction().begin();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date startDate;
        try {
            startDate = df.parse("2020-01-25 17:30");
        } catch (ParseException e) {
            throw new InvalidParameterException("Wrong date format: " + e.getMessage());
        }

        int minsToPlay = 12;
        int minsPauseInBetween = 2;
        int minsToPlayPlusPause = minsToPlay + minsPauseInBetween;
        int minsPauseBeforeSemiFinal = 15;
        int minsPauseBeforeFinal = 15;

        Tournament t = new Tournament(
                "Hallenzauber 2020",
                startDate,
                minsToPlay * 60, minsPauseInBetween * 60, minsPauseBeforeFinal * 60,
                2);
        t.setSubtitle("Firmenturnier in der Weibertreuhalle Weinsberg");
        entityManager.persist(t);

        // groups + teams

        ArrayList<Team> teams = new ArrayList<>();

        Group groupA = new Group("A", t);
        entityManager.persist(groupA);

        Team teamA1 = new Team("Kardex Mlog I", groupA);
        teams.add(teamA1);
        groupA.addTeam(teamA1);
        Team teamA2 = new Team("Magna Steyr", groupA);
        teams.add(teamA2);
        groupA.addTeam(teamA2);
        Team teamA3 = new Team("Gienger & Renz KG", groupA);
        teams.add(teamA3);
        groupA.addTeam(teamA3);
        Team teamA4 = new Team("C.R. Laurence of Europe", groupA);
        teams.add(teamA4);
        groupA.addTeam(teamA4);

        t.addGroup(groupA);

        Group groupB = new Group("B", t);
        entityManager.persist(groupB);

        Team teamB1 = new Team("Kardex Mlog II", groupB);
        teams.add(teamB1);
        groupB.addTeam(teamB1);
        Team teamB2 = new Team("Bechtle AG", groupB);
        teams.add(teamB2);
        groupB.addTeam(teamB2);
        Team teamB3 = new Team("IT.Works GmbH", groupB);
        teams.add(teamB3);
        groupB.addTeam(teamB3);
        Team teamB4 = new Team("Tennis Academy Sica/Wunder", groupB);
        teams.add(teamB4);
        groupB.addTeam(teamB4);

        t.addGroup(groupB);

        Group groupC = new Group("C", t);
        entityManager.persist(groupC);

        Team teamC1 = new Team("Hornung GmbH", groupC);
        teams.add(teamC1);
        groupC.addTeam(teamC1);
        Team teamC2 = new Team("Clean Service", groupC);
        teams.add(teamC2);
        groupC.addTeam(teamC2);
        Team teamC3 = new Team("Schweikert GmbH", groupC);
        teams.add(teamC3);
        groupC.addTeam(teamC3);

        t.addGroup(groupC);

        for (Team team : teams) {
            entityManager.persist(team);
        }

        // matches

        Integer matchNo = 0;

        Date workDate = startDate;

        // #1 A
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA1, teamA2);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // #2 A
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA3, teamA4);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // #3 C
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamC1, teamC2);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // #4 B
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamB1, teamB2);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // #5 B
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamB3, teamB4);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // #6 C
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamC2, teamC3);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // #7 A
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA4, teamA1);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // #8 A
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA2, teamA3);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // #9 C
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamC3, teamC1);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // #10 B
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamB4, teamB1);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // #11 B
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamB2, teamB3);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // #12 A
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA1, teamA3);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // #13 A
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA2, teamA4);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // #14 B
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamB1, teamB3);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // #15 B
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamB2, teamB4);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlay);

        // pause before final
        workDate = DateUtils.addMinutes(workDate, minsPauseBeforeFinal);

        /**
         * finals
         */

        ArrayList<Team> finalTeams = new ArrayList<>();

        Group finalGroup = new Group("-", t, true);
        entityManager.persist(finalGroup);

        t.addGroup(finalGroup);
        entityManager.persist(t);

        // quarter-teams

        Team finalTeam1A = new Team("Erster A", null);
        finalTeams.add(finalTeam1A);
        Team finalTeam2A = new Team("Zweiter A", null);
        finalTeams.add(finalTeam2A);
        Team finalTeam3A = new Team("Dritter A", null);
        finalTeams.add(finalTeam3A);

        Team finalTeam1B = new Team("Erster B", null);
        finalTeams.add(finalTeam1B);
        Team finalTeam2B = new Team("Zweiter B", null);
        finalTeams.add(finalTeam2B);
        Team finalTeam3B = new Team("Dritter B", null);
        finalTeams.add(finalTeam3B);

        Team finalTeam1C = new Team("Erster C", null);
        finalTeams.add(finalTeam1C);
        Team finalTeam2C = new Team("Zweiter C", null);
        finalTeams.add(finalTeam2C);
        Team finalTeam3C = new Team("Dritter C", null);
        finalTeams.add(finalTeam3C);

        // semi-teams
        Team finalTeamWinner16 = new Team("Sieger 1/4-Finale Spiel 16", null);
        finalTeams.add(finalTeamWinner16);
        Team finalTeamWinner17 = new Team("Sieger 1/4-Finale Spiel 17", null);
        finalTeams.add(finalTeamWinner17);
        Team finalTeamWinner18 = new Team("Sieger 1/4-Finale Spiel 18", null);
        finalTeams.add(finalTeamWinner18);
        Team finalTeamWinner19 = new Team("Sieger 1/4-Finale Spiel 19", null);
        finalTeams.add(finalTeamWinner19);

        // small final teams
        Team finalTeamLoser20 = new Team("Verlierer 1/2-Finale Spiel 20", null);
        finalTeams.add(finalTeamLoser20);
        Team finalTeamLoser21 = new Team("Verlierer 1/2-Finale Spiel 21", null);
        finalTeams.add(finalTeamLoser21);

        // final teams
        Team finalTeamWinner20 = new Team("Sieger 1/2-Finale Spiel 20", null);
        finalTeams.add(finalTeamWinner20);
        Team finalTeamWinner21 = new Team("Sieger 1/2-Finale Spiel 21", null);
        finalTeams.add(finalTeamWinner21);

        for (Team team : finalTeams) {
            entityManager.persist(team);
            finalGroup.addTeam(team);
            entityManager.persist(finalGroup);
        }

        // quarterfinals
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.QUARTERFINAL, finalTeam3A, finalTeam2C);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.QUARTERFINAL,finalTeam3B, finalTeam2A);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.QUARTERFINAL, finalTeam2B, finalTeam1A);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.QUARTERFINAL, finalTeam1B, finalTeam1C);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlay);

        // pause
        workDate = DateUtils.addMinutes(workDate, minsPauseBeforeSemiFinal);

        // semifinals
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.SEMIFINAL, finalTeamWinner16, finalTeamWinner18);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.SEMIFINAL, finalTeamWinner17, finalTeamWinner19);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlay);

        // pause
        workDate = DateUtils.addMinutes(workDate, minsPauseBeforeFinal);

        // smallfinal + final
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.SMALLFINAL, finalTeamLoser20, finalTeamLoser21);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.FINAL, finalTeamWinner20, finalTeamWinner21);
            entityManager.persist(m);
            t.addMatch(m);
        }

        //

        for (Team team : teams) {
            team.setMatches(
                    t.getMatches().stream()
                            .filter(
                                    m -> m.getTeam1() == team || m.getTeam2() == team)
                            .collect(Collectors.toList()));
            entityManager.persist(team);
        }

        //

        entityManager.persist(t);

        entityManager.getTransaction().commit();
    }

    //

    public void createTournamentAH2020() {
        EntityManager entityManager = em;
        entityManager.getTransaction().begin();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date startDate;
        try {
            startDate = df.parse("2020-01-24 18:00");
        } catch (ParseException e) {
            throw new InvalidParameterException("Wrong date format: " + e.getMessage());
        }

        int minsToPlay = 8;
        int minsPauseInBetween = 1;
        int minsToPlayPlusPause = minsToPlay + minsPauseInBetween;

        Tournament t = new Tournament(
                "Frank Baumgärtel Gedächtnisturnier 2020",
                startDate,
                minsToPlay * 60, minsPauseInBetween * 60, 0,
                2);
        t.setSubtitle("Senioren-Turnier in der Weibertreuhalle Weinsberg");
        entityManager.persist(t);

        // groups + teams

        ArrayList<Team> teams = new ArrayList<>();

        Group groupA = new Group("1", t);
        entityManager.persist(groupA);

        Team teamA1 = new Team("Mannschaft 8", groupA);
        teams.add(teamA1);
        groupA.addTeam(teamA1);
        Team teamA2 = new Team("SV Sülzbach", groupA);
        teams.add(teamA2);
        groupA.addTeam(teamA2);
        Team teamA3 = new Team("TSV Weinsberg", groupA);
        teams.add(teamA3);
        groupA.addTeam(teamA3);
        Team teamA4 = new Team("VfL Eberstadt", groupA);
        teams.add(teamA4);
        groupA.addTeam(teamA4);
        Team teamA5 = new Team("TSV Neuenstein", groupA);
        teams.add(teamA5);
        groupA.addTeam(teamA5);
        Team teamA6 = new Team("Old Boys Weinsberg", groupA);
        teams.add(teamA6);
        groupA.addTeam(teamA6);
        Team teamA7 = new Team("Friedrichshaller Kicker", groupA);
        teams.add(teamA7);
        groupA.addTeam(teamA7);
        Team teamA8 = new Team("CVJM", groupA);
        teams.add(teamA8);
        groupA.addTeam(teamA8);

        t.addGroup(groupA);

        for (Team team : teams) {
            entityManager.persist(team);
        }

        // matches

        Integer matchNo = 0;

        Date workDate = startDate;

        // round 1
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA1, teamA2);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA3, teamA4);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA5, teamA6);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA7, teamA8);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // round 2
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA4, teamA1);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA2, teamA3);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA8, teamA5);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA6, teamA7);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // round 3
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA5, teamA4);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA1, teamA3);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA2, teamA7);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA6, teamA8);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // round 4
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA4, teamA2);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA7, teamA5);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA8, teamA1);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA3, teamA6);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // round 5
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA4, teamA7);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA2, teamA8);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA1, teamA6);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA5, teamA3);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // round 6
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA8, teamA4);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA6, teamA2);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA7, teamA3);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA5, teamA1);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);

        // round 7
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA4, teamA6);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA3, teamA8);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA2, teamA5);
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.PRELIM, teamA1, teamA7);
            entityManager.persist(m);
            t.addMatch(m);
        }

        //

        for (Team team : teams) {
            team.setMatches(
                    t.getMatches().stream()
                            .filter(
                                    m -> m.getTeam1() == team || m.getTeam2() == team)
                            .collect(Collectors.toList()));
            entityManager.persist(team);
        }

        //

        entityManager.persist(t);

        entityManager.getTransaction().commit();
    }
}
