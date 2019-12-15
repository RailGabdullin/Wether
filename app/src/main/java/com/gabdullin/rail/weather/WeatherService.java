package com.gabdullin.rail.weather;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import androidx.annotation.Nullable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherService extends Service {

    //Пока разобраться в API погодных сервисов не осилил, сделал такой себе WebView, который в фоне
    //постоянно обновляется.
    //Вещь бесмысленная и беспощадная, просто чтобы потренироваться.
    private String url = "https://yandex.ru/pogoda/";
    private OkHttpClient client = new OkHttpClient();

    //Тут, конечно, надо бы синглтон сделать, но пока так подкостылю. Технический долг
    static String result;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).build();
        Log.i("SERVICE", "StartCommand");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.i("SERVICE", "New request");
                result = response.body().string();
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }
}
