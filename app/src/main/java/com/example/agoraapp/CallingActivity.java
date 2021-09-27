package com.example.agoraapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;

public class CallingActivity extends AppCompatActivity {

    private static final int PERMISSION_REQ_ID = 22;

    private static final String[] REQUESTED_PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
    };
    private String appId = "4150416d65534a7e8c54b25f5192a37f";
    // Fill the channel name.
    private String channelName = "test";
    // Fill the temp token generated on Agora Console.
    private String token = null;
    private RtcEngine mRtcEngine;
    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);
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