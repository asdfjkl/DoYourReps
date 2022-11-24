package com.asdfjkl.doyourreps;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.screen_stats);

        setButtonDoneClickListener();

        TextView tvName = findViewById(R.id.text_view_excercise);
        tvName.setText(getIntent().getStringExtra("name"));

        TextView tvOverallCount = findViewById(R.id.text_view_overall_count);
        tvOverallCount.setText(String.valueOf(getIntent().getIntExtra("overallCount", 0)));

        TextView tvYearCount = findViewById(R.id.text_view_year_count);
        tvYearCount.setText(String.valueOf(getIntent().getIntExtra("yearCount", 0)));

        TextView tvMontCount = findViewById(R.id.text_view_month_count);
        tvMontCount.setText(String.valueOf(getIntent().getIntExtra("monthCount", 0)));

    }

    private void setButtonDoneClickListener() {

        Button toggleButton = (Button)findViewById(R.id.button_done);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

}
