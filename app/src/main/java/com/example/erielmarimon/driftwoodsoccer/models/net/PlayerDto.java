package com.example.erielmarimon.driftwoodsoccer.models.net;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Eriel.Marimon on 4/3/17.
 */

public class PlayerDto {

    @SerializedName("_id")
    public String id;

    public String name;
    
    public String username;
    public String password;
    public Integer totalGoals;
    public Integer totalAssists;
    public Integer noShow;
    public Integer totalGames;
    public Integer effectiveness;
    public Date playingSince;
    public Date lastDayPlayed;
    public boolean active;
    
    
    @Override
    public String toString() {
        return name + "(" + username + ")";
    }
}
