package com.example.e_learningcourse.api;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.example.e_learningcourse.data.local.TokenManager;
import com.example.e_learningcourse.ui.auth.login.LoginActivity;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class TokenAuthenticator implements Authenticator {

    private final Context context;

    public TokenAuthenticator(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        // Đã thử lại rồi mà vẫn 401 => không retry nữa
        if (responseCount(response) >= 2) {
            TokenManager.getInstance(context).clearToken();
            redirectToLogin();
            return null;
        }

        // Token hết hạn → logout
        TokenManager.getInstance(context).clearToken();
        redirectToLogin();
        return null;
    }

    private void redirectToLogin() {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    private int responseCount(Response response) {
        int count = 1;
        while ((response = response.priorResponse()) != null) {
            count++;
        }
        return count;
    }
}