package com.emacberry.uuid0xfd6fscan.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UUIDBeacon.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public static void initialize(@NonNull Context context) {
        instance = Room.databaseBuilder(context, AppDatabase.class, "uuids")
                .allowMainThreadQueries() // TODO: Remove this obviously
                .build();
    }

    public static AppDatabase getInstance() {
        return instance;
    }

    public abstract UUIDBeaconDao beaconDao();

}
