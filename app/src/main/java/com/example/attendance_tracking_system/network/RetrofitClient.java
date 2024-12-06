package com.example.attendance_tracking_system.network;

import android.util.Log;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String TAG = "RetrofitClient";
    private static final String BASE_URL = "http://10.0.2.2:8000/"; // Use this for Android Emulator
    private static RetrofitClient instance;
    private final ApiService apiService;

    private RetrofitClient() {
        // Create logging interceptor
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> 
            Log.d(TAG, "API Log: " + message));
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Create OkHttp Client
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(chain -> {
                    Log.d(TAG, "Making request to: " + chain.request().url());
                    return chain.proceed(chain.request());
                })
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        // Create Retrofit Instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        Log.d(TAG, "RetrofitClient initialized with BASE_URL: " + BASE_URL);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public ApiService getApiService() {
        return apiService;
    }
}
