package com.example.erielmarimon.driftwoodsoccer.activities;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.erielmarimon.driftwoodsoccer.R;
import com.example.erielmarimon.driftwoodsoccer.models.Player;
import com.example.erielmarimon.driftwoodsoccer.models.net.Result;
import com.example.erielmarimon.driftwoodsoccer.util.Constants;
import com.example.erielmarimon.driftwoodsoccer.util.Helper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



/**
 * Created by Eriel.Marimon on 3/31/17.
 */

public class SearchableActivity extends ListActivity {

    private final String LOG_TAG = getClass().getSimpleName();

    private SharedPreferences globalSharedPref ;

    public static ArrayAdapter<Player> searchResultAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        globalSharedPref = getSharedPreferences(
                        getString(R.string.app_global_preferences), Context.MODE_PRIVATE);

        AdapterView.OnItemClickListener adapterOnItemClickListener = createOnItemClickListener();

        final ListView searchResultsListView = getListView();
        searchResultAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1);

        Intent searchIntent = getIntent();
        if (Intent.ACTION_SEARCH.equals(searchIntent.getAction())){
            String query = searchIntent.getStringExtra(SearchManager.QUERY);

            setListAdapter(searchResultAdapter);

            MainActivity.playerService.usernamePartialSearch(query, Boolean.TRUE).enqueue(onHttpResult());
        }

        searchResultsListView.setOnItemClickListener(adapterOnItemClickListener);

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    // ==========================================================================================
    // ===================================== HELPER METHODS =====================================
    // ==========================================================================================
    private Callback<Result<List<Player>>> onHttpResult() {
        return new Callback<Result<List<Player>>>() {
            @Override
            public void onResponse(Call<Result<List<Player>>> call, Response<Result<List<Player>>> response) {
                searchResultAdapter.addAll(response.body().data);
            }

            @Override
            public void onFailure(Call<Result<List<Player>>> call, Throwable t) {
                t.printStackTrace();
                Log.e(LOG_TAG, t.getLocalizedMessage());
            }
        };
    }


    private AdapterView.OnItemClickListener createOnItemClickListener(){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String searchSource = globalSharedPref.getString(
                        Constants.Search.SEARCH_ACTIVITY_SOURCE_KEY,
                        "");

                Intent backToSourceActivityIntent = null;

                if(searchSource.equals(Constants.Search.SOURCE_ACTIVITY_GROUP_MANAGEMENT_FRAGMENT)){
                    backToSourceActivityIntent = new Intent(getApplicationContext(),
                            GroupManagementActivity.class);

                    backToSourceActivityIntent.putExtra(Constants.CustomIntentExtras.HEADER_ACTION,
                            Constants.CustomIntentExtras.ACTION_ADD_PLAYER_TO_GROUP);
                }else if(searchSource.equals(Constants.Search.SOURCE_ACTIVITY_NEW_GAME_FRAGMENT)){
                    backToSourceActivityIntent = new Intent(getApplicationContext(),
                            NewGameActivity.class);

                    backToSourceActivityIntent.putExtra(Constants.CustomIntentExtras.HEADER_ACTION,
                            Constants.CustomIntentExtras.ACTION_ADD_PLAYER_TO_GAME);
                }

                backToSourceActivityIntent.putExtra(
                        Constants.CustomIntentExtras.PLAYER_EXTRA,
                        Helper.objectToJsonString(searchResultAdapter.getItem(position)));


                startActivity(backToSourceActivityIntent);

            }
        };
    }
}
