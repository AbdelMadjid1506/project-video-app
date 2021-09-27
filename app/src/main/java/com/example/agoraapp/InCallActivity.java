package com.example.agoraapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;

public class InCallActivity extends AppCompatActivity {

    private static final int PERMISSION_REQ_ID = 22;
    private TextView tv_incall;

    private static final String[] REQUESTED_PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
    };
    private String appId = "4150416d65534a7e8c54b25f5192a37f";
    // Fill the channel name.
    private String channelName;
    // Fill the temp token generated on Agora Console.
    private String token;
    private RtcEngine mRtcEngine;
    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {
        @Override
        // Listen for the onJoinChannelSuccess callback.
        // This callback occurs when the local user successfully joins the channel.
        public void onJoinChannelSuccess(String channel, final int uid, int elapsed) {
            runOnUiThread(new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {
                    tv_incall.setText("Your call start");


                }
            });
        }

        @Override
        // Listen for the onFirstRemoteVideoDecoded callback.
        // This callback occurs when the first video frame of a remote user is received and decoded after the remote user successfully joins the channel.
        // You can call the setupRemoteVideo method in this callback to set up the remote video view.
        public void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
        }

        @Override
        // Listen for the onUserOffline callback.
        // This callback occurs when the remote user leaves the channel or drops offline.
        public void onUserOffline(final int uid, int reason) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_call);

        channelName= getIntent().getExtras().getString("channelName");
        token= getIntent().getExtras().getString("token");
        tv_incall= findViewById(R.id.tv_incall);
        if (checkSelfPermission(REQUESTED_PERMISSIONS[0], PERMISSION_REQ_ID)) {
            initializeAndJoinChannel();
        }

    }

    private boolean checkSelfPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, requestCode);
            return false;
        }
        return true;
    }

    private void initializeAndJoinChannel() {
        try {
            mRtcEngine = RtcEngine.create(getBaseContext(), appId, mRtcEventHandler);
            Log.d("mRtc", "initializeAndJoinChannel: you have joined the channel");
        } catch (Exception e) {

            throw new RuntimeException("Check the error");
        }
        mRtcEngine.joinChannel(token, channelName, "", 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRtcEngine.leaveChannel();
        RtcEngine.destroy();
    }
}