package com.tellioglu.workmanagerjava;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class RefreshDataBase extends Worker {
    Context context;
    public RefreshDataBase(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {

        try{
            Data data = getInputData();
            int number =  data.getInt("intKey",1);
            refreshDataBase(number);
            return Result.success();
        }
        catch (Exception e){
            return Result.failure();
        }

    }
     private void refreshDataBase(int number){
         SharedPreferences sharedPreferences = context.getSharedPreferences("com.tellioglu.workmanagerjava",Context.MODE_PRIVATE);
         int mySavedNumber = sharedPreferences.getInt("myNumber",0);
         mySavedNumber+=number;
         System.out.println(mySavedNumber);
         sharedPreferences.edit().putInt("myNumber",mySavedNumber).apply();

     }
}
