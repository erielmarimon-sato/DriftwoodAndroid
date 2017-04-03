package com.example.erielmarimon.driftwoodsoccer.interfaces;

import com.example.erielmarimon.driftwoodsoccer.models.net.Player;
import com.example.erielmarimon.driftwoodsoccer.models.net.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Eriel.Marimon on 4/3/17.
 */

public interface PlayerService {
    @GET
    Call<Result<Player>> getPlayer(@Url String url);
}
