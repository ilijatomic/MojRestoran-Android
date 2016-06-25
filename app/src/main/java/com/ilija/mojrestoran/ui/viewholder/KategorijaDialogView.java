package com.ilija.mojrestoran.ui.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.ilija.mojrestoran.AppObject;
import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.model.Kategorija;
import com.ilija.mojrestoran.util.ToastMessage;
import com.ilija.mojrestoran.util.Utilities;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by ilija.tomic on 1/19/2016.
 */
public class KategorijaDialogView implements DialogView {

    private String idKategorija;

    private EditText etNaziv;
    private Kategorija kategorija;
    private Context context;

    private ArrayList<EditText> lista = new ArrayList<>();

    public KategorijaDialogView(Context context, String idKategorija, View view) {
        this.context = context;
        this.idKategorija = idKategorija;

        if (idKategorija != null)
            kategorija = AppObject.getAppInstance().getKategorijaById(idKategorija);

        etNaziv = (EditText) view.findViewById(R.id.add_naziv_kategorija);
    }

    @Override
    public void setData() {
        if (kategorija != null) {
            etNaziv.setText(kategorija.getNaziv());
        }
    }

    @Override
    public boolean saveData() {

        lista.clear();
        lista.add(etNaziv);
        if (!Utilities.checkRequiredFields(lista))
            return false;

        if (AppObject.getAppInstance().checkIfKategorijaExists(etNaziv.getText().toString())) {
            ToastMessage.showToast(context, "Kategorija sa unetim imenom vec postoji!");
            return false;
        }

        if (kategorija == null) {
            idKategorija = UUID.randomUUID().toString();
            kategorija = new Kategorija(idKategorija, etNaziv.getText().toString());
            AppObject.getAppInstance().getMojRestoran().getKategorijaArrayList().add(kategorija);
            AppObject.getAppInstance().updateRestoranBase();
        } else {
            kategorija.setNaziv(etNaziv.getText().toString());
            AppObject.getAppInstance().updateRestoranBase();
        }

        return true;

    }

    @Override
    public String getTitle() {
        return idKategorija != null ? "Izmeni kategoriju" : "Dodaj kategoriju";
    }
}
