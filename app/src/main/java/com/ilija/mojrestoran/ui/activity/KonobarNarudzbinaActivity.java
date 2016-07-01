package com.ilija.mojrestoran.ui.activity;

import android.content.Intent;
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
import com.ilija.mojrestoran.ui.adapter.KonobarStavkeListAdapter;
import com.ilija.mojrestoran.ui.dialog.DataChangeListener;
import com.ilija.mojrestoran.ui.dialog.NaplatiDialog;
import com.ilija.mojrestoran.ui.dialog.RacunChangeListener;
import com.ilija.mojrestoran.util.Constants;
import com.ilija.mojrestoran.util.ToastMessage;
import com.ilija.mojrestoran.util.Utilities;

import java.util.ArrayList;
import java.util.Iterator;

public class KonobarNarudzbinaActivity extends BaseActivity implements DataChangeListener, View.OnClickListener, RacunChangeListener {

    private AutoCompleteTextView autoCompleteTextView;
    private KonobarStavkeListAdapter konobarStavkeListAdapter;
    private Narudzbina narudzbina;
    private ArrayList<NaruceneStavke> naruceneStavke;
    private ListView stavke;
    private Button naplati;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konobar_narudzbina);

        Intent intent = getIntent();
        final String narudzbinId = intent.getStringExtra(Constants.EXTRA_NARUDZBINA_ID);
        if (narudzbinId != null) {
            narudzbina = AppObject.getAppInstance().getNarudzbinaById(narudzbinId);
            naruceneStavke = new ArrayList<>(narudzbina.getNenaplaceneStavke());
        }

        addToolbar(true);
        getSupportActionBar().setTitle("Sto broj " + narudzbina.getSto().getBroj());

        ArrayList<String> strings = new ArrayList<>();
        Utilities.populateSpinnerStrings(strings, AppObject.getAppInstance().getMojRestoran().getStavkaArrayList(), "");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, strings);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.actv_stavka);
        autoCompleteTextView.setAdapter(arrayAdapter);

        konobarStavkeListAdapter = new KonobarStavkeListAdapter(this, R.layout.list_item_konobar_narudzbina_detalji, naruceneStavke, this, narudzbinId, false);
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
        this.naruceneStavke.add(naruceneStavke);
        konobarStavkeListAdapter.notifyDataSetChanged();
        autoCompleteTextView.setText("");
        AppObject.getAppInstance().updateNarudzbina(narudzbina.getId(), this.naruceneStavke);
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

    @Override
    public void naplaceno(boolean celaNarudzbina) {
        if (celaNarudzbina) {
            ToastMessage.showToast(this, "Narudzbina kompletno naplacena!");
            finish();
        } else {
            ToastMessage.showToast(this, "Naplacen deo racuna narudzbine!");
            narudzbina = AppObject.getAppInstance().getNarudzbinaById(narudzbina.getId());
            for (Iterator<NaruceneStavke> iterator = narudzbina.getNenaplaceneStavke().iterator(); iterator.hasNext(); ) {
                NaruceneStavke temp = iterator.next();
                if (temp.getKolicina() == 0)
                    iterator.remove();
            }
            naruceneStavke.clear();
            naruceneStavke.addAll(narudzbina.getNenaplaceneStavke());
            konobarStavkeListAdapter.notifyDataSetChanged();
            AppObject.getAppInstance().updateNarudzbina(narudzbina.getId(), naruceneStavke);
        }
    }
}
