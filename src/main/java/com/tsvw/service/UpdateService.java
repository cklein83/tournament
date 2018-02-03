package com.tsvw.service;

import com.tsvw.model.Match;
import com.tsvw.model.Status;
import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.json.JSONObject;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

@WebSocket
public class UpdateService {

    private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();
    private static final MatchService matchService = new MatchService();

    static Map<Session, String> listeners = new ConcurrentHashMap<>();

    @OnWebSocketConnect
    public void connected(Session session) {

        //System.out.println("someone conneced");
        sessions.add(session);
    }

    @OnWebSocketClose
    public void closed(Session session, int statusCode, String reason) {
        sessions.remove(session);
    }

    @OnWebSocketMessage
    public void message(Session session, String message) throws IOException {
        //System.out.println("Got: " + message);   // Print message
        session.getRemote().sendString(message); // and send it back
    }

    public static void broadcastMessage(String type, String message) {
//        final Match currentMatch = matchService.getCurrentMatch();
//        if( currentMatch.getStatus() == Status.STARTED){
//            if(message.equals("1")){
//                int g1 = currentMatch.getGoalsTeam1();
//                currentMatch.setGoalsTeam1(++g1);
//            }
//            else if(message.equals("2")){
//                int g2 = currentMatch.getGoalsTeam2();
//                currentMatch.setGoalsTeam2(++g2);
//            }
//            matchService.updateMatch(currentMatch.getId(), currentMatch.getGoalsTeam1(), currentMatch.getGoalsTeam2());
//        }
//        sessions.stream().filter(Session::isOpen).forEach(session -> {
//            try {
//                if(type.equals("updateMatch")){
//                    session.getRemote().sendString(
//                            String.valueOf(new JSONObject()
//                                    .put("goalsTeam1", currentMatch.getGoalsTeam1())
//                                    .put("goalsTeam2", currentMatch.getGoalsTeam2()))
//                    );
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
    }
}
