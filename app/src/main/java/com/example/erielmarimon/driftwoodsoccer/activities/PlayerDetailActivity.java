package com.example.erielmarimon.driftwoodsoccer.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.erielmarimon.driftwoodsoccer.R;
import com.example.erielmarimon.driftwoodsoccer.fragments.PlayerDetailFragment;

public class PlayerDetailActivity extends AppCompatActivity {

    private final String LOG_TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_detail);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.player_detail_container, new PlayerDetailFragment())
                    .commit();
        }
    }
}
