package com.tellioglu.sqlliteproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteDatabase database = this.openOrCreateDatabase("Musicians",MODE_PRIVATE,null);
        database.execSQL("CREATE TABLE IF NOT EXISTS musicians (id INTEGER PRIMARY KEY, name VARCHAR, age INTEGER)");
        database.execSQL("DELETE FROM musicians WHERE id=3 OR id=4");
       // database.execSQL("INSERT INTO musicians (name,age) VALUES ('Celal',35)");
//        database.execSQL("INSERT INTO musicians (name,age) VALUES ('Cemil',40)");
        Cursor cursor = database.rawQuery("SELECT * FROM musicians",null);
        int idIx = cursor.getColumnIndex("id");
        int ageIx = cursor.getColumnIndex("age");
        int nameIx = cursor.getColumnIndex("name");
        while ((cursor.moveToNext())){
            System.out.println("id: "+cursor.getInt(idIx));
            System.out.println("name: "+cursor.getString(nameIx));
            System.out.println("age: "+cursor.getInt(ageIx));
        }
        cursor.close();
    }
}