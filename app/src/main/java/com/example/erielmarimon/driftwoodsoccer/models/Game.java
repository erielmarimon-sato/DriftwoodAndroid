package com.example.erielmarimon.driftwoodsoccer.models;

import com.example.erielmarimon.driftwoodsoccer.util.StringFormatter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by Eriel.Marimon on 3/22/17.
 */

public class Game {
    public String id;
    public String date;
    public String time;
    public List<String> teamOne;
    public List<String> teamTwo;
    public List<String> players;
    public Integer teamOneScore;
    public Integer teamTwoScore;
    public String mVP;
    public String gameType;

    @Override
    public String toString() {
        String format = "Id: %s, Game: %s, Date: %s";
        return StringFormatter.format(format, id, gameType, date);
    }
}
