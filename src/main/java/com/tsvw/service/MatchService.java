package com.tsvw.service;

import com.tsvw.model.*;
import com.tsvw.util.JPAUtil;
import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class MatchService {
//
//    public List<Match> getAllTournaments() {
//        EntityManager entityManager = JPAUtil.getEntityManager();
//
//        entityManager.getTransaction().begin();
//        List<Match> allMatches = entityManager.createQuery("select m from Matches as m").getResultList();
//
//        entityManager.close();
//        JPAUtil.shutdown();
//        return allMatches;
//    }
//
//    public Match getCurrentMatch(){
//        EntityManager entityManager = JPAUtil.getEntityManager();
//
//        entityManager.getTransaction().begin();
//        List<Match> activeMatches = entityManager.createQuery("select m from Matches as m where m.status = '0'").getResultList();
//
//        if (activeMatches.isEmpty()){
//            //SELECT * FROM Matches ORDER BY timestamp DESC LIMIT 1;
//            List<Match>  resultList = entityManager.createQuery("SELECT m FROM Matches AS m ORDER BY m.timestamp DESC")
//                    .setMaxResults(1).getResultList();
//            return resultList.get(0);
//        }
//
//        entityManager.close();
//        JPAUtil.shutdown();
//        return activeMatches.get(0);
//    }
//
//    /**
//     * Delete a match by id
//     *
//     * @param matchId the Id of the match you want to delete
//     * @return false if match could not be delete because it was not found
//     */
//    public boolean deleteMatch(Long matchId){
//        EntityManager entityManager = JPAUtil.getEntityManager();
//        entityManager.getTransaction().begin();
//
//
//        Match match = entityManager.find(Match.class, matchId);
//        final Tournament season = match.getSeason();
//        final Team keeperTeam1 = match.getKeeperTeam1();
//        final Team keeperTeam2 = match.getKeeperTeam2();
//        final Team strikerTeam1 = match.getStrikerTeam1();
//        final Team strikerTeam2 = match.getStrikerTeam2();
//
//        keeperTeam1.getMatches().removeIf(m -> m.getId() == matchId);
//        keeperTeam2.getMatches().removeIf(m -> m.getId() == matchId);
//
//        season.getMatches().removeIf(m -> m.getId() == matchId);
//
//        entityManager.merge(season);
//        entityManager.merge(keeperTeam1);
//        entityManager.merge(keeperTeam2);
//
//        // only relevant for death match and death match bo3
//        if(strikerTeam1 !=  null){
//            strikerTeam1.getMatches().removeIf(m -> m.getId() == matchId);
//            entityManager.merge(strikerTeam1);
//        }
//        if(strikerTeam2 != null){
//            strikerTeam2.getMatches().removeIf(m -> m.getId() == matchId);
//            entityManager.merge(strikerTeam2);
//        }
//
//        entityManager.remove(match);
//
//        entityManager.getTransaction().commit();
//
//        JPAUtil.shutdown();
//        return true;
//    }
//

    public Match getMatch(Long id) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        //entityManager.getTransaction().begin();
        Match match = entityManager.find(Match.class, id);
        return match;
    }
