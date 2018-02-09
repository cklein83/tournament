package com.tsvw.service;

import com.tsvw.model.Team;

import javax.persistence.EntityManager;

public class TeamService extends Service {

    public TeamService(EntityManager em) {
        super(em);
    }

    public Team getTeam(Long id) {
        Team team = em.find(Team.class, id);
        return team;
    }

}
