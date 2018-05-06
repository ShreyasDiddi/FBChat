package com.a6studios.fbchat.package_ChatBox;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.a6studios.fbchat.FirestoreDataBase;
import com.a6studios.fbchat.package_MainActivity.Repository_Users;
import com.a6studios.fbchat.package_OTPVerifiation.POJO_User;

import java.util.List;

/**
 * Created by HP on 3/31/2018.
 */

public class Repository_Messages {
    private DAO_Messages dao_messages;
    private LiveData<List<POJO_Message>> mAllMessages;
    private String oUID;

    Repository_Messages(Application application)
    {
        Database_Message db = Database_Message.getDatabase(application);
        dao_messages = db.daoMesages();
        oUID = FirestoreDataBase.getFirestoreDatabase().getToUID();
        mAllMessages = dao_messages.getAllWords(oUID);
    }

    public void setLr_messageList()
    {
        FirestoreDataBase.getFirestoreDatabase().setLr_messageList(this);
    }

    LiveData<List<POJO_Message>> getAllWords() {
        return mAllMessages;
    }

    public void insert(POJO_Message m)
    {
        new insertAsyncTask(dao_messages).execute(m);
    }

    public void setoUID(String oUID) {
        this.oUID = oUID;
    }

    private static class insertAsyncTask extends AsyncTask<POJO_Message,Void,Void>
    {
        private DAO_Messages mAsyncTaskDao;
        insertAsyncTask(DAO_Messages dao_messages){
            mAsyncTaskDao = dao_messages;
        }

        @Override
        protected Void doInBackground(POJO_Message... pojo_messages) {
            mAsyncTaskDao.insert(pojo_messages[0]);
            return null;
        }
    }
}
