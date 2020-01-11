package com.tsvw.controller;

import com.tsvw.model.Match;
import com.tsvw.model.Status;
import com.tsvw.model.Team;
import com.tsvw.model.Tournament;
import com.tsvw.service.MatchService;
import com.tsvw.service.TeamService;
import com.tsvw.service.TournamentService;
import com.tsvw.service.UpdateService;
import com.tsvw.util.Constants;
import org.hibernate.Hibernate;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;

public class BackendController {


    public static ModelAndView index(Request request, Response response) {
        HashMap<String, Object> map = new HashMap<>();
        return new ModelAndView(map, "views/backend/index.vm");
    }

    public static ModelAndView loginForm(Request request, Response response) {
        HashMap<String, Object> map = new HashMap<>();
        final Boolean loggedin = (Boolean) request.session().attribute("loggedin");
        if(loggedin != null  && loggedin == true){
            response.redirect("/backend");
        }
        return new ModelAndView(map, "views/backend/login/login.vm");
    }

    public static ModelAndView logout(Request request, Response response) {
        HashMap<String, Object> map = new HashMap<>();
        request.session().removeAttribute("loggedin");
        request.session().removeAttribute("username");
        response.redirect("/backend/login");
        return new ModelAndView(map, "views/backend/login/login.vm");
    }

    public static String login(Request request, Response response) {
        final String username = request.queryParams("username");
        final String password = request.queryParams("password");

        if (username != null && password != null &&
            username.toLowerCase().trim().equals(Constants.ADMIN) &&
            password.toLowerCase().trim().equals(Constants.ADMIN_PW)) {

            request.session().attribute("loggedin", true);
            request.session().attribute("username", "chris");
            response.redirect("/backend/matches");
            return "logged in";
        } else {
            response.redirect("/backend/login");
            return "";
        }
    }

    public static String logMeIn(Request request, Response response) {
        request.session().attribute("loggedin", true);
        request.session().attribute("username", "chris");

        response.redirect("/backend/matches");

        return "logged in";
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

            // teams
            TeamService teamService = new TeamService(em);
            String idTeam1 = request.queryParams("team1");
            if (idTeam1 != null) {
                Team team1 = teamService.getTeam(Long.parseLong(idTeam1));
                match.setTeam1(team1);
            }
            String idTeam2 = request.queryParams("team2");
            if (idTeam2 != null) {
                Team team2 = teamService.getTeam(Long.parseLong(idTeam2));
                match.setTeam2(team2);
            }

            em.merge(match);
            em.getTransaction().commit();

            //TODO use the mid to determine match details?
            //UpdateService.broadcastMessage("refresh-data", "");
        }

        String redirectUrl = "/backend/matches";

        String tournamentId = request.queryParams("tid");
        if (tournamentId != null) {
            redirectUrl += "?tid=" + tournamentId;

            String showOnlyFinals = request.queryParams("showOnlyFinals");
            if (showOnlyFinals != null) {
                redirectUrl += "&showOnlyFinals=" + showOnlyFinals;
            }
        }

        response.redirect(redirectUrl);
        return "OK";
    }

    //

    public static String createExampleTournament2018(Request request, Response response) {
        EntityManager em = request.attribute("em");
        TournamentService tournamentService = new TournamentService(em);
        tournamentService.createTournament2018();
        return "OK";
    }

    public static String deleteExampleTournament2018(Request request, Response response) {
        EntityManager em = request.attribute("em");
        TournamentService tournamentService = new TournamentService(em);
        tournamentService.deleteTournament2018();
        return "OK";
    }

    //

    public static String createExampleTournament2019(Request request, Response response) {
        EntityManager em = request.attribute("em");
        TournamentService tournamentService = new TournamentService(em);
        tournamentService.createTournament2019();
        return "OK";
    }

    public static String cleanupTournament2019(Request request, Response response) {
        EntityManager em = request.attribute("em");
        TournamentService tournamentService = new TournamentService(em);
        tournamentService.cleanupTournament2019();
        return "OK";
    }

    public static String deleteExampleTournament2019(Request request, Response response) {
        EntityManager em = request.attribute("em");
        TournamentService tournamentService = new TournamentService(em);
        tournamentService.deleteTournament2019();
        return "OK";
    }

    //

    public static String createExampleTournament2020(Request request, Response response) {
        EntityManager em = request.attribute("em");
        TournamentService tournamentService = new TournamentService(em);
        tournamentService.createTournament2020();
        return "OK";
    }

    public static String deleteExampleTournament2020(Request request, Response response) {
        EntityManager em = request.attribute("em");
        TournamentService tournamentService = new TournamentService(em);
        tournamentService.deleteTournament2020();
        return "OK";
    }


    public static String createExampleTournamentAH2020(Request request, Response response) {
        EntityManager em = request.attribute("em");
        TournamentService tournamentService = new TournamentService(em);
        tournamentService.createTournamentAH2020();
        return "OK";
    }

    public static String deleteExampleTournamentAH2020(Request request, Response response) {
        EntityManager em = request.attribute("em");
        TournamentService tournamentService = new TournamentService(em);
        tournamentService.deleteTournamentAH2020();
        return "OK";
    }
}
