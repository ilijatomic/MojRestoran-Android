package com.ilija.mojrestoran.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.os.Bundle;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.gson.Gson;
import com.ilija.mojrestoran.AppObject;
import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.model.Korisnik;
import com.ilija.mojrestoran.model.MojRestoran;
import com.ilija.mojrestoran.ui.activity.AdminHomeActivity;
import com.ilija.mojrestoran.ui.activity.BaseActivity;
import com.ilija.mojrestoran.ui.activity.KonobarHomeActivity;
import com.ilija.mojrestoran.ui.activity.LoginActivity;
import com.ilija.mojrestoran.util.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class SplashActivity extends BaseActivity {

    Firebase mRef;

    private static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        downloadDB();

        getFirebase();

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
                    File file = new File(Environment.getExternalStorageDirectory()  + File.separator + Constants.RESTORAN_JSON);
                    if (!file.exists()) {
                        MojRestoran mojRestoran = new MojRestoran(null, null, null, null, null, null);
                        mojRestoran.getKorisnikArrayList().add(new Korisnik("007", "Ilija", "Tomic", "", "ilija@email.com", "qwe", "admin"));
                        AppObject.getAppInstance().setMojRestoran(mojRestoran);
                        AppObject.getAppInstance().updateRestoranBase();
                    } else {
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                        Gson gson = new Gson();
                        MojRestoran mojRestoran = gson.fromJson(bufferedReader, MojRestoran.class);
                        if (mojRestoran != null)
                            AppObject.getAppInstance().setMojRestoran(mojRestoran);
                    }
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

    private void getFirebase() {
        final MojRestoran[] mojRestoran = new MojRestoran[1];
        mRef = new Firebase("https://project-3585542348729097062.firebaseio.com/");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mojRestoran[0] = dataSnapshot.getValue(MojRestoran.class);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mojRestoran[0].getKorisnikArrayList().get(0).setBrTel("064/123");
                mRef.setValue(mojRestoran[0]);
            }
        }, 5000);
    }

    /*private void downloadDB() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    URL url = new URL(Constants.URL_DOWNLOAD);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.connect();
                    if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        Log.e(TAG, "File does not exist on this address!");
                    }

                    InputStream inputStream = httpURLConnection.getInputStream();
                    Gson gson = new Gson();
                    MojRestoran mojRestoran = gson.fromJson(new InputStreamReader(inputStream), MojRestoran.class);
                    if (mojRestoran != null)
                        AppObject.getAppInstance().setMojRestoran(mojRestoran);

                    httpURLConnection.disconnect();
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

    }*/
}
