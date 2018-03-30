package com.a6studios.fbchat.package_MainActivity;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

/**
 * Created by HP on 3/30/2018.
 */

@Database(entities = {POJO_Users.class}, version = 1,exportSchema = false)
public abstract class Database_Users extends RoomDatabase {
    public abstract DAO_Users dao_users();

    private static Database_Users INSTANCE;

    static Database_Users getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database_Users.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            Database_Users.class, "word_database")
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                }
            };


}
