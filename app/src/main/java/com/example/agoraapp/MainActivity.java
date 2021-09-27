package com.example.agoraapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button btn_login;
    private EditText et_email;
    private EditText et_password;
    private FirebaseAuth mAuth;
    private EditText et_userName;
    private final FirebaseFirestore db= FirebaseFirestore.getInstance();
    private final CollectionReference collectionReference=db.collection("Users");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_login= findViewById(R.id.btn_login);
        et_email= findViewById(R.id.et_email);
        et_password= findViewById(R.id.et_pswrd);
        et_userName= findViewById(R.id.et_username);
        mAuth= FirebaseAuth.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!TextUtils.isEmpty(et_email.getText().toString().trim())
                        && !TextUtils.isEmpty(et_password.getText().toString().trim())
                        && !TextUtils.isEmpty(et_userName.getText().toString().trim())){
                    login(et_email.getText().toString().trim(), et_password.getText().toString().trim());

                }else{
                    Log.d("Login", "onClick: Empty field are not allowed");;
                }

            }
        });




    }


    private void login(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String currentUserId= mAuth.getUid();
                Map<String, Object> userMap= new HashMap<>();
                userMap.put("Email", email);
                userMap.put("Password", password);
                userMap.put("userName", et_userName.getText().toString().trim());
                userMap.put("uId", currentUserId);

                assert currentUserId != null;
                collectionReference.document(currentUserId).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Intent intent= new Intent(MainActivity.this, AllUsersActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Login", "onComplete: "+ e.getMessage());
                    }
                });




            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent= new Intent(MainActivity.this, AllUsersActivity.class);
            startActivity(intent);
            finish();
        }
    }
}