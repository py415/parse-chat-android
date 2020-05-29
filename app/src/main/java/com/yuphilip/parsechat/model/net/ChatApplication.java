package com.yuphilip.parsechat.model.net;

import android.app.Application;
import android.os.Build;

import com.parse.Parse;
import com.yuphilip.parsechat.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ChatApplication extends Application {

    @Override
    public void onCreate() {

        super.onCreate();

        // Use for monitoring Parse network traffic
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        // Can be Level.BASIC, Level.HEADERS, or Level.BODY
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);

        // set applicationId and server based on the values in the Heroku settings.
        // any network interceptors must be added with the Configuration Builder given this syntax
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(BuildConfig.APP_ID) // should correspond to APP_ID env variable
                .clientKey(BuildConfig.MASTER_KEY)
                .server(BuildConfig.SERVER_URL)
                .build());

    }

}
