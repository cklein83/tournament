package com.tsvw.service;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@WebSocket
public class UpdateService {

    private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();
    private static final MatchService matchService = new MatchService();

    static Map<Session, String> listeners = new ConcurrentHashMap<>();

    @OnWebSocketConnect
    public void connected(Session session) {

        System.out.println("someone conneced");
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
        /*
        final Match currentMatch = matchService.getCurrentMatch();
        if( currentMatch.getStatus() == Status.STARTED){
            if(message.equals("1")){
                int g1 = currentMatch.getGoalsTeam1();
                currentMatch.setGoalsTeam1(++g1);
            }
            else if(message.equals("2")){
                int g2 = currentMatch.getGoalsTeam2();
                currentMatch.setGoalsTeam2(++g2);
            }
            matchService.updateMatch(currentMatch.getId(), currentMatch.getGoalsTeam1(), currentMatch.getGoalsTeam2());
        }
        */
        sessions.stream().filter(Session::isOpen).forEach(session -> {
            try {
                if (type.equals("refresh-data")) {
                    session.getRemote().sendString(
                            /*
                            String.valueOf(new JSONObject()
                                    .put("goalsTeam1", currentMatch.getGoalsTeam1())
                                    .put("goalsTeam2", currentMatch.getGoalsTeam2()))
                                    */
                            "refresh-data"
                    );
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
