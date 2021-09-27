package com.example.agoraapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.agoraapp.Adapters.UsersRecyclerView;
import com.example.agoraapp.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AllUsersActivity extends AppCompatActivity {

    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private CollectionReference collectionReference= db.collection("Users");
    private RecyclerView recyclerView;
    private UsersRecyclerView usersRecyclerView;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        userList= new ArrayList<>();

        recyclerView=findViewById(R.id.rv_users);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        getUsersList();
        listenerforCall();

    }




    private void getUsersList(){
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                     if(!value.isEmpty()){
                         for (DocumentChange documentChange: value.getDocumentChanges()){
                             User user = documentChange.getDocument().toObject(User.class);
                             userList.add(user);
                         }
                         usersRecyclerView= new UsersRecyclerView(AllUsersActivity.this,userList);
                         recyclerView.setAdapter(usersRecyclerView);
                         usersRecyclerView.notifyDataSetChanged();

                     }
            }
        });
    }


    private void listenerforCall(){
        FirebaseFirestore.getInstance().collection("Calls").whereEqualTo("Receiver", FirebaseAuth.getInstance().getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                assert value != null;
                if(!value.isEmpty()){

                    for(DocumentChange documentChange: value.getDocumentChanges()){
                        String key= documentChange.getDocument().getString("Caller");
                        String token= documentChange.getDocument().getString("token");

                        Bundle bundle= new Bundle();
                        bundle.putString("token", token);
                        bundle.putString("channelName", key);
                        Log.d("listener", "onEvent: "+ token);
                        Log.d("listener", "onEvent: "+ key);

                        Intent intent= new Intent(getApplicationContext(), IncomingCallActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }





                }
            }
        });
    }





}