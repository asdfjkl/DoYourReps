package com.asdfjkl.doyourreps;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    final static int REQUEST_CODE = 1337;
    private AppBarConfiguration appBarConfiguration;
    private Excercise currentExcercise;
    private String tempString = "";
    ArrayList<Excercise> excercises;
    ArrayAdapter<Excercise> excerciseArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // somehow retrieve the stored excercises
        excercises = new ArrayList<>();

        // restore excercises
        SharedPreferences spPrefs = getPreferences(MODE_PRIVATE);
        String prefSave = spPrefs.getString("EXCERCISES", "");
        String[] prefItems = prefSave.split("\\?");
        for(int i=0;i<prefItems.length;i++) {
            Excercise e = new Excercise("");
            e.restore(prefItems[i]);
            if(!e.name.isEmpty()) {
                excercises.add(e);
            }
        }

        // if our list of excercises is empty, we add the default ones
        if(excercises.size() == 0) {
            Excercise pushups = new Excercise("Push-Ups");
            Excercise pullups = new Excercise("Pull-Ups");
            Excercise crunches = new Excercise("Crunches");
            excercises.add(pushups);
            excercises.add(pullups);
            excercises.add(crunches);
        }

        // set current excercise to the beginning of the list
        currentExcercise = excercises.get(0);

        /*
        currentExcercise.reset();
        currentExcercise.currentLevel = 8;
        for(int i=0;i<10;i++) {
            System.out.println("week 1: "+ currentExcercise.getCurrentWorkoutSteps());
            currentExcercise.weekStep++;
            System.out.println("week 2: "+ currentExcercise.getCurrentWorkoutSteps());
            currentExcercise.weekStep++;
            System.out.println("week 3: "+ currentExcercise.getCurrentWorkoutSteps());
            currentExcercise.weekStep = 0;
            currentExcercise.currentLevel = (int) Math.round(currentExcercise.currentLevel * 1.15);
        } */

        setContentView(R.layout.screen_main);
        // set up spinner
        Spinner excerciseSpinner = (Spinner) findViewById(R.id.spinner);
        excerciseArrayAdapter = new ArrayAdapter<Excercise>(this,
                R.layout.spinner_item, excercises);
        excerciseArrayAdapter.setNotifyOnChange(true);
        excerciseSpinner.setAdapter(excerciseArrayAdapter);

        setButtonStartClickListener();
        setButtonAddClickListener();
        setButtonRemoveClickListener();
        setButtonResetClickListener();
        setButtonStatsClickListener();
        setSpinnerItemSelectedListener();

    }

    private void setButtonStartClickListener() {

        Button startButton = (Button)findViewById(R.id.button_start);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ArrayList<Integer> reps = currentExcercise.getCurrentWorkoutSteps();
                Intent repsActivityIntent = new Intent(MainActivity.this, RepActivity.class);
                repsActivityIntent.putExtra("setReps0", reps.get(0));
                repsActivityIntent.putExtra("setReps1", reps.get(1));
                repsActivityIntent.putExtra("setReps2", reps.get(2));
                repsActivityIntent.putExtra("setReps3", reps.get(3));
                repsActivityIntent.putExtra("setReps4", reps.get(4));
                startActivityForResult(repsActivityIntent,REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                Boolean success = data.getBooleanExtra("success", false);
                //System.out.println("success received: " + success);
                int repsDone = data.getIntExtra("repsDone", 0);
                if(currentExcercise.currentLevel == 0) {
                    // this was the initial test
                    currentExcercise.currentLevel = repsDone;
                } else {
                    // otherwise this was a regular excercise
                    if (success) {
                        // three steps of a week not yet completed
                        if (currentExcercise.weekStep < 2) {
                            currentExcercise.weekStep++;
                        } else { // we succeeded the week -> increase
                            currentExcercise.currentLevel = (int) Math.round(currentExcercise.currentLevel * 1.15);
                            currentExcercise.weekStep = 0;
                        }
                    }
                    currentExcercise.overallCount += repsDone;
                    String currentYear = Excercise.getCurrentYear();
                    String currentMonth = Excercise.getCurrentMonth();
                    if(currentYear.equals(currentExcercise.thisYear)) {
                        currentExcercise.yearCount += repsDone;
                        if(currentMonth.equals(currentExcercise.thisMonth)) {
                            currentExcercise.monthCount += repsDone;
                        } else {
                            currentExcercise.thisMonth = currentMonth;
                        }
                    } else {
                        currentExcercise.thisYear = currentYear;
                        currentExcercise.thisMonth = currentMonth;
                    }

                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    } //onActivityResult


    private void setButtonAddClickListener() {

        Button btnAdd = (Button)findViewById(R.id.button_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showAddDialog();
            }
        });
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Excercise Name");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tempString = input.getText().toString();
                // remove special chars used for storing/restoring excercises
                tempString = tempString.replace("|", "");
                excercises.add(new Excercise(tempString));
                Spinner excerciseSpinner = (Spinner) findViewById(R.id.spinner);
                excerciseSpinner.setSelection(excercises.size()-1);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void setButtonRemoveClickListener() {

        Button btnRemove = (Button)findViewById(R.id.button_remove);
        btnRemove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showRemoveDialog();
            }
        });
    }

    private void showRemoveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remove Excercise '"+currentExcercise.name+"'?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (excercises.size() > 1) {
                    excercises.remove(currentExcercise);
                    Spinner excerciseSpinner = (Spinner) findViewById(R.id.spinner);
                    excerciseArrayAdapter.notifyDataSetChanged();
                    currentExcercise = excercises.get(0);
                    excerciseSpinner.setSelection(0);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void setButtonResetClickListener() {

        Button btnReset = (Button)findViewById(R.id.button_reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showResetDialog();
            }
        });
    }

    private void showResetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reset Excercise '"+currentExcercise.name+"'?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentExcercise.reset();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
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

        Button statsButton = (Button)findViewById(R.id.button_stats);
        statsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    Intent statsActivityIntent = new Intent(MainActivity.this, StatsActivity.class);
                    statsActivityIntent.putExtra("name", currentExcercise.name);
                    statsActivityIntent.putExtra("overallCount", currentExcercise.overallCount);
                    statsActivityIntent.putExtra("monthCount", currentExcercise.monthCount);
                    statsActivityIntent.putExtra("yearCount", currentExcercise.yearCount);
                    statsActivityIntent.putExtra("month", currentExcercise.thisMonth);
                    statsActivityIntent.putExtra("year", currentExcercise.thisYear);
                    startActivity(statsActivityIntent);
                }
            });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // save excercises
        String s = "";
        for(Excercise e : excercises) {
            s += e.store() + "?";
        }
        // remove last ?
        String store = "";
        if ((s != null) && (s.length() > 0)) {
            store = s.substring(0, s.length() - 1);
        }
        if(!s.isEmpty()) {
            SharedPreferences spPrefs = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = spPrefs.edit();
            prefsEditor.putString("EXCERCISES", store);
            prefsEditor.commit();
        }


    }



}