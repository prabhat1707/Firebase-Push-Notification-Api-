package com.trenzlr.firebasesample;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by prabhat on 15/1/18.
 */

public class SharedPrefUtil {
    private static final String SHARED_APP_PREFERENCE_NAME = "SharedPref";
    Context cxt;
    private SharedPreferences pref;
    private SharedPreferences.Editor mEditor;

    public SharedPrefUtil(Context context) {
        this.pref = context.getSharedPreferences(SHARED_APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPrefUtil getInstance(Context context) {
        return new SharedPrefUtil(context);
    }

    public void put(String key, String value) {
        pref.edit().putString(key, value).apply();

    }
    public String getString(String key)
    {
        return pref.getString(key,"");
    }
}
