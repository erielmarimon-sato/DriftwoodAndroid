package com.example.erielmarimon.driftwoodsoccer.util;

import com.example.erielmarimon.driftwoodsoccer.models.Game;
import com.example.erielmarimon.driftwoodsoccer.models.Player;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Eriel.Marimon on 3/22/17.
 */

//private String id;
//private String name;
//private String username;
//private String password;
//private Integer totalGoals;
//private Integer totalAssists;
//private Integer noShow;
//private Integer totalGames;
//private Integer effectiveness;
//private Date playingSince;
//private Date lastDayPlayed;
//private boolean active;

public class Helper {

    public static Game jsonStringToGame(String jsonString){
        ObjectMapper objectMapper = new ObjectMapper();
        Game game = null;

        try{
            game = objectMapper.readValue(jsonString, Game.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return game;
    }

    public static Player jsonStringToPlayer(String jsonStr){
        ObjectMapper mapperObj = new ObjectMapper();
        Player player = null;

        try{
            player = mapperObj.readValue(jsonStr, Player.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return player;
    }

    public static String objectToJsonString(Object object){

        ObjectMapper mapperObj = new ObjectMapper();
        String jsonStr = "";

        try {
            jsonStr = mapperObj.writeValueAsString(object);
            System.out.println(jsonStr);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonStr;
    }

    public static Player[] createPlayerList(int totalPlayers){

        Player[] players = new Player[totalPlayers];

        String id = "0";
        String name = "test";
        String username = "test";
        String password = "test";
        Integer totalGoals = 0;
        Integer totalAssists = 0;
        Integer noShow = 0;
        Integer totalGames = 0;
        Integer effectiveness = 0;
        Date playingSince = new Date();
        Date lastDayPlayed = new Date();
        boolean active = true;

        for(int i = 0; i < players.length; i++){
            Player newPlayer = new Player();

            newPlayer.set_id(id+i);
            newPlayer.setName(name + i);
            newPlayer.setUsername(username+i);
            newPlayer.setPassword(password+i);
            newPlayer.setTotalGoals(totalGoals);
            newPlayer.setTotalAssists(totalAssists);
            newPlayer.setNoShow(noShow);
            newPlayer.setTotalGames(totalGames);
            newPlayer.setEffectiveness(effectiveness);
            newPlayer.setPlayingSince(playingSince);
            newPlayer.setLastDayPlayed(lastDayPlayed);
            newPlayer.setActive(active);

            players[i] = newPlayer;
        }

        return players;
    }

    public static Game[] createGameList(int totalGames) {
        Game[] gameList = new Game[totalGames];

        Player[] t1 = createPlayerList(3);
        Player[] t2 = createPlayerList(3);

        String id = "0";
        Date date = new Date();
        List<Player> teamOne = Arrays.asList(t1);
        List<Player> teamTwo = Arrays.asList(t2);
        List<Player> players = Arrays.asList(addPlayerArrays(t1, t2));
        Integer teamOneScore = 0;
        Integer teamTwoScore = 0;
        Player mVP = t1[0];
        String gameType = "indoor";

        for (int i = 0; i < totalGames; i++) {
            Game newGame = new Game();
            newGame.setId(id+i);
            newGame.setDate(date);
            newGame.setTeamOne(teamOne);
            newGame.setTeamTwo(teamTwo);
            newGame.setTeamOneScore(teamOneScore);
            newGame.setTeamTwoScore(teamTwoScore);
            newGame.setmVP(mVP);
            newGame.setGameType(gameType);

            gameList[i] = newGame;
        }
        return gameList;
    }

    public static Player[] addPlayerArrays(Player[] a, Player[] b){
        Player[] c = new Player[a.length + b.length];
        int j=0;
        for (int i = 0; i < a.length; i++) {
            c[j] = a[i];
            j++;
        }
        for (int i = 0; i < b.length; i++){
            c[j] = b[i];
            j++;
        }
        return c;
    }
}
