package com.example.erielmarimon.driftwoodsoccer.models;

import com.example.erielmarimon.driftwoodsoccer.util.StringFormatter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Eriel.Marimon on 3/22/17.
 */

public class Player {


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
    public String playingSince;
    public String lastDayPlayed;
    public boolean active;

    @Override
    public String toString() {
        String format = "User: %s, Name: %s, Id: %s";
        return StringFormatter.format(format, username, name, id);
    }

}
