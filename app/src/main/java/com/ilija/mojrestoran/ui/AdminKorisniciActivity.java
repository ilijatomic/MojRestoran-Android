package com.ilija.mojrestoran.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ilija.mojrestoran.R;

public class AdminKorisniciActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_korisnici);
        addToolbar(true);
    }
}
