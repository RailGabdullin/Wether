package com.gabdullin.rail.weather;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

import androidx.annotation.Nullable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherService extends Service {

    String url = "https://google.com";
    OkHttpClient client = new OkHttpClient();
    static String result;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("SERVICE", String.valueOf(Thread.interrupted()));
                while (!Thread.interrupted()){
                    Request.Builder builder = new Request.Builder();
                    Request request = builder.url(url).build();
                    Log.i("SERVICE", "New request");
                    try {
                        Response response = client.newCall(request).execute();
                        result = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }
}
