package com.ilija.mojrestoran.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.ilija.mojrestoran.AppObject;
import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.util.Utilities;

import java.util.ArrayList;

public class KonobarNarudzbinaActivity extends BaseActivity {

    private AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konobar_narudzbina);

        addToolbar(true);

        ArrayList<String> strings = new ArrayList<>();
        Utilities.populateSpinnerStrings(strings, AppObject.getAppInstance().getMojRestoran().getStavkaArrayList(), "");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, strings);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.actv_stavka);
        autoCompleteTextView.setAdapter(arrayAdapter);
    }
}
