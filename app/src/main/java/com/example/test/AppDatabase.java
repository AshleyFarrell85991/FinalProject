package com.example.test;

import androidx.room.RoomDatabase;


import androidx.room.Room;
import androidx.room.Database;
import android.content.Context;
import android.util.Log;

@Database(entities = {Pet.class,Owner.class}, version = 25, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "petlist";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .fallbackToDestructiveMigration()

                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract OwnerDao oDao();

    public abstract PetDAO pDao();

    public abstract PetandOwnerDAO pandoDao();
}