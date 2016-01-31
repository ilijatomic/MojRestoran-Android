package com.ilija.mojrestoran.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.model.NaruceneStavke;
import com.ilija.mojrestoran.model.Narudzbina;
import com.ilija.mojrestoran.model.Racun;
import com.ilija.mojrestoran.ui.activity.AdminNarudzbinaDetaljiActivity;
import com.ilija.mojrestoran.ui.activity.KonobarNarudzbinaActivity;
import com.ilija.mojrestoran.ui.dialog.DataChangeListener;
import com.ilija.mojrestoran.util.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ilija on 1/31/2016.
 */
public class NarudzbinaListAdapter extends ArrayAdapter<Narudzbina> {

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

    private Context context;
    private ArrayList<Narudzbina> narudzbinas;
    private int layout;

    public NarudzbinaListAdapter(Context context, int resource, ArrayList<Narudzbina> objects) {
        super(context, resource, objects);

        this.context = context;
        this.layout = resource;
        this.narudzbinas = objects;
    }

    @Override
    public Narudzbina getItem(int position) {
        return narudzbinas.get(position);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout, null);
        }

        view.setTag(position);

        // TODO check if this need to be implemented
        /*ImageButton delete = (ImageButton) view.findViewById(R.id.btn_delete);
        delete.setTag(position);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeleteDialog deleteKategorija = new DeleteDialog();
                deleteKategorija.setDeleteDialog(getItem(position).getId(), dataChangeListener, DialogDataType.NARUDZBINA);
                FragmentActivity fragmentActivity = (FragmentActivity) context;
                deleteKategorija.show(fragmentActivity.getFragmentManager(), "DeleteNarudzbina");
            }
        });*/

        TextView datum = (TextView) view.findViewById(R.id.nar_datum);
        datum.setText(dateFormatter.format(getItem(position).getDatum()) + ";");
        TextView ime = (TextView) view.findViewById(R.id.nar_ime);
        ime.setText(getItem(position).getKorisnik().getIme());
        TextView prezime = (TextView) view.findViewById(R.id.nar_prezime);
        prezime.setText(getItem(position).getKorisnik().getPrezime() + ";");
        TextView ukupno = (TextView) view.findViewById(R.id.nar_ukupno);
        ukupno.setText("" + izracunajUkupnuCenu(getItem(position).getRacunArrayList()) + " din");

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AdminNarudzbinaDetaljiActivity.class);
                intent.putExtra(Constants.EXTRA_NARUDZBINA_ID, getItem(position).getId());
                context.startActivity(intent);
            }
        });

        return view;
    }

    private double izracunajUkupnuCenu(ArrayList<Racun> racuns) {

        double ukupno = 0;

        for (Racun racun : racuns)
            for (NaruceneStavke naruceneStavke : racun.getNaplaceneStavke())
                ukupno += naruceneStavke.getStavka().getCena() * naruceneStavke.getKolicina();

        return ukupno;
    }
}
