package com.tsvw;

import com.tsvw.controller.*;
import com.tsvw.service.UpdateService;
import com.tsvw.util.JPAUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import spark.ModelAndView;

import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;

import static spark.Spark.*;

public class Start {
    private final static VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

    public static UpdateService updateService = new UpdateService();

    public static void main(String[] args) {

        System.setProperty("hibernate.dialect.storage_engine", "myisam");
        port(8888);
        staticFileLocation("/static");

        //webSocket("/update", UpdateService.class);

        after((request, response) -> {
            JPAUtil.shutdown();
        });

        // index
        path("/", () -> {
            get("", IndexController::index, velocityTemplateEngine);
        });

        // tournament
        path("/tournament", () -> {
            get("/:id", IndexController::showTournament, velocityTemplateEngine);
        });

        // backend
        path("/backend", () -> {
            get("", BackendController::index, velocityTemplateEngine);
            get("/createExampleTournament", BackendController::createExampleTournament);

        });

    }
}
