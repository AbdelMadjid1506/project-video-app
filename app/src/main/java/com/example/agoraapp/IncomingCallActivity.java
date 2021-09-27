package com.example.agoraapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class IncomingCallActivity extends AppCompatActivity {

    private Button btn_answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_call);
        btn_answer= findViewById(R.id.btn_answer);
        btn_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key= getIntent().getExtras().getString("channelName");
                String token= getIntent().getExtras().getString("token");
                Bundle bundle= new Bundle();
                bundle.putString("token", token);
                bundle.putString("channelName", key);
                Log.d("listener incom", "onEvent: "+ token);
                Log.d("listener incom", "onEvent: "+ key);
                Intent intent= new Intent(getApplicationContext(), InCallActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();

            }
        });
    }
}