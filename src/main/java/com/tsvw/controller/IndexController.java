package com.tsvw.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.tsvw.model.Match;
import com.tsvw.model.MatchType;
import com.tsvw.model.Status;
import com.tsvw.model.Tournament;
import com.tsvw.service.TournamentService;
import org.hibernate.Hibernate;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
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

        List<Match> matchesPrelim = tournamentService.getMatchesByMatchType(tournament, MatchType.PRELIM);
        List<Match> matchesQuarter = tournamentService.getMatchesByMatchType(tournament, MatchType.QUARTERFINAL);
        List<Match> matchesSemi = tournamentService.getMatchesByMatchType(tournament, MatchType.SEMIFINAL);
        List<Match> matchesSmall = tournamentService.getMatchesByMatchType(tournament, MatchType.SMALLFINAL);
        List<Match> matchesFinal = tournamentService.getMatchesByMatchType(tournament, MatchType.FINAL);

        List<List<Match>> matches = new ArrayList<>();
        matches.add(matchesPrelim);
        matches.add(matchesQuarter);
        matches.add(matchesSemi);
        matches.add(matchesSmall);
        matches.add(matchesFinal);

        map.put("t", tournament);
        map.put("matchesByType", matches);

        return new ModelAndView(map, "views/index/tournament.vm");
    }


}
