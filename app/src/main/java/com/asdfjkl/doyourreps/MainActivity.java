package com.asdfjkl.doyourreps;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // somehow retrieve the stored excercises
        ArrayList<Excercise> excercises = new ArrayList<>();
        Excercise pushups = new Excercise("Push-Ups");
        Excercise pullups = new Excercise("Pull-Ups");
        excercises.add(pushups);
        excercises.add(pullups);

        setContentView(R.layout.screen_main);
        // set up spinner
        Spinner excerciseSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<Excercise> excerciseArrayAdapter = new ArrayAdapter<Excercise>(this,
                R.layout.spinner_item, excercises);
        excerciseSpinner.setAdapter(excerciseArrayAdapter);
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
}