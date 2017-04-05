package com.example.erielmarimon.driftwoodsoccer.util;

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
    public static PlayerService createPlayerServiceApi() {
        Gson gson = new GsonBuilder()
                .setDateFormat(DateFormat.DEFAULT)
                .serializeNulls()
                .setPrettyPrinting()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PlayerService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(PlayerService.class);
    }
}
