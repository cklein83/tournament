package com.tsvw.controller;

import com.tsvw.model.Match;
import com.tsvw.model.Status;
import com.tsvw.model.Tournament;
import com.tsvw.service.MatchService;
import com.tsvw.service.TournamentService;
import com.tsvw.service.UpdateService;
import com.tsvw.util.JPAUtil;
import org.hibernate.Hibernate;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BackendController {

    private static final TournamentService tournamentService = new TournamentService();
    private static final MatchService matchService = new MatchService();

    public static ModelAndView index(Request request, Response response) {
        HashMap<String, Object> map = new HashMap<>();
        return new ModelAndView(map, "views/backend/index.vm");
    }

    public static ModelAndView matchesList(Request request, Response response) {
        HashMap<String, Object> map = new HashMap<>();

        List<Tournament> tournaments = tournamentService.getAllTournaments();
        map.put("tournaments", tournaments);

        String tournamentId = request.queryParams("tid");
        if (tournamentId == null && tournaments != null && tournaments.size() > 0) {
            tournamentId = Long.toString(tournaments.get(0).getId());
        }
        Tournament tournament = tournamentService.getTournament(Long.parseLong(tournamentId));
        if (tournament != null) {
            map.put("matches", tournament.getMatches());
        }

        String finishRound = request.queryParams("finishRound");
        if (finishRound != null && Boolean.parseBoolean(finishRound)) {
            String alert = "Konnte aktuelle Runde nicht beenden, da noch nicht abgeschlossene Spiele offen sind.";
            map.put("alert", alert);
        }

        map.put("tid", tournamentId);

        map.put("matchStatusVariants", Status.values());

        map.put("isMatch", true);

        return new ModelAndView(map, "views/backend/matches/index.vm");
    }

    public static String saveListMatch(Request request, Response response) {
        String matchId = request.queryParams("mid");
        if (matchId != null && matchId.isEmpty() == false) {
            EntityManager entityManager = JPAUtil.getEntityManager();
            entityManager.getTransaction().begin();

            Match match = matchService.getMatch(Long.parseLong(matchId));
            Hibernate.initialize(match);

            // goals
            String goals1 = request.queryParams("goals1");
            if (goals1 != null && goals1.isEmpty() == false) {
                match.setGoalsTeam1(Integer.parseInt(goals1));
            }
            String goals2 = request.queryParams("goals2");
            if (goals2 != null && goals2.isEmpty() == false) {
                match.setGoalsTeam2(Integer.parseInt(goals2));
            }
            // status
            String status = request.queryParams("status");
            if (status != null && status.isEmpty() == false) {
                //match.setGoalsTeam1(Integer.parseInt(status));
                Status matchStatus = null;
                switch (status) {
                    case "new":
                        matchStatus = Status.NEW;
                        break;
                    case "started":
                        matchStatus = Status.STARTED;
                        break;
                    case "finished":
                        matchStatus = Status.FINISHED;
                        break;
                    default:
                        matchStatus = Status.NEW;
                        break;
                }
                match.setStatus(matchStatus);
            }

            entityManager.merge(match);
            entityManager.getTransaction().commit();

            UpdateService.broadcastMessage("refresh-data", "");
        }
        response.redirect("/backend/matches");
        return "OK";
    }

    public static String createExampleTournament(Request request, Response response) {
        tournamentService.createExampleTournament();
        return "OK";
    }

}
