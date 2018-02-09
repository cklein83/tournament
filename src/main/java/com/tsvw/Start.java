package com.tsvw;

import com.tsvw.controller.*;
import com.tsvw.service.UpdateService;
import com.tsvw.util.JPAUtil;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import spark.ModelAndView;

import spark.template.velocity.VelocityTemplateEngine;

import javax.persistence.EntityManager;
import java.util.HashMap;

import static spark.Spark.*;

public class Start {
    private final static VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

    public static UpdateService updateService = new UpdateService();

    public static void main(String[] args) {

        System.setProperty("hibernate.dialect.storage_engine", "myisam");
        port(8888);
        staticFileLocation("/static");

        webSocket("/update", UpdateService.class);

        before((request, response) -> {
            SessionFactory sf = new Configuration().configure().buildSessionFactory();
            EntityManager session = sf.createEntityManager();
            request.attribute("em", session);
        });

        after((request, response) -> {
            EntityManager session = (EntityManager)request.attribute("em");
            session.close();
        });

        // index
        path("/", () -> {
            get("", IndexController::index, velocityTemplateEngine);
            get("/", IndexController::index, velocityTemplateEngine);
        });

        // tournament
        path("/tournament", () -> {
            get("/:id", IndexController::showTournament, velocityTemplateEngine);
        });

        // match
        path("/match", () -> {
            get("/prelimMatches/:tid", MatchController::showPrelimMatches, velocityTemplateEngine);
            get("/finalMatches/:tid", MatchController::showFinalMatches, velocityTemplateEngine);
        });

        // match
        path("/group", () -> {
            get("/rankedGroups/:tid", GroupController::showRankedGroups, velocityTemplateEngine);
        });

        // backend
        path("/backend", () -> {
            get("", BackendController::index, velocityTemplateEngine);
            get("/", BackendController::index, velocityTemplateEngine);
            get("/matches", BackendController::matchesList, velocityTemplateEngine);
            post("/matches", BackendController::saveListMatch);
            get("/createExampleTournament", BackendController::createExampleTournament);
        });

    }
}
