package com.ilija.mojrestoran;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.ilija.mojrestoran.model.Kategorija;
import com.ilija.mojrestoran.model.Korisnik;
import com.ilija.mojrestoran.model.MojRestoran;
import com.ilija.mojrestoran.model.Podkategorija;
import com.ilija.mojrestoran.model.Stavka;
import com.ilija.mojrestoran.model.Sto;
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
import java.util.ArrayList;

/**
 * Created by ilija.tomic on 1/4/2016.
 */
public class AppObject extends Application {

    private static final String TAG = AppObject.class.getSimpleName();

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

    }

    public Korisnik getKorisnikById(String idKorisnik) {
        for (Korisnik korisnik : mojRestoran.getKorisnikArrayList())
            if (korisnik.getId().equals(idKorisnik))
                return korisnik;
        return null;
    }

    public Sto getStoById(String idSto) {
        for (Sto sto : mojRestoran.getStoArrayList())
            if (sto.getId().equals(idSto))
                return sto;
        return null;
    }

    public Kategorija getKategorijaById(String idKategorija) {
        for (Kategorija kategorija : mojRestoran.getKategorijaArrayList())
            if (kategorija.getId().equals(idKategorija))
                return kategorija;
        return null;
    }

    public Podkategorija getPodkategorijaById(String idPodkategorija) {
        for (Podkategorija podkategorija : mojRestoran.getPodkategorijaArrayList())
            if (podkategorija.getId().equals(idPodkategorija))
                return podkategorija;
        return null;
    }

    public Stavka getStavkaById(String idStavka) {
        for (Stavka stavka : mojRestoran.getStavkaArrayList())
            if (stavka.getId().equals(idStavka))
                return stavka;
        return null;
    }

    public boolean checkIfUserExists(String email) {
        for (Korisnik korisnik : mojRestoran.getKorisnikArrayList())
            if (korisnik.getEmail().equals(email))
                return true;
        return false;
    }

    public boolean checkIfStoExists(String broj) {
        for (Sto sto : mojRestoran.getStoArrayList())
            if (sto.getBroj() == Integer.parseInt(broj))
                return true;
        return false;
    }

    public boolean checkIfKategorijaExists(String naziv) {
        for (Kategorija kategorija : mojRestoran.getKategorijaArrayList())
            if (kategorija.getNaziv().equals(naziv))
                return true;
        return false;
    }

    public boolean checkIfPodkategorijaExists(String naziv) {
        for (Podkategorija podkategorija : mojRestoran.getPodkategorijaArrayList())
            if (podkategorija.getNaziv().equals(naziv))
                return true;
        return false;
    }

    public boolean checkIfStavkaExists(String naziv) {
        for (Stavka stavka : mojRestoran.getStavkaArrayList())
            if (stavka.getNaziv().equals(naziv))
                return true;
        return false;
    }

    public void populatePodkategorijeOfKategorija(Kategorija kategorija, ArrayList<String> podkategorijas) {
        podkategorijas.clear();
        podkategorijas.add("podkategorija");
        for (Podkategorija podkategorija : AppObject.getAppInstance().getMojRestoran().getPodkategorijaArrayList())
            if (podkategorija.getKategorija().getId().equals(kategorija.getId()))
                podkategorijas.add(podkategorija.getNaziv());
    }

    public boolean checkIfLoggedUser(String id) {
        return ulogovanKorisnik.getId().equals(id);
    }

    /*public void updateRestoranBase() {

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
                        httpURLConnection.setRequestProperty("Cache-Control", "no-cache");
                        httpURLConnection.setRequestProperty("Accept", "application/json");
                        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

                        String json = gson.toJson(mojRestoran);
                        String payload = "{\"jsonrpc\":\"2.0\",\"method\":\"changeDetail\",\"params\":[{\"id\":11376}],\"id\":2}";
                        DataOutputStream outputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                        outputStream.writeBytes(payload);
                        outputStream.writeBytes(json);
                        outputStream.flush();
                        outputStream.close();
                        Log.i(TAG, httpURLConnection.getResponseMessage() + httpURLConnection.getResponseCode());
                        httpURLConnection.disconnect();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

    }*/

}
