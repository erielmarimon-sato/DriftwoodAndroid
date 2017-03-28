package com.example.erielmarimon.driftwoodsoccer.models;

import com.example.erielmarimon.driftwoodsoccer.util.StringFormatter;

import java.util.Date;
import java.util.List;

/**
 * Created by Eriel.Marimon on 3/22/17.
 */

public class Game {
    public static final String GAME_EXTRA = "Game";
    public static final String GAME_ID_EXTRA = "GameId";

    private String id;
    private Date date;
    private List<Player> teamOne;
    private List<Player> teamTwo;
    private List<Player> players;
    private Integer teamOneScore;
    private Integer teamTwoScore;
    private Player mVP;
    private String gameType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Player> getTeamOne() {
        return teamOne;
    }

    public void setTeamOne(List<Player> teamOne) {
        this.teamOne = teamOne;
    }

    public List<Player> getTeamTwo() {
        return teamTwo;
    }

    public void setTeamTwo(List<Player> teamTwo) {
        this.teamTwo = teamTwo;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Integer getTeamOneScore() {
        return teamOneScore;
    }

    public void setTeamOneScore(Integer teamOneScore) {
        this.teamOneScore = teamOneScore;
    }

    public Integer getTeamTwoScore() {
        return teamTwoScore;
    }

    public void setTeamTwoScore(Integer teamTwoScore) {
        this.teamTwoScore = teamTwoScore;
    }

    public Player getmVP() {
        return mVP;
    }

    public void setmVP(Player mVP) {
        this.mVP = mVP;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    @Override
    public String toString() {
        String format = "Id: %s, Game: %s, Date: %s";
        return StringFormatter.format(format, id, gameType, date);
    }
}
