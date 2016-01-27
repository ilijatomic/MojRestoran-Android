package com.ilija.mojrestoran.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.ilija.mojrestoran.AppObject;
import com.ilija.mojrestoran.model.Kategorija;
import com.ilija.mojrestoran.model.Korisnik;
import com.ilija.mojrestoran.model.Podkategorija;
import com.ilija.mojrestoran.model.Stavka;
import com.ilija.mojrestoran.model.Sto;
import com.ilija.mojrestoran.util.ToastMessage;

/**
 * Created by Ilija on 1/16/2016.
 */
public class DeleteDialog extends DialogFragment {

    private String id;
    private String title;
    private String message;

    private DataChangeDialogListener dataChangeDialogListener;
    private DialogDataType dialogDataType;

    public DeleteDialog(String id, DataChangeDialogListener dataChangeDialogListener, DialogDataType dialogDataType) {
        this.id = id;
        this.dataChangeDialogListener = dataChangeDialogListener;
        this.dialogDataType = dialogDataType;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        generateTitleAndMessage();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (dialogDataType) {
                            case KORISNIK:
                                deleteKorisnik();
                                break;
                            case STO:
                                deleteSto();
                                break;
                            case KATEGORIJA:
                                deleteKategorija();
                                break;
                            case PODKATEGORIJA:
                                deletePodkategorija();
                                break;
                            case STAVKA:
                                deleteStavka();
                                break;
                        }

                    }
                })
                .setNegativeButton("Odustani", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }

    private void generateTitleAndMessage() {
        switch (dialogDataType) {
            case KORISNIK:
                title = "Obrisi korisnika";
                message = "Da li ste sigurni da zelite da obriste korisnika?";
                break;
            case STO:
                title = "Obrisi sto";
                message = "Da li ste sigurni da zelite da obriste sto?";
                break;
            case KATEGORIJA:
                title = "Obrisi kategoriju";
                message = "Da li ste sigurni da zelite da obriste kategoriju?";
                break;
            case PODKATEGORIJA:
                title = "Obrisi podkategoriju";
                message = "Da li ste sigurni da zelite da obriste podkategoriju?";
            case STAVKA:
                title = "Obrisi stavku";
                message = "Da li ste sigurni da zelite da obriste stavku?";
                break;
        }
    }

    private void deleteKorisnik() {

        if (AppObject.getAppInstance().checkIfLoggedUser(id)) {
            ToastMessage.showToast(getActivity(), "Taj korisnik ne moze biti obrisan!");
            return;
        }

        for (Korisnik korisnik : AppObject.getAppInstance().getMojRestoran().getKorisnikArrayList()) {
            if (korisnik.getId().equals(id)) {
                AppObject.getAppInstance().getMojRestoran().getKorisnikArrayList().remove(korisnik);
                AppObject.getAppInstance().updateRestoranBase();
                dataChangeDialogListener.onDataChanged();
                break;
            }
        }
    }

    private void deleteSto() {

        for (Sto sto : AppObject.getAppInstance().getMojRestoran().getStoArrayList()) {
            if (sto.getId().equals(id)) {
                AppObject.getAppInstance().getMojRestoran().getStoArrayList().remove(sto);
                AppObject.getAppInstance().updateRestoranBase();
                dataChangeDialogListener.onDataChanged();
                break;
            }
        }
    }

    private void deleteKategorija() {

        for (Kategorija kategorija : AppObject.getAppInstance().getMojRestoran().getKategorijaArrayList()) {
            if (kategorija.getId().equals(id)) {
                AppObject.getAppInstance().getMojRestoran().getKategorijaArrayList().remove(kategorija);
                AppObject.getAppInstance().updateRestoranBase();
                dataChangeDialogListener.onDataChanged();
                break;
            }
        }
    }

    private void deletePodkategorija() {

        for (Podkategorija podkategorija : AppObject.getAppInstance().getMojRestoran().getPodkategorijaArrayList())
            if (podkategorija.getId().equals(id)) {
                AppObject.getAppInstance().getMojRestoran().getPodkategorijaArrayList().remove(podkategorija);
                AppObject.getAppInstance().updateRestoranBase();
                dataChangeDialogListener.onDataChanged();
                break;
            }
    }

    private void deleteStavka() {

        for (Stavka stavka : AppObject.getAppInstance().getMojRestoran().getStavkaArrayList())
            if (stavka.getId().equals(id)) {
                AppObject.getAppInstance().getMojRestoran().getStavkaArrayList().remove(stavka);
                AppObject.getAppInstance().updateRestoranBase();
                dataChangeDialogListener.onDataChanged();
                break;
            }
    }
}
