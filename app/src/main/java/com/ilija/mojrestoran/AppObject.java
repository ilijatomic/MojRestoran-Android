package com.ilija.mojrestoran;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.gson.Gson;
import com.ilija.mojrestoran.event.DatabaseDownloaded;
import com.ilija.mojrestoran.model.Kategorija;
import com.ilija.mojrestoran.model.Korisnik;
import com.ilija.mojrestoran.model.MojRestoran;
import com.ilija.mojrestoran.model.Narudzbina;
import com.ilija.mojrestoran.model.Podkategorija;
import com.ilija.mojrestoran.model.Stavka;
import com.ilija.mojrestoran.model.Sto;
import com.ilija.mojrestoran.util.Constants;

import org.greenrobot.eventbus.EventBus;

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

    Firebase mRef;
    private MojRestoran mojRestoran;
    private Korisnik ulogovanKorisnik;

    @Override
    public void onCreate() {
        super.onCreate();
        if (appObject == null)
            appObject = (AppObject) getApplicationContext();
        Firebase.setAndroidContext(appObject.getApplicationContext());

        getFirebase();
    }

    private void getFirebase() {
        mRef = new Firebase("https://project-3585542348729097062.firebaseio.com/");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mojRestoran = dataSnapshot.getValue(MojRestoran.class);
                EventBus.getDefault().postSticky(new DatabaseDownloaded());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
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

    public void updateRestoranBase() {

        mRef.setValue(mojRestoran);

        /*new AsyncTask<Void, Void, Void>() {
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
        }.execute();*/

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

    public void populateStavkeOfPodkategorija(Podkategorija podkategorija, ArrayList<String> stavkes) {
        stavkes.clear();
        stavkes.add("stavka");
        for (Stavka stavka : AppObject.getAppInstance().getMojRestoran().getStavkaArrayList())
            if (stavka.getPodkategorija().getId().equals(podkategorija.getId()))
                stavkes.add(stavka.getNaziv());
    }

    public void populateStavkeOfKategorija(Kategorija kategorija, ArrayList<String> stavkes) {
        stavkes.clear();
        stavkes.add("stavka");
        for (Stavka stavka : AppObject.getAppInstance().getMojRestoran().getStavkaArrayList())
            if (stavka.getPodkategorija().getKategorija().getId().equals(kategorija.getId()))
                stavkes.add(stavka.getNaziv());
    }

    public boolean checkIfLoggedUser(String id) {
        return ulogovanKorisnik.getId().equals(id);
    }

    public ArrayList<Sto> getSlobodniStolovi() {

        ArrayList<Sto> slobodni = new ArrayList<>();

        if (mojRestoran.getNarudzbinaArrayList().size() > 0) {
            for (Sto sto : mojRestoran.getStoArrayList()) {
                boolean slobodan = true;
                for (Narudzbina narudzbina : mojRestoran.getNarudzbinaArrayList()) {
                    if (sto.getId().equals(narudzbina.getSto().getId()) && !narudzbina.isNaplacena()) {
                        slobodan = false;
                        break;
                    }
                }
                if (slobodan)
                    slobodni.add(sto);
            }
        } else {
            for (Sto sto : mojRestoran.getStoArrayList())
                slobodni.add(sto);
        }
        return slobodni;
    }

    public Stavka getstavkaByName(String naziv) {
        for (Stavka stavka : mojRestoran.getStavkaArrayList())
            if (stavka.getNaziv().equals(naziv))
                return stavka;
        return null;
    }

    public boolean checkIfOtherUserExist(String email, String id) {
        for (Korisnik korisnik : getMojRestoran().getKorisnikArrayList())
            if (korisnik.getEmail().equals(email) && !korisnik.getId().equals(id))
                return true;
        return false;
    }

    public Narudzbina getNarudzbinaById(String id) {
        for (Narudzbina narudzbina : getMojRestoran().getNarudzbinaArrayList())
            if (narudzbina.getId().equals(id))
                return narudzbina;
        return null;
    }

}