//
//    public boolean playersValid(Team... pX ){
//
//        List<Team> collect = Arrays.stream(pX).filter(distinctByKey(Team::getId)).collect(Collectors.toList());
//
//        if(pX.length == collect.size())return true;
//        return false;
//    }
//
//    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
//        Set<Object> seen = ConcurrentHashMap.newKeySet();
//        return t -> seen.add(keyExtractor.apply(t));
//    }
//
//    /**
//     * Create a match, if striker team 1 (white) and striker team 2 (black) is not present the match is either of type
//     * Matchtype.
//     * @param keeperTeam1
//     * @param strikerTeam1
//     * @param keeperTeam2
//     * @param strikerTeam2
//     * @param season
//     * @return
//     */
//    public long createMatch(Team keeperTeam1, Team strikerTeam1, Team keeperTeam2, Team strikerTeam2, Tournament season) {
//
//        EntityManager entityManager = JPAUtil.getEntityManager();
//        entityManager.getTransaction().begin();
//
//        Match match = new Match(keeperTeam1, strikerTeam1, keeperTeam2, strikerTeam2, REGULAR, season);
//
//        List<Team> loksafePlayers = checkIfPlayerIsLoksafe(Arrays.asList(keeperTeam1, strikerTeam1, keeperTeam2, strikerTeam2));
//        if(!loksafePlayers.isEmpty())
//        {
//            match.setLoksafePlayer(loksafePlayers.get(0));
//        }
//
//        Tournament s = entityManager.find(Tournament.class, season.getId());
//
//        s.getMatches();
//        s.addMatch(match);
//
//        entityManager.merge(season);
//        entityManager.persist(match);
//
//        keeperTeam1.addMatch(match);
//        entityManager.merge(keeperTeam1);
//
//        keeperTeam2.addMatch(match);
//        entityManager.merge(keeperTeam2);
//
//        strikerTeam1.addMatch(match);
//        entityManager.merge(strikerTeam1);
//
//        strikerTeam2.addMatch(match);
//        entityManager.merge(strikerTeam2);
//
//        entityManager.getTransaction().commit();
//        JPAUtil.shutdown();
//
//        return match.getId();
//    }
//
//    public long createMatch(Team player1, Team player2, Matchtype matchtype, Tournament season) {
//
//        EntityManager entityManager = JPAUtil.getEntityManager();
//        entityManager.getTransaction().begin();
//
//        Match m = new Match(player1, player2, matchtype, season);
//
//        List<Team> loksafePlayers = checkIfPlayerIsLoksafe(Arrays.asList(player1, player2));
//        if(!loksafePlayers.isEmpty())
//        {
//            m.setLoksafePlayer(loksafePlayers.get(0));
//        }
//
//        Tournament s = entityManager.find(Tournament.class, season.getId());
//
//        s.getMatches();
//        s.addMatch(m);
//
//        entityManager.persist(m);
//
//        player1.addMatch(m);
//        entityManager.merge(player1);
//
//        player2.addMatch(m);
//        entityManager.merge(player2);
//
//        entityManager.getTransaction().commit();
//        JPAUtil.shutdown();
//
//        return m.getId();
//    }
//
//    /**
//     * Updates a match
//     *
//     * @param matchId : the id of that match that needs to be updated
//     * @param goalsTeam1 : the goals for team 1
//     * @param goalsTeam2 : the goals for team 2
//     * @return : a match id if a follow up game is created
//     */
//    public void updateMatch(long matchId, int goalsTeam1, int goalsTeam2) {
//        EntityManager entityManager = JPAUtil.getEntityManager();
//
//        Match match = entityManager.find(Match.class, matchId);
//        match.setGoalsTeam1(goalsTeam1);
//        match.setGoalsTeam2(goalsTeam2);
//
//        //TODO: not clean code change this  #agj
//        // reopen maybe necessary because createFollowUpGame opens and closes the entityManager
//        if(!entityManager.isOpen()){
//            entityManager = JPAUtil.getEntityManager();
//        }
//
//        // in any case update the match
//        entityManager.getTransaction().begin();
//        entityManager.merge(match);
//
//        entityManager.getTransaction().commit();
//        JPAUtil.shutdown();
//    }
//
//    public Long finishMatch(long matchId){
//        EntityManager entityManager = JPAUtil.getEntityManager();
//
//        Match match = entityManager.find(Match.class, matchId);
//        int goalsTeam1 = match.getGoalsTeam1();
//        int goalsTeam2 = match.getGoalsTeam2();
//
//        Long followUpMatch = null;
//
//        // check if this match needs to be set to status finished
//        switch (match.getMatchtype())
//        {
//            case DEATH_MATCH_BO3:
//                if(goalsTeam1 == 2 || goalsTeam2 == 2) match.setStatus(Status.FINISHED);
//                break;
//            case DEATH_MATCH:
//                if(goalsTeam1 == 1 || goalsTeam2 == 1) match.setStatus(Status.FINISHED);
//                break;
//            case REGULAR :
//                if(goalsTeam1 == 5 || goalsTeam2 == 5){
//                    match.setStatus(Status.FINISHED);
//                    // -- check if Deathmatch is necessary --
//                    if (goalsTeam1 == 0){
//                        followUpMatch = createFollowUpGame(match, match.getKeeperTeam1(), match.getStrikerTeam1());
//                    }
//                    else if (goalsTeam2 == 0){
//                        followUpMatch = createFollowUpGame(match, match.getKeeperTeam2(), match.getStrikerTeam2());
//                    }
//                }
//                break;
//        }
//
//        //TODO: not clean code change this  #agj
//        // reopen maybe necessary because createFollowUpGame opens and closes the entityManager
//        if(!entityManager.isOpen()){
//            entityManager = JPAUtil.getEntityManager();
//        }
//
//        // in any case update the match
//        entityManager.getTransaction().begin();
//        entityManager.merge(match);
//
//        entityManager.getTransaction().commit();
//        JPAUtil.shutdown();
//
//        return  followUpMatch;
//    }
//
//    private List<Team> checkIfPlayerIsLoksafe(List<Team> playerList){
//
//        List<Team> playersLockSafe = playerList.stream()
//                .filter(player -> player.getLokSafe() == true)
//                .collect(Collectors.toList());
//
//        return playersLockSafe;
//    }
//
//    private long createFollowUpGame(Match match, Team player1, Team player2){
//
//        if(this.teamIsLokSafe(player1, player2)){
//            return createMatch(player1, player2, DEATH_MATCH_BO3, match.getSeason());
//        }
//        else{
//            return createMatch(player1, player2, DEATH_MATCH, match.getSeason());
//        }
//    }
//
//    public boolean teamIsLokSafe(Team player1, Team player2){
//        return  (Boolean.TRUE.equals(player1.getLokSafe()) || Boolean.TRUE.equals(player2.getLokSafe()));
//    }
//

}
