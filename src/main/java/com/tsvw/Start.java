package com.tsvw;

import com.tsvw.controller.*;
import com.tsvw.service.UpdateService;

import spark.template.velocity.VelocityTemplateEngine;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import static spark.Spark.*;

public class Start {
    private final static VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

    public static UpdateService updateService = new UpdateService();

    static final String PERSISTENCE_UNIT_NAME = "TournamentPersistence";
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    public static void main(String[] args) {

        System.setProperty("hibernate.dialect.storage_engine", "myisam");
        port(8888);
        staticFileLocation("/static");

        webSocket("/update", UpdateService.class);

        before((request, response) -> {
//            SessionFactory sf = new Configuration().configure().buildSessionFactory();
//            EntityManager session = sf.createEntityManager();

            final long t1 = System.currentTimeMillis();


            EntityManager session = emf.createEntityManager();
            request.attribute("emf", emf);
            request.attribute("em", session);

            System.out.println("time: " + (System.currentTimeMillis() - t1));
        });

        before((request, response) -> {
            request.session().maxInactiveInterval(Integer.MAX_VALUE); // 1 hour
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
            //EntityManagerFactory emf = (EntityManagerFactory)request.attribute("emf");
            //emf.close();
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
            get("/quarterFinalMatches/:tid", MatchController::showQuarterFinalMatches, velocityTemplateEngine);
            get("/semiFinalMatches/:tid", MatchController::showSemiFinalMatches, velocityTemplateEngine);
            get("/realFinalMatches/:tid", MatchController::showRealFinalMatches, velocityTemplateEngine);
            get("/current/:tid", MatchController::showCurrentMatch, velocityTemplateEngine);
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

            get("/createTournament2018", BackendController::createExampleTournament2018);

            get("/createTournament2019", BackendController::createExampleTournament2019);
            get("/cleanupTournament2019", BackendController::cleanupTournament2019);

            get("/createTournament2020", BackendController::createExampleTournament2020);

            get("/createTournamentAH2020", BackendController::createExampleTournamentAH2020);

            get("/deleteTournament/:tid", BackendController::deleteTournamentById);
        });

    }
}
