package com.example.agoraapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agoraapp.CallingActivity;
import com.example.agoraapp.InCallActivity;
import com.example.agoraapp.Models.User;
import com.example.agoraapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersRecyclerView extends RecyclerView.Adapter<UsersRecyclerView.ViewHolder> {

    Context context;
    private List<User> userList;

    FirebaseUser mAuth= FirebaseAuth.getInstance().getCurrentUser();


    public UsersRecyclerView(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UsersRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersRecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        User user= userList.get(position);
        holder.tv_name.setText(user.getUserName());
        holder.tv_phoneNumber.setText(user.getEmail());


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> callsMap= new HashMap<>();

                callsMap.put("Caller", "test");
                callsMap.put("Receiver", user.getuId());
                callsMap.put("token","0064150416d65534a7e8c54b25f5192a37fIAA+FWUHsxGIzX2R2v9VX/Y56d2FIzvpYX1HEL783IQEkwx+f9gAAAAAEABekxT0+elSYQEAAQD66VJh");


                FirebaseFirestore.getInstance().collection("Calls").document(user.getuId()).set(callsMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Bundle bundle= new Bundle();
                        bundle.putString("token","0064150416d65534a7e8c54b25f5192a37fIAA+FWUHsxGIzX2R2v9VX/Y56d2FIzvpYX1HEL783IQEkwx+f9gAAAAAEABekxT0+elSYQEAAQD66VJh");
                        bundle.putString("channelName", "test");
                        Intent intent= new Intent(context,  InCallActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
            }
        });



    }

    @Override
    public int getItemCount() {
        return userList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder{
        public View view;
        public TextView tv_name, tv_phoneNumber;
        public LinearLayout layout;
        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context= ctx;
            layout =itemView.findViewById(R.id.ll_item_user);
            tv_name = itemView.findViewById(R.id.name_user);
            tv_phoneNumber = itemView.findViewById(R.id.phoneNumber_user);







        }
    }
}
