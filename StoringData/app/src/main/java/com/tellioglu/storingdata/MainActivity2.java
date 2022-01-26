package com.tellioglu.storingdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView = findViewById(R.id.textView2);
        sharedPreferences = this.getSharedPreferences("com.tellioglu.storingdata", Context.MODE_PRIVATE);
    }
    @Override
    protected void onResume() {
        super.onResume();
        int age  =sharedPreferences.getInt("storedAge", 0);
        if(age!=0)
            textView.setText("Your age is " + age);
    }
    public void goBack(View view)
    {
Intent intent = new Intent(getApplicationContext(),MainActivity.class);
startActivity(intent);
    }
}