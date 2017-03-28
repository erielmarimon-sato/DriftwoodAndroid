package com.example.erielmarimon.driftwoodsoccer.fragments;



import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.erielmarimon.driftwoodsoccer.R;
import com.example.erielmarimon.driftwoodsoccer.activities.GameListActivity;
import com.example.erielmarimon.driftwoodsoccer.activities.PlayerDetailActivity;
import com.example.erielmarimon.driftwoodsoccer.models.Game;
import com.example.erielmarimon.driftwoodsoccer.models.Player;
import com.example.erielmarimon.driftwoodsoccer.util.Helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameDetailFragment extends Fragment {

    private final String LOG_TAG = getClass().getSimpleName();

    public static ArrayAdapter playerAdapter;
    private TextView dateEditText;
    private TextView timeEditText;
    private ListView playersListView;
    private Button addPlayerButton;
    private Button gameDeleteButton;

    private Game thisGame;

    List<Player> players;

    public GameDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_game_detail, container, false);

        final Calendar calendar = Calendar.getInstance();

        dateEditText = (TextView) rootView.findViewById(R.id.date_edit_text);
        timeEditText = (TextView) rootView.findViewById(R.id.time_edit_text);
        playersListView = (ListView) rootView.findViewById(R.id.player_list_view);
        addPlayerButton = (Button) rootView.findViewById(R.id.player_add_button);
        gameDeleteButton = (Button) rootView.findViewById(R.id.game_delete_button);

        String jsonGameString = "";


        jsonGameString = getActivity().getIntent().getStringExtra(Game.GAME_EXTRA);

        Log.v(LOG_TAG, jsonGameString);

        thisGame = Helper.jsonStringToGame(jsonGameString);

        Log.v(LOG_TAG, "THIS GAME : " + thisGame.toString());

        players = thisGame.getPlayers();
        playerAdapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.list_item_player,
                R.id.list_item_player_textview,
                players);

        Log.v(LOG_TAG, players.size()+"");

        playersListView.setAdapter(playerAdapter);

        dateEditText.setText(
                new SimpleDateFormat("MM/dd/yyyy").format(thisGame.getDate()));
        timeEditText.setText(
                new SimpleDateFormat("HH:MM").format(thisGame.getDate()));

        // For date, bring up a date selector popup on edixtext click, capture the selected
        // date and update the edittext with that date string
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth, 0, 0);
                dateEditText.setText(new SimpleDateFormat("MM/dd/yyyy").format(calendar.getTime()));
            }
        };

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                timeEditText.setText(new SimpleDateFormat("HH:MM").format(calendar.getTime()));
            }
        };

        // TODO: is24HourView value should be a setting
        final boolean is24HourView = true;
        timeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getContext(), time, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), is24HourView).show();
            }
        });



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

        addPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Click", Toast.LENGTH_LONG).show();
                Log.v(LOG_TAG, "Click");

                Player newPlayer = Helper.createPlayerList(1)[0];
                players.add(newPlayer);
                playerAdapter.notifyDataSetChanged();
            }
        });

        gameDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gameId = thisGame.getId();
                Intent deleteGameIntent = new Intent(getContext(), GameListActivity.class);
                deleteGameIntent.putExtra(Game.GAME_ID_EXTRA, gameId);
                startActivity(deleteGameIntent);
            }
        });
//
//
//
//
//        updateTimeLabel(thisGame.getDate());
//        updateDateLabel(thisGame.getDate());
//
        return rootView;
    }
//
//    private void updateTimeLabel(Date date){
//        calendar.set(Calendar.HOUR_OF_DAY, date.getHours());
//        calendar.set(Calendar.MINUTE, date.getMinutes());
//        String format = "HH:MM";
//        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
//        timeEditText.setText(sdf.format(calendar.getTime()));
//    }
//
//    private void updateDateLabel(Date date){
//        calendar.set(Calendar.YEAR, date.getYear());
//        calendar.set(Calendar.MONTH, date.getMonth());
//        calendar.set(Calendar.DAY_OF_MONTH, date.getDay());
//        String format = "MM/dd/yy";
//        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
//
//        dateEditText.setText(sdf.format(calendar.getTime()));
//    }

}
