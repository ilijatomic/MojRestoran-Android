package com.ilija.mojrestoran.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.model.NaruceneStavke;
import com.ilija.mojrestoran.model.Racun;
import com.ilija.mojrestoran.util.NarudzbinaDetalji;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by ilija.tomic on 2/1/2016.
 */
public class RacunListAdapter extends ArrayAdapter<NarudzbinaDetalji> {

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH);

    private Context context;
    private ArrayList<NarudzbinaDetalji> racuns;
    private int layout;

    public RacunListAdapter(Context context, int resource, ArrayList<NarudzbinaDetalji> objects) {
        super(context, resource, objects);

        this.context = context;
        this.layout = resource;
        this.racuns = objects;
    }

    @Override
    public NarudzbinaDetalji getItem(int position) {
        return racuns.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (getItem(position).getDetaljiType() == NarudzbinaDetalji.DetaljiType.HEADER) {
                view = layoutInflater.inflate(R.layout.list_item_admin_racun_header, null);
                view.setTag(position);

                TextView racun = (TextView) view.findViewById(R.id.tv_racun);
                racun.setText(dateFormatter.format(getItem(position).getNaplacen()) + " ");
                TextView racunUkupno = (TextView) view.findViewById(R.id.tv_racun_ukupno);
                racunUkupno.setText("" + getItem(position).getHeaderUkupno() + " din");
            } else if (getItem(position).getDetaljiType() == NarudzbinaDetalji.DetaljiType.STAVKE) {
                view = layoutInflater.inflate(R.layout.list_item_admin_racun, null);
                view.setTag(position);

                TextView kolicina = (TextView) view.findViewById(R.id.tv_racun_kolicina);
                kolicina.setText("" + getItem(position).getStavkaKolicina() + "x");
                TextView stavka = (TextView) view.findViewById(R.id.tv_racun_stavka);
                stavka.setText(getItem(position).getStavka().getNaziv() + "|");
                TextView ukupno = (TextView) view.findViewById(R.id.tv_racun_stavka_ukupno);
                ukupno.setText("" + getItem(position).getStavka().getCena() * getItem(position).getStavkaKolicina()  + " din");
            }
        }

        return view;
    }

}
