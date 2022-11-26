package com.asdfjkl.doyourreps;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RepActivity extends AppCompatActivity {

    private ArrayList<Integer> steps;
    private int round = 0;
    private int repsDoneInCurrentRound = 0;
    private int repsDoneOverall = 0;
    private boolean success = true;
    private boolean initialTest = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        steps = new ArrayList<Integer>();
        for(int i=0;i<5;i++) {
            steps.add(0);
        }

        setContentView(R.layout.screen_reps);
        int steps0 = getIntent().getIntExtra("setReps0", 0);
        if(steps0 == 0) {
            initialTest = true;
        }
        int steps1 = getIntent().getIntExtra("setReps1", 0);
        int steps2 = getIntent().getIntExtra("setReps2", 0);
        int steps3 = getIntent().getIntExtra("setReps3", 0);
        int steps4 = getIntent().getIntExtra("setReps4", 0);

        steps.set(0, steps0);
        steps.set(1, steps1);
        steps.set(2, steps2);
        steps.set(3, steps3);
        steps.set(4, steps4);

        setupNextRound();

        setButtonDoneClickListener();
        setButtonPlusClickListener();
        setButtonMinusClickListener();

    }

    private void setupNextRound() {

        if(initialTest) {
            TextView tvSteps = findViewById(R.id.textview_set);
            tvSteps.setText("INITIAL TEST");

            int stepsToDo = 0;
            repsDoneInCurrentRound = stepsToDo;

            TextView tvReps = findViewById(R.id.textview_reps);
            tvReps.setText(String.valueOf(stepsToDo));
        } else {
            TextView tvSteps = findViewById(R.id.textview_set);
            tvSteps.setText("SET " + (round + 1) + "/5");

            int stepsToDo = steps.get(round);
            repsDoneInCurrentRound = stepsToDo;

            TextView tvReps = findViewById(R.id.textview_reps);
            tvReps.setText(String.valueOf(stepsToDo));
        }

    }

    private void setButtonPlusClickListener() {
        Button btnPlus = (Button)findViewById(R.id.button_plus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                repsDoneInCurrentRound++;
                TextView tvShowSteps = (TextView)findViewById(R.id.textview_reps);
                tvShowSteps.setText(String.valueOf(repsDoneInCurrentRound));
            }
        });
    }

    private void setButtonMinusClickListener() {
        Button btnMinus = (Button)findViewById(R.id.button_minus);
        btnMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                repsDoneInCurrentRound--;
                TextView tvShowSteps = (TextView)findViewById(R.id.textview_reps);
                tvShowSteps.setText(String.valueOf(repsDoneInCurrentRound));
            }
        });
    }

    private void setButtonDoneClickListener() {

        Button btnDone = (Button)findViewById(R.id.button_done);
        btnDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(initialTest) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("success", success);
                    returnIntent.putExtra("repsDone", repsDoneOverall);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                } else {
                    if (round >= 4) {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("success", success);
                        returnIntent.putExtra("repsDone", repsDoneOverall);
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();
                    } else {
                        showRestDialog();
                        if(repsDoneInCurrentRound < steps.get(round)) {
                            success = false;
                        }
                        repsDoneOverall += repsDoneInCurrentRound;
                        round++;
                        setupNextRound();
                    }
                }
            }
        });
    }

    private void showRestDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Rest at least one minute.");
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

}
