package com.example.e_learningcourse.data.local;

import android.content.Context;
import android.content.SharedPreferences;

public class UserManager {
    private static final String PREF_NAME = "user_info";
    private static final String KEY_ID = "user_id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NAME = "name";

    private static UserManager instance;
    private final SharedPreferences prefs;

    private UserManager(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized UserManager getInstance(Context context) {
        if (instance == null) {
            instance = new UserManager(context);
        }
        return instance;
    }

    public void saveUser(long id, String email, String name) {
        prefs.edit()
                .putLong(KEY_ID, id)
                .putString(KEY_EMAIL, email)
                .putString(KEY_NAME, name)
                .apply();
    }

    public long getUserId() {
        return prefs.getLong(KEY_ID, -1);
    }

    public String getEmail() {
        return prefs.getString(KEY_EMAIL, null);
    }

    public String getName() {
        return prefs.getString(KEY_NAME, null);
    }

    public void clear() {
        prefs.edit().clear().apply();
    }
}