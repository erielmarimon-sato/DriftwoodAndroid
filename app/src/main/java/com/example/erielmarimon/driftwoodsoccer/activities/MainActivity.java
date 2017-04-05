package com.example.erielmarimon.driftwoodsoccer.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.erielmarimon.driftwoodsoccer.R;
import com.example.erielmarimon.driftwoodsoccer.fragments.MainActivityFragment;
import com.example.erielmarimon.driftwoodsoccer.interfaces.PlayerService;
import com.example.erielmarimon.driftwoodsoccer.util.ApiHandler;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = getClass().getSimpleName();

    public static PlayerService playerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init services
        playerService = ApiHandler.createPlayerServiceApi();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainActivityFragment()).commit();
        }
    }
}
