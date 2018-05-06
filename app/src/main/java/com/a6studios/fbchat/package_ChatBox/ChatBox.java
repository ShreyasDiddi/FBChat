package com.a6studios.fbchat.package_ChatBox;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import com.a6studios.fbchat.FirestoreDataBase;
import com.a6studios.fbchat.R;
import com.a6studios.fbchat.package_MainActivity.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ChatBox extends AppCompatActivity {
    String toName;
    String toUID;
    TextView tv ;
    EditText msg;
    String collection_name;
    RecyclerView rvMessages;
    RV_Adapter chatAdapter;
    ViewModel_Message viewModel_message;
     Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);
        tv = findViewById(R.id.tv_displayName);
        msg = findViewById(R.id.msg_et);
        send = findViewById(R.id.s);
        rvMessages = findViewById(R.id.rvMessages);
        LinearLayoutManager m = new LinearLayoutManager(this);
        rvMessages.setLayoutManager(m);
        chatAdapter = new RV_Adapter(this);
        rvMessages.setAdapter(chatAdapter);

        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            toName = extras.getString("toName");
            toUID = extras.getString("toUID");
            FirestoreDataBase.getFirestoreDatabase().setToUID(toUID);
            tv.setText(toName);
            String mUID = FirebaseAuth.getInstance().getUid();
            if(mUID.compareTo(toUID)<0)
                collection_name = mUID+toUID;
            else
                collection_name = toUID+mUID;
            FirestoreDataBase.getFirestoreDatabase().setCr_chat(collection_name);
            FirestoreDataBase.getFirestoreDatabase().setQ_messageList();



            viewModel_message = ViewModelProviders.of(this).get(ViewModel_Message.class);
            viewModel_message.setLr_messageList();
            viewModel_message.getAllMessages().observe(this, new Observer<List<POJO_Message>>() {
                @Override
                public void onChanged(@Nullable List<POJO_Message> pojo_messages) {
                    chatAdapter.setMessageList(pojo_messages);
                    rvMessages.smoothScrollToPosition(chatAdapter.getItemCount());
                }
            });

            FirestoreDataBase.getFirestoreDatabase().setCr_chat(collection_name);
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!msg.getText().toString().isEmpty()&!msgLength0(msg.getText().toString())) {
                        POJO_Message m = new POJO_Message();
                        m.setMessage(msg.getText().toString());
                        m.setFrom_uid(FirestoreDataBase.getFirestoreDatabase().getUserId());
                        m.setTo_uid(toUID);
                        m.setSent();
                        m.setTs();
                        msg.setText("");

                        FirestoreDataBase.getFirestoreDatabase().setCr_chat(collection_name);

                        FirestoreDataBase.getFirestoreDatabase().addMessage(m);
                    }
                }
            });


        }
    }

    boolean msgLength0(String s)
    {
        String modifieds = s.replaceAll("^\\s+","");
        s = modifieds.replaceAll("\\s+$","");
        return s.length()==0;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
