package com.example.erielmarimon.driftwoodsoccer.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.erielmarimon.driftwoodsoccer.R;
import com.example.erielmarimon.driftwoodsoccer.activities.GameDetailActivity;
import com.example.erielmarimon.driftwoodsoccer.activities.NewGameActivity;
import com.example.erielmarimon.driftwoodsoccer.models.Game;
import com.example.erielmarimon.driftwoodsoccer.util.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameListFragment extends Fragment {

    private ArrayAdapter gameListAdapter;
    private List<Game> games;

    private ListView gameListView;

    public GameListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_game_list, container, false);

        Intent intent = getActivity().getIntent();

        games = new ArrayList<>(Arrays.asList(Helper.createGameList(2)));

        if(intent != null && intent.getStringExtra(NewGameFragment.GAME_EXTRA) != null){
            Game newGame = Helper.jsonStringToGame(intent.getStringExtra(NewGameFragment.GAME_EXTRA));
            games.add(newGame);
        }

        gameListAdapter = new ArrayAdapter(
                getContext(),
                R.layout.list_item_game,
                R.id.list_item_game_textview,
                games);

        gameListView = (ListView) rootView.findViewById(R.id.game_list_view);

        gameListView.setAdapter(gameListAdapter);

        gameListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent gameDetailIntent = new Intent(getContext(), GameDetailActivity.class);
                gameDetailIntent.putExtra(
                        Intent.EXTRA_TEXT, Helper.objectToJsonString(games.get(position)));
                startActivity(gameDetailIntent);
            }
        });

        return rootView;
    }
}
