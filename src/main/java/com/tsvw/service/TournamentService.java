package com.tsvw.service;


import com.tsvw.model.Match;
import com.tsvw.model.MatchType;
import com.tsvw.model.Tournament;
import com.tsvw.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

public class TournamentService {

        public List<Tournament> getAllTournaments() {
        EntityManager entityManager = JPAUtil.getEntityManager();

        entityManager.getTransaction().begin();
        List<Tournament> tournaments = entityManager.createQuery("select t from tournaments as t").getResultList();

        entityManager.close();

        return tournaments;
    }

    public Tournament getTournament(Long id) {
        EntityManager entityManager = JPAUtil.getEntityManager();

        Tournament tournament = entityManager.find(Tournament.class, id);

        return tournament;
    }

    public List<Match> getMatchesByMatchType(Tournament tournament, MatchType matchType) {
        List<Match> matches = tournament.getMatches().stream()
                .filter(m -> m.getMatchType() == matchType)
                .collect(Collectors.toList());
        return matches;
    }
}
