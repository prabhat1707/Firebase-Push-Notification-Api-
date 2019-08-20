package com.trenzlr.firebasenotificationhelper;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static android.content.ContentValues.TAG;
import static com.trenzlr.firebasenotificationhelper.Constants.APPLICATION_JSON;
import static com.trenzlr.firebasenotificationhelper.Constants.AUTHORIZATION;
import static com.trenzlr.firebasenotificationhelper.Constants.CONTENT_TYPE;
import static com.trenzlr.firebasenotificationhelper.Constants.FCM_URL;
import static com.trenzlr.firebasenotificationhelper.Constants.SUCCESS_CODE;

/**
 * Created by prabhat on 14/2/18.
 */

public class NetworkCall extends AsyncTask<String, Void, String> {
    private FirebaseNotiCallBack firebaseNotiCallBack;

    public NetworkCall(FirebaseNotiCallBack firebaseNotiCallBack) {
        this.firebaseNotiCallBack = firebaseNotiCallBack;
    }

    @Override
    protected String doInBackground(String... strings) {
        String JsonResponse = null;
        String JsonDATA = strings[0];
        String authKey = "key=" + strings[1];
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(FCM_URL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            // is output buffer writter
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON);
            urlConnection.setRequestProperty(AUTHORIZATION, authKey);
            //set headers and method
            Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), StandardCharsets.UTF_8));
            writer.write(JsonDATA);
            // json data
            writer.close();
            InputStream inputStream = urlConnection.getInputStream();
            //input stream
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String inputLine;
            while ((inputLine = reader.readLine()) != null)
                buffer.append(inputLine).append("\n");
            if (buffer.length() == 0) {
                // Stream was empty. No point in parsing.
                return null;
            }
            JsonResponse = buffer.toString();
            //response data
            Log.i(TAG, JsonResponse);
            //send to post execute
            return JsonResponse;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }
        return JsonResponse;
    }

    @Override
    protected void onPostExecute(String s) {
        if (firebaseNotiCallBack != null && s != null)
            try {
                JSONObject jsonObject = new JSONObject(s);
                int successCode = jsonObject.getInt(SUCCESS_CODE);
                if (successCode == 0) {
                    firebaseNotiCallBack.fail(s);
                } else if (successCode == 1) {
                    firebaseNotiCallBack.success(s);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }


}

