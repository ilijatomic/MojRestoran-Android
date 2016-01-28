package com.ilija.mojrestoran.ui.adapter;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.model.Narudzbina;
import com.ilija.mojrestoran.ui.activity.KonobarNarudzbinaActivity;
import com.ilija.mojrestoran.ui.dialog.DataChangeListener;
import com.ilija.mojrestoran.ui.dialog.DeleteDialog;
import com.ilija.mojrestoran.ui.dialog.DialogDataType;
import com.ilija.mojrestoran.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilija.tomic on 1/28/2016.
 */
public class KonobarNarudzbineListAdapter extends ArrayAdapter<Narudzbina> {

    private Context context;
    private DataChangeListener dataChangeListener;
    private ArrayList<Narudzbina> narudzbinas;

    public KonobarNarudzbineListAdapter(Context context, int resource, ArrayList<Narudzbina> objects, DataChangeListener dataChangeListener) {
        super(context, resource, objects);

        this.context = context;
        this.narudzbinas = objects;
        this.dataChangeListener = dataChangeListener;
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
            view = layoutInflater.inflate(R.layout.list_item_konobar_narudzbina, null);
        }

        view.setTag(position);

        ImageButton delete = (ImageButton) view.findViewById(R.id.btn_delete);
        delete.setTag(position);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment deleteKategorija = new DeleteDialog(getItem(position).getId(), dataChangeListener, DialogDataType.NARUDZBINA);
                FragmentActivity fragmentActivity = (FragmentActivity) context;
                deleteKategorija.show(fragmentActivity.getFragmentManager(), "DeleteNarudzbina");
            }
        });

        TextView brojStola = (TextView) view.findViewById(R.id.tv_broj_stola);
        brojStola.setText("" + getItem(position).getSto().getBroj());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, KonobarNarudzbinaActivity.class);
                intent.putExtra(Constants.EXTRA_NARUDZBINA_ID, getItem(position).getId());
                context.startActivity(intent);
            }
        });

        return view;
    }
}
