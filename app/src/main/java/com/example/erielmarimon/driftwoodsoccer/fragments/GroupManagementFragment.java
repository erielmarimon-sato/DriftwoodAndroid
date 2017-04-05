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
import android.widget.Toast;

import com.example.erielmarimon.driftwoodsoccer.R;
import com.example.erielmarimon.driftwoodsoccer.activities.MainActivity;
import com.example.erielmarimon.driftwoodsoccer.activities.PlayerDetailActivity;
import com.example.erielmarimon.driftwoodsoccer.activities.SearchableActivity;
import com.example.erielmarimon.driftwoodsoccer.interfaces.PlayerService;
import com.example.erielmarimon.driftwoodsoccer.models.Player;
import com.example.erielmarimon.driftwoodsoccer.models.net.PlayerDto;
import com.example.erielmarimon.driftwoodsoccer.models.net.Result;
import com.example.erielmarimon.driftwoodsoccer.util.ApiHandler;
import com.example.erielmarimon.driftwoodsoccer.util.Constants;
import com.example.erielmarimon.driftwoodsoccer.util.Helper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        // init player adapter
        playersAdapter = new ArrayAdapter(
                getContext(),
                R.layout.list_item_player,
                R.id.list_item_player_textview,
                players);

        // link playersAdapter to playersListView
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

        //load players for this group
        refreshPlayers();

        // Handle the coming intent
        Intent intent = getActivity().getIntent();
        if(intent != null){
            String action = intent.getStringExtra(Constants.CustomIntentExtras.HEADER_ACTION);
            if(action != null){
                switch (action){
                    case Constants.CustomIntentExtras.ACTION_ADD_PLAYER_TO_GROUP:
                        Log.v(LOG_TAG, "adding player to playersAdapter.");
                        Player player = Helper.jsonStringToPlayer(
                                intent.getStringExtra(Constants.CustomIntentExtras.PLAYER_EXTRA));
                        Log.v(LOG_TAG, player.toString());

                        updatePlayerInfo(player);

                        break;
                    default:
                        break;
                }
            }
        }


//TODO: Fix the GetGroupPlayersTask


        return rootView;
    }

    private void refreshPlayers(){
        // get players in this group
        MainActivity.playerService.getGroupPlayers(Constants.CURRENT_GROUP_ID)
                .enqueue(new Callback<Result<List<Player>>>() {
                    @Override
                    public void onResponse(Call<Result<List<Player>>> call, Response<Result<List<Player>>> response) {
                        List<Player> players = response.body().data;
                        playersAdapter.clear();
                        playersAdapter.addAll(players);

                    }
                    @Override
                    public void onFailure(Call<Result<List<Player>>> call, Throwable t) {
                        t.printStackTrace();
                        Log.e(LOG_TAG, t.getLocalizedMessage());
                    }
                });
    }

    private void updatePlayerInfo(Player player){
        // TODO: This should be a shared preference
        MainActivity.playerService.updatePlayerGroup(player.id, Constants.CURRENT_GROUP_ID).enqueue(
                new Callback<Result<Player>>() {
                    @Override
                    public void onResponse(Call<Result<Player>> call, Response<Result<Player>> response) {
                        Toast.makeText(
                                getContext(),
                                "Successfully updated player " + response.body().data.username,
                                Toast.LENGTH_SHORT).show();
                        refreshPlayers();
                    }

                    @Override
                    public void onFailure(Call<Result<Player>> call, Throwable t) {

                    }
                }
        );
    }

}
