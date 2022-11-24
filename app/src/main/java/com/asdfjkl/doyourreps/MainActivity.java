package com.asdfjkl.doyourreps;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private Excercise currentExcercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // somehow retrieve the stored excercises
        ArrayList<Excercise> excercises = new ArrayList<>();
        Excercise pushups = new Excercise("Push-Ups");
        pushups.monthCount = 231;
        pushups.yearCount = 2412;
        pushups.overallCount = 2393;
        Excercise pullups = new Excercise("Pull-Ups");
        Excercise crunches = new Excercise("Crunches");
        excercises.add(pushups);
        excercises.add(pullups);
        excercises.add(crunches);

        currentExcercise = crunches;

        setContentView(R.layout.screen_main);
        // set up spinner
        Spinner excerciseSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<Excercise> excerciseArrayAdapter = new ArrayAdapter<Excercise>(this,
                R.layout.spinner_item, excercises);
        excerciseSpinner.setAdapter(excerciseArrayAdapter);

        setButtonAddClickListener();
        setButtonStatsClickListener();
        setSpinnerItemSelectedListener();
        //binding = ActivityMainBinding.inflate(getLayoutInflater());

        /*
        R.layout.screen_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }


    private void setButtonAddClickListener() {

        Button toggleButton = (Button)findViewById(R.id.button_add);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // create entry dialog
            }
        });
    }

    private void setSpinnerItemSelectedListener() {
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentExcercise = (Excercise) spinner.getSelectedItem();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }


    private void setButtonStatsClickListener() {

        Button toggleButton = (Button)findViewById(R.id.button_stats);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    Intent statsActivityIntent = new Intent(MainActivity.this, StatsActivity.class);
                    statsActivityIntent.putExtra("name", currentExcercise.name);
                    statsActivityIntent.putExtra("overallCount", currentExcercise.overallCount);
                    statsActivityIntent.putExtra("monthCount", currentExcercise.monthCount);
                    statsActivityIntent.putExtra("yearCount", currentExcercise.yearCount);
                    startActivity(statsActivityIntent);
                }
            });
    }



}