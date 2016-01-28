package com.ilija.mojrestoran.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.ilija.mojrestoran.AppObject;
import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.ui.activity.KonobarNarudzbinaActivity;
import com.ilija.mojrestoran.ui.viewholder.DialogView;
import com.ilija.mojrestoran.ui.viewholder.KategorijaDialogView;
import com.ilija.mojrestoran.ui.viewholder.KorisnikDialogView;
import com.ilija.mojrestoran.ui.viewholder.NewNarudzbinaDialogView;
import com.ilija.mojrestoran.ui.viewholder.PodkategorijaDialogView;
import com.ilija.mojrestoran.ui.viewholder.StavkaDialogView;
import com.ilija.mojrestoran.ui.viewholder.StoDialogView;
import com.ilija.mojrestoran.util.Constants;

/**
 * Created by Ilija on 1/16/2016.
 */
public class AddEditDialog extends DialogFragment {

    private String id;

    private DataChangeListener dataChangeListener;
    private DialogDataType dialogDataType;
    private DialogView dialogView;

    public AddEditDialog() {}

    public AddEditDialog(String id, DataChangeListener dataChangeListener, DialogDataType dialogDataType) {
        this.id = id;
        this.dataChangeListener = dataChangeListener;
        this.dialogDataType = dialogDataType;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = generateLayout();
        generateViewHolder(view);

        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(dialogView.getTitle())
                .setPositiveButton("Sacuvaj", null)
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
                        if (dialogView.saveData()) {
                            getDialog().dismiss();
                            dataChangeListener.onDataChanged();
                            if (dialogDataType == DialogDataType.NARUDZBINA) {
                                Intent intent = new Intent(getActivity(), KonobarNarudzbinaActivity.class);
                                intent.putExtra(Constants.EXTRA_NARUDZBINA_ID,
                                        AppObject.getAppInstance().getMojRestoran().getNenaplaceneNarudzbine().get(AppObject.getAppInstance().getMojRestoran().getNenaplaceneNarudzbine().size() - 1).getId());
                                getActivity().startActivity(intent);
                            }
                        }
                    }
                });
            }
        });

        return alertDialog;

    }

    private void generateViewHolder(View view) {

        switch (dialogDataType) {
            case KORISNIK:
                dialogView = new KorisnikDialogView(getActivity(), id, view);
                dialogView.setData();
                break;
            case STO:
                dialogView = new StoDialogView(getActivity(), id, view);
                dialogView.setData();
                break;
            case KATEGORIJA:
                dialogView = new KategorijaDialogView(getActivity(), id, view);
                dialogView.setData();
                break;
            case PODKATEGORIJA:
                dialogView = new PodkategorijaDialogView(getActivity(), id, view);
                dialogView.setData();
                break;
            case STAVKA:
                dialogView = new StavkaDialogView(getActivity(), id, view);
                dialogView.setData();
                break;
            case NARUDZBINA:
                dialogView = new NewNarudzbinaDialogView(getActivity(), view);
                break;
        }

    }

    private View generateLayout() {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        switch (dialogDataType) {
            case KORISNIK:
                return inflater.inflate(R.layout.dialog_add_edit_korisnik, null);
            case STO:
                return inflater.inflate(R.layout.dialog_add_edit_sto, null);
            case KATEGORIJA:
                return inflater.inflate(R.layout.dialog_add_edit_kategorija, null);
            case PODKATEGORIJA:
                return inflater.inflate(R.layout.dialog_add_edit_podkategorija, null);
            case STAVKA:
                return inflater.inflate(R.layout.dialog_add_edit_stavka, null);
            case NARUDZBINA:
                return inflater.inflate(R.layout.dialog_add_narudzbina, null);
            default:
                return null;
        }

    }
}
