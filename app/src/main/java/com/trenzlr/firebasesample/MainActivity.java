package com.trenzlr.firebasesample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.trenzlr.firebasenotificationhelper.FirebaseNotiCallBack;
import com.trenzlr.firebasenotificationhelper.FirebaseNotificationHelper;

import org.json.JSONException;
import org.json.JSONObject;

import static com.trenzlr.firebasesample.Constants.KEY_TEXT;
import static com.trenzlr.firebasesample.Constants.KEY_TITLE;
import static com.trenzlr.firebasesample.MyFirebaseMessagingService.FIREBASE_TOKEN;


public class MainActivity extends AppCompatActivity implements FirebaseNotiCallBack {
    private Button sendMess, sendMess2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendMess = findViewById(R.id.sendMess);
        sendMess2 = findViewById(R.id.sendMess2);

        sendMess2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseNotificationHelper.initialize(getString(R.string.server_key))
                        .defaultJson(false, getJsonBody())
                        .setCallBack(MainActivity.this)
                        .receiverFirebaseToken(SharedPrefUtil.getInstance(MainActivity.this).getString(FIREBASE_TOKEN))
                        .send();
            }
        });

        sendMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseNotificationHelper.initialize(getString(R.string.server_key))
                        .defaultJson(true, null)
                        .title("Test")
                        .message("Test Mess")
                        .setCallBack(MainActivity.this)
                        .receiverFirebaseToken(SharedPrefUtil.getInstance(MainActivity.this).getString(FIREBASE_TOKEN))
                        .send();
            }
        });
    }

    @Override
    public void success(String s) {
        Log.i(Constants.TAG, "----------->" + s);
        Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fail(String s) {
        Log.i(Constants.TAG, "----------->" + s);
        Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();

    }

    private String getJsonBody() {

        JSONObject jsonObjectData = new JSONObject();
        try {
            jsonObjectData.put(KEY_TITLE, "Custom Title");
            jsonObjectData.put(KEY_TEXT, "Custom Mess");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jsonObjectData.toString();
    }


}
