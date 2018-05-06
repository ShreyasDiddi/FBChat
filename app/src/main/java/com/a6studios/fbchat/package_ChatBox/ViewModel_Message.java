package com.a6studios.fbchat.package_ChatBox;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.a6studios.fbchat.package_MainActivity.POJO_Users;

import java.util.List;

/**
 * Created by HP on 3/31/2018.
 */

public class ViewModel_Message extends AndroidViewModel {
    private Repository_Messages mRepository;
    private LiveData<List<POJO_Message>> mAllWords;
    public ViewModel_Message(@NonNull Application application) {
        super(application);
        mRepository = new Repository_Messages(application);
        mAllWords = mRepository.getAllWords();
    }
    LiveData<List<POJO_Message>> getAllMessages()
    {
        return mAllWords;
    }

    public void insert(POJO_Message message)
    {
        mRepository.insert(message);
    }

    public void  setLr_messageList()
    {
        mRepository.setLr_messageList();
    }

}
