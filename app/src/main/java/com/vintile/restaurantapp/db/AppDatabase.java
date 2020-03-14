package com.vintile.restaurantapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.vintile.restaurantapp.models.RestuarantMenu;
import com.vintile.restaurantapp.util.AppConstants;

/**
 * Created by Sharath TN on 2019-07-11.
 */
@Database(entities = {RestuarantMenu.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static AppDatabase sInstance;
    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {

                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppConstants.DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }
    public abstract MenuDao menuDao();
}