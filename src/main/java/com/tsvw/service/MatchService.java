package com.tsvw.service;

import com.tsvw.model.Match;

import javax.persistence.EntityManager;


public class MatchService extends Service {

    public MatchService(EntityManager em) {
        super(em);
    }

    public Match getMatch(Long id) {
        Match match = em.find(Match.class, id);
        return match;
    }

}
