package com.example.erielmarimon.driftwoodsoccer.util;

/**
 * Created by Eriel.Marimon on 3/28/17.
 */

public class Constants {
    public static class DriftwoodDb{
//        public static final String BASE_URL = "http://192.168.2.119:8080/CoreServices";
        public static final String BASE_URL = "http://192.168.1.12:8080/CoreServices";

        public static final String PLAYERS_END_POINT = "/players";

        public static final String WRAPER_DATA_KEY = "data";
        public static final String USERNAME_KEY = "username";
        public static final String ACTIVE_KEY = "active";

    }

    public static class Symbols{
        public static final String QUERY = "?";
        public static final String EQUALS = "=";
        public static final String AMPERSAND = "&";
    }

    public static class RequestMethods{
        public static final String GET = "GET";
    }

    public class CustomIntentExtras {
        public static final String HEADER_ACTION = "driftwoodHeaderAction";

        public static final String ACTION_ADD_PLAYER_TO_GROUP = "driftwoodAddPlayerToGroupAction";

        public static final String GAME_EXTRA = "driftwoodGameExtra";
        public static final String PLAYER_EXTRA = "driftwoodPlayerExtra";
    }
}
