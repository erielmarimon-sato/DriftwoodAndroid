package com.example.erielmarimon.driftwoodsoccer.fragments;


import android.content.Intent;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends Fragment {

    private final String LOG_TAG = getClass().getSimpleName();

    private Button newGameButton;
    private Button gameListButton;
    private Button groupManagementButton;

    public MainActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
