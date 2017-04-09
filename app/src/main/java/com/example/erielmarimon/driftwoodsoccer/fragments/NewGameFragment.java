package com.example.erielmarimon.driftwoodsoccer.fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.erielmarimon.driftwoodsoccer.R;
import com.example.erielmarimon.driftwoodsoccer.activities.GameListActivity;
import com.example.erielmarimon.driftwoodsoccer.activities.PlayerDetailActivity;
import com.example.erielmarimon.driftwoodsoccer.models.Game;
import com.example.erielmarimon.driftwoodsoccer.models.Player;
import com.example.erielmarimon.driftwoodsoccer.util.Constants;
import com.example.erielmarimon.driftwoodsoccer.util.Helper;
import com.example.erielmarimon.driftwoodsoccer.util.StringFormatter;

import org.codehaus.jackson.map.ser.std.AsArraySerializerBase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewGameFragment extends Fragment {

    private final String LOG_TAG = getClass().getName();

    private SharedPreferences globalSharedPref ;

    private Game newGame;
    private ArrayAdapter playerAdapter;
    private TextView dateEditText;
    private TextView timeEditText;
    private Calendar calendar;
    private Button createGameButton;
    private Button addPlayerButton;
    private ListView playerListView;

    private List<Player> players;

    public NewGameFragment() {
        // Required empty public constructor
        calendar = Calendar.getInstance();
        players = new ArrayList<>();
        newGame = new Game();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // This points to a file created in main activity
        globalSharedPref = getActivity()
                .getSharedPreferences(
                        getString(R.string.app_global_preferences), Context.MODE_PRIVATE);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_new_game, container, false);

        View.OnClickListener onClickListener = createOnClickListener();
        AdapterView.OnItemClickListener adapterOnItemClickListener = createOnItemClickListener();

        dateEditText = (TextView) rootView.findViewById(R.id.date_edit_text);
        dateEditText.setOnClickListener(onClickListener);

        timeEditText = (TextView) rootView.findViewById(R.id.time_edit_text);
        timeEditText.setOnClickListener(onClickListener);

        updateTimeLabel(calendar, timeEditText);
        updateDateLabel(calendar, dateEditText);

        // TODO: Capture the current player and add it to player before init the adapter
        playerAdapter = new ArrayAdapter(
                getActivity(), R.layout.list_item_player, R.id.list_item_player_textview, players);

        playerListView = (ListView) rootView.findViewById(R.id.player_list_view);
        playerListView.setAdapter(playerAdapter);
        playerListView.setOnItemClickListener(adapterOnItemClickListener);

        addPlayerButton = (Button) rootView.findViewById(R.id.player_add_button);
        addPlayerButton.setOnClickListener(onClickListener);

        createGameButton = (Button) rootView.findViewById(R.id.game_create_button);
        createGameButton.setOnClickListener(onClickListener);

        return rootView;
    }

    private Player getCurrentPlayer(SharedPreferences globalSharedPref) {
        String playerJsonString = globalSharedPref
                .getString(Constants.CustomIntentExtras.CURRENT_LOGGED_PLAYER, "");
        return Helper.jsonStringToPlayer(playerJsonString);
    }

    private AdapterView.OnItemClickListener createOnItemClickListener(){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getId()){
                    case R.id.player_list_view:
                        // TODO: Show pop up with basic player information
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
                    case R.id.game_create_button:

                        List<String> playerIds = extractPlayerIds(players);

                        newGame.date = dateEditText.getText().toString();
                        newGame.time = timeEditText.getText().toString();
                        newGame.players = playerIds;

                        Intent createGameIntent =
                            new Intent(getContext(), GameListActivity.class);

                        createGameIntent.putExtra(
                            Constants.CustomIntentExtras.GAME_EXTRA,
                            Helper.objectToJsonString(newGame));

                        createGameIntent.putExtra(
                                Constants.CustomIntentExtras.HEADER_ACTION,
                                Constants.CustomIntentExtras.ACTION_CREATE_GAME);

                        startActivity(createGameIntent);

                        break;

                    case R.id.player_add_button:
                        // TODO: Add player to this game
//                        getActivity().onSearchRequested();
                        break;

                    case R.id.date_edit_text:

                        final DatePickerDialog.OnDateSetListener date = createDateChangeListener();

                        new DatePickerDialog(
                                getContext(),
                                date,
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)).show();

                        break;

                    case R.id.time_edit_text:
                        // For time, bring up a time selector popup on edit text click, capture the selected time
                        // and update the edittext with that time
                        final TimePickerDialog.OnTimeSetListener timeChangeListener = createTimeChangeListener();

                        // TODO: is24HourView value should be a setting
                        final boolean is24HourView = true;
                        new TimePickerDialog(
                                getContext(),
                                timeChangeListener,
                                calendar.get(Calendar.HOUR_OF_DAY),
                                calendar.get(Calendar.MINUTE),
                                is24HourView).show();
                        break;

                    default:
                        break;
                }
            }
        };
    }

    private List<String> extractPlayerIds(List<Player> players) {
        List<String> playerIds = new ArrayList<>();
        for(Player player : players){
            playerIds.add(player.id);
        }
        return playerIds;
    }

    private TimePickerDialog.OnTimeSetListener createTimeChangeListener() {
        return new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                updateTimeLabel(calendar, timeEditText);
            }
        };
    }

    private DatePickerDialog.OnDateSetListener createDateChangeListener() {
        return new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateDateLabel(calendar, dateEditText);
            }
        };
    }

    private void updateTimeLabel(Calendar calendar, TextView timeEditText){
        String format = "HH:MM";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        timeEditText.setText(sdf.format(calendar.getTime()));
    }

    private void updateDateLabel(Calendar calendar, TextView dateEditText){
        String format = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

        dateEditText.setText(sdf.format(calendar.getTime()));
    }
}
