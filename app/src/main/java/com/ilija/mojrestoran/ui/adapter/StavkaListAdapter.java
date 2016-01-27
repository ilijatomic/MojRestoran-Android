package com.ilija.mojrestoran.ui.adapter;

import android.app.DialogFragment;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.model.Stavka;
import com.ilija.mojrestoran.ui.dialog.AddEditDialog;
import com.ilija.mojrestoran.ui.dialog.DataChangeDialogListener;
import com.ilija.mojrestoran.ui.dialog.DeleteDialog;
import com.ilija.mojrestoran.ui.dialog.DialogDataType;

import java.util.ArrayList;

/**
 * Created by ilija.tomic on 1/27/2016.
 */
public class StavkaListAdapter extends ArrayAdapter<Stavka> {

    private Context context;
    private Fragment fragment;
    private DataChangeDialogListener dataChangeDialogListener;
    private ArrayList<Stavka> stavkas;

    public StavkaListAdapter(Fragment fragment, int resource, ArrayList<Stavka> objects, DataChangeDialogListener dataChangeDialogListener) {
        super(fragment.getContext(), resource, objects);

        this.context = fragment.getContext();
        this.stavkas = objects;
        this.dataChangeDialogListener = dataChangeDialogListener;
        this.fragment = fragment;
    }

    @Override
    public Stavka getItem(int position) {
        return stavkas.get(position);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item_admin_podkategorija, null);
        }

        view.setTag(position);

        ImageButton edit = (ImageButton) view.findViewById(R.id.btn_edit);
        edit.setTag(position);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment editPodkategorija = new AddEditDialog(getItem(position).getId(), dataChangeDialogListener, DialogDataType.STAVKA);
                FragmentActivity fragmentActivity = (FragmentActivity) context;
                editPodkategorija.show(fragmentActivity.getFragmentManager(), "EditPodkategorija");
            }
        });
        ImageButton delete = (ImageButton) view.findViewById(R.id.btn_delete);
        delete.setTag(position);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment deleteKategorija = new DeleteDialog(getItem(position).getId(), dataChangeDialogListener, DialogDataType.STAVKA);
                FragmentActivity fragmentActivity = (FragmentActivity) context;
                deleteKategorija.show(fragmentActivity.getFragmentManager(), "DeleteStavka");
            }
        });

        TextView nazivKategorije = (TextView) view.findViewById(R.id.tv_naziv_kategorije);
        nazivKategorije.setText(getItem(position).getPodkategorija().getKategorija().getNaziv());
        TextView nazivPodkategorije = (TextView) view.findViewById(R.id.tv_naziv_podkategorije);
        nazivPodkategorije.setText(getItem(position).getPodkategorija().getNaziv());
        TextView naziv = (TextView) view.findViewById(R.id.tv_stavka_naziv);
        naziv.setText(getItem(position).getNaziv());
        TextView cena = (TextView) view.findViewById(R.id.tv_stavka_cena);
        cena.setText("" + getItem(position).getCena());

        return view;
    }
}
