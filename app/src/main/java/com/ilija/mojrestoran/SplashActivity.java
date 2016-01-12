package com.ilija.mojrestoran;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.os.Bundle;

import com.google.gson.Gson;
import com.ilija.mojrestoran.model.Korisnik;
import com.ilija.mojrestoran.model.MojRestoran;
import com.ilija.mojrestoran.ui.AdminHomeActivity;
import com.ilija.mojrestoran.ui.BaseActivity;
import com.ilija.mojrestoran.ui.KonobarHomeActivity;
import com.ilija.mojrestoran.ui.LoginActivity;
import com.ilija.mojrestoran.util.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SplashActivity extends BaseActivity {

//    private static final String DB_URL = "https://www.dropbox.com/s/kilxntjowk6hr7f/restoran.json?raw=1";
//    private static final String DB_URL = "http://172.17.130.208:8081/index.html";
    public static final String RESTORAN_JSON = "restoran.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        downloadDB();

    }

    private void checkLogin() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userLogin = sharedPreferences.getString(Constants.PREF_USER_LOGIN, null);
        Intent intent = null;
        if (userLogin == null )
            intent = new Intent(this, LoginActivity.class);
        else {

            for (Korisnik korisnik : AppObject.getAppInstance().getMojRestoran().getKorisnikArrayList()) {
                if (korisnik.getEmail().equals(userLogin)) {
                    AppObject.getAppInstance().setUlogovanKorisnik(korisnik);
                    if (AppObject.getAppInstance().getUlogovanKorisnik().getTip().equals(Constants.USER_LOGIN_ADMIN))
                        intent = new Intent(this, AdminHomeActivity.class);
                    else if (AppObject.getAppInstance().getUlogovanKorisnik().getTip().equals(Constants.USER_LOGIN_KONOBAR))
                        intent = new Intent(this, KonobarHomeActivity.class);
                    break;
                }
            }

        }

        startActivity(intent);
        finish();
    }

    private void downloadDB() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(Environment.getExternalStorageDirectory() + File.separator + RESTORAN_JSON));
                    Gson gson = new Gson();
                    MojRestoran mojRestoran = gson.fromJson(bufferedReader, MojRestoran.class);
                    if (mojRestoran != null)
                        AppObject.getAppInstance().setMojRestoran(mojRestoran);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                checkLogin();
            }
        }.execute();

    }
}
