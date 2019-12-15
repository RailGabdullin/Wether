package com.gabdullin.rail.weather;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentBrowser extends Fragment {

    String content;
    Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.server_browser_test, container, false);
    }

    private void loadPage(final View view) {
        new AsyncTask<String, String, String>(){
            @Override
            protected String doInBackground(String... strings) {
                if(content != WeatherService.result) content = WeatherService.result;
                Log.i("SERVICE", "getData");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("SERVICE", "Update browser");
                        WebView webView = view.findViewById(R.id.browser);
                        webView.loadData(content, "text/html; charset=utf-8", "utf-8" );
                    }
                });
                return content;
            }
        }.execute(content);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        loadPage(view);
        super.onViewCreated(view, savedInstanceState);
    }
}
