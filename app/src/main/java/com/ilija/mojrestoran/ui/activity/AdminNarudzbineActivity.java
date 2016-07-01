package com.ilija.mojrestoran.ui.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ilija.mojrestoran.AppObject;
import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.model.Kategorija;
import com.ilija.mojrestoran.model.NaruceneStavke;
import com.ilija.mojrestoran.model.Narudzbina;
import com.ilija.mojrestoran.model.Podkategorija;
import com.ilija.mojrestoran.model.Racun;
import com.ilija.mojrestoran.model.Stavka;
import com.ilija.mojrestoran.model.Sto;
import com.ilija.mojrestoran.ui.adapter.NarudzbinaListAdapter;
import com.ilija.mojrestoran.util.Fx;
import com.ilija.mojrestoran.util.Utilities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AdminNarudzbineActivity extends BaseActivity implements View.OnClickListener {

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

    private EditText ime;
    private EditText prezime;
    private EditText email;
    private EditText etDateFrom;
    private EditText etDateTo;
    private Date dateFrom;
    private Date dateTo;

    private Spinner spKategorija;
    private Kategorija selKategorija;
    private ArrayAdapter<String> kategorijaAdapter;
    private ArrayList<String> kategorijaStrings = new ArrayList<>();

    private Spinner spPodkategorija;
    private Podkategorija selPodkategorija;
    private ArrayAdapter<String> podkategorijaAdapter;
    private ArrayList<String> podkategorijaStrings = new ArrayList<>();

    private Spinner spStavka;
    private Stavka selStavka;
    private ArrayAdapter<String> stavkaAdapter;
    private ArrayList<String> stavkaStrings = new ArrayList<>();

    private Spinner spSto;
    private Sto selSto;
    private ArrayAdapter<String> stoAdapter;
    private ArrayList<String> stoStrings = new ArrayList<>();

    private TextView toggleSearch;
    private ImageView ivToggleSearch;
    private LinearLayout llSearch;
    private Button ponisti;
    private Button pretraga;

    private ListView lvNarudzbine;
    private ArrayList<Narudzbina> listNarudzbina = new ArrayList<>();
    private NarudzbinaListAdapter narudzbinaListAdapter;

    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_narudzbine);

        addToolbar(true);

        toggleSearch = (TextView) findViewById(R.id.tv_toggle_search);
        toggleSearch.setOnClickListener(this);
        ivToggleSearch = (ImageView) findViewById(R.id.img_toogle_search);
        ivToggleSearch.setOnClickListener(this);
        llSearch = (LinearLayout) findViewById(R.id.ll_search);
        llSearch.setVisibility(View.GONE);

        ime = (EditText) findViewById(R.id.nar_search_ime);
        prezime = (EditText) findViewById(R.id.nar_search_prezime);
        email = (EditText) findViewById(R.id.nar_search_email);

        spKategorija = (Spinner) findViewById(R.id.nar_search_kategorija);
        Utilities.populateSpinnerStrings(kategorijaStrings, AppObject.getAppInstance().getMojRestoran().getKategorijaArrayList(), "kategorija");
        kategorijaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, kategorijaStrings);
        kategorijaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKategorija.setAdapter(kategorijaAdapter);
        spKategorija.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selKategorija = null;
                    Utilities.populateSpinnerStrings(podkategorijaStrings, AppObject.getAppInstance().getMojRestoran().getPodkategorijaArrayList(), "podkategorija");
                    podkategorijaAdapter.notifyDataSetChanged();
                    Utilities.populateSpinnerStrings(stavkaStrings, AppObject.getAppInstance().getMojRestoran().getStavkaArrayList(), "stavka");
                    stavkaAdapter.notifyDataSetChanged();
                } else {
                    for (Kategorija kategorija : AppObject.getAppInstance().getMojRestoran().getKategorijaArrayList())
                        if (kategorija.getNaziv().equals(kategorijaAdapter.getItem(position))) {
                            selKategorija = kategorija;
                            AppObject.getAppInstance().populatePodkategorijeOfKategorija(selKategorija, podkategorijaStrings);
                            podkategorijaAdapter.notifyDataSetChanged();
                            AppObject.getAppInstance().populateStavkeOfKategorija(selKategorija, stavkaStrings);
                            stavkaAdapter.notifyDataSetChanged();
                        }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spPodkategorija = (Spinner) findViewById(R.id.nar_search_podkategorija);
        Utilities.populateSpinnerStrings(podkategorijaStrings, AppObject.getAppInstance().getMojRestoran().getPodkategorijaArrayList(), "podkategorija");
        podkategorijaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, podkategorijaStrings);
        podkategorijaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPodkategorija.setAdapter(podkategorijaAdapter);
        spPodkategorija.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selPodkategorija = null;
                    Utilities.populateSpinnerStrings(podkategorijaStrings, AppObject.getAppInstance().getMojRestoran().getPodkategorijaArrayList(), "podkategorija");
                    podkategorijaAdapter.notifyDataSetChanged();
                    Utilities.populateSpinnerStrings(kategorijaStrings, AppObject.getAppInstance().getMojRestoran().getKategorijaArrayList(), "kategorija");
                    kategorijaAdapter.notifyDataSetChanged();
                    Utilities.populateSpinnerStrings(stavkaStrings, AppObject.getAppInstance().getMojRestoran().getStavkaArrayList(), "stavka");
                    stavkaAdapter.notifyDataSetChanged();
                } else {
                    for (Podkategorija podkategorija : AppObject.getAppInstance().getMojRestoran().getPodkategorijaArrayList())
                        if (podkategorija.getNaziv().equals(podkategorijaAdapter.getItem(position))) {
                            selPodkategorija = podkategorija;
                            spKategorija.setSelection(kategorijaAdapter.getPosition(selPodkategorija.getKategorija().getNaziv()));
                            AppObject.getAppInstance().populateStavkeOfPodkategorija(selPodkategorija, stavkaStrings);
                            stavkaAdapter.notifyDataSetChanged();
                        }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spStavka = (Spinner) findViewById(R.id.nar_search_stavka);
        Utilities.populateSpinnerStrings(stavkaStrings, AppObject.getAppInstance().getMojRestoran().getStavkaArrayList(), "stavka");
        stavkaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stavkaStrings);
        stavkaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStavka.setAdapter(stavkaAdapter);
        spStavka.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selStavka = null;
                    Utilities.populateSpinnerStrings(podkategorijaStrings, AppObject.getAppInstance().getMojRestoran().getPodkategorijaArrayList(), "podkategorija");
                    podkategorijaAdapter.notifyDataSetChanged();
                    Utilities.populateSpinnerStrings(kategorijaStrings, AppObject.getAppInstance().getMojRestoran().getKategorijaArrayList(), "kategorija");
                    kategorijaAdapter.notifyDataSetChanged();
                    Utilities.populateSpinnerStrings(stavkaStrings, AppObject.getAppInstance().getMojRestoran().getStavkaArrayList(), "stavka");
                    stavkaAdapter.notifyDataSetChanged();
                } else {
                    for (Stavka stavka : AppObject.getAppInstance().getMojRestoran().getStavkaArrayList())
                        if (stavka.getNaziv().equals(stavkaAdapter.getItem(position))) {
                            selStavka = stavka;
                            spPodkategorija.setSelection(podkategorijaAdapter.getPosition(selStavka.getPodkategorija().getNaziv()));
                            spKategorija.setSelection(kategorijaAdapter.getPosition(selStavka.getPodkategorija().getKategorija().getNaziv()));
                        }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spSto = (Spinner) findViewById(R.id.nar_search_sto);
        Utilities.populateSpinnerStrings(stoStrings, AppObject.getAppInstance().getMojRestoran().getStoArrayList(), "sto");
        stoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stoStrings);
        stoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSto.setAdapter(stoAdapter);
        spSto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selSto = null;
                } else {
                    for (Sto sto : AppObject.getAppInstance().getMojRestoran().getStoArrayList())
                        if (sto.getBroj() == Integer.parseInt(stoAdapter.getItem(position))) {
                            selSto = sto;
                        }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        etDateFrom = (EditText) findViewById(R.id.nar_search_date_from);
        etDateFrom.setInputType(InputType.TYPE_NULL);
        etDateFrom.setOnClickListener(this);
        etDateFrom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    getDate("from", dateFrom);
            }
        });
        etDateTo = (EditText) findViewById(R.id.nar_search_date_to);
        etDateTo.setInputType(InputType.TYPE_NULL);
        etDateTo.setOnClickListener(this);
        etDateTo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    getDate("to", dateTo);
            }
        });

        ponisti = (Button) findViewById(R.id.nar_search_ponisti);
        ponisti.setOnClickListener(this);
        pretraga = (Button) findViewById(R.id.nar_search_pretraga);
        pretraga.setOnClickListener(this);

        narudzbinaListAdapter = new NarudzbinaListAdapter(this, R.layout.list_item_admin_narudzbina, listNarudzbina);
        lvNarudzbine = (ListView) findViewById(R.id.nar_narudzbine);
        lvNarudzbine.setAdapter(narudzbinaListAdapter);

        toggle_contents();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_toggle_search:
            case R.id.img_toogle_search:
                toggle_contents();
                break;
            case R.id.nar_search_date_from:
                getDate("from", dateFrom);
                break;
            case R.id.nar_search_date_to:
                getDate("to", dateTo);
                break;
            case R.id.nar_search_ponisti:
                clearSearch();
                break;
            case R.id.nar_search_pretraga:
                search();
                break;
        }

    }

    private void getDate(final String dateString, Date date) {

        if (date == null)
            date = new Date();

        final Calendar finalDate = Calendar.getInstance();
        finalDate.setTime(date);
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                finalDate.set(year, monthOfYear, dayOfMonth);
                setDate(dateString, finalDate);
            }
        }, finalDate.get(Calendar.YEAR), finalDate.get(Calendar.MONTH), finalDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void setDate(String dateString, Calendar finalDate) {

        switch (dateString) {
            case "from":
                etDateFrom.setText(dateFormatter.format(finalDate.getTime()));
                llSearch.requestFocus();
                dateFrom = finalDate.getTime();
                break;
            case "to":
                etDateTo.setText(dateFormatter.format(finalDate.getTime()));
                llSearch.requestFocus();
                dateTo = finalDate.getTime();
                break;
        }
    }

    public void toggle_contents() {

        if (llSearch.isShown()) {
            Fx.slide_up(this, llSearch);
            llSearch.setVisibility(View.GONE);
            ime.setVisibility(View.GONE);
            prezime.setVisibility(View.GONE);
            email.setVisibility(View.GONE);
            spKategorija.setVisibility(View.GONE);
            spPodkategorija.setVisibility(View.GONE);
            spStavka.setVisibility(View.GONE);
            spSto.setVisibility(View.GONE);
            etDateFrom.setVisibility(View.GONE);
            etDateTo.setVisibility(View.GONE);
            ponisti.setVisibility(View.GONE);
            pretraga.setVisibility(View.GONE);
            ivToggleSearch.setRotation(0);
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } else {
            llSearch.setVisibility(View.VISIBLE);
            ime.setVisibility(View.VISIBLE);
            prezime.setVisibility(View.VISIBLE);
            email.setVisibility(View.VISIBLE);
            spKategorija.setVisibility(View.VISIBLE);
            spPodkategorija.setVisibility(View.VISIBLE);
            spStavka.setVisibility(View.VISIBLE);
            spSto.setVisibility(View.VISIBLE);
            etDateFrom.setVisibility(View.VISIBLE);
            etDateTo.setVisibility(View.VISIBLE);
            ponisti.setVisibility(View.VISIBLE);
            pretraga.setVisibility(View.VISIBLE);
            Fx.slide_down(this, llSearch);
            ivToggleSearch.setRotation(180);
        }
    }

    private void clearSearch() {

        ime.setText("");
        prezime.setText("");
        email.setText("");
        spKategorija.setSelection(0);
        spPodkategorija.setSelection(0);
        spStavka.setSelection(0);
        spSto.setSelection(0);
        etDateFrom.setText("");
        dateFrom = null;
        etDateTo.setText("");
        dateTo = null;
        listNarudzbina.clear();
        narudzbinaListAdapter.notifyDataSetChanged();
    }

    private void search() {

        String imeSearch = ime.getText().toString();
        String prezimeSearch = prezime.getText().toString();
        String emailSearch = email.getText().toString();

        listNarudzbina.clear();
        for (Narudzbina narudzbina : AppObject.getAppInstance().getMojRestoran().getNarudzbinaArrayList()) {
            if (!imeSearch.isEmpty() && !narudzbina.getKorisnik().getIme().startsWith(imeSearch))
                continue;
            if (!prezimeSearch.isEmpty() && !narudzbina.getKorisnik().getPrezime().startsWith(prezimeSearch))
                continue;
            if (!emailSearch.isEmpty() && !narudzbina.getKorisnik().getEmail().startsWith(emailSearch))
                continue;
            if (selKategorija != null && !checkNarudzbinaKategorija(narudzbina))
                continue;
            if (selPodkategorija != null && !checkNarudzbinaPodkategorija(narudzbina))
                continue;
            if (selStavka != null && !checkNarudzbinaStavka(narudzbina))
                continue;
            if (selSto != null && !narudzbina.getSto().getId().equals(selSto.getId()))
                continue;
            if (dateFrom != null && !new Date(narudzbina.getDatum()).after(dateFrom))
                continue;
            if (dateTo != null && !new Date(narudzbina.getDatum()).before(dateTo))
                continue;

            listNarudzbina.add(narudzbina);
        }
        narudzbinaListAdapter.notifyDataSetChanged();
        toggle_contents();
    }

    private boolean checkNarudzbinaKategorija(Narudzbina narudzbina) {
        for (Racun racun : narudzbina.getRacunArrayList())
            for (NaruceneStavke naruceneStavke : racun.getNaplaceneStavke())
                if (naruceneStavke.getStavka().getPodkategorija().getKategorija().getId().equals(selKategorija.getId()))
                    return true;
        return false;
    }

    private boolean checkNarudzbinaPodkategorija(Narudzbina narudzbina) {
        for (Racun racun : narudzbina.getRacunArrayList())
            for (NaruceneStavke naruceneStavke : racun.getNaplaceneStavke())
                if (naruceneStavke.getStavka().getPodkategorija().getId().equals(selPodkategorija.getId()))
                    return true;
        return false;
    }

    private boolean checkNarudzbinaStavka(Narudzbina narudzbina) {
        for (Racun racun : narudzbina.getRacunArrayList())
            for (NaruceneStavke naruceneStavke : racun.getNaplaceneStavke())
                if (naruceneStavke.getStavka().getId().equals(selStavka.getId()))
                    return true;
        return false;
    }

}
