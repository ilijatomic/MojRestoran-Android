package com.ilija.mojrestoran.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ilija.mojrestoran.AppObject;
import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.model.Korisnik;
import com.ilija.mojrestoran.model.MojRestoran;
import com.ilija.mojrestoran.util.Constants;
import com.ilija.mojrestoran.util.ToastMessage;

public class LoginActivity extends BaseActivity {

    private EditText etEmail;
    private EditText etSifra;
    private Button btnUlogujSe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.la_email);
        etSifra = (EditText) findViewById(R.id.la_password);
        btnUlogujSe = (Button) findViewById(R.id.la_login);
        btnUlogujSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MojRestoran mojRestoran = AppObject.getAppInstance().getMojRestoran();

                if (mojRestoran != null) {
                    if (!etEmail.getText().toString().isEmpty() && !etSifra.getText().toString().isEmpty()) {
                        for (Korisnik korisnik : mojRestoran.getKorisnikArrayList()) {
                            if (korisnik.getEmail().equals(etEmail.getText().toString()) && korisnik.getPassword().equals(etSifra.getText().toString())) {
                                loginSuccess(korisnik);
                                return;
                            }
                        }
                    }
                    ToastMessage.showToast(AppObject.getAppInstance().getApplicationContext(), "Pogresno uneti podaci!");
                }
            }
        });

    }

    private void loginSuccess(Korisnik korisnik) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putString(Constants.PREF_USER_LOGIN, korisnik.getEmail()).commit();
        Intent intent = null;

        switch (korisnik.getTip()) {
            case "admin":
                intent = new Intent(this, AdminHomeActivity.class);
                break;
            case "konobar":
                intent = new Intent(this, KonobarHomeActivity.class);
                break;
        }

        if (intent != null) {
            AppObject.getAppInstance().setUlogovanKorisnik(korisnik);
            startActivity(intent);
            finish();
        }

    }
}
