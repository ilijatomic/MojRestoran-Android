package com.ilija.mojrestoran.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ilija.mojrestoran.AppObject;
import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.model.NaruceneStavke;
import com.ilija.mojrestoran.ui.dialog.DataChangeListener;

import java.util.ArrayList;

/**
 * Created by Ilija on 1/28/2016.
 */
public class KonobarStavkeListAdapter extends ArrayAdapter<NaruceneStavke> {

    private Context context;
    private DataChangeListener dataChangeListener;
    private ArrayList<NaruceneStavke> naruceneStavkes;

    public KonobarStavkeListAdapter(Context context, int resource, ArrayList<NaruceneStavke> objects, DataChangeListener dataChangeListener) {
        super(context, resource, objects);
        this.dataChangeListener = dataChangeListener;
        this.naruceneStavkes = objects;
    }

    @Override
    public NaruceneStavke getItem(int position) {
        return naruceneStavkes.get(position);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item_konobar_narudzbina_detalji, null);
        }

        view.setTag(position);

        ImageButton plus = (ImageButton) view.findViewById(R.id.btn_plus);
        plus.setTag(position);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getItem(position).setKolicina(getItem(position).getKolicina() + 1);
                AppObject.getAppInstance().updateRestoranBase();
                dataChangeListener.onDataChanged();
            }
        });
        ImageButton minus = (ImageButton) view.findViewById(R.id.btn_minus);
        minus.setTag(position);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItem(position).getKolicina() == 1) {
                    naruceneStavkes.remove(position);
                } else {
                    getItem(position).setKolicina(getItem(position).getKolicina() - 1);
                }
                AppObject.getAppInstance().updateRestoranBase();
                dataChangeListener.onDataChanged();
            }
        });

        TextView naziv = (TextView) view.findViewById(R.id.tv_narudzbina_stavka);
        naziv.setText(getItem(position).getStavka().getNaziv());
        TextView cena = (TextView) view.findViewById(R.id.tv_narudzbina_stavka_cena);
        cena.setText(String.valueOf(getItem(position).getStavka().getCena() * getItem(position).getKolicina()));
        TextView kolicina = (TextView) view.findViewById(R.id.tv_narudzbina_stavka_kolicina);
        kolicina.setText("" + getItem(position).getKolicina());

        return view;
    }
}
