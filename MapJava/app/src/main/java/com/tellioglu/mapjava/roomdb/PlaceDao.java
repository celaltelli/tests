package com.tellioglu.mapjava.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.tellioglu.mapjava.model.MyPlace;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface PlaceDao {

    @Query("SELECT * FROM MyPlace")
    Flowable<List<MyPlace>> getAll();

    @Insert
    Completable insert(MyPlace myPlace);

    @Delete
    Completable delete(MyPlace myPlace);
}
