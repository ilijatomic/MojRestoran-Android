package com.ilija.mojrestoran.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ilija.mojrestoran.R;

public class AdminHomeActivity extends BaseActivity implements View.OnClickListener {

    private Button btnKorisnici;
    private Button btnManiRestorana;
    private Button btnStolovi;
    private Button btnNarudzbine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        addToolbar(false);

        btnKorisnici = (Button) findViewById(R.id.btn_korisnici);
        btnManiRestorana = (Button) findViewById(R.id.btn_meni_restorana);
        btnStolovi = (Button) findViewById(R.id.btn_Stolovi);
        btnNarudzbine = (Button) findViewById(R.id.btn_Narudzbine);

        btnKorisnici.setOnClickListener(this);
        btnManiRestorana.setOnClickListener(this);
        btnStolovi.setOnClickListener(this);
        btnNarudzbine.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        Intent intent;

        switch (v.getId()) {
            case R.id.btn_korisnici:
                intent = new Intent(this, AdminKorisniciActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_Stolovi:
                intent = new Intent(this, AdminStoloviActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_meni_restorana:
                intent = new Intent(this, AdminMeniActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }
}
