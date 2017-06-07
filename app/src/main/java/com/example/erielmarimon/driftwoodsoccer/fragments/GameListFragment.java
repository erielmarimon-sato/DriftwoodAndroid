package com.example.erielmarimon.driftwoodsoccer.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.erielmarimon.driftwoodsoccer.R;
import com.example.erielmarimon.driftwoodsoccer.activities.GameDetailActivity;
import com.example.erielmarimon.driftwoodsoccer.activities.MainActivity;
import com.example.erielmarimon.driftwoodsoccer.activities.NewGameActivity;
import com.example.erielmarimon.driftwoodsoccer.activities.PlayerDetailActivity;
import com.example.erielmarimon.driftwoodsoccer.models.Game;
import com.example.erielmarimon.driftwoodsoccer.models.Player;
import com.example.erielmarimon.driftwoodsoccer.models.net.Result;
import com.example.erielmarimon.driftwoodsoccer.util.Constants;
import com.example.erielmarimon.driftwoodsoccer.util.Helper;

import java.io.IOException;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameListFragment extends Fragment {

    private final String LOG_TAG = getClass().getSimpleName();

    private ArrayAdapter gameListAdapter;

    private List<Game> games;

    private ListView gameListView;

    public GameListFragment() {
        // Required empty public constructor
        games = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_game_list, container, false);

        AdapterView.OnItemClickListener adapterOnItemClickListener = createOnItemClickListener();


        gameListAdapter = new ArrayAdapter(
                getContext(), R.layout.list_item_game, R.id.list_item_game_textview, games);

        gameListView = (ListView) rootView.findViewById(R.id.game_list_view);
        gameListView.setAdapter(gameListAdapter);
        gameListView.setOnItemClickListener(adapterOnItemClickListener);

        refreshGames(gameListAdapter);

        Intent intent = getActivity().getIntent();
        handleIntent(intent);

        return rootView;
    }

    private AdapterView.OnItemClickListener createOnItemClickListener(){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getId()){
                    case R.id.game_list_view:
                        Intent gameDetailIntent = new Intent(getContext(), GameDetailActivity.class);
                        gameDetailIntent.putExtra(
                                Constants.CustomIntentExtras.GAME_EXTRA,
                                Helper.objectToJsonString(games.get(position)));
                        startActivity(gameDetailIntent);
                        break;

                    default:
                        break;
                }
            }
        };
    }

    private void handleIntent(Intent intent){
        if (intent != null){
            String action = intent.getStringExtra(Constants.CustomIntentExtras.HEADER_ACTION);
            if (action != null){
                switch (action){
                    case Constants.CustomIntentExtras.ACTION_CREATE_GAME:
                        Game game = Helper.jsonStringToGame(
                                intent.getStringExtra(Constants.CustomIntentExtras.GAME_EXTRA));
                        games.add(game); // add to games for record keeping

                        saveGame(game); // saves game to database and then show it on UI

                        break;

                    default:
                        break;
                }
            }
        }
    }

    private void saveGame(Game game) {
        Log.v(LOG_TAG, "saving...");
        MainActivity.gameService.createGame(game.players, game.gameType, game.date, game.time)
                .enqueue(new Callback<Result<Game>>() {
                    @Override
                    public void onResponse(Call<Result<Game>> call, Response<Result<Game>> response) {
                        if(response.isSuccessful()){
                            Game newGame = response.body().data;
                            gameListAdapter.add(newGame);
                            Log.w(LOG_TAG, response.raw().toString());
                        }else{
                            Toast.makeText(getContext(), "Failed to create game", Toast.LENGTH_LONG)
                                    .show();
                            Log.e(LOG_TAG, response.raw().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Result<Game>> call, Throwable t) {

                    }
                });
    }

    private void refreshGames(final ArrayAdapter gameListAdapter){
        // get games for this group
        MainActivity.gameService.getGroupGames(Constants.CURRENT_GROUP_ID)
                .enqueue(new Callback<Result<List<Game>>>() {
                    @Override
                    public void onResponse(Call<Result<List<Game>>> call, Response<Result<List<Game>>> response) {
                        List<Game> games = response.body().data;
                        gameListAdapter.clear();
                        gameListAdapter.addAll(games);
                    }
                    @Override
                    public void onFailure(Call<Result<List<Game>>> call, Throwable t) {
                        t.printStackTrace();
                        Log.e(LOG_TAG, t.getMessage());
                    }
                });
    }
}
