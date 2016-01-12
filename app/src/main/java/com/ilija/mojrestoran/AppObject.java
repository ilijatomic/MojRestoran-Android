package com.ilija.mojrestoran;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Environment;

import com.google.gson.Gson;
import com.ilija.mojrestoran.model.Korisnik;
import com.ilija.mojrestoran.model.MojRestoran;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ilija.tomic on 1/4/2016.
 */
public class AppObject extends Application {

    private static AppObject appObject;

    private MojRestoran mojRestoran;
    private Korisnik ulogovanKorisnik;

    @Override
    public void onCreate() {
        super.onCreate();
        if (appObject == null)
            appObject = (AppObject) getApplicationContext();
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

    public void izmeniKorisnik() {
        for (Korisnik korisnik : mojRestoran.getKorisnikArrayList()) {
            if (korisnik.getEmail().equals(ulogovanKorisnik.getEmail())) {
                mojRestoran.getKorisnikArrayList().remove(korisnik);
                mojRestoran.getKorisnikArrayList().add(ulogovanKorisnik);
                updateRestoranBase();
            }
        }
    }

    public void updateRestoranBase() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    FileWriter file = new FileWriter(Environment.getExternalStorageDirectory() + File.separator + SplashActivity.RESTORAN_JSON, false);
                    Gson gson = new Gson();
                    if (mojRestoran != null) {
                        String json = gson.toJson(mojRestoran);
                        file.write(json);
                        file.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

    }

}
