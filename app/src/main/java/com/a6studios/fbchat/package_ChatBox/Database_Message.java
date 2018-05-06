package com.a6studios.fbchat.package_ChatBox;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by HP on 3/31/2018.
 */
@Database(entities = {POJO_Message.class},version = 1)
public abstract class Database_Message extends RoomDatabase {
    public abstract DAO_Messages daoMesages();
    private static Database_Message INSTANCE;

    static Database_Message getDatabase(final Context context)
    {
        if(INSTANCE==null)
        {
            synchronized (Database_Message.class)
            {
                if(INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),Database_Message.class,"chat_message")
                                   .build();
                }
            }
        }
        return INSTANCE;
    }
}
