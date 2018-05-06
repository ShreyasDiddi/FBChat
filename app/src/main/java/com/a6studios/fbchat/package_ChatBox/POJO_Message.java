package com.a6studios.fbchat.package_ChatBox;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import com.a6studios.fbchat.FirestoreDataBase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.ServerTimestamp;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by HP on 3/20/2018.
 */
@Entity(tableName = "chat_message")
public class POJO_Message {
    @PrimaryKey
    @NonNull
    String ts;
    String from_uid;
    String to_uid;
    String message;
    boolean sent;

    POJO_Message(){}

    @NonNull
    public String getTs() {
        return ts;
    }

    public void setTs(@NonNull String ts) {
        this.ts = ts;
    }

    public String getFrom_uid() {
        return from_uid;
    }

    public void setFrom_uid(String from_uid) {
        this.from_uid = from_uid;
    }

    public String getTo_uid() {
        return to_uid;
    }

    public void setTo_uid(String to_uid) {
        this.to_uid = to_uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    //custom setter
    public void setTs() {
        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
        ts = s.format(new Date());
        /*DateFormat df = DateFormat.getDateTimeInstance();
        df.setTimeZone(TimeZone.getTimeZone("gmt"));
        ts = df.format(new Date());*/
    }

    //cusstoim
    public void setSent() {
        if(from_uid.compareTo(FirestoreDataBase.getFirestoreDatabase().getUserId())==0)
            sent = true;
        else
            sent = false;
    }
}

