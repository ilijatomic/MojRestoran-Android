package com.ilija.mojrestoran.ui.activity;

import android.app.DialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.ilija.mojrestoran.AppObject;
import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.model.Korisnik;
import com.ilija.mojrestoran.ui.adapter.KorisniciListAdapter;
import com.ilija.mojrestoran.ui.dialog.AddEditDialog;
import com.ilija.mojrestoran.ui.dialog.DataChangeListener;
import com.ilija.mojrestoran.ui.dialog.DialogDataType;

import java.util.ArrayList;

public class AdminKorisniciActivity extends BaseActivity implements DataChangeListener, View.OnClickListener {

    private EditText etIme;
    private EditText etPrezime;
    private EditText etEmail;
    private String tip;
    private Spinner spTip;
    private ImageButton btnSearch;
    private FloatingActionButton fabAdd;

    private ListView korisnici;
    private KorisniciListAdapter korisniciListAdapter;
    private ArrayList<Korisnik> listKorisnik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_korisnici);

        addToolbar(true);

        etIme = (EditText) findViewById(R.id.et_search_ime);
        etPrezime = (EditText) findViewById(R.id.et_search_prezime);
        etEmail = (EditText) findViewById(R.id.et_search_email);
        spTip = (Spinner) findViewById(R.id.et_search_tip);
        spTip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tip = parent.getItemAtPosition(position).toString();
                if (tip.equals("tip"))
                    tip = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tip = null;
            }
        });
        btnSearch = (ImageButton) findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
        btnSearch.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clearSearch();
                return true;
            }
        });

        fabAdd = (FloatingActionButton) findViewById(R.id.add);
        fabAdd.setOnClickListener(this);

        listKorisnik = new ArrayList<>(AppObject.getAppInstance().getMojRestoran().getKorisnikArrayList());

        korisniciListAdapter = new KorisniciListAdapter(this, R.layout.list_item_admin_korisnici, listKorisnik, this);
        korisnici = (ListView) findViewById(R.id.lv_korisnici);
        korisnici.setAdapter(korisniciListAdapter);

    }

    private void clearSearch() {

        etIme.setText("");
        etPrezime.setText("");
        etEmail.setText("");
        tip = "";
        spTip.setSelection(0);

        onDataChanged();

    }

    private void search() {

        String imeSearch = etIme.getText().toString();
        String prezimeSearch = etPrezime.getText().toString();
        String emailSearch = etEmail.getText().toString();

        listKorisnik.clear();
        for (Korisnik korisnik : AppObject.getAppInstance().getMojRestoran().getKorisnikArrayList()) {
            if (!imeSearch.isEmpty() || !prezimeSearch.isEmpty() || !emailSearch.isEmpty() || !tip.isEmpty()) {
                if (!korisnik.getIme().startsWith(imeSearch) && !imeSearch.isEmpty())
                    continue;
                if (!korisnik.getPrezime().startsWith(prezimeSearch) && !prezimeSearch.isEmpty())
                    continue;
                if (!korisnik.getEmail().startsWith(emailSearch) && !emailSearch.isEmpty())
                    continue;
                if (!korisnik.getTip().startsWith(tip) && !tip.isEmpty())
                    continue;

                listKorisnik.add(korisnik);
            } else {
                listKorisnik.add(korisnik);
            }
        }
        korisniciListAdapter.notifyDataSetChanged();

    }


    @Override
    public void onDataChanged() {
        listKorisnik.clear();

        for (Korisnik korisnik : AppObject.getAppInstance().getMojRestoran().getKorisnikArrayList())
            listKorisnik.add(korisnik);
        korisniciListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        DialogFragment addKorinik = new AddEditDialog(null, this, DialogDataType.KORISNIK);
        addKorinik.show(getFragmentManager(), "AddKorisnik");
    }

}
