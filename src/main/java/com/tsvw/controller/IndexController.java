package com.tsvw.controller;

import com.tsvw.model.Tournament;
import com.tsvw.service.TournamentService;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;

public class IndexController {

    private static final TournamentService tournamentService = new TournamentService();

    public static ModelAndView index(Request request, Response response){
        HashMap<String, Object> map = new HashMap<>();

        List<Tournament> tournaments = tournamentService.getAllTournaments();

        map.put("tournaments", tournaments);

        return new ModelAndView(map, "views/index/index.vm");
    }

    public static ModelAndView showTournament(Request request, Response response){
        HashMap<String, Object> map = new HashMap<>();

        final String tournamentId = request.params(":id");
        Tournament tournament = tournamentService.getTournament(Long.parseLong(tournamentId));
        map.put("t", tournament);

        map.put("prelimDone", tournament.isPreliminationDone());

        List<Match> matchesPrelim = tournamentService.getMatchesByMatchType(tournament, MatchType.PRELIM);

        List<List<Match>> prelimMatches = new ArrayList<>();
        prelimMatches.add(matchesPrelim);
        map.put("prelimMatches", prelimMatches);

        List<Match> matchesQuarter = tournamentService.getMatchesByMatchType(tournament, MatchType.QUARTERFINAL);
        List<Match> matchesSemi = tournamentService.getMatchesByMatchType(tournament, MatchType.SEMIFINAL);
        List<Match> matchesSmall = tournamentService.getMatchesByMatchType(tournament, MatchType.SMALLFINAL);
        List<Match> matchesFinal = tournamentService.getMatchesByMatchType(tournament, MatchType.FINAL);

        List<List<Match>> finalMatches = new ArrayList<>();
        finalMatches.add(matchesQuarter);
        finalMatches.add(matchesSemi);
        finalMatches.add(matchesSmall);
        finalMatches.add(matchesFinal);
        map.put("finalMatches", finalMatches);

        return new ModelAndView(map, "views/index/tournament.vm");
    }


}
