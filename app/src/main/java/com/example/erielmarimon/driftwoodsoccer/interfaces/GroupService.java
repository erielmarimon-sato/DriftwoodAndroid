package com.example.erielmarimon.driftwoodsoccer.interfaces;

import com.example.erielmarimon.driftwoodsoccer.models.Group;
import com.example.erielmarimon.driftwoodsoccer.models.net.Result;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Eriel.Marimon on 4/8/17.
 */

public interface GroupService {
    @PUT("groups/{id}")
    Call<Result<Group>> addPlayer(@Path("id") String id, @Query("playerIds") List<String> playerIds);
}
