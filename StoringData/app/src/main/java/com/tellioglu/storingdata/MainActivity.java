package com.tellioglu.storingdata;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
EditText editText;
TextView textView;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editTextNumber);
        textView = findViewById(R.id.textView);
         sharedPreferences = this.getSharedPreferences("com.tellioglu.storingdata", Context.MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int age  =sharedPreferences.getInt("storedAge", 0);
        if(age!=0)
            textView.setText("Your age is " + age);
    }

    public void saveAge(View view)
    {
        AlertDialog.Builder  alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Save");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!editText.getText().toString().matches("")) {
                    try {
                        int userAge = Integer.parseInt(editText.getText().toString());
                        textView.setText("Your age is " + userAge);
                        sharedPreferences.edit().putInt("storedAge", userAge).apply();
                        Toast.makeText(MainActivity.this, "Age saved", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Please enter only number", Toast.LENGTH_LONG).show();
                    }
                    ;
                }
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Not Saved", Toast.LENGTH_LONG).show();
            }
        });
        alertDialog.show();



    }
    public void delete(View view)
    {

            try {
                textView.setText("Your age is " );
                sharedPreferences.edit().remove("storedAge").apply();
            }
            catch (Exception e){

            };


    }
}