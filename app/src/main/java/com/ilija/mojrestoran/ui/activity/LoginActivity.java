package com.ilija.mojrestoran.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ilija.mojrestoran.AppObject;
import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.event.DatabaseDownloaded;
import com.ilija.mojrestoran.model.Korisnik;
import com.ilija.mojrestoran.model.MojRestoran;
import com.ilija.mojrestoran.util.Constants;
import com.ilija.mojrestoran.util.ToastMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class LoginActivity extends BaseActivity {

    private EditText etEmail;
    private EditText etSifra;
    private Button btnUlogujSe;
    private LinearLayout linearLayout;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        linearLayout = (LinearLayout) findViewById(R.id.ll_progress);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
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

        if (AppObject.getAppInstance().getMojRestoran() != null) {
            checkLogin();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(DatabaseDownloaded event) {
        checkLogin();
    }

    private void checkLogin() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userLogin = sharedPreferences.getString(Constants.PREF_USER_LOGIN, null);
        Intent intent = null;
        if (userLogin != null ) {

            for (Korisnik korisnik : AppObject.getAppInstance().getMojRestoran().getKorisnikArrayList()) {
                if (korisnik.getEmail().equals(userLogin)) {
                    AppObject.getAppInstance().setUlogovanKorisnik(korisnik);
                    if (AppObject.getAppInstance().getUlogovanKorisnik().getTip().equals(Constants.USER_LOGIN_ADMIN))
                        intent = new Intent(this, AdminHomeActivity.class);
                    else if (AppObject.getAppInstance().getUlogovanKorisnik().getTip().equals(Constants.USER_LOGIN_KONOBAR))
                        intent = new Intent(this, KonobarHomeActivity.class);
                    break;
                }
            }

            startActivity(intent);
            finish();
        } else {
            linearLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
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
