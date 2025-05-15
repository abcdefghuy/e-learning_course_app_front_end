package com.example.e_learningcourse.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class TokenManager {
    private static final String PREF_NAME = "secure_prefs";
    private static final String KEY_TOKEN = "auth_token";
    private static final String EXPIRED_IN = "expired_in";

    private static TokenManager instance;
    private SharedPreferences sharedPreferences;


    private TokenManager(Context context) throws GeneralSecurityException, IOException {
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
    }

    public static synchronized TokenManager getInstance(Context context) {
        if (instance == null) {
            try {
                instance = new TokenManager(context.getApplicationContext());
            } catch (GeneralSecurityException | IOException e) {
                throw new RuntimeException("TokenManager initialization failed", e);
            }
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
