package com.a6studios.fbchat.package_ChatBox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.a6studios.fbchat.R;
import com.a6studios.fbchat.package_MainActivity.MainActivity;

public class ChatBox extends AppCompatActivity {
    String toName;
    String toUID;
    TextView tv ;
    SearchView sv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);
        tv = findViewById(R.id.tv_displayName);
        sv = findViewById(R.id.searchView);
        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            toName = extras.getString("toName");
            toUID = extras.getString("toUID");
            tv.setText(toName);
        }

        sv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    tv.setText("");
                    tv.setVisibility(View.INVISIBLE);
                }

                else
                {
                    tv.setText(toName);
                    tv.setSingleLine(true);
                }


            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
