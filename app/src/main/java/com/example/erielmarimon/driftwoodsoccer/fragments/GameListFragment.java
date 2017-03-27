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
import com.example.erielmarimon.driftwoodsoccer.models.Game;
import com.example.erielmarimon.driftwoodsoccer.util.Helper;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameListFragment extends Fragment {

    private ArrayAdapter gameListAdapter;
    private Game[] games;

    private ListView gameListView;

    public GameListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_game_list, container, false);

        games = Helper.createGameList(2);

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
                        Intent.EXTRA_TEXT, Helper.objectToJsonString(games[position]));
                startActivity(gameDetailIntent);
            }
        });

        return rootView;
    }

}
