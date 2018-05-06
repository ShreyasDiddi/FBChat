package com.a6studios.fbchat.package_MainActivity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.a6studios.fbchat.package_OTPVerifiation.POJO_User;

import java.util.List;

/**
 * Created by HP on 3/30/2018.
 */

public class ViewModel_Users extends AndroidViewModel {

    private Repository_Users mRepository;

    private LiveData<List<POJO_Users>> mAllUsers;

    public ViewModel_Users(@NonNull Application application) {
        super(application);
        mRepository = new Repository_Users(application);
        mAllUsers = mRepository.getAllUsers();
    }

    LiveData<List<POJO_Users>> getAllUsers() {
        return mAllUsers;
    }

    public void insert(POJO_Users user)
    {
        mRepository.insert(user);
    }

    public void setListenerUsers()
    {
        mRepository.setListenerUsers();
    }
}
