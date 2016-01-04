package com.ilija.mojrestoran;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.ilija.mojrestoran.model.MojRestoran;
import com.ilija.mojrestoran.ui.LoginActivity;
import com.ilija.mojrestoran.util.Constants;
import com.ilija.mojrestoran.util.DB;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {

    private static final String DB_URL = "https://www.dropbox.com/s/kilxntjowk6hr7f/restoran.json?raw=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        downloadDB();
        checkLogin();
    }

    private void checkLogin() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userLogin = sharedPreferences.getString(Constants.PREF_USER_LOGIN, null);
        Intent intent = null;
        if (userLogin == null )
            intent = new Intent(this, LoginActivity.class);
        else if (userLogin.equals(Constants.USER_LOGIN_ADMIN))
            intent = new Intent(this, LoginActivity.class);
        else if (userLogin.equals(Constants.USER_LOGIN_WAITER))
            intent = new Intent(this, LoginActivity.class);

        startActivity(intent);
        finish();
    }

    private void downloadDB() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                URL url = null;
                try {
                    url = new URL(DB_URL);
                    HttpURLConnection httpclient = (HttpURLConnection) url.openConnection();
                    httpclient.setRequestMethod("GET");
                    httpclient.connect();
                    if (httpclient.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        return null;
                    }
                    InputStream inputStream = httpclient.getInputStream();
                    Gson gson = new Gson();
                    MojRestoran mojRestoran = gson.fromJson(new InputStreamReader(inputStream), MojRestoran.class);
                    if (mojRestoran != null)
                        DB.getInstance().setMojRestoran(mojRestoran);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

    }
}
