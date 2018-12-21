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

import spark.Request;
import spark.template.velocity.VelocityTemplateEngine;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
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
//            SessionFactory sf = new Configuration().configure().buildSessionFactory();
//            EntityManager session = sf.createEntityManager();
            final String PERSISTENCE_UNIT_NAME = "TournamentPersistence";
            EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            EntityManager session = emf.createEntityManager();
            request.attribute("emf", emf);
            request.attribute("em", session);
        });

        before((request, response) -> {
            //todo: geht safe eleganter ... aber gerade kein bock agj
            final String uri = request.uri();
            if(uri.contains("/backend")){
                if(!uri.contains("/backend/login")){
                    final Boolean loggedin = (Boolean) request.session().attribute("loggedin");
                    if(loggedin == null  || loggedin == false){
                        response.redirect("/backend/login");
                    }
                }
            }

        });

        after((request, response) -> {
            EntityManager session = (EntityManager)request.attribute("em");
            session.close();
            EntityManagerFactory emf = (EntityManagerFactory)request.attribute("emf");
            emf.close();
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
            get("/login", BackendController::loginForm, velocityTemplateEngine);
            get("/logout", BackendController::logout, velocityTemplateEngine);
            post("/login", BackendController::login);
            get("/createExampleTournament", BackendController::createExampleTournament);
        });

    }
}
