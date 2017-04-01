package com.example.erielmarimon.driftwoodsoccer.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.erielmarimon.driftwoodsoccer.R;
import com.example.erielmarimon.driftwoodsoccer.fragments.GroupManagementFragment;

public class GroupManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_management);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.group_management_container, new GroupManagementFragment()).commit();
        }
    }
}
