package com.a6studios.fbchat;

import android.support.annotation.NonNull;
import android.util.Log;

import com.a6studios.fbchat.package_ChatBox.POJO_Message;
import com.a6studios.fbchat.package_ChatBox.Repository_Messages;
import com.a6studios.fbchat.package_ChatBox.ViewModel_Message;
import com.a6studios.fbchat.package_MainActivity.POJO_Users;
import com.a6studios.fbchat.package_MainActivity.RV_Adapter_UsersList;
import com.a6studios.fbchat.package_MainActivity.Repository_Users;
import com.a6studios.fbchat.package_MainActivity.ViewModel_Users;
import com.a6studios.fbchat.package_OTPVerifiation.POJO_User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.HashMap;

/**
 * Created by HP on 3/20/2018.
 */

public class FirestoreDataBase {
    public static FirestoreDataBase mFirestoreDatabase;

    private static FirebaseFirestore db;

    private static FirebaseFirestoreSettings settings;

    private static FirebaseUser firebaseUser;
    private static String UserId ;
    private String toUID;
    private String deleteAfterwards1;

    public String getToUID() {
        return toUID;
    }

    public void setToUID(String toUID) {
        this.toUID = toUID;
    }

    private static Query q_usersList;
    private static Query q_messageList;

    private static ListenerRegistration lr_usersList;
    private static ListenerRegistration lr_messageList;

    private String collection_name;

    private static final String rUsers = "reged_users";;


    FirestoreDataBase()
    {
        if(mFirestoreDatabase!=null)
            throw new RuntimeException("Use getFireStoreDataBase() method");
        else {
            db = FirebaseFirestore.getInstance();

            settings = new FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true)
                    .build();
            db.setFirestoreSettings(settings);

            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            UserId = FirebaseAuth.getInstance().getUid();
            lr_messageList = null;
            q_usersList = db.collection(rUsers);
        }
    }

    public static FirestoreDataBase getFirestoreDatabase()
    {
        if(mFirestoreDatabase==null)
            mFirestoreDatabase = new FirestoreDataBase();
        return mFirestoreDatabase;
    }

    public String getUserId() {
        return UserId;
    }

    public FirebaseUser getFirebaseUser(){return firebaseUser;}

    public void addNewUser(POJO_User u)
    {
        HashMap<String,String> m = new HashMap<String, String>();
        m.put("name",u.getName());
        m.put("UID",u.getUID());
        db.collection(rUsers).document(getUserId()).set(m);
    }

    public void setLr_usersList(final Repository_Users repository_users)
    {
        lr_messageList = q_usersList.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for(DocumentChange dc : documentSnapshots.getDocumentChanges())
                {
                    POJO_Users mUser = dc.getDocument().toObject(POJO_Users.class);
                    repository_users.insert(mUser);
                }
            }
        });
    }

    public void setLr_messageList(final Repository_Messages m)
    {
        lr_messageList = q_messageList.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for(DocumentChange dc :documentSnapshots.getDocumentChanges()) {
                    m.insert(dc.getDocument().toObject(POJO_Message.class));
                }
            }
        });
    }

    public void addMessage(POJO_Message m)
    {
        HashMap<Object,Object> hm = new HashMap<Object, Object>();
        hm.put("ts",m.getTs());
        hm.put("from_uid",m.getFrom_uid());
        hm.put("to_uid",m.getTo_uid());
        hm.put("message",m.getMessage());
        hm.put("sent",m.isSent());
        FirebaseFirestore.getInstance().collection("ChatBubbles").document(collection_name).collection("messages").document(m.getTs()).set(hm);

    }

    public void setCr_chat(String collection_name)
    {
        this.collection_name = collection_name;
    }

    public void setQ_messageList()
    {
        q_messageList = FirebaseFirestore.getInstance().collection("ChatBubbles").document(collection_name).collection("messages");
    }
    public void removeListner_Q_messageList()
    {
        lr_messageList.remove();
    }

    public static void cleanUp()
    {
        if(lr_usersList!=null)
        {
            lr_messageList.remove();
            lr_usersList = null;
        }

        UserId = null;
        firebaseUser = null;
        db = null;
        settings=null;
        mFirestoreDatabase = null;
        q_messageList = null;
        q_usersList = null;

    }
}