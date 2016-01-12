package com.ilija.mojrestoran.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ilija.mojrestoran.AppObject;
import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.util.ToastMessage;

import java.util.ArrayList;

public class ProfilDetaljiActivity extends BaseActivity {

    private TextView tvEmail;
    private EditText etIme;
    private EditText etPrezime;
    private EditText etBrojTelefona;
    private EditText etSifra;
    private EditText etPonovoSifra;
    private Button btnSacuvaj;

    private ArrayList<EditText> lista = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_detalji);

        tvEmail = (TextView) findViewById(R.id.email);
        tvEmail.setText(AppObject.getAppInstance().getUlogovanKorisnik().getEmail());
        etIme = (EditText) findViewById(R.id.ime);
        etIme.setText(AppObject.getAppInstance().getUlogovanKorisnik().getIme());
        etPrezime = (EditText) findViewById(R.id.prezime);
        etPrezime.setText(AppObject.getAppInstance().getUlogovanKorisnik().getPrezime());
        etBrojTelefona = (EditText) findViewById(R.id.broj_telefona);
        etBrojTelefona.setText(AppObject.getAppInstance().getUlogovanKorisnik().getBrTel());
        etSifra = (EditText) findViewById(R.id.nova_sifra);
        etPonovoSifra = (EditText) findViewById(R.id.potvrdi_novu_sifru);

        btnSacuvaj = (Button) findViewById(R.id.profil_sacuvaj);
        btnSacuvaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lista.clear();
                lista.add(etIme);
                lista.add(etPrezime);
                if (checkRequiredFields(lista))
                    updateKorisnik();
            }
        });
    }

    private void updateKorisnik() {
        if (!etSifra.getText().toString().isEmpty()) {
            if (!etSifra.getText().toString().equals(etPonovoSifra.getText().toString())) {
                ToastMessage.showToast(AppObject.getAppInstance().getApplicationContext(), "Nova sifra se ne poklapa!");
                return;
            } else {
                AppObject.getAppInstance().getUlogovanKorisnik().setPassword(etSifra.getText().toString());
            }
        }

        AppObject.getAppInstance().getUlogovanKorisnik().setIme(etIme.getText().toString());
        AppObject.getAppInstance().getUlogovanKorisnik().setPrezime(etPrezime.getText().toString());
        AppObject.getAppInstance().getUlogovanKorisnik().setBrTel(etBrojTelefona.getText().toString());

        AppObject.getAppInstance().izmeniKorisnik();

        ToastMessage.showToast(AppObject.getAppInstance().getApplicationContext(), "Podaci uspesno promenjeni");
        finish();

    }

}