package com.example.erielmarimon.driftwoodsoccer.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.erielmarimon.driftwoodsoccer.R;
import com.example.erielmarimon.driftwoodsoccer.models.Player;
import com.example.erielmarimon.driftwoodsoccer.util.Helper;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerDetailFragment extends Fragment {

    private final String LOG_TAG = getClass().getSimpleName();

    public PlayerDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_player_detail, container, false);

        Intent playerDetailIntent = getActivity().getIntent();
        String playerJsonString = playerDetailIntent.getStringExtra(Intent.EXTRA_TEXT);
        Player thisPlayer = Helper.jsonStringToPlayer(playerJsonString);

        TextView tv = (TextView) rootView.findViewById(R.id.text_view_1);
        tv.setText(thisPlayer.toString());


        return rootView;
    }

}
