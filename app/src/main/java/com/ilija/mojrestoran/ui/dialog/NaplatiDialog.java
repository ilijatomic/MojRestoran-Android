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

import com.ilija.mojrestoran.AppObject;
import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.model.NaruceneStavke;
import com.ilija.mojrestoran.model.Narudzbina;
import com.ilija.mojrestoran.model.Racun;
import com.ilija.mojrestoran.ui.adapter.KonobarStavkeListAdapter;
import com.ilija.mojrestoran.util.ToastMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by ilija.tomic on 1/29/2016.
 */
public class NaplatiDialog extends DialogFragment implements DataChangeListener {

    private Narudzbina narudzbina;
    private ArrayList<NaruceneStavke> naruceneStavkes;
    private KonobarStavkeListAdapter konobarStavkeListAdapter;
    private ListView stavke;
    private TextView tvUkupno;
    private RacunChangeListener racunChangeListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_naplati, null);
        stavke = (ListView) view.findViewById(R.id.stavke_naplati);
        konobarStavkeListAdapter = new KonobarStavkeListAdapter(getActivity(), R.layout.list_item_konobar_naplati, naruceneStavkes, this, narudzbina.getId());
        stavke.setAdapter(konobarStavkeListAdapter);
        tvUkupno = (TextView) view.findViewById(R.id.tv_ukupno_naplati);
        tvUkupno.setText(String.valueOf(izracunajUkupnuCenu(naruceneStavkes)));

        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Naplata narudzbine za stolom " + narudzbina.getSto().getBroj())
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
                        if (Double.parseDouble(tvUkupno.getText().toString()) == 0) {
                            ToastMessage.showToast(getActivity(), "Morate izabrati stavke za naplatu!!");
                            getDialog().dismiss();
                            return;
                        }
                        String racunId = UUID.randomUUID().toString();
                        if (Double.parseDouble(tvUkupno.getText().toString()) == izracunajUkupnuCenu(narudzbina.getNenaplaceneStavke())) {
                            Racun racun = new Racun(racunId, System.currentTimeMillis(), Double.parseDouble(tvUkupno.getText().toString()), naruceneStavkes);
                            narudzbina.setNaplacena(true);
                            narudzbina.getRacunArrayList().add(racun);
                            narudzbina.setNenaplaceneStavke(null);
                            AppObject.getAppInstance().updateRestoranBase();
                            racunChangeListener.naplaceno(true);
                        } else {
                            for (NaruceneStavke naruceneStavke : narudzbina.getNenaplaceneStavke()) {
                                NaruceneStavke naplatiStavku = getStavkaNaplata(naruceneStavke);
                                if (naplatiStavku != null) {
                                    Integer nenaplacena = naruceneStavke.getKolicina();
                                    Integer naplacena = naplatiStavku.getKolicina();
                                    naruceneStavke.setKolicina(nenaplacena - naplacena);
                                }
                            }
                            Racun racun = new Racun(racunId, System.currentTimeMillis(), izracunajUkupnuCenu(naruceneStavkes), naruceneStavkes);
                            narudzbina.getRacunArrayList().add(racun);
                            AppObject.getAppInstance().updateRestoranBase();
                            racunChangeListener.naplaceno(false);
                            getDialog().dismiss();
                        }
                    }
                });
            }
        });

        return alertDialog;
    }

    private NaruceneStavke getStavkaNaplata(NaruceneStavke stavka) {
        for (NaruceneStavke naruceneStavke : naruceneStavkes)
            if (naruceneStavke.getStavka().getId().equals(stavka.getStavka().getId()))
                return naruceneStavke;
        return null;
    }

    private double izracunajUkupnuCenu(ArrayList<NaruceneStavke> stavkes) {

        double ukupno = 0;
        for (NaruceneStavke naruceneStavke : stavkes) {
            ukupno += naruceneStavke.getStavka().getCena() * naruceneStavke.getKolicina();
        }

        return ukupno;
    }

    public void setNaplatiDialog(Narudzbina selectedNarudzbina, RacunChangeListener racunChangeListener) {
        this.narudzbina = selectedNarudzbina;
        this.naruceneStavkes = new ArrayList<>();
        for (NaruceneStavke naruceneStavke : selectedNarudzbina.getNenaplaceneStavke()) {
            NaruceneStavke newNarucenaStavka = new NaruceneStavke(naruceneStavke.getStavka(), naruceneStavke.getKolicina());
            naruceneStavkes.add(newNarucenaStavka);
        }
        this.racunChangeListener = racunChangeListener;
    }

    @Override
    public void onDataChanged() {
        konobarStavkeListAdapter.notifyDataSetChanged();
        tvUkupno.setText(String.valueOf(izracunajUkupnuCenu(naruceneStavkes)));
    }
}
