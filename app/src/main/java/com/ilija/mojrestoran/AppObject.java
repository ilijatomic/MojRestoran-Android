package com.ilija.mojrestoran;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Environment;

import com.google.gson.Gson;
import com.ilija.mojrestoran.model.Korisnik;
import com.ilija.mojrestoran.model.MojRestoran;
import com.ilija.mojrestoran.util.Constants;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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

    /*public void updateRestoranBase() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    FileWriter file = new FileWriter(Environment.getExternalStorageDirectory() + File.separator + Constants.RESTORAN_JSON, false);
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

    }*/

    public void updateRestoranBase() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    FileWriter fileWriter = new FileWriter(Environment.getExternalStorageDirectory() + File.separator + Constants.RESTORAN_JSON, false);
                    Gson gson = new Gson();
                    if (mojRestoran != null) {
                        String json = gson.toJson(mojRestoran);
                        fileWriter.write(json);
                        fileWriter.close();
                    }

                    File file = new File(Environment.getExternalStorageDirectory()  + File.separator + Constants.RESTORAN_JSON);
                    if (file.exists()) {
                        URL url = new URL(Constants.URL_UPLOAD);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setUseCaches(false);
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setChunkedStreamingMode(0);
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                        httpURLConnection.setRequestProperty("Cache-Control", "no-cache");
                        httpURLConnection.setRequestProperty(
                                "Content-Type", "multipart/form-data;boundary=" + "*****");

                        String json = gson.toJson(mojRestoran);
                        DataOutputStream outputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                        outputStream.writeBytes(json);
                        outputStream.flush();
                        outputStream.close();
                        httpURLConnection.disconnect();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

    }

}
