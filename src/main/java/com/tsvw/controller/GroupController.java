package com.tsvw.controller;

import com.tsvw.model.Match;
import com.tsvw.model.MatchType;
import com.tsvw.model.Tournament;
import com.tsvw.service.TournamentService;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GroupController {

    private static final TournamentService tournamentService = new TournamentService();

    public static ModelAndView showRankedGroups(Request request, Response response) {
        HashMap<String, Object> map = new HashMap<>();

        final String tournamentId = request.params(":tid");
        Tournament tournament = tournamentService.getTournament(Long.parseLong(tournamentId));
        map.put("t", tournament);


        map.put("prelimDone", tournament.isPreliminationDone());

        List<Match> matchesPrelim = tournamentService.getMatchesByMatchType(tournament, MatchType.PRELIM);

        return new ModelAndView(map, "views/group/_groups.vm");
    }
}
