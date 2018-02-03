package com.tsvw.service;


import com.tsvw.model.Tournament;
import com.tsvw.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;

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

}
