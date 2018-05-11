package com.a6studios.fbchat.package_ChatBox;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;



@Dao
public interface DAO_Messages {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(POJO_Message m);

    @Query("select * from chat_message where to_uid = :cUID or from_uid = :cUID order by ts ASC")
    LiveData<List<POJO_Message>> getAllWords(String cUID);
/*
    @Query("DELETE from word_table")
    void deleteAll();*/


}


