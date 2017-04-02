package com.example.erielmarimon.driftwoodsoccer.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.erielmarimon.driftwoodsoccer.activities.SearchableActivity;
import com.example.erielmarimon.driftwoodsoccer.fragments.GroupManagementFragment;
import com.example.erielmarimon.driftwoodsoccer.models.Player;
import com.example.erielmarimon.driftwoodsoccer.util.Constants;
import com.example.erielmarimon.driftwoodsoccer.util.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eriel.Marimon on 4/1/17.
 */

public class SearchPlayersTask extends AsyncTask<String, Void, List<Player>> {

    private final String LOG_TAG = getClass().getSimpleName();

    @Override
    protected List<Player> doInBackground(String... params) {

        String username = params[0];
        boolean active = params[1].equalsIgnoreCase(Boolean.TRUE.toString());

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String responseString = null;

        try{
            URL getPlayersUrl = buildGetPlayersByUsernameUrl(username, active);
            Log.v(LOG_TAG, getPlayersUrl.toString());
            urlConnection = initUrlConnection(getPlayersUrl);

            Log.v(LOG_TAG, urlConnection.toString());

            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }

            responseString = buffer.toString();

            // if response is not null, then parse response
            if(responseString != null){
                return getListOfPlayers(responseString);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            StackTraceElement[] trace = e.getStackTrace();
            for(StackTraceElement el : trace){
                Log.e(LOG_TAG, el.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
            StackTraceElement[] trace = e.getStackTrace();
            for(StackTraceElement el : trace){
                Log.e(LOG_TAG, el.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
            StackTraceElement[] trace = e.getStackTrace();
            for(StackTraceElement el : trace){
                Log.e(LOG_TAG, el.toString());
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Player> players) {

        if (players == null) return;

        SearchableActivity.searchResultAdapter.clear();
        SearchableActivity.searchResultAdapter.addAll(players);

    }

    private List<Player> getListOfPlayers(String responseString) throws JSONException {
        JSONObject response = new JSONObject(responseString);
        JSONArray playersJsonList = response.getJSONArray(Constants.DriftwoodDb.WRAPER_DATA_KEY);

        List<Player> players = new ArrayList<>();
        for (int i = 0; i < playersJsonList.length(); i++){
            JSONObject playerJson = playersJsonList.getJSONObject(i);

            Log.v(LOG_TAG, playerJson.toString());
            Log.v(LOG_TAG, "------");
            Player player = Helper.jsonStringToPlayer(playerJson.toString());
            players.add(player);
        }
        return players;
    }

    private HttpURLConnection initUrlConnection(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod(Constants.RequestMethods.GET);
        return urlConnection;
    }

    private URL buildGetPlayersByUsernameUrl(String username, boolean active) throws MalformedURLException {
        String url = Constants.DriftwoodDb.BASE_URL +
                Constants.DriftwoodDb.PLAYERS_END_POINT +
                Constants.Symbols.QUERY +
                Constants.DriftwoodDb.USERNAME_KEY + Constants.Symbols.EQUALS + username +
                Constants.Symbols.AMPERSAND +
                Constants.DriftwoodDb.ACTIVE_KEY + Constants.Symbols.EQUALS + active;
        return new URL(url);
    }
}