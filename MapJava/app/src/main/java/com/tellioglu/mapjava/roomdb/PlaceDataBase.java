package com.tellioglu.mapjava.roomdb;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.tellioglu.mapjava.model.MyPlace;

@Database(entities = {MyPlace.class}, version = 1)
public abstract class PlaceDataBase extends RoomDatabase {
    public abstract PlaceDao placeDao();
}