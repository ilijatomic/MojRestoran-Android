package com.ilija.mojrestoran;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;
import com.google.gson.Gson;
import com.ilija.mojrestoran.model.Korisnik;
import com.ilija.mojrestoran.model.MojRestoran;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by ilija.tomic on 1/4/2016.
 */
public class AppObject extends Application {

    private static final String APP_KEY = "wzg097s234bmf6e";
    private static final String APP_SECRET = "v7vsk1vebufy0tx";

    private static final String RESTORAN_JSON = "restoran.json";

    private static AppObject appObject;

    private MojRestoran mojRestoran;
    private Korisnik ulogovanKorisnik;

    private DropboxAPI<AndroidAuthSession> mDBApi;

    @Override
    public void onCreate() {
        super.onCreate();
        if (appObject == null)
            appObject = (AppObject) getApplicationContext();

        AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeys);
        mDBApi = new DropboxAPI<AndroidAuthSession>(session);
        mDBApi.getSession().startOAuth2Authentication(appObject);
    }

    public static AppObject getAppInstance() {
        return appObject;
    }

    public MojRestoran getMojRestoran() {
        return mojRestoran;
    }

    public void setMojRestoran(MojRestoran mojRestoran) {
        this.mojRestoran = mojRestoran;
    }

    public Korisnik getUlogovanKorisnik() {
        return ulogovanKorisnik;
    }

    public void setUlogovanKorisnik(Korisnik ulogovanKorisnik) {
        this.ulogovanKorisnik = ulogovanKorisnik;
    }

    public void sacuvajKorisnika() {
        onDropboxResume();

        for (Korisnik korisnik : mojRestoran.getKorisnikArrayList()) {
            if (korisnik.getEmail().equals(ulogovanKorisnik.getEmail())) {
                mojRestoran.getKorisnikArrayList().remove(korisnik);
                mojRestoran.getKorisnikArrayList().add(ulogovanKorisnik);

                Gson gson = new Gson();
                try {
                    FileWriter file = new FileWriter(Environment.getExternalStorageDirectory() + "/" + RESTORAN_JSON);
                    gson.toJson(mojRestoran, file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    File file = new File(Environment.getExternalStorageDirectory() + "/" + RESTORAN_JSON);
                    FileInputStream inputStream = new FileInputStream(file);
                    DropboxAPI.Entry response = mDBApi.putFile(RESTORAN_JSON, inputStream, file.length(), null, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onDropboxResume() {
        if (mDBApi.getSession().authenticationSuccessful()) {
            try {
                // Required to complete auth, sets the access token on the session
                mDBApi.getSession().finishAuthentication();

                String accessToken = mDBApi.getSession().getOAuth2AccessToken();
            } catch (IllegalStateException e) {
                Log.i("DbAuthLog", "Error authenticating", e);
            }
        }
    }
}
