package com.ilija.mojrestoran.ui.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ilija.mojrestoran.AppObject;
import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.model.Narudzbina;
import com.ilija.mojrestoran.model.Sto;
import com.ilija.mojrestoran.util.ToastMessage;
import com.ilija.mojrestoran.util.Utilities;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by ilija.tomic on 1/28/2016.
 */
public class NewNarudzbinaDialogView implements DialogView {

    private Spinner spBrojStola;
    private Sto selectedSto;
    private Narudzbina narudzbina;
    private Context context;

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> stolovi = new ArrayList<>();

    public NewNarudzbinaDialogView(Context context, View view) {
        this.context = context;

        spBrojStola = (Spinner) view.findViewById(R.id.add_narudzbina_sto);

        Utilities.populateSpinnerStrings(stolovi, AppObject.getAppInstance().getSlobodniStolovi(), "slobodni sto");
        arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, stolovi);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBrojStola.setAdapter(arrayAdapter);
        spBrojStola.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selectedSto = null;
                } else {
                    for (Sto sto : AppObject.getAppInstance().getSlobodniStolovi())
                        if (sto.getBroj() == Double.parseDouble(arrayAdapter.getItem(position)))
                            selectedSto = sto;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void setData() {
    }

    @Override
    public boolean saveData() {

        if (selectedSto == null) {
            ToastMessage.showToast(context, "Izaberite slobodan selectedSto!");
            return false;
        }

        String idNarudzbine = UUID.randomUUID().toString();
        narudzbina = new Narudzbina(idNarudzbine, false, new Date(), selectedSto, AppObject.getAppInstance().getUlogovanKorisnik(), null, null);
        AppObject.getAppInstance().getMojRestoran().getNenaplaceneNarudzbine().add(narudzbina);
        AppObject.getAppInstance().updateRestoranBase();

        return true;
    }

    @Override
    public String getTitle() {
        return "Dodaj narudzbinu";
    }
}
