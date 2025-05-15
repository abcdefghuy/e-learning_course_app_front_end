package com.example.e_learningcourse.api;

import static com.example.e_learningcourse.constants.Constants.BASE_URL;
import static com.example.e_learningcourse.constants.Constants.TEST_URL;

import com.example.e_learningcourse.App;
import com.example.e_learningcourse.data.local.TokenManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit authenticatedRetrofit;
    private static Retrofit unauthenticatedRetrofit;

    private static OkHttpClient authenticatedClient;
    private static OkHttpClient unauthenticatedClient;

    private static final Gson gson = new GsonBuilder().setLenient().create();

    private static OkHttpClient getAuthenticatedClient() {
        if (authenticatedClient == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

            Interceptor tokenInterceptor = chain -> {
                Request originalRequest = chain.request();

                // Kiểm tra token còn hạn không
                String token = TokenManager.getInstance(App.getContext()).getToken();
                boolean isExpired = TokenManager.getInstance(App.getContext()).isTokenExpired();

                // Nếu có token và chưa hết hạn → gắn Authorization
                if (token != null && !isExpired) {
                    Request requestWithToken = originalRequest.newBuilder()
                            .addHeader("Authorization", "Bearer " + token)
                            .build();
                    return chain.proceed(requestWithToken);
                }

                // Nếu token đã hết hạn → không gắn token (coi như client công khai)
                return chain.proceed(originalRequest);
            };


            authenticatedClient = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addInterceptor(tokenInterceptor)
                    .authenticator(new TokenAuthenticator(App.getContext()))
                    .build();
        }
        return authenticatedClient;
    }

    private static OkHttpClient getUnauthenticatedClient() {
        if (unauthenticatedClient == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

            unauthenticatedClient = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();
        }
        return unauthenticatedClient;
    }

    // Dành cho các API cần token
    public static <S> S createAuthenticatedService(Class<S> serviceClass) {
        if (authenticatedRetrofit == null) {
            authenticatedRetrofit = new Retrofit.Builder()
                    .baseUrl(TEST_URL)
                    .client(getAuthenticatedClient())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return authenticatedRetrofit.create(serviceClass);
    }

    // Dành cho login, register...
    public static <S> S createUnauthenticatedService(Class<S> serviceClass) {
        if (unauthenticatedRetrofit == null) {
            unauthenticatedRetrofit = new Retrofit.Builder()
                    .baseUrl(TEST_URL)
                    .client(getUnauthenticatedClient())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return unauthenticatedRetrofit.create(serviceClass);
    }
}

