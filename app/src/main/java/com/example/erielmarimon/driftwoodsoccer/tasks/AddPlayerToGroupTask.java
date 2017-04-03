package com.example.erielmarimon.driftwoodsoccer.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.erielmarimon.driftwoodsoccer.activities.SearchableActivity;
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
 * Created by Eriel.Marimon on 4/2/17.
 */

public class AddPlayerToGroupTask extends AsyncTask<String, Void, String> {

    private final String LOG_TAG = getClass().getSimpleName();


    @Override
    protected String doInBackground(String... params) {

        String groupId = params[0];
        String playerId = params[1];

        return null;
    }

}
