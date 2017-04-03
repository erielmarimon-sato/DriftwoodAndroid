package com.example.erielmarimon.driftwoodsoccer.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.erielmarimon.driftwoodsoccer.R;
import com.example.erielmarimon.driftwoodsoccer.activities.PlayerDetailActivity;
import com.example.erielmarimon.driftwoodsoccer.activities.SearchableActivity;
import com.example.erielmarimon.driftwoodsoccer.models.Player;
import com.example.erielmarimon.driftwoodsoccer.tasks.GetGroupPlayersTask;
import com.example.erielmarimon.driftwoodsoccer.util.Helper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupManagementFragment extends Fragment {

    private final String LOG_TAG = getClass().getSimpleName();

    private ListView playersListView;
    private Button addPlayerButton;

    public static ArrayAdapter playersAdapter;

    public static List<Player> players;

    public GroupManagementFragment() {
        // Required empty public constructor
        players = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_group_management, container, false);


        playersListView = (ListView) rootView.findViewById(R.id.player_list_view);
        addPlayerButton = (Button) rootView.findViewById(R.id.player_add_button);

        playersAdapter = new ArrayAdapter(
                getContext(),
                R.layout.list_item_player,
                R.id.list_item_player_textview,
                players);

        playersListView.setAdapter(playersAdapter);
        playersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent playerDetailIntent = new Intent(getContext(), PlayerDetailActivity.class);
                // Sending the player as a json string, will be deserialized upon arrival
                playerDetailIntent.putExtra(
                        Intent.EXTRA_TEXT, Helper.objectToJsonString(players.get(position)));
                startActivity(playerDetailIntent);
            }
        });

        // Listener
        addPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add player to database
                // TODO: Add player to UI
                Log.v(LOG_TAG, "Add player");

                getActivity().onSearchRequested();
            }
        });

        // Async. Will update ListView
        GetGroupPlayersTask getPlayersTask = new GetGroupPlayersTask();
        getPlayersTask.execute("58e0443877c8194d1839fc13");



//TODO: Fix the GetGroupPlayersTask


        return rootView;
    }

}
