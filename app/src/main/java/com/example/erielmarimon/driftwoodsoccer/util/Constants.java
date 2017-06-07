package com.example.erielmarimon.driftwoodsoccer.util;

import com.example.erielmarimon.driftwoodsoccer.fragments.GroupManagementFragment;
import com.example.erielmarimon.driftwoodsoccer.fragments.NewGameFragment;

/**
 * Created by Eriel.Marimon on 3/28/17.
 */

public class Constants {

    public static String CURRENT_GROUP_ID = "58e045ad77c80173b50bb073";

    public static class Search{
        public static final String SEARCH_ACTIVITY_SOURCE_KEY = "search_activity_source";
        public static final String SOURCE_ACTIVITY_NEW_GAME_FRAGMENT = NewGameFragment.class.getName();
        public static final String SOURCE_ACTIVITY_GROUP_MANAGEMENT_FRAGMENT = GroupManagementFragment.class.getName();
    }

    public static class DriftwoodDb{
//        public static final String BASE_URL = "http://localhost:8081/CoreServices/";
        public static final String BASE_URL = "http://192.168.2.119:8081/CoreServices/";
//        public static final String BASE_URL = "http://192.168.1.12:8081/CoreServices/";

        public static final String PLAYERS_END_POINT = "/players";
        public static final String GROUPS_END_POINT = "/groups";

        public static final String WRAPPER_DATA_KEY = "data";
        public static final String USERNAME_KEY = "username";
        public static final String ACTIVE_KEY = "active";

    }

    public static class Symbols{
        public static final String QUERY = "?";
        public static final String EQUALS = "=";
        public static final String AMPERSAND = "&";
        public static final String SLASH = "/";
    }

    public static class RequestMethods{
        public static final String GET = "GET";
        public static final String PUT = "PUT";

    }

    public class CustomIntentExtras {
        public static final String HEADER_ACTION = "driftwood_header_action";

        public static final String ACTION_ADD_PLAYER_TO_GROUP = "driftwood_add_player_to_group_action";
        public static final String ACTION_ADD_PLAYER_TO_GAME = "driftwood_add_player_to_game_action";

        public static final String ACTION_CREATE_GAME = "driftwood_create_game_action";
        public static final String GAME_EXTRA = "driftwood_game_extra";
        public static final String PLAYER_EXTRA = "driftwood_player_extra";
        public static final String CURRENT_LOGGED_PLAYER = "driftwood_current_logged_player_extra";
    }
}
