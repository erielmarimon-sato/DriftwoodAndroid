package com.example.erielmarimon.driftwoodsoccer.util;

import android.content.SharedPreferences;

import com.example.erielmarimon.driftwoodsoccer.activities.MainActivity;
import com.example.erielmarimon.driftwoodsoccer.models.Game;
import com.example.erielmarimon.driftwoodsoccer.models.Player;
import com.example.erielmarimon.driftwoodsoccer.models.net.Result;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public static void initTestPlayer(final SharedPreferences.Editor globalSharedPrefEditor){
        MainActivity.playerService.getPlayer("58d84cbbfcec77b0c5323d2c").enqueue(new Callback<Result<Player>>() {
            @Override
            public void onResponse(Call<Result<Player>> call, Response<Result<Player>> response) {
                Player player = response.body().data;
                globalSharedPrefEditor.putString(
                        Constants.CustomIntentExtras.CURRENT_LOGGED_PLAYER,
                        Helper.objectToJsonString(player));
                globalSharedPrefEditor.commit();
            }

            @Override
            public void onFailure(Call<Result<Player>> call, Throwable t) {

            }
        });
    }

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

}
