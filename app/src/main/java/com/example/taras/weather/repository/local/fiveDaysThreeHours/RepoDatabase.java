package com.example.taras.weather.repository.local.fiveDaysThreeHours;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;



/**
 * Created by Taras on 23.11.2017.
 */
@Database(entities = {Repo.class}, version = 1)
public abstract class RepoDatabase extends RoomDatabase {
    private static final String DB_NAME = "repoDatabase.db";
    private static volatile  RepoDatabase instance;

    public static synchronized RepoDatabase getInstance(Context context){
        if(instance == null){
            instance = create(context);
        }
        return instance;
    }
    private static RepoDatabase create(final Context context){
        return Room.databaseBuilder(
                context,
                RepoDatabase.class,
                DB_NAME
        ).build();
    }
    public abstract RepoDAO getRepoDao();
}