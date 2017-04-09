package com.example.erielmarimon.driftwoodsoccer.models;

import java.util.List;

/**
 * Created by Eriel.Marimon on 4/9/17.
 */

public class Group {
    public String id;
    public String dateCreated;
    public String lastUpdated;
    public List<String> playerIds;
    public String admin;

    @Override
    public String toString() {
        return "id = " + id + "  adminId = " + admin;
    }
}
