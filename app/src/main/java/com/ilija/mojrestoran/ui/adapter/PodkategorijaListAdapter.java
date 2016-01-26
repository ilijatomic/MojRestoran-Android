package com.ilija.mojrestoran.ui.adapter;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ilija.mojrestoran.AppObject;
import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.model.Podkategorija;
import com.ilija.mojrestoran.model.Stavka;
import com.ilija.mojrestoran.ui.activity.fragment.PodkategorijaFragment;
import com.ilija.mojrestoran.ui.activity.fragment.StavkaFragment;
import com.ilija.mojrestoran.ui.dialog.AddEditDialog;
import com.ilija.mojrestoran.ui.dialog.DataChangeDialogListener;
import com.ilija.mojrestoran.ui.dialog.DeleteDialog;
import com.ilija.mojrestoran.ui.dialog.DialogDataType;
import com.ilija.mojrestoran.util.ToastMessage;

import java.util.ArrayList;

/**
 * Created by ilija.tomic on 1/25/2016.
 */
public class PodkategorijaListAdapter extends ArrayAdapter<Podkategorija> {

    private Context context;
    private Fragment fragment;
    private DataChangeDialogListener dataChangeDialogListener;
    private ArrayList<Podkategorija> podkategorijas;

    public PodkategorijaListAdapter(Fragment fragment, int resource, ArrayList<Podkategorija> objects, DataChangeDialogListener dataChangeDialogListener) {
        super(fragment.getContext(), resource, objects);

        this.context = fragment.getContext();
        this.podkategorijas = objects;
        this.dataChangeDialogListener = dataChangeDialogListener;
        this.fragment = fragment;
    }

    @Override
    public Podkategorija getItem(int position) {
        return podkategorijas.get(position);
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
                DialogFragment editPodkategorija = new AddEditDialog(getItem(position).getId(), dataChangeDialogListener, DialogDataType.PODKATEGORIJA);
                FragmentActivity fragmentActivity = (FragmentActivity) context;
                editPodkategorija.show(fragmentActivity.getFragmentManager(), "EditPodkategorija");
            }
        });
        ImageButton delete = (ImageButton) view.findViewById(R.id.btn_delete);
        delete.setTag(position);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Stavka stavka : AppObject.getAppInstance().getMojRestoran().getStavkaArrayList()) {
                    if (stavka.getPodkategorija().getId().equals(getItem(position).getId())) {
                        ToastMessage.showToast(context, "Podkategorija ne moze biti obrisana jer sadrzi stavke!");
                        return;
                    }
                }

                DialogFragment deleteKategorija = new DeleteDialog(getItem(position).getId(), dataChangeDialogListener, DialogDataType.PODKATEGORIJA);
                FragmentActivity fragmentActivity = (FragmentActivity) context;
                deleteKategorija.show(fragmentActivity.getFragmentManager(), "DeletePodkategorija");
            }
        });

        TextView nazivKategorije = (TextView) view.findViewById(R.id.tv_naziv_kategorije);
        nazivKategorije.setText(getItem(position).getKategorija().getNaziv());
        TextView naziv = (TextView) view.findViewById(R.id.tv_naziv_podkategorije);
        naziv.setText(getItem(position).getNaziv());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StavkaFragment stavkaFragment = (StavkaFragment) fragment.getFragmentManager().findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + 2);
                stavkaFragment.setPodkategorija(getItem(position));

                TabLayout tabLayout = (TabLayout) ((Activity) context).findViewById(R.id.sliding_tabs);
                TabLayout.Tab tab = tabLayout.getTabAt(2);
                tab.select();
            }
        });

        return view;
    }
}
