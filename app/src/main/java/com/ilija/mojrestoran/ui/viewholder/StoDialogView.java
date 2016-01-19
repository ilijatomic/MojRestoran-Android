package com.ilija.mojrestoran.ui.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.ilija.mojrestoran.AppObject;
import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.model.Sto;
import com.ilija.mojrestoran.util.ToastMessage;
import com.ilija.mojrestoran.util.Utilities;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Ilija on 1/16/2016.
 */
public class StoDialogView implements DialogView {

    private String idSto;

    private EditText etBroj;
    private Sto sto;
    private Context context;

    private ArrayList<EditText> lista = new ArrayList<>();

    public StoDialogView(Context context, String idSto, View view) {
        this.context = context;
        this.idSto = idSto;

        if (idSto != null)
            sto = AppObject.getAppInstance().getStoById(idSto);

        etBroj = (EditText) view.findViewById(R.id.add_broj);
    }

    @Override
    public void setData() {
        if (sto != null) {
            etBroj.setText("" + sto.getBroj());
        }
    }

    @Override
    public boolean saveData() {

        lista.clear();
        lista.add(etBroj);
        if (!Utilities.checkRequiredFields(lista))
            return false;

        if (AppObject.getAppInstance().checkIfStoExists(etBroj.getText().toString())) {
            ToastMessage.showToast(context, "Sto sa unetim brojem vec postoji!");
            return false;
        }

        if (sto == null) {
            idSto = UUID.randomUUID().toString();
            sto = new Sto(idSto, Integer.parseInt(etBroj.getText().toString()));
            AppObject.getAppInstance().getMojRestoran().getStoArrayList().add(sto);
            AppObject.getAppInstance().updateRestoranBase();
        } else {
            sto.setBroj(Integer.parseInt(etBroj.getText().toString()));
            AppObject.getAppInstance().updateRestoranBase();
        }

        return true;

    }

    @Override
    public String getTitle() {
        return idSto != null ? "Izmeni sto" : "Dodaj sto";
    }
}
