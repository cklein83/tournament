package com.tsvw.service;

import javax.persistence.EntityManager;

public class Service {
    EntityManager em;

    public Service(EntityManager em) {
        this.em = em;
    }
}
