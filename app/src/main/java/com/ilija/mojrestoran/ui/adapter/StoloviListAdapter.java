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
import com.ilija.mojrestoran.model.Sto;
import com.ilija.mojrestoran.ui.dialog.AddEditDialog;
import com.ilija.mojrestoran.ui.dialog.DataChangeDialogListener;
import com.ilija.mojrestoran.ui.dialog.DeleteDialog;
import com.ilija.mojrestoran.ui.dialog.DialogDataType;

import java.util.ArrayList;

/**
 * Created by Ilija on 1/16/2016.
 */
public class StoloviListAdapter extends ArrayAdapter<Sto> {

    private Context context;
    private DataChangeDialogListener dataChangeDialogListener;
    private ArrayList<Sto> stos;

    public StoloviListAdapter(Context context, int resource, ArrayList<Sto> objects, DataChangeDialogListener dataChangeDialogListener) {
        super(context, resource, objects);

        this.context = context;
        this.stos = objects;
        this.dataChangeDialogListener = dataChangeDialogListener;
    }

    @Override
    public Sto getItem(int position) {
        return stos.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item_admin_sto, null);
        }

        view.setTag(position);

        ImageButton edit = (ImageButton) view.findViewById(R.id.btn_edit);
        edit.setTag(position);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment editSto = new AddEditDialog(getItem(position).getId(), dataChangeDialogListener, DialogDataType.STO);
                FragmentActivity fragmentActivity = (FragmentActivity) context;
                editSto.show(fragmentActivity.getFragmentManager(), "EditSto");
            }
        });
        ImageButton delete = (ImageButton) view.findViewById(R.id.btn_delete);
        delete.setTag(position);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment deleteSto = new DeleteDialog(getItem(position).getId(), dataChangeDialogListener, DialogDataType.STO);
                FragmentActivity fragmentActivity = (FragmentActivity) context;
                deleteSto.show(fragmentActivity.getFragmentManager(), "DeleteSto");
            }
        });

        TextView broj = (TextView) view.findViewById(R.id.tv_broj);
        broj.setText("" + getItem(position).getBroj());

        return view;
    }
}
