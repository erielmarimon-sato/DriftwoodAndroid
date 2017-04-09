package com.example.erielmarimon.driftwoodsoccer.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.erielmarimon.driftwoodsoccer.R;
import com.example.erielmarimon.driftwoodsoccer.activities.GameListActivity;
import com.example.erielmarimon.driftwoodsoccer.activities.GroupManagementActivity;
import com.example.erielmarimon.driftwoodsoccer.activities.NewGameActivity;
import com.example.erielmarimon.driftwoodsoccer.models.Player;
import com.example.erielmarimon.driftwoodsoccer.util.Helper;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends Fragment {

    private final String LOG_TAG = getClass().getSimpleName();

    private SharedPreferences globalSharedPref;
    private SharedPreferences.Editor globalSharedPrefEditor;

    private Button newGameButton;
    private Button gameListButton;
    private Button groupManagementButton;

    public static Player testPlayer;

    public MainActivityFragment() {
        // Required empty public constructor
        testPlayer = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        globalSharedPref = getActivity().getSharedPreferences(
                getString(R.string.app_global_preferences), Context.MODE_PRIVATE);
        globalSharedPrefEditor = globalSharedPref.edit();

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main_activity, container, false);

        // Create the onClickListener
        View.OnClickListener onClickListener = createOnClickListener();

        // Starts new Game activity
        newGameButton = (Button) rootView.findViewById(R.id.new_game_button);
        newGameButton.setOnClickListener(onClickListener);

        // Starts game list activity
        gameListButton = (Button) rootView.findViewById(R.id.game_list_button);
        gameListButton.setOnClickListener(onClickListener);

        // Starts group management activity
        groupManagementButton = (Button) rootView.findViewById(R.id.group_management_button);
        groupManagementButton.setOnClickListener(onClickListener);

        return rootView;
    }

    private View.OnClickListener createOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.group_management_button:
                        Intent groupManagementIntent = new Intent(getContext(), GroupManagementActivity.class);
                        startActivity(groupManagementIntent);
                        break;

                    case R.id.game_list_button:
                        Intent gameListIntent = new Intent(getContext(), GameListActivity.class);
                        startActivity(gameListIntent);
                        break;

                    case R.id.new_game_button:
                        Intent newGameIntent = new Intent(getContext(), NewGameActivity.class);
                        startActivity(newGameIntent);
                        break;
                    default:
                        break;
                }
            }
        };
    }
}
