package com.ilija.mojrestoran.ui.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.ilija.mojrestoran.AppObject;
import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.model.Kategorija;
import com.ilija.mojrestoran.model.Podkategorija;
import com.ilija.mojrestoran.util.ToastMessage;
import com.ilija.mojrestoran.util.Utilities;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by ilija.tomic on 1/25/2016.
 */
public class PodkategorijaDialogView implements DialogView {

    private String idPodkategorija;

    private Spinner spKategorija;
    private EditText etNaziv;
    private Kategorija selectedKategorija;
    private Podkategorija podkategorija;
    private Context context;

    private ArrayList<EditText> lista = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

    public PodkategorijaDialogView(Context context, String idPodkategorija, View view) {
        this.context = context;
        this.idPodkategorija = idPodkategorija;

        if (idPodkategorija != null) {
            podkategorija = AppObject.getAppInstance().getPodkategorijaById(idPodkategorija);
            selectedKategorija = podkategorija.getKategorija();
        }

        etNaziv = (EditText) view.findViewById(R.id.add_naziv_podkategorija);
        spKategorija = (Spinner) view.findViewById(R.id.add_kategorija);
        ArrayList<String> strings = new ArrayList<>();
        Utilities.populateSpinnerStrings(strings, AppObject.getAppInstance().getMojRestoran().getKategorijaArrayList(), "kategorija");
        arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, strings);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKategorija.setAdapter(arrayAdapter);
        spKategorija.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selectedKategorija = null;
                    return;
                }
                for (Kategorija kategorija : AppObject.getAppInstance().getMojRestoran().getKategorijaArrayList())
                    if (kategorija.getNaziv().equals(arrayAdapter.getItem(position)))
                        selectedKategorija = kategorija;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void setData() {
        if (podkategorija != null) {
            etNaziv.setText(podkategorija.getNaziv());
            spKategorija.setSelection(arrayAdapter.getPosition(podkategorija.getKategorija().getNaziv()));
        }
    }

    @Override
    public boolean saveData() {

        lista.clear();
        lista.add(etNaziv);
        if (!Utilities.checkRequiredFields(lista))
            return false;

        if (selectedKategorija == null) {
            ToastMessage.showToast(context, "Selektujte kategoriju!");
            return false;
        }

        if (podkategorija!= null) {
            if (AppObject.getAppInstance().checkIfPodkategorijaExists(etNaziv.getText().toString()) && selectedKategorija.getId().equals(podkategorija.getKategorija().getId())) {
                ToastMessage.showToast(context, "Podkategorija sa unetim nazivom vec postoji!");
                return false;
            }
        } else if (AppObject.getAppInstance().checkIfPodkategorijaExists(etNaziv.getText().toString())) {
            ToastMessage.showToast(context, "Podkategorija sa unetim nazivom vec postoji!");
            return false;
        }

        if (podkategorija == null) {
            idPodkategorija = UUID.randomUUID().toString();
            podkategorija = new Podkategorija(idPodkategorija, etNaziv.getText().toString(), selectedKategorija);
            AppObject.getAppInstance().getMojRestoran().getPodkategorijaArrayList().add(podkategorija);
            AppObject.getAppInstance().updateRestoranBase();
        } else {
            podkategorija.setNaziv(etNaziv.getText().toString());
            podkategorija.setKategorija(selectedKategorija);
            AppObject.getAppInstance().updateRestoranBase();
        }

        return true;

    }

    @Override
    public String getTitle() {
        return idPodkategorija != null ? "Izmeni podkategoriju" : "Dodaj podkategoriju";
    }
}
