package com.tsvw.controller;

import com.tsvw.model.Match;
import com.tsvw.model.MatchType;
import com.tsvw.model.Tournament;
import com.tsvw.service.TournamentService;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MatchController {

    public static <T> List<List<T>> getPages(Collection<T> c, Integer pageSize, Integer pageNum) {
        if (c == null)
            return Collections.emptyList();
        List<T> list = new ArrayList<T>(c);
        if (pageSize == null || pageSize <= 0 || pageSize > list.size())
            pageSize = list.size();
        int numPages = (int) Math.ceil((double)list.size() / (double)pageSize);
        List<List<T>> pages = new ArrayList<List<T>>(numPages);
        //for (int pageNum = 0; pageNum < numPages;)
            //pages.add(list.subList(pageNum * pageSize, Math.min(++pageNum * pageSize, list.size())));
        pages.add(list.subList(pageNum * pageSize, Math.min(++pageNum * pageSize, list.size())));
        return pages;
    }

    public static ModelAndView showPrelimMatches(Request request, Response response){
        HashMap<String, Object> map = new HashMap<>();

        EntityManager em = request.attribute("em");
        TournamentService tournamentService = new TournamentService(em);

        final String tournamentId = request.params(":tid");
        Tournament tournament = tournamentService.getTournament(Long.parseLong(tournamentId));
        map.put("t", tournament);

        List<Match> matchesPrelim = tournamentService.getMatchesByMatchType(tournament, MatchType.PRELIM);

        String page = request.queryParams("page");
        if (page != null) {
            final int totalCount = matchesPrelim.size();
            final int resultsPerPage = 4;

            int pageNo = Integer.parseInt(page);

            final int maxPageCnt = totalCount / resultsPerPage;
            if (pageNo < 1) pageNo = 0;
            else if (pageNo > maxPageCnt) pageNo = (int) Math.ceil(maxPageCnt);

/*
            int offset0 = pageNo * resultsPerPage;
            int offset = offset0 - resultsPerPage;
            matchesPrelim = getSlice(matchesPrelim.stream(), offset, offset + resultsPerPage - 1).collect(Collectors.toList());
            */

            matchesPrelim = getPages(matchesPrelim, resultsPerPage, pageNo).stream().findFirst().get();
        }

        List<List<Match>> matches = new ArrayList<>();
        matches.add(matchesPrelim);
        map.put("allMatches", matches);

        return new ModelAndView(map, "views/match/_matches.vm");
    }

    public static ModelAndView showFinalMatches(Request request, Response response) {
        HashMap<String, Object> map = new HashMap<>();

        EntityManager em = request.attribute("em");
        TournamentService tournamentService = new TournamentService(em);

        final String tournamentId = request.params(":tid");
        Tournament tournament = tournamentService.getTournament(Long.parseLong(tournamentId));
        map.put("t", tournament);

        List<List<Match>> finalMatches = new ArrayList<>();
        List<Match> matchesQuarter = tournamentService.getMatchesByMatchType(tournament, MatchType.QUARTERFINAL);
        List<Match> matchesSemi = tournamentService.getMatchesByMatchType(tournament, MatchType.SEMIFINAL);
        List<Match> matchesSmall = tournamentService.getMatchesByMatchType(tournament, MatchType.SMALLFINAL);
        List<Match> matchesFinal = tournamentService.getMatchesByMatchType(tournament, MatchType.FINAL);
        finalMatches.add(matchesQuarter);
        finalMatches.add(matchesSemi);
        finalMatches.add(matchesSmall);
        finalMatches.add(matchesFinal);

        map.put("allMatches", finalMatches);

        return new ModelAndView(map, "views/match/_matches.vm");
    }

    public static ModelAndView showQuarterFinalMatches(Request request, Response response) {
        HashMap<String, Object> map = new HashMap<>();

        EntityManager em = request.attribute("em");
        TournamentService tournamentService = new TournamentService(em);

        final String tournamentId = request.params(":tid");
        Tournament tournament = tournamentService.getTournament(Long.parseLong(tournamentId));
        map.put("t", tournament);

        List<List<Match>> finalMatches = new ArrayList<>();
        finalMatches.add(tournamentService.getMatchesByMatchType(tournament, MatchType.QUARTERFINAL));

        map.put("allMatches", finalMatches);

        return new ModelAndView(map, "views/match/_matches.vm");
    }

    public static ModelAndView showSemiFinalMatches(Request request, Response response) {
        HashMap<String, Object> map = new HashMap<>();

        EntityManager em = request.attribute("em");
        TournamentService tournamentService = new TournamentService(em);

        final String tournamentId = request.params(":tid");
        Tournament tournament = tournamentService.getTournament(Long.parseLong(tournamentId));
        map.put("t", tournament);

        List<List<Match>> finalMatches = new ArrayList<>();
        finalMatches.add(tournamentService.getMatchesByMatchType(tournament, MatchType.SEMIFINAL));

        map.put("allMatches", finalMatches);

        return new ModelAndView(map, "views/match/_matches.vm");
    }

    public static ModelAndView showRealFinalMatches(Request request, Response response) {
        HashMap<String, Object> map = new HashMap<>();

        EntityManager em = request.attribute("em");
        TournamentService tournamentService = new TournamentService(em);

        final String tournamentId = request.params(":tid");
        Tournament tournament = tournamentService.getTournament(Long.parseLong(tournamentId));
        map.put("t", tournament);

        List<List<Match>> finalMatches = new ArrayList<>();
        finalMatches.add(tournamentService.getMatchesByMatchType(tournament, MatchType.SMALLFINAL));
        finalMatches.add(tournamentService.getMatchesByMatchType(tournament, MatchType.FINAL));

        map.put("allMatches", finalMatches);

        return new ModelAndView(map, "views/match/_matches.vm");
    }

    public static ModelAndView showCurrentMatch(Request request, Response response){
        HashMap<String, Object> map = new HashMap<>();

        EntityManager em = request.attribute("em");
        TournamentService tournamentService = new TournamentService(em);

        final String tournamentId = request.params(":tid");
        Tournament tournament = tournamentService.getTournament(Long.parseLong(tournamentId));
        //map.put("t", tournament);

        Match currentMatch = tournamentService.getCurrentMatch(tournament).orElse(null);

        map.put("m", currentMatch);

        return new ModelAndView(map, "views/match/_current.vm");
    }
}
