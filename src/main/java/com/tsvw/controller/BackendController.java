package com.tsvw.controller;

import com.tsvw.model.Match;
import com.tsvw.model.MatchType;
import com.tsvw.model.Status;
import com.tsvw.model.Tournament;
import com.tsvw.service.MatchService;
import com.tsvw.service.TournamentService;
import com.tsvw.service.UpdateService;
import org.hibernate.Hibernate;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class BackendController {

    public static ModelAndView index(Request request, Response response) {
        HashMap<String, Object> map = new HashMap<>();
        return new ModelAndView(map, "views/backend/index.vm");
    }

    public static ModelAndView matchesList(Request request, Response response) {
        HashMap<String, Object> map = new HashMap<>();

        EntityManager em = request.attribute("em");
        TournamentService tournamentService = new TournamentService(em);

        List<Tournament> tournaments = tournamentService.getAllTournaments();
        map.put("tournaments", tournaments);

        Tournament tournament = null;
        String tournamentId = request.queryParams("tid");
        if (tournamentId != null) {
            tournament = tournamentService.getTournament(Long.parseLong(tournamentId));
        } else if (tournaments != null && tournaments.size() > 0) {
            tournament = tournaments.get(0);
        }
        if (tournament != null) {
            map.put("matches", tournament.getMatches());
            map.put("tid", tournament.getId());
            map.put("teams", tournament.getAllTeams());
        }

        String showOnlyFinals = request.queryParams("showOnlyFinals");
        if (showOnlyFinals != null && Boolean.parseBoolean(showOnlyFinals)) {
            map.put("showOnlyFinals", true);
        }

        /*
        String finishRound = request.queryParams("finishRound");
        if (finishRound != null && Boolean.parseBoolean(finishRound) && tournament != null) {
            if (tournament.isPreliminationDone() == false) {
                String alert = "Konnte aktuelle Runde nicht beenden, da noch nicht abgeschlossene Spiele offen sind.";
                map.put("alert", alert);
            } else {
                List<Match> matchList = tournament.getMatches().stream()
                        .filter(m -> m.getMatchType() != MatchType.PRELIM)
                        .collect(Collectors.toList());
                for (Match match : matchList) {
                    if (match.getMatchType() == MatchType.QUARTERFINAL) {

                    }
                }
                String success = "Runde erfolgreich beendet. Nächste Runde wurde automatisch mit Teams befüllt.";
                map.put("success", success);
            }
        }
        */

        map.put("matchStatusVariants", Status.values());

        map.put("isMatch", true);

        return new ModelAndView(map, "views/backend/matches/index.vm");
    }

    public static String saveListMatch(Request request, Response response) {
        String matchId = request.queryParams("mid");
        if (matchId != null && matchId.isEmpty() == false) {

            EntityManager em = request.attribute("em");
            em.getTransaction().begin();

            MatchService matchService = new MatchService(em);
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

            em.merge(match);
            em.getTransaction().commit();

            UpdateService.broadcastMessage("refresh-data", "");
        }

        String redirectUrl = "/backend/matches";

        String tournamentId = request.queryParams("tid");
        if (tournamentId != null) {
            redirectUrl += "?tid=" + tournamentId;
        }

        response.redirect(redirectUrl);
        return "OK";
    }

    public static String createExampleTournament(Request request, Response response) {
        EntityManager em = request.attribute("em");
        TournamentService tournamentService = new TournamentService(em);
        tournamentService.createExampleTournament();
        return "OK";
    }

}
