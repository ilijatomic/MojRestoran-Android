package com.ilija.mojrestoran.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import com.ilija.mojrestoran.AppObject;
import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.model.NaruceneStavke;
import com.ilija.mojrestoran.model.Narudzbina;
import com.ilija.mojrestoran.model.Stavka;
import com.ilija.mojrestoran.ui.adapter.KonobarStavkeListAdapter;
import com.ilija.mojrestoran.ui.dialog.DataChangeListener;
import com.ilija.mojrestoran.ui.dialog.NaplatiDialog;
import com.ilija.mojrestoran.util.Constants;
import com.ilija.mojrestoran.util.Utilities;

import java.util.ArrayList;
import java.util.Map;

public class KonobarNarudzbinaActivity extends BaseActivity implements DataChangeListener, View.OnClickListener {

    private AutoCompleteTextView autoCompleteTextView;
    private ArrayList<NaruceneStavke> listStavke;
    private KonobarStavkeListAdapter konobarStavkeListAdapter;
    private Narudzbina narudzbina;
    private ListView stavke;
    private Button naplati;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konobar_narudzbina);

        Intent intent = getIntent();
        final String narudzbinId = intent.getStringExtra(Constants.EXTRA_NARUDZBINA_ID);
        if (narudzbinId != null) {
            narudzbina = AppObject.getAppInstance().getGetNenaplacenaById(narudzbinId);
            listStavke = narudzbina.getNaruceneStavkeArrayList();
        }

        addToolbar(true);
        getSupportActionBar().setTitle("Sto broj " + narudzbina.getSto().getBroj());

        ArrayList<String> strings = new ArrayList<>();
        Utilities.populateSpinnerStrings(strings, AppObject.getAppInstance().getMojRestoran().getStavkaArrayList(), "");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, strings);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.actv_stavka);
        autoCompleteTextView.setAdapter(arrayAdapter);

        konobarStavkeListAdapter = new KonobarStavkeListAdapter(this, R.layout.list_item_konobar_narudzbina_detalji, listStavke,  this);
        stavke = (ListView) findViewById(R.id.lv_narudzbina_stavka);
        stavke.setAdapter(konobarStavkeListAdapter);

        naplati = (Button) findViewById(R.id.naplati);
        naplati.setOnClickListener(this);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updateNarudzbina();
            }
        });

    }

    // TODO ako vec postoji ta stavka samo ++ kolicina a ne nova stavka
    private void updateNarudzbina() {
        NaruceneStavke naruceneStavke = new NaruceneStavke(AppObject.getAppInstance().getstavkaByName(autoCompleteTextView.getText().toString()), 1);
        listStavke.add(naruceneStavke);
        AppObject.getAppInstance().updateRestoranBase();
        konobarStavkeListAdapter.notifyDataSetChanged();
        autoCompleteTextView.setText("");
    }

    @Override
    public void onDataChanged() {
        konobarStavkeListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        NaplatiDialog naplatiDialog = new NaplatiDialog();
        naplatiDialog.setNaplatiDialog(narudzbina, this);
        naplatiDialog.show(getFragmentManager(), "NaplatiDialog");
    }
}
