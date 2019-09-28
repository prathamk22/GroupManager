package com.example.groupmanager.New_Trip;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.groupmanager.R;

public class AddActivity extends AppCompatActivity {

    LocationFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        fragment = new LocationFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame,fragment)
                .commit();
    }

    public void setFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //transaction.show(explore);
        transaction.commit();
    }
}
