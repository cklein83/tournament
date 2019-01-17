package com.tsvw.controller;

import com.tsvw.model.Tournament;
import com.tsvw.service.TournamentService;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;

public class IndexController {


    public static ModelAndView index(Request request, Response response) {
        HashMap<String, Object> map = new HashMap<>();

        EntityManager em = request.attribute("em");
        TournamentService tournamentService = new TournamentService(em);
        List<Tournament> tournaments = tournamentService.getAllTournaments();

        map.put("tournaments", tournaments);

        return new ModelAndView(map, "views/index/index.vm");
    }

    public static ModelAndView showTournament(Request request, Response response) {
        HashMap<String, Object> map = new HashMap<>();

        EntityManager em = request.attribute("em");
        TournamentService tournamentService = new TournamentService(em);

        final String tournamentId = request.params(":id");
        Tournament tournament = tournamentService.getTournament(Long.parseLong(tournamentId));
        map.put("t", tournament);

        String beamerMode = request.queryParams("beamer");
        if (beamerMode != null && Boolean.parseBoolean(beamerMode)) {
            map.put("beamerMode", true);
        }

        /*
        map.put("prelimDone", tournament.isPreliminationDone());
        */

        return new ModelAndView(map, "views/index/tournament.vm");
    }


}
