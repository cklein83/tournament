package com.tsvw.controller;

import com.tsvw.model.*;
import com.tsvw.util.JPAUtil;
import org.apache.commons.lang.time.DateUtils;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class BackendController {

    //private static final TournamentService tournamentService = new TournamentService();

    public static ModelAndView index(Request request, Response response){
        HashMap<String, Object> map = new HashMap<>();

        return new ModelAndView(map, "views/backend/index.vm");
    }

    public static String createExampleTournament(Request request, Response response) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        entityManager.getTransaction().begin();

        SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm" );

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
                minsToPlay*60, minsPauseInBetween*60, minsPauseBeforeFinal*60,
                2);
        t.setSubtitle("Firmenturnier in der Weibertreuhalle Weinsberg");
        entityManager.persist(t);

        // groups

        Group groupA = new Group("Gruppe A", t);
        entityManager.persist(groupA);

        Team teamA1 = new Team("FC Bierdurst", groupA);
        entityManager.persist(teamA1);
        groupA.addTeam(teamA1);
        Team teamA2 = new Team("Kaufland", groupA);
        entityManager.persist(teamA2);
        groupA.addTeam(teamA2);
        Team teamA3 = new Team("Kardex Mlog", groupA);
        entityManager.persist(teamA3);
        groupA.addTeam(teamA3);
        Team teamA4 = new Team("Bechtle", groupA);
        entityManager.persist(teamA4);
        groupA.addTeam(teamA4);

        t.addGroup(groupA);

        Group groupB = new Group("Gruppe B", t);
        entityManager.persist(groupB);

        Team teamB1 = new Team("Therapiezentrum", groupB);
        entityManager.persist(teamB1);
        groupB.addTeam(teamB1);
        Team teamB2 = new Team("AS Tralkörper", groupB);
        entityManager.persist(teamB2);
        groupB.addTeam(teamB2);
        Team teamB3 = new Team("Magna I", groupB);
        entityManager.persist(teamB3);
        groupB.addTeam(teamB3);
        Team teamB4 = new Team("Amos", groupB);
        entityManager.persist(teamB4);
        groupB.addTeam(teamB4);

        t.addGroup(groupB);

        Group groupC = new Group("Gruppe C", t);
        entityManager.persist(groupC);

        Team teamC1 = new Team("Campina", groupC);
        entityManager.persist(teamC1);
        groupC.addTeam(teamC1);
        Team teamC2 = new Team("Telekom", groupC);
        entityManager.persist(teamC2);
        groupC.addTeam(teamC2);
        Team teamC3 = new Team("DMDLJZW", groupC);
        entityManager.persist(teamC3);
        groupC.addTeam(teamC3);
        Team teamC4 = new Team("mybet", groupC);
        entityManager.persist(teamC4);
        groupC.addTeam(teamC4);

        t.addGroup(groupC);

        Group groupD = new Group("Gruppe D", t);
        entityManager.persist(groupD);

        Team teamD1 = new Team("El Fuego", groupD);
        entityManager.persist(teamD1);
        groupD.addTeam(teamD1);
        Team teamD2 = new Team("IT Works", groupD);
        entityManager.persist(teamD2);
        groupD.addTeam(teamD2);
        Team teamD3 = new Team("Reisebüro Dogru", groupD);
        entityManager.persist(teamD3);
        groupD.addTeam(teamD3);
        Team teamD4 = new Team("Magna II", groupD);
        entityManager.persist(teamD4);
        groupD.addTeam(teamD4);

        t.addGroup(groupD);

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

        // quarterfinals
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.QUARTERFINAL, null, null, "Erster Gruppe B", "Zweiter Gruppe A");
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.QUARTERFINAL, null, null, "Erster Gruppe A", "Zweiter Gruppe B");
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.QUARTERFINAL, null, null, "Erster Gruppe D", "Zweiter Gruppe C");
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.QUARTERFINAL, null, null, "Erster Gruppe C", "Zweiter Gruppe D");
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlay);

        // pause
        workDate = DateUtils.addMinutes(workDate, minsPauseBeforeFinal);

        // semifinals
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.SEMIFINAL, null, null, "Sieger 1/4-Finale Spiel 26", "Sieger 1/4-Finale Spiel 27");
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.SEMIFINAL, null, null, "Sieger 1/4-Finale Spiel 25", "Sieger 1/4-Finale Spiel 28");
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlay);

        // pause
        workDate = DateUtils.addMinutes(workDate, minsPauseBeforeFinal);

        // smallfinal + final
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.SMALLFINAL, null, null, "Verlierer 1/2-Finale Spiel 29", "Verlierer 1/2-Finale Spiel 30");
            entityManager.persist(m);
            t.addMatch(m);
        }
        workDate = DateUtils.addMinutes(workDate, minsToPlayPlusPause);
        {
            Match m = new Match(t, ++matchNo, workDate, MatchType.FINAL, null, null, "Sieger 1/2-Finale Spiel 29", "Sieger 1/2-Finale Spiel 30");
            entityManager.persist(m);
            t.addMatch(m);
        }

        //

        entityManager.persist(t);

        entityManager.getTransaction().commit();
        JPAUtil.shutdown();

        return "OK";
    }

}
