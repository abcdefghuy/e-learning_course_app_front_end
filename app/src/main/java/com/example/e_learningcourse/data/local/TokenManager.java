package com.example.e_learningcourse.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.security.KeyStoreException;
import android.util.Log;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class TokenManager {
    private static final String TAG = "TokenManager";
    private static final String PREF_NAME = "secure_prefs";
    private static final String KEY_TOKEN = "auth_token";
    private static final String EXPIRED_IN = "expired_in";

    private static TokenManager instance;
    private SharedPreferences sharedPreferences;

    private TokenManager(Context context) {
        try {
            initializeEncryptedPrefs(context);
        } catch (Exception e) {
            Log.e(TAG, "Failed to initialize encrypted preferences", e);
            // Fallback to regular SharedPreferences if encryption fails
            sharedPreferences = context.getApplicationContext()
                    .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
    }

    private void initializeEncryptedPrefs(Context context) throws GeneralSecurityException, IOException {
        try {
            MasterKey masterKey = new MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            sharedPreferences = EncryptedSharedPreferences.create(
                    context,
                    PREF_NAME,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (Exception e) {
            Log.e(TAG, "Error creating encrypted preferences", e);
            // If we get a verification error, try to recreate the master key
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (e.getCause() instanceof KeyStoreException) {
                    // Clear the existing preferences
                    context.getApplicationContext()
                            .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                            .edit()
                            .clear()
                            .apply();

                    // Create a new master key
        MasterKey masterKey = new MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();

        sharedPreferences = EncryptedSharedPreferences.create(
                context,
                PREF_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );
                } else {
                    throw e;
                }
            } else {
                // For older Android versions, fall back to regular SharedPreferences
                sharedPreferences = context.getApplicationContext()
                        .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            }
        }
    }

    public static synchronized TokenManager getInstance(Context context) {
        if (instance == null) {
                instance = new TokenManager(context.getApplicationContext());
        }
        return instance;
    }

    public void saveToken(String token) {
        sharedPreferences.edit().putString(KEY_TOKEN, token).apply();
    }
    public void saveExpiredToken(String expiredIn) {
        sharedPreferences.edit().putString(EXPIRED_IN,expiredIn).apply();
    }
    public boolean isTokenExpired() {
        String expiredIn = sharedPreferences.getString(EXPIRED_IN, null);
        if (expiredIn != null) {
            long currentTime = System.currentTimeMillis() / 1000;
            long expirationTime = Long.parseLong(expiredIn);
            return currentTime > expirationTime;
        }
        return true;
    }

    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    public void clearToken() {
        sharedPreferences.edit().remove(KEY_TOKEN).apply();
    }
}
