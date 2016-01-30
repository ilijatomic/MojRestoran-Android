package com.ilija.mojrestoran.ui.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ListView;

import com.ilija.mojrestoran.AppObject;
import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.model.Narudzbina;
import com.ilija.mojrestoran.ui.adapter.KonobarNarudzbineListAdapter;
import com.ilija.mojrestoran.ui.dialog.AddEditDialog;
import com.ilija.mojrestoran.ui.dialog.DataChangeListener;
import com.ilija.mojrestoran.ui.dialog.DialogDataType;
import com.ilija.mojrestoran.util.Constants;

import java.util.ArrayList;

public class KonobarHomeActivity extends BaseActivity implements DataChangeListener {

    private ListView narudzbine;
    private KonobarNarudzbineListAdapter konobarNarudzbineListAdapter;
    private FloatingActionButton fabAdd;

    private ArrayList<Narudzbina> myNarudzbine = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konobar_home);

        addToolbar(false);

        fabAdd = (FloatingActionButton) findViewById(R.id.add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDialog();
            }
        });
        konobarNarudzbineListAdapter = new KonobarNarudzbineListAdapter(this, R.layout.list_item_konobar_narudzbina, myNarudzbine, this);
        narudzbine = (ListView) findViewById(R.id.lv_konobar_narudzbine);
        narudzbine.setAdapter(konobarNarudzbineListAdapter);

    }

    private void addDialog() {
        AddEditDialog addNarudzbina = new AddEditDialog();
        addNarudzbina.setAddEditDialog(null, this, DialogDataType.NARUDZBINA);
        addNarudzbina.show(getFragmentManager(), "AddNarudzbina");
    }

    private void getMyNarudzbine() {

        myNarudzbine.clear();

        for (Narudzbina narudzbina : AppObject.getAppInstance().getMojRestoran().getNarudzbinaArrayList())
            if (narudzbina.getKorisnik().getId().equals(AppObject.getAppInstance().getUlogovanKorisnik().getId())
                    && !narudzbina.isNaplacena())
                myNarudzbine.add(narudzbina);

        konobarNarudzbineListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyNarudzbine();
    }

    @Override
    public void onDataChanged() {
        getMyNarudzbine();
    }
}
