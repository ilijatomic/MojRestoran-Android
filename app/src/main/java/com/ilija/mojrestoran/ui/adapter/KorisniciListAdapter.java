package com.ilija.mojrestoran.ui.adapter;

import android.app.DialogFragment;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.model.Korisnik;
import com.ilija.mojrestoran.ui.dialog.AddEditDialog;
import com.ilija.mojrestoran.ui.dialog.DataChangeListener;
import com.ilija.mojrestoran.ui.dialog.DeleteDialog;
import com.ilija.mojrestoran.ui.dialog.DialogDataType;

import java.util.ArrayList;

/**
 * Created by Ilija on 1/14/2016.
 */
public class KorisniciListAdapter extends ArrayAdapter<Korisnik> {

    private Context context;
    private DataChangeListener dataChangeListener;
    private ArrayList<Korisnik> korisniks;

    public KorisniciListAdapter(Context context, int resource, ArrayList<Korisnik> objects, DataChangeListener dataChangeListener) {
        super(context, resource, objects);

        this.context = context;
        this.korisniks = objects;
        this.dataChangeListener = dataChangeListener;
    }

    @Override
    public Korisnik getItem(int position) {
        return korisniks.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item_admin_korisnici, null);
        }

        view.setTag(position);

        ImageButton edit = (ImageButton) view.findViewById(R.id.btn_edit);
        edit.setTag(position);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEditDialog editKorisnik = new AddEditDialog();
                editKorisnik.setAddEditDialog(getItem(position).getId(), dataChangeListener, DialogDataType.KORISNIK);
                FragmentActivity fragmentActivity = (FragmentActivity) context;
                editKorisnik.show(fragmentActivity.getFragmentManager(), "EditKorisnik");
            }
        });
        ImageButton delete = (ImageButton) view.findViewById(R.id.btn_delete);
        delete.setTag(position);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteDialog deleteKorisnik = new DeleteDialog();
                deleteKorisnik.setDeleteDialog(getItem(position).getId(), dataChangeListener, DialogDataType.KORISNIK);
                FragmentActivity fragmentActivity = (FragmentActivity) context;
                deleteKorisnik.show(fragmentActivity.getFragmentManager(), "DeleteKorisnik");
            }
        });

        TextView ime = (TextView) view.findViewById(R.id.tv_ime);
        ime.setText(getItem(position).getIme());
        TextView prezime = (TextView) view.findViewById(R.id.tv_prezime);
        prezime.setText(getItem(position).getPrezime());
        TextView tip = (TextView) view.findViewById(R.id.tv_tip);
        tip.setText("(" + getItem(position).getTip() + ")");

        return view;
    }

}
