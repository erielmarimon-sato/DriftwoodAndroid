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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.erielmarimon.driftwoodsoccer.R;
import com.example.erielmarimon.driftwoodsoccer.activities.MainActivity;
import com.example.erielmarimon.driftwoodsoccer.activities.PlayerDetailActivity;
import com.example.erielmarimon.driftwoodsoccer.models.Game;
import com.example.erielmarimon.driftwoodsoccer.models.Player;
import com.example.erielmarimon.driftwoodsoccer.models.net.Result;
import com.example.erielmarimon.driftwoodsoccer.util.Constants;
import com.example.erielmarimon.driftwoodsoccer.util.Helper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameDetailFragment extends Fragment {

    private final String LOG_TAG = getClass().getSimpleName();

    private Game mGame;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor sharedPrefEditor;

    private ArrayAdapter playerAdapter;
    private TextView dateEditText;
    private TextView timeEditText;
    private Calendar calendar;
    private Button addPlayerButton;
    private ListView playerListView;

    private List<Player> players;

    public GameDetailFragment() {
        // Required empty public constructor
        players = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        sharedPrefEditor = sharedPref.edit();

        Intent intent = getActivity().getIntent();
        mGame = getGameFromIntent(intent);
        if(mGame == null){
            mGame = getGameFromPreferences(sharedPref);
        }

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_game_detail, container, false);

        View.OnClickListener onClickListener = createOnClickListener();
        AdapterView.OnItemClickListener adapterOnItemClickListener = createOnItemClickListener();

        // Use this calendar instance to help pick both date and time.
        // Set default values on calendar for today at 8:00 pm and update date and time views
        calendar = Calendar.getInstance();

        dateEditText = (TextView) rootView.findViewById(R.id.date_edit_text);
        dateEditText.setOnClickListener(onClickListener);

        timeEditText = (TextView) rootView.findViewById(R.id.time_edit_text);
        timeEditText.setOnClickListener(onClickListener);

        updateTimeLabel(calendar, timeEditText);
        updateDateLabel(calendar, dateEditText);

        playerAdapter = new ArrayAdapter<>(
                getActivity(), R.layout.list_item_player, R.id.list_item_player_textview, players);

        refreshPlayers(playerAdapter);

        playerListView = (ListView) rootView.findViewById(R.id.player_list_view);
        playerListView.setAdapter(playerAdapter);
        playerListView.setOnItemClickListener(adapterOnItemClickListener);

        addPlayerButton = (Button) rootView.findViewById(R.id.player_add_button);
        addPlayerButton.setOnClickListener(onClickListener);

        return rootView;
    }

    private Game getGameFromPreferences(SharedPreferences sharedPref) {
        return Helper.jsonStringToGame(sharedPref.getString(
                Constants.CustomIntentExtras.GAME_EXTRA, ""));
    }

    private void refreshPlayers(final ArrayAdapter playersAdapter){
        // get players in this group
        MainActivity.gameService.getGamePlayers(mGame.id)
                .enqueue(new Callback<Result<List<Player>>>() {
                    @Override
                    public void onResponse(Call<Result<List<Player>>> call, Response<Result<List<Player>>> response) {
                        Log.v(LOG_TAG, response.raw().toString());
                        if(response.isSuccessful()){
                            List<Player> players = response.body().data;
                            playersAdapter.clear();
                            playersAdapter.addAll(players);
                        }else{
                            try {
                                Log.e(LOG_TAG, response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<Result<List<Player>>> call, Throwable t) {
                        t.printStackTrace();
                        Log.e(LOG_TAG, t.getLocalizedMessage());
                    }
                });
    }

    private Game getGameFromIntent(Intent intent){
        if (!intent.hasExtra(Constants.CustomIntentExtras.GAME_EXTRA)){
            Log.wtf(LOG_TAG, "Intent should have GAME_EXTRA.");
            return null;
        }

        String gameJsonString = intent.getStringExtra(Constants.CustomIntentExtras.GAME_EXTRA);

        return Helper.jsonStringToGame(gameJsonString);
    }

    private AdapterView.OnItemClickListener createOnItemClickListener(){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getId()){
                    case R.id.player_list_view:
                        Log.v(LOG_TAG, "clicked item in player_list_view");
                        Intent playerDetailIntent = new Intent(getContext(), PlayerDetailActivity.class);
                        // Sending the player as a json string, will be deserialized upon arrival
                        playerDetailIntent.putExtra(
                                Constants.CustomIntentExtras.PLAYER_EXTRA,
                                Helper.objectToJsonString(players.get(position)));

                        // Save game in share prefs to recover it on button back
                        sharedPrefEditor.putString(Constants.CustomIntentExtras.GAME_EXTRA,
                                Helper.objectToJsonString(mGame));
                        sharedPrefEditor.commit();
                        startActivity(playerDetailIntent);
                        break;

                    default:
                        Log.v(LOG_TAG, "clicked " + parent.getId());
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
                        // TODO: Add player to this game
                        Toast.makeText(getContext(), "Add Player", Toast.LENGTH_LONG).show();
                        Log.v(LOG_TAG, "Add player");
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

                    case R.id.date_edit_text:
                        // For date, bring up a date selector popup on edixtext click, capture the selected
                        // date and update the edittext with that date string
                        final DatePickerDialog.OnDateSetListener date = createDateChangeListener();

                        new DatePickerDialog(
                                getContext(),
                                date,
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)).show();
                        break;

                    default:
                        break;
                }
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
