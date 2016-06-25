package com.ilija.mojrestoran.ui.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ilija.mojrestoran.AppObject;
import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.model.Podkategorija;
import com.ilija.mojrestoran.model.Stavka;
import com.ilija.mojrestoran.util.ToastMessage;
import com.ilija.mojrestoran.util.Utilities;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by ilija.tomic on 1/27/2016.
 */
public class StavkaDialogView implements DialogView {
    private String idStavka;

    private Spinner spPodkategorija;
    private EditText etNaziv;
    private EditText etCena;
    private TextView tvKategorija;

    private Podkategorija selectedPodkategorija;
    private Stavka selectedStavka;
    private Context context;

    private ArrayList<EditText> lista = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

    public StavkaDialogView(Context context, String idStavka, View view, Podkategorija podkategorija) {
        this.context = context;
        this.idStavka = idStavka;

        etNaziv = (EditText) view.findViewById(R.id.add_naziv_stavke);
        etCena = (EditText) view.findViewById(R.id.add_stavka_cena);
        tvKategorija = (TextView) view.findViewById(R.id.add_tv_kategorija);

        if (idStavka != null) {
            selectedStavka = AppObject.getAppInstance().getStavkaById(idStavka);
            selectedPodkategorija = selectedStavka.getPodkategorija();
        }

        spPodkategorija = (Spinner) view.findViewById(R.id.add_podkategorija);
        ArrayList<String> strings = new ArrayList<>();
        Utilities.populateSpinnerStrings(strings, AppObject.getAppInstance().getMojRestoran().getPodkategorijaArrayList(), "podkategorija");
        arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, strings);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPodkategorija.setAdapter(arrayAdapter);
        spPodkategorija.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selectedPodkategorija = null;
                    tvKategorija.setText("kategorija");
                    return;
                }
                for (Podkategorija podkategorija : AppObject.getAppInstance().getMojRestoran().getPodkategorijaArrayList())
                    if (podkategorija.getNaziv().equals(arrayAdapter.getItem(position))) {
                        selectedPodkategorija = podkategorija;
                        tvKategorija.setText(podkategorija.getKategorija().getNaziv());
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (podkategorija != null) {
            spPodkategorija.setSelection(arrayAdapter.getPosition(podkategorija.getNaziv()));
        }
    }

    @Override
    public void setData() {
        if (selectedStavka != null) {
            etNaziv.setText(selectedStavka.getNaziv());
            etCena.setText(String.valueOf(selectedStavka.getCena()));
            tvKategorija.setText(selectedStavka.getPodkategorija().getKategorija().getNaziv());
            spPodkategorija.setSelection(arrayAdapter.getPosition(selectedStavka.getPodkategorija().getNaziv()));
        }
    }

    @Override
    public boolean saveData() {

        lista.clear();
        lista.add(etNaziv);
        lista.add(etCena);
        if (!Utilities.checkRequiredFields(lista))
            return false;

        if (selectedPodkategorija == null) {
            ToastMessage.showToast(context, "Selektujte podkategoriju!");
            return false;
        }

        if (selectedPodkategorija != null) {
            if (AppObject.getAppInstance().checkIfStavkaExists(etNaziv.getText().toString()) && selectedPodkategorija.getId().equals(selectedStavka.getPodkategorija().getId())) {
                ToastMessage.showToast(context, "Stavka sa unetim nazivom vec postoji!");
                return false;
            }
        } else if (AppObject.getAppInstance().checkIfPodkategorijaExists(etNaziv.getText().toString())) {
            ToastMessage.showToast(context, "Stavka sa unetim nazivom vec postoji!");
            return false;
        }

        if (selectedStavka == null) {
            idStavka = UUID.randomUUID().toString();
            selectedStavka = new Stavka(idStavka, etNaziv.getText().toString(), Double.parseDouble(etCena.getText().toString()), selectedPodkategorija);
            AppObject.getAppInstance().getMojRestoran().getStavkaArrayList().add(selectedStavka);
            AppObject.getAppInstance().updateRestoranBase();
        } else {
            selectedStavka.setNaziv(etNaziv.getText().toString());
            selectedStavka.setCena(Double.parseDouble(etCena.getText().toString()));
            selectedStavka.setPodkategorija(selectedPodkategorija);
            AppObject.getAppInstance().updateRestoranBase();
        }

        return true;

    }

    @Override
    public String getTitle() {
        return idStavka != null ? "Izmeni stavku" : "Dodaj podkategoriju";
    }
}
