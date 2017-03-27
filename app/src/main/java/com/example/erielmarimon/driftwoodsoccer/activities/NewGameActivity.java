package com.example.erielmarimon.driftwoodsoccer.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.erielmarimon.driftwoodsoccer.R;
import com.example.erielmarimon.driftwoodsoccer.fragments.NewGameFragment;

public class NewGameActivity extends AppCompatActivity {

    private final String LOG_TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.new_game_container, new NewGameFragment())
                    .commit();
        }
    }
}
