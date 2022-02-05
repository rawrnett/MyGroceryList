package com.example.ourgrocerylist.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Items.class}, version = 1 )
public abstract class AppDatabase extends RoomDatabase {

    public abstract InventoryDao inventoryDao();
    public static AppDatabase INSTANCE;

    public static AppDatabase getDBInstance(Context context) {
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "AppDB")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }


}
