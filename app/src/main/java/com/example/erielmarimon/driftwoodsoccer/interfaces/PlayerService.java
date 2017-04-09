package com.example.erielmarimon.driftwoodsoccer.interfaces;

import com.example.erielmarimon.driftwoodsoccer.models.Player;
import com.example.erielmarimon.driftwoodsoccer.models.net.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Eriel.Marimon on 4/3/17.
 */


public interface PlayerService {

    @GET("players/{id}")
    Call<Result<Player>> getPlayer(@Path("id") String id);

    @GET("players")
    Call<Result<List<Player>>> getGroupPlayers(@Query("groupId") String groupId);

    @GET("players")
    Call<Result<List<Player>>> usernamePartialSearch(@Query("username") String username,
                                                     @Query("active") boolean active);



    // this should actualluy be a PATCH, fix the backend!!
    @PUT("players")
    Call<Result<Player>> updatePlayerGroup(@Query("id") String playerId,
                                           @Query("groupId") String groupId);
}
