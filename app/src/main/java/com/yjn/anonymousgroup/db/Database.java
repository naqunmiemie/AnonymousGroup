package com.yjn.anonymousgroup.db;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.yjn.anonymousgroup.model.Message;

@androidx.room.Database(entities = {Message.class},version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {
    private static final String DATABASE_NAME = "database";
    private static Database databaseInstance;
    public static synchronized Database getInstance(Context context){
        if (databaseInstance == null){
            databaseInstance = Room.databaseBuilder(context.getApplicationContext(),Database.class,DATABASE_NAME).build();
        }
        return databaseInstance;
    }

    public abstract MessageDao messageDao();
}
