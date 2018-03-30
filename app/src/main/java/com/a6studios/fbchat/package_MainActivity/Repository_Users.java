package com.a6studios.fbchat.package_MainActivity;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by HP on 3/30/2018.
 */

public class Repository_Users {
    private DAO_Users mDAO_Users;
    private LiveData<List<POJO_Users>> mAllUsers;
    private POJO_Users lastUser;

    Repository_Users(Application application) {
        Database_Users db = Database_Users.getDatabase(application);
        mDAO_Users = db.dao_users();
        mAllUsers = mDAO_Users.getAllUsers();
        lastUser = mDAO_Users.getLastUser();
    }

    LiveData<List<POJO_Users>> getAllUsers() {
        return mAllUsers;
    }

    public POJO_Users getLastUser(){
        return lastUser;
    }

    public void insert (POJO_Users user) {
        new insertAsyncTaskROOM(mDAO_Users).execute(user);
    }

    private static class insertAsyncTaskROOM extends AsyncTask<POJO_Users, Void, Void> {

        private DAO_Users mAsyncTaskDao;

        insertAsyncTaskROOM(DAO_Users dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final POJO_Users... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
