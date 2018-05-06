package com.a6studios.fbchat.package_MainActivity;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.google.firebase.firestore.ListenerRegistration;

import java.util.List;

/**
 * Created by HP on 3/30/2018.
 */

@Dao
public interface DAO_Users {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(POJO_Users pojo_users);

    @Query("select * from users where UID != :uid order by UID ASC")
    LiveData<List<POJO_Users>> getAllUsers(String uid);

    @Query("select * from users order by UID DESC limit 1")
    POJO_Users getLastUser();
}
