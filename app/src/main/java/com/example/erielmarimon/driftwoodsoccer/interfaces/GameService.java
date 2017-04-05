package com.example.erielmarimon.driftwoodsoccer.interfaces;

import com.example.erielmarimon.driftwoodsoccer.models.Game;
import com.example.erielmarimon.driftwoodsoccer.models.net.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Eriel.Marimon on 4/5/17.
 */

public interface GameService {

    // TODO: filter by groupId doesnt exist on the back end yet, create it! This returns all games.
    @GET("games")
    Call<Result<List<Game>>> getGroupGames(@Query("groupId") String groupId);
}
