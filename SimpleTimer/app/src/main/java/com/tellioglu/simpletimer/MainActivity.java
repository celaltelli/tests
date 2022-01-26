package com.tellioglu.simpletimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
 public int number = 0;
 TextView textView;
 Runnable runnable;
 Handler handler;
 Button buttonSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        buttonSave = findViewById(R.id.button);
    }
    public void start(View view){
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                number++;
                textView.setText("Time: " + number);
                handler.postDelayed(runnable,1000);
            }
        };
        handler.post(runnable);
        buttonSave.setEnabled(false);

    }
    public void stop(View view){
        handler.removeCallbacks(runnable);
        number = 0;
        textView.setText("Time: " + number);
        buttonSave.setEnabled(true);

    }
}