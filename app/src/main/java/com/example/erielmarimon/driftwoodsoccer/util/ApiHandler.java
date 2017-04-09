package com.example.erielmarimon.driftwoodsoccer.util;

import com.example.erielmarimon.driftwoodsoccer.interfaces.GameService;
import com.example.erielmarimon.driftwoodsoccer.interfaces.GroupService;
import com.example.erielmarimon.driftwoodsoccer.interfaces.PlayerService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Eriel.Marimon on 4/4/17.
 */

public class ApiHandler {

    static Gson gson = new GsonBuilder()
            .setDateFormat(DateFormat.DEFAULT)
            .serializeNulls()
            .setPrettyPrinting()
            .create();

    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.DriftwoodDb.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    public static PlayerService createPlayerServiceApi() {
        return retrofit.create(PlayerService.class);
    }

    public static GameService createGameServiceApi() {
        return retrofit.create(GameService.class);
    }

    public static GroupService createGroupServiceApi() {
        return retrofit.create(GroupService.class);
    }
}
