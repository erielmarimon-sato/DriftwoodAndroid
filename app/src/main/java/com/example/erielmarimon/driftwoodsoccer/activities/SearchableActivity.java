package com.example.erielmarimon.driftwoodsoccer.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.erielmarimon.driftwoodsoccer.R;
import com.example.erielmarimon.driftwoodsoccer.fragments.GroupManagementFragment;
import com.example.erielmarimon.driftwoodsoccer.models.Player;
import com.example.erielmarimon.driftwoodsoccer.tasks.SearchPlayersTask;
import com.example.erielmarimon.driftwoodsoccer.util.Constants;
import com.example.erielmarimon.driftwoodsoccer.util.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Eriel.Marimon on 3/31/17.
 */

public class SearchableActivity extends ListActivity {

    private final String LOG_TAG = getClass().getSimpleName();

    public static ArrayAdapter<Player> searchResultAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        final EditText searchBox = (EditText) findViewById(R.id.sear)
        final ListView searchResultsListView = getListView();
        searchResultAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1);

        Intent searchIntent = getIntent();
        if (Intent.ACTION_SEARCH.equals(searchIntent.getAction())){
            String query = searchIntent.getStringExtra(SearchManager.QUERY);
            // TODO: Perform search
            Log.v(LOG_TAG, "Perform search: " + query);
            setListAdapter(searchResultAdapter);

            SearchPlayersTask searchPlayersTask = new SearchPlayersTask();
            searchPlayersTask.execute(query, Boolean.TRUE.toString());

        }

        searchResultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent backToGroupManagementIntent = new Intent(getApplicationContext(),
                        GroupManagementActivity.class);

                backToGroupManagementIntent.putExtra(Constants.CustomIntentExtras.HEADER_ACTION,
                        Constants.CustomIntentExtras.ACTION_ADD_PLAYER_TO_GROUP);

                backToGroupManagementIntent.putExtra(
                        Constants.CustomIntentExtras.PLAYER_EXTRA,
                        Helper.objectToJsonString(searchResultAdapter.getItem(position)));

                startActivity(backToGroupManagementIntent);
            }
        });

    }



    @Override
    public boolean onSearchRequested() {
        Log.v(LOG_TAG, "onSearchRequested");
        return super.onSearchRequested();

    }
}
