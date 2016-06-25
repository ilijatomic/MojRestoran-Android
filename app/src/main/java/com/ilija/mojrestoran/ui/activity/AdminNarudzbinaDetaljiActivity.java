package com.ilija.mojrestoran.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.ilija.mojrestoran.AppObject;
import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.model.NaruceneStavke;
import com.ilija.mojrestoran.model.Narudzbina;
import com.ilija.mojrestoran.model.Racun;
import com.ilija.mojrestoran.ui.adapter.RacunListAdapter;
import com.ilija.mojrestoran.util.Constants;
import com.ilija.mojrestoran.util.NarudzbinaDetalji;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdminNarudzbinaDetaljiActivity extends BaseActivity {

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH);

    private TextView datum;
    private TextView ime;
    private TextView email;
    private TextView ukupno;

    private Narudzbina narudzbina;
    private ListView racuni;
    private ArrayList<Racun> listRacun = new ArrayList<>();
    private ArrayList<NarudzbinaDetalji> listDetalji = new ArrayList<>();

    private RacunListAdapter racunListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_narudzbina_detalji);

        addToolbar(true);

        Intent intent = getIntent();
        final String narudzbinId = intent.getStringExtra(Constants.EXTRA_NARUDZBINA_ID);
        if (narudzbinId != null) {
            narudzbina = AppObject.getAppInstance().getNarudzbinaById(narudzbinId);
            listRacun = narudzbina.getRacunArrayList();
        }

        datum = (TextView) findViewById(R.id.detalji_datum);
        datum.setText("Broj stola: " + narudzbina.getSto().getBroj() + "; Vreme: " + dateFormatter.format(narudzbina.getDatum()));
        ime = (TextView) findViewById(R.id.detalji_ime);
        ime.setText(narudzbina.getKorisnik().getIme() + " " + narudzbina.getKorisnik().getPrezime());
        email = (TextView) findViewById(R.id.detalji_email);
        email.setText(narudzbina.getKorisnik().getEmail());
        ukupno = (TextView) findViewById(R.id.detalji_ukupno);
        ukupno.setText("Ukupno: " + izracunajUkupnuCenuRacuna(listRacun) + " din");

        popuniDetalje();

        racuni = (ListView) findViewById(R.id.detalji_racun);
        racunListAdapter = new RacunListAdapter(this, R.layout.list_item_admin_racun, listDetalji);
        racuni.setAdapter(racunListAdapter);
    }

    private void popuniDetalje() {
        for (Racun racun : listRacun) {
            listDetalji.add(new NarudzbinaDetalji(NarudzbinaDetalji.DetaljiType.HEADER, izracunajUkupnuCenuStavki(racun.getNaplaceneStavke()), 0, null, racun.getDatum()));
            for (NaruceneStavke naruceneStavke : racun.getNaplaceneStavke())
                listDetalji.add(new NarudzbinaDetalji(NarudzbinaDetalji.DetaljiType.STAVKE, 0, naruceneStavke.getKolicina(), naruceneStavke.getStavka(), null));
        }
    }

    private double izracunajUkupnuCenuRacuna(ArrayList<Racun> racuns) {

        double ukupno = 0;
        for (Racun racun : racuns)
            for (NaruceneStavke naruceneStavke : racun.getNaplaceneStavke())
                ukupno += naruceneStavke.getStavka().getCena() * naruceneStavke.getKolicina();

        return ukupno;
    }

    private double izracunajUkupnuCenuStavki(ArrayList<NaruceneStavke> stavke) {

        double ukupno = 0;
        for (NaruceneStavke naruceneStavke : stavke)
            ukupno += naruceneStavke.getStavka().getCena() * naruceneStavke.getKolicina();

        return ukupno;
    }
}
