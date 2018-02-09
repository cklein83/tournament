package com.tsvw.controller;

import com.tsvw.model.Match;
import com.tsvw.model.MatchType;
import com.tsvw.model.Tournament;
import com.tsvw.service.TournamentService;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MatchController {

    public static ModelAndView showPrelimMatches(Request request, Response response){
        HashMap<String, Object> map = new HashMap<>();

        EntityManager em = request.attribute("em");
        TournamentService tournamentService = new TournamentService(em);

        final String tournamentId = request.params(":tid");
        Tournament tournament = tournamentService.getTournament(Long.parseLong(tournamentId));
        map.put("t", tournament);

        List<Match> matchesPrelim = tournamentService.getMatchesByMatchType(tournament, MatchType.PRELIM);

        List<List<Match>> matches = new ArrayList<>();
        matches.add(matchesPrelim);
        map.put("allMatches", matches);

        return new ModelAndView(map, "views/match/_matches.vm");
    }

    public static ModelAndView showFinalMatches(Request request, Response response){
        HashMap<String, Object> map = new HashMap<>();

        EntityManager em = request.attribute("em");
        TournamentService tournamentService = new TournamentService(em);

        final String tournamentId = request.params(":tid");
        Tournament tournament = tournamentService.getTournament(Long.parseLong(tournamentId));
        map.put("t", tournament);

        List<Match> matchesQuarter = tournamentService.getMatchesByMatchType(tournament, MatchType.QUARTERFINAL);
        List<Match> matchesSemi = tournamentService.getMatchesByMatchType(tournament, MatchType.SEMIFINAL);
        List<Match> matchesSmall = tournamentService.getMatchesByMatchType(tournament, MatchType.SMALLFINAL);
        List<Match> matchesFinal = tournamentService.getMatchesByMatchType(tournament, MatchType.FINAL);

        List<List<Match>> finalMatches = new ArrayList<>();
        finalMatches.add(matchesQuarter);
        finalMatches.add(matchesSemi);
        finalMatches.add(matchesSmall);
        finalMatches.add(matchesFinal);

        map.put("allMatches", finalMatches);

        return new ModelAndView(map, "views/match/_matches.vm");
    }
}
