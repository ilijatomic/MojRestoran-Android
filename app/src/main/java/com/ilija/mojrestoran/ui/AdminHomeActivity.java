package com.ilija.mojrestoran.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

        switch (v.getId()) {
            case R.id.btn_korisnici:
                Intent intent = new Intent(this, AdminKorisniciActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }
}
