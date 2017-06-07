package com.example.erielmarimon.driftwoodsoccer.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.example.erielmarimon.driftwoodsoccer.models.Group;
import com.example.erielmarimon.driftwoodsoccer.models.Player;
import com.example.erielmarimon.driftwoodsoccer.models.net.Result;
import com.example.erielmarimon.driftwoodsoccer.util.Constants;
import com.example.erielmarimon.driftwoodsoccer.util.Helper;
import com.example.erielmarimon.driftwoodsoccer.util.StringFormatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupManagementFragment extends Fragment {

    private final String LOG_TAG = getClass().getSimpleName();

    private SharedPreferences globalSharedPref ;
    private SharedPreferences.Editor globalSharedPrefEditor ;

    static final int ADD_PLAYER_REQUEST = 1;

    private ListView playersListView;
    private Button addPlayerButton;

    public ArrayAdapter playersAdapter;

    public static List<Player> players;

    public GroupManagementFragment() {
        // Required empty public constructor
        players = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        globalSharedPref = getActivity()
                .getSharedPreferences(
                        getString(R.string.app_global_preferences), Context.MODE_PRIVATE);
        globalSharedPrefEditor = globalSharedPref.edit();

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_group_management, container, false);

        View.OnClickListener onClickListener = createOnClickListener();
        AdapterView.OnItemClickListener adapterOnItemClickListener = createOnItemClickListener();

        // init player adapter
        playersAdapter = new ArrayAdapter(
                getContext(), R.layout.list_item_player, R.id.list_item_player_textview, players);

        addPlayerButton = (Button) rootView.findViewById(R.id.player_add_button);
        addPlayerButton.setOnClickListener(onClickListener);

        // link playersAdapter to playersListView
        playersListView = (ListView) rootView.findViewById(R.id.player_list_view);
        playersListView.setAdapter(playersAdapter);
        playersListView.setOnItemClickListener(adapterOnItemClickListener);

        //load players for this group
        refreshPlayers(playersAdapter);

        // Handle the coming intent
        Intent intent = getActivity().getIntent();
        handleIntent(intent);

        return rootView;
    }

    private AdapterView.OnItemClickListener createOnItemClickListener(){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getId()){
                    case R.id.player_list_view:
                        Intent playerDetailIntent = new Intent(getContext(), PlayerDetailActivity.class);
                        // Sending the player as a json string, will be deserialized upon arrival
                        playerDetailIntent.putExtra(
                                Intent.EXTRA_TEXT, Helper.objectToJsonString(players.get(position)));
                        startActivity(playerDetailIntent);
                        break;

                    default:
                        break;
                }
            }
        };
    }

    private View.OnClickListener createOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.player_add_button:
                        globalSharedPrefEditor.putString(
                                Constants.Search.SEARCH_ACTIVITY_SOURCE_KEY,
                                Constants.Search.SOURCE_ACTIVITY_GROUP_MANAGEMENT_FRAGMENT);
                        globalSharedPrefEditor.commit();
                        getActivity().onSearchRequested();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private void handleIntent(Intent intent){
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
    }

    private void refreshPlayers(final ArrayAdapter playersAdapter){
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
        MainActivity.playerService.updatePlayerGroup(
                player.id, Constants.CURRENT_GROUP_ID).enqueue(onUpdatePlayerResult());

    }

    private Callback<Result<Player>> onUpdatePlayerResult(){
        return new Callback<Result<Player>>() {
            @Override
            public void onResponse(Call<Result<Player>> call, Response<Result<Player>> response) {

                if(response.isSuccessful()){
                    Toast.makeText(
                            getContext(),
                            "Successfully updated player " + response.body().data.username,
                            Toast.LENGTH_SHORT).show();

                    // TODO: Update group as well when player has been confirmed
                    Player player = response.body().data;
                    List<String> playerIds = new ArrayList<>();
                    playerIds.add(player.id);
                    MainActivity.groupService.addPlayer(
                            Constants.CURRENT_GROUP_ID, playerIds).enqueue(onUpdateGroupResult());

                    refreshPlayers(playersAdapter);
                }else {
                    Log.e(LOG_TAG, "Failed to update player. Raw response = " + response.raw());
                }
            }
            @Override
            public void onFailure(Call<Result<Player>> call, Throwable t) {
                Log.e(LOG_TAG, "Failed to update player. Throwable message = " + t.getMessage());
            }
        };
    }

    private Callback<Result<Group>> onUpdateGroupResult() {
        return new Callback<Result<Group>>() {
            @Override
            public void onResponse(Call<Result<Group>> call, Response<Result<Group>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(
                            getContext(),
                            "Successfully updated group ",
                            Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        Log.e(LOG_TAG, response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.e(LOG_TAG, "Failed to update group. Raw response = " + response.raw());
                }
            }
            @Override
            public void onFailure(Call<Result<Group>> call, Throwable t) {
                Log.e(LOG_TAG, "Failed to update group. Throwable message = " + t.getMessage());
            }
        };
    }
}
