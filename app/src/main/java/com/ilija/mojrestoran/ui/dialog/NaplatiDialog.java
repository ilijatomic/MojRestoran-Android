package com.ilija.mojrestoran.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.model.NaruceneStavke;
import com.ilija.mojrestoran.model.Narudzbina;
import com.ilija.mojrestoran.ui.adapter.KonobarStavkeListAdapter;

import java.util.ArrayList;

/**
 * Created by ilija.tomic on 1/29/2016.
 */
public class NaplatiDialog extends DialogFragment implements DataChangeListener {

    private Narudzbina narudzbina;
    private ArrayList<NaruceneStavke> naruceneStavkes;
    private KonobarStavkeListAdapter konobarStavkeListAdapter;
    private ListView stavke;
    private TextView tvUkupno;
    private DataChangeListener dataChangeListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_naplati, null);
        stavke = (ListView) view.findViewById(R.id.stavke_naplati);
        konobarStavkeListAdapter = new KonobarStavkeListAdapter(getActivity(), R.layout.list_item_konobar_naplati, naruceneStavkes, this);
        stavke.setAdapter(konobarStavkeListAdapter);
        tvUkupno = (TextView) view.findViewById(R.id.tv_ukupno_naplati);

        izracunajUkupnuCenu();

        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Naplata narudzbinu za stolom " + narudzbina.getSto().getBroj())
                .setPositiveButton("Naplati", null)
                .setNegativeButton("Odustani", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button ok = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dataChangeListener.onDataChanged();
                    }
                });
            }
        });

        return alertDialog;
    }

    private void izracunajUkupnuCenu() {

        double ukupno = 0;
        for (NaruceneStavke naruceneStavke : naruceneStavkes) {
            ukupno += naruceneStavke.getStavka().getCena() * naruceneStavke.getKolicina();
        }

        tvUkupno.setText("Za naplatu: " + ukupno);
    }

    public void setNaplatiDialog(Narudzbina selectedNarudzbina, DataChangeListener dataChangeListener) {
        this.narudzbina = selectedNarudzbina;
        this.naruceneStavkes = new ArrayList<>();
        for (NaruceneStavke naruceneStavke : selectedNarudzbina.getNaruceneStavkeArrayList()) {
            NaruceneStavke newNarucenaStavka = new NaruceneStavke(naruceneStavke.getStavka(), naruceneStavke.getKolicina());
            naruceneStavkes.add(newNarucenaStavka);
        }
        this.dataChangeListener = dataChangeListener;
    }

    @Override
    public void onDataChanged() {
        konobarStavkeListAdapter.notifyDataSetChanged();
        izracunajUkupnuCenu();
    }
}
