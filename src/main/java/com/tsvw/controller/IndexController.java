package com.tsvw.controller;

import com.tsvw.model.Tournament;
import com.tsvw.service.TournamentService;
import org.hibernate.Hibernate;
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

        return new ModelAndView(map, "views/index/tournament.vm");
    }

}
