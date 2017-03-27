package com.example.erielmarimon.driftwoodsoccer.fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.erielmarimon.driftwoodsoccer.R;
import com.example.erielmarimon.driftwoodsoccer.activities.PlayerDetailActivity;
import com.example.erielmarimon.driftwoodsoccer.models.Player;
import com.example.erielmarimon.driftwoodsoccer.util.Helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewGameFragment extends Fragment {

    private final String LOG_TAG = getClass().getSimpleName();

    public static ArrayAdapter<String> playerAdapter;
    private TextView dateEditText;
    private TextView timeEditText;
    private Calendar calendar;

    public NewGameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_new_game, container, false);


        dateEditText = (TextView) rootView.findViewById(R.id.date_edit_text);
        timeEditText = (TextView) rootView.findViewById(R.id.time_edit_text);

        // Use this calendar instance to help pick both date and time.
        // Set default values on calendar for today at 8:00 pm and update date and time views
        Date today = new Date();
        calendar = Calendar.getInstance();

        updateTimeLabel();
        updateDateLabel();

        // For date, bring up a date selector popup on edixtext click, capture the selected
        // date and update the edittext with that date string
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateDateLabel();
            }
        };

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(
                        getContext(),
                        date,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // For time, bring up a time selector popup on edit text click, capture the selected time
        // and update the edittext with that time
        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                updateTimeLabel();
            }
        };

        // TODO: is24HourView value should be a setting
        final boolean is24HourView = true;
        timeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new TimePickerDialog(
                        getContext(),
                        time,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        is24HourView).show();
            }
        });



        // For player list view, get all the players in this game and load them up with an array
        // adapter
        final Player[] players = Helper.createPlayerList(2);
        String[] playerNames = new String[players.length];
        for (int i = 0; i < playerNames.length; i++){
            playerNames[i] = players[i].getName();
        }

        playerAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item_player,
                R.id.list_item_player_textview,
                playerNames);

        ListView playerListView = (ListView) rootView.findViewById(R.id.player_list_view);
        playerListView.setAdapter(playerAdapter);

        playerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent playerDetailIntent = new Intent(getContext(), PlayerDetailActivity.class);
                // Sending the player as a json string, will be deserialized upon arrival
                playerDetailIntent.putExtra(
                        Intent.EXTRA_TEXT, Helper.objectToJsonString(players[position]));
                startActivity(playerDetailIntent);
            }
        });

        return rootView;
    }



    private void updateTimeLabel(){
        String format = "HH:MM";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        timeEditText.setText(sdf.format(calendar.getTime()));
    }

    private void updateDateLabel(){
        String format = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

        dateEditText.setText(sdf.format(calendar.getTime()));
    }
}
