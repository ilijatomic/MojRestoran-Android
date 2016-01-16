package com.ilija.mojrestoran.ui.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.ilija.mojrestoran.AppObject;
import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.model.Korisnik;
import com.ilija.mojrestoran.util.ToastMessage;
import com.ilija.mojrestoran.util.Utilities;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Ilija on 1/16/2016.
 */
public class KorisnikDialogView implements DialogView {

    private String idKorisnik;
    private String tip;

    private EditText etIme;
    private EditText etPrezime;
    private EditText etEmail;
    private EditText etBroj;
    private EditText etSifra;
    private Spinner spTip;

    private Korisnik korisnik;
    private Context context;

    private ArrayList<EditText> lista = new ArrayList<>();

    public KorisnikDialogView(Context context, String idKorisnik, View view) {
        this.context = context;
        this.idKorisnik = idKorisnik;

        if (idKorisnik != null)
            korisnik = AppObject.getAppInstance().getKorisnikById(idKorisnik);

        etIme = (EditText) view.findViewById(R.id.add_ime);
        etPrezime = (EditText) view.findViewById(R.id.add_prezime);
        etEmail = (EditText) view.findViewById(R.id.add_email);
        etBroj = (EditText) view.findViewById(R.id.add_broj_telefona);
        etSifra = (EditText) view.findViewById(R.id.add_sifra);
        spTip = (Spinner) view.findViewById(R.id.add_tip);
        spTip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tip = parent.getItemAtPosition(position).toString();
                if (tip.equals("tip"))
                    tip = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void setData() {

        if (korisnik != null) {
            etIme.setText(korisnik.getIme());
            etPrezime.setText(korisnik.getPrezime());
            etEmail.setText(korisnik.getEmail());
            etBroj.setText(korisnik.getBrTel());
            etSifra.setText(korisnik.getPassword());
            switch (korisnik.getTip()) {
                case "admin":
                    spTip.setSelection(1);
                    break;
                case "konobar":
                    spTip.setSelection(2);
                    break;
            }
        }

    }

    @Override
    public boolean saveData() {

        lista.clear();
        lista.add(etEmail);
        lista.add(etSifra);
        if (!Utilities.checkRequiredFields(lista))
            return false;

        if (tip.equals("tip"))
            return false;

        if (AppObject.getAppInstance().checkIfUserExists(etEmail.getText().toString())) {
            ToastMessage.showToast(context, "Korisnik sa unetim email vec postoji!");
            return false;
        }

        if (korisnik == null) {
            idKorisnik = UUID.randomUUID().toString();
            korisnik = new Korisnik(idKorisnik, etIme.getText().toString(), etPrezime.getText().toString(), etBroj.getText().toString(), etEmail.getText().toString(), etSifra.getText().toString(), tip);
            AppObject.getAppInstance().getMojRestoran().getKorisnikArrayList().add(korisnik);
            AppObject.getAppInstance().updateRestoranBase();
        } else {
            korisnik.setIme(etIme.getText().toString());
            korisnik.setPrezime(etPrezime.getText().toString());
            korisnik.setBrTel(etBroj.getText().toString());
            korisnik.setEmail(etEmail.getText().toString());
            korisnik.setPassword(etSifra.getText().toString());
            korisnik.setTip(tip);
            AppObject.getAppInstance().updateRestoranBase();
        }

        return true;

    }

    @Override
    public String getTitle() {
        return idKorisnik != null ? "Izmeni korisnika" : "Dodaj korisnika";
    }
}
