package com.example.erielmarimon.driftwoodsoccer.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.erielmarimon.driftwoodsoccer.R;


/**
 * Created by Eriel.Marimon on 3/31/17.
 */

public class SearchableActivity extends ListActivity {

    private final String LOG_TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent searchIntent = getIntent();
        if (Intent.ACTION_SEARCH.equals(searchIntent.getAction())){
            String query = searchIntent.getStringExtra(SearchManager.QUERY);
            // TODO: Perform search
            Log.v(LOG_TAG, "Perform search: " + query);
            onSearchRequested();
        }

    }

    @Override
    public boolean onSearchRequested() {
        Log.v(LOG_TAG, "onSearchRequested");
        return super.onSearchRequested();

    }
}
