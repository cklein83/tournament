package com.tsvw.controller;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class BackendController {

    //private static final TournamentService tournamentService = new TournamentService();

    public static ModelAndView index(Request request, Response response){
        HashMap<String, Object> map = new HashMap<>();

        return new ModelAndView(map, "views/backend/index.vm");
    }

}
