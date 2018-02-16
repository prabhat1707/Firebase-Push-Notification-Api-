package com.trenzlr.firebasenotificationhelper;

import org.json.JSONException;
import org.json.JSONObject;

import static com.trenzlr.firebasenotificationhelper.Constants.KEY_DATA;
import static com.trenzlr.firebasenotificationhelper.Constants.KEY_TEXT;
import static com.trenzlr.firebasenotificationhelper.Constants.KEY_TITLE;
import static com.trenzlr.firebasenotificationhelper.Constants.KEY_TO;

/**
 * Created by prabhat on 14/2/18.
 */

public class FirebaseNotificationHelper {
    private String mTitle;
    private String mMessage;
    private String mUsername;
    private String mUid;
    private String mFirebaseToken;
    private String mReceiverFirebaseToken;
    private FirebaseNotiCallBack callBack;
    private String serverApiKey;
    private boolean useDefaultJson;
    private String jsonObject;

    private FirebaseNotificationHelper(String serverApiKey) {
        this.serverApiKey = serverApiKey;
    }

    public static FirebaseNotificationHelper initialize(String serverApiKey) {

        return new FirebaseNotificationHelper(serverApiKey);
    }

    public FirebaseNotificationHelper title(String title) {
        mTitle = title;
        return this;
    }

    public FirebaseNotificationHelper message(String message) {
        mMessage = message;
        return this;
    }

    public FirebaseNotificationHelper username(String username) {
        mUsername = username;
        return this;
    }

    public FirebaseNotificationHelper uid(String uid) {
        mUid = uid;
        return this;
    }

    public FirebaseNotificationHelper firebaseToken(String firebaseToken) {
        mFirebaseToken = firebaseToken;
        return this;
    }

    public FirebaseNotificationHelper receiverFirebaseToken(String receiverFirebaseToken) {
        mReceiverFirebaseToken = receiverFirebaseToken;
        return this;
    }

    public FirebaseNotificationHelper defaultJson(boolean useDefaultJson, String object) {
        this.useDefaultJson = useDefaultJson;
        this.jsonObject = object;
        return this;
    }

    public FirebaseNotificationHelper setCallBack(FirebaseNotiCallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    public void send() {

        if (useDefaultJson) {

            new NetworkCall(callBack).execute(getValidJsonBody().toString(), serverApiKey);


        } else {
            new NetworkCall(callBack).execute(getValidJsonBody().toString(), serverApiKey);
        }

    }

    private JSONObject getValidJsonBody() {
        JSONObject jsonObjectBody = new JSONObject();
        try {
            jsonObjectBody.put(KEY_TO, mReceiverFirebaseToken);
            if (useDefaultJson) {
                JSONObject jsonObjectData = new JSONObject();
                jsonObjectData.put(KEY_TITLE, mTitle);
                jsonObjectData.put(KEY_TEXT, mMessage);
                jsonObjectBody.put(KEY_DATA, jsonObjectData);

            } else {
                JSONObject jsonObjectData = new JSONObject(jsonObject);
                jsonObjectBody.put(KEY_DATA, jsonObjectData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObjectBody;
    }

}
