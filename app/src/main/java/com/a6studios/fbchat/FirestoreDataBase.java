package com.a6studios.fbchat;

import android.support.annotation.NonNull;
import android.util.Log;

import com.a6studios.fbchat.package_MainActivity.POJO_Users;
import com.a6studios.fbchat.package_MainActivity.RV_Adapter_UsersList;
import com.a6studios.fbchat.package_OTPVerifiation.POJO_User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
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
    private static final String rUsers = "reged_users";
    private static final String TAG = "MY FiREBASE ERROR";


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
        }
    }

    public static FirestoreDataBase getFirestoreDatabase()
    {
        if(mFirestoreDatabase==null)
            mFirestoreDatabase = new FirestoreDataBase();
        return mFirestoreDatabase;
    }


    public  FirestoreDataBase getmFirestoreDatabase() {
        return mFirestoreDatabase;
    }

    public void getRegedUsersList(final RV_Adapter_UsersList mAdapter)
    {
        db.collection("reged_users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                POJO_Users u = document.toObject(POJO_Users.class);
                                FirestoreDataBase fdb = FirestoreDataBase.getFirestoreDatabase();
                                String name = fdb.getFirebaseUser().getDisplayName();
                                mAdapter.addUser(u);
                            }
                        } else {
                            Log.d("ERROR", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public  FirebaseFirestore getDb() {
        return db;
    }

    public  FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }


    public String getUserId() {
        return UserId;
    }

    public void addNewUser(POJO_User u)
    {
        HashMap<String,String> m = new HashMap<String, String>();
        m.put("name",u.getName());
        m.put("UID",u.getUID());
        db.collection(rUsers).document(getUserId()).set(m);
    }



    public static void cleanUp()
    {
        UserId = null;
        firebaseUser = null;
        db = null;
        settings=null;
        mFirestoreDatabase = null;
    }

}