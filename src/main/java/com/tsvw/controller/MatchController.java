package com.tsvw.controller;

import com.tsvw.model.Match;
import com.tsvw.model.Team;
import com.tsvw.model.Tournament;
import com.tsvw.service.MatchService;

import com.tsvw.util.Constants;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MatchController {
//
//    private static final MatchService matchService = new MatchService();
//    private static final SeasonService seasonService = new SeasonService();
//    private static final PlayerService playerService = new PlayerService();
//
//
//    public static ModelAndView showDashboard(Request request, Response Request){
//        Match currentMatch = matchService.getCurrentMatch();
//
//        HashMap<String, Match> matchesMap = new HashMap<>();
//        matchesMap.put("match", currentMatch);
//
//        return new ModelAndView(matchesMap, "views/match/dashboard_match.vm");
//    }
//
//    public static ModelAndView showPlayer(Request request, Response response){
//        final String matchId = request.params(":id");
//        Match match = matchService.getMatch(Long.parseLong(matchId));
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("match", match);
//
//        return new ModelAndView(map, "views/match/show_match.vm");
//    }
//
//
//    public static ModelAndView listMatches(Request request, Response response){
//        List<Match> allMatches = matchService.getAllTournaments();
//
//        HashMap<String, List<Match>> matchesMap = new HashMap<>();
//        matchesMap.put("matches", allMatches);
//
//        return new ModelAndView(matchesMap, "views/match/matches.vm");
//    }
//
//    public static String updateMatch(Request request, Response response){
//        // -- required attributes
//        Long matchId = Long.parseLong(request.queryParams("matchId"));
//        int goalsTeam1 = Integer.parseInt(request.queryParams("goalsTeam1"));
//        int goalsTeam2 = Integer.parseInt(request.queryParams("goalsTeam2"));
//
//        matchService.updateMatch(matchId, goalsTeam1, goalsTeam2);
//        return "";
//    }
//
//    public static String finishMatch(Request request, Response response){
//        Long matchId = Long.parseLong(request.queryParams("matchId"));
//        Long newMatchId = matchService.finishMatch(matchId);
//        return ""+newMatchId;
//    }
//
//    public static String instantFinish(Request request, Response response){
//        MatchController.updateMatch(request, response);
//        return MatchController.finishMatch(request, response);
//    }
//
//    public static ModelAndView showNewMatchFrom(Request request, Response response){
//        return new ModelAndView(prepareMatchForm(), "views/match/new_match.vm");
//    }
//
//
//    public static ModelAndView createNewMatch(Request request, Response response){
//        String matchType = request.queryParams("matchType");
//        // -- you receive only ids --
//        String season = request.queryParams("season");
//        String keeperTeam1 = request.queryParams("keeperTeam1");
//        String keeperTeam2 = request.queryParams("keeperTeam2");
//        String strikerTeam1 = request.queryParams("strikerTeam1");
//        String strikerTeam2 = request.queryParams("strikerTeam2");
//
//        Tournament s = seasonService.getSeason(Long.parseLong(season));
//        Team kT1 = playerService.getPlayer(Long.parseLong(keeperTeam1));
//        Team kT2 = playerService.getPlayer(Long.parseLong(keeperTeam2));
//
//        Long matchId = null;
//
//        /*
//        if (Matchtype.REGULAR.toString().equals(matchType)){
//
//            Team sT1 = playerService.getPlayer(Long.parseLong(strikerTeam1));
//            Team sT2 = playerService.getPlayer(Long.parseLong(strikerTeam2));
//
//            boolean playersValid = matchService.playersValid(kT1, kT2, sT1, sT2);
//
//            if(playersValid == false){
//
//                return getStateAndValidation(season, matchType, keeperTeam1, keeperTeam2, strikerTeam1, strikerTeam2);
//            }
//
//            matchId = matchService.createMatch(kT1,sT1,kT2,sT2,s);
//        }
//        else {
//
//            if (Matchtype.DEATH_MATCH.toString().equals(matchType)){
//                boolean playersValid = matchService.playersValid(kT1, kT2);
//                if(playersValid == false){
//
//                    return getStateAndValidation(season, matchType, keeperTeam1, keeperTeam2, strikerTeam1, strikerTeam2);
//                }
//                matchId = matchService.createMatch(kT1, kT2, Matchtype.DEATH_MATCH, s);
//            }
//            else if(Matchtype.DEATH_MATCH_BO3.toString().equals(matchType))
//            {
//                boolean playersValid = matchService.playersValid(kT1, kT2);
//                if(playersValid == false){
//
//                    return getStateAndValidation(season, matchType, keeperTeam1, keeperTeam2, strikerTeam1, strikerTeam2);
//                }
//                matchId = matchService.createMatch(kT1, kT2, Matchtype.DEATH_MATCH_BO3, s);
//            }
//
//        }
//        */
//
//        response.redirect("/match/"+matchId);
//        // you never get to this state because of the redirect before ... but it is necessary
//        return new ModelAndView(new HashMap<>(), "views/player/new_match.vm");
//    }
//
//    public static String deleteMatch(Request request, Response response){
//        final String matchId = request.params(":id");
//        final long matchIdLong = Long.parseLong(matchId);
//
//        matchService.deleteMatch(matchIdLong);
//        return "Ok";
//    }
//
//    // helpers ...
//    private static ModelAndView getStateAndValidation(String season, String matchType, String keeperTeam1,
//                                                      String keeperTeam2, String strikerTeam1, String strikerTeam2){
//        // -- prepare form like you do on /new
//        Map map = prepareMatchForm();
//
//        // -- add also the validation info
//        List<String> validionProblems = new LinkedList<>();
//        validionProblems.add("Duplicate player(s)! Every match should consist of distinct players");
//        map.put(Constants.VALIDATION, validionProblems);
//
//        // now you need to restore the state of the form as it was before submitting
//        Map<String, Object> state = new HashMap<>();
//        state.put("seasonId", season);
//        state.put("matchTypeId", matchType);
//        state.put("keeperTeam1Id", keeperTeam1);
//        state.put("keeperTeam2Id", keeperTeam2);
//        state.put("strikerTeam1Id", strikerTeam1);
//        state.put("strikerTeam2Id", strikerTeam2);
//        map.put("state", state);
//
//        return new ModelAndView(map, "views/match/new_match.vm");
//    }
//
//    private static Map prepareMatchForm(){
//        HashMap<String, Object> map = new HashMap<>();
///*
//
//        // now get all players
//        List<Team> players = playerService.getPlayers();
//
//        // ... get all seasons
//        List<Tournament> allSeasons = seasonService.getAllSeasons();
//
//        map.put(Constants.PLAYERS, players);
//        map.put(Constants.SEASONS, allSeasons);
//        map.put(Constants.MATCH_TYPES, Matchtype.values());
//        */
//
//        return map;
//    }
}
