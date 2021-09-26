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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button btn_login;
    private EditText et_email;
    private EditText et_password;
    private FirebaseAuth mAuth;
    private EditText et_userName;



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
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent= new Intent(MainActivity.this, .class);
                    intent.putExtra("userName", et_userName.getText().toString().trim());
                    startActivity(intent);
                }
            }
        });
    }

}