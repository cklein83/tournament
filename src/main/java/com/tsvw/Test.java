package com.tsvw;

import com.tsvw.model.*;
import com.tsvw.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.Random;

public class Test {

    public static void main(String[] args) {

        EntityManager entityManager = JPAUtil.getEntityManager();
        entityManager.getTransaction().begin();

        Tournament t = new Tournament("Hallenzauber 2018", new Date(), 8*60, 3);
        t.setSubtitle("Firmenturnier in der Weibertreuhalle Weinsberg");
        entityManager.persist(t);

        // groups

        Group groupA = new Group("Gruppe A", t);
        entityManager.persist(groupA);

        Team teamA1 = new Team("Team A1", groupA);
        entityManager.persist(teamA1);
        groupA.addTeam(teamA1);
        Team teamA2 = new Team("Team A2", groupA);
        entityManager.persist(teamA2);
        groupA.addTeam(teamA2);
        Team teamA3 = new Team("Team A3", groupA);
        entityManager.persist(teamA3);
        groupA.addTeam(teamA3);
        Team teamA4 = new Team("Team A4", groupA);
        entityManager.persist(teamA4);
        groupA.addTeam(teamA4);

        t.addGroup(groupA);

        Group groupB = new Group("Gruppe B", t);
        entityManager.persist(groupB);

        Team teamB1 = new Team("Team B1", groupB);
        entityManager.persist(teamB1);
        groupB.addTeam(teamB1);
        Team teamB2 = new Team("Team B2", groupB);
        entityManager.persist(teamB2);
        groupB.addTeam(teamB2);
        Team teamB3 = new Team("Team B3", groupB);
        entityManager.persist(teamB3);
        groupB.addTeam(teamB3);
        Team teamB4 = new Team("Team B4", groupB);
        entityManager.persist(teamB4);
        groupB.addTeam(teamB4);

        t.addGroup(groupB);

        Group groupC = new Group("Gruppe C", t);
        entityManager.persist(groupC);

        Team teamC1 = new Team("Team C1", groupC);
        entityManager.persist(teamC1);
        groupC.addTeam(teamC1);
        Team teamC2 = new Team("Team C2", groupC);
        entityManager.persist(teamC2);
        groupC.addTeam(teamC2);
        Team teamC3 = new Team("Team C3", groupC);
        entityManager.persist(teamC3);
        groupC.addTeam(teamC3);
        Team teamC4 = new Team("Team C4", groupC);
        entityManager.persist(teamC4);
        groupC.addTeam(teamC4);

        t.addGroup(groupC);

        Group groupD = new Group("Gruppe D", t);
        entityManager.persist(groupD);

        Team teamD1 = new Team("Team D1", groupD);
        entityManager.persist(teamD1);
        groupD.addTeam(teamD1);
        Team teamD2 = new Team("Team D2", groupD);
        entityManager.persist(teamD2);
        groupD.addTeam(teamD2);
        Team teamD3 = new Team("Team D3", groupD);
        entityManager.persist(teamD3);
        groupD.addTeam(teamD3);
        Team teamD4 = new Team("Team D4", groupD);
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

        {
            Match m = new Match(t, new Date(), MatchType.PRELIM, teamA1, teamA2);
            entityManager.persist(m);
        }

        {
            Match m = new Match(t, new Date(), MatchType.PRELIM, teamB2, teamB3);
            entityManager.persist(m);
        }

        {
            Match m = new Match(t, new Date(), MatchType.PRELIM, teamC1, teamC4);
            entityManager.persist(m);
        }

        {
            Match m = new Match(t, new Date(), MatchType.PRELIM, teamD3, teamD1);
            entityManager.persist(m);
        }

        entityManager.getTransaction().commit();
        JPAUtil.shutdown();
    }
}
