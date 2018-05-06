package com.a6studios.fbchat.package_MainActivity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by HP on 3/20/2018.
 */
@Entity(tableName = "users")
public class POJO_Users {
    @PrimaryKey
    @NonNull
    private String UID="";
    @NonNull
    private String name="";


    public POJO_Users(){}

    public String getUID() {
        return UID;
    }
    public void setUID(String UID) {
        this.UID = UID;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

}
