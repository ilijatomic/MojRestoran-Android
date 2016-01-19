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

import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.model.Kategorija;
import com.ilija.mojrestoran.ui.activity.fragment.PodkategorijaFragment;
import com.ilija.mojrestoran.ui.dialog.AddEditDialog;
import com.ilija.mojrestoran.ui.dialog.DataChangeDialogListener;
import com.ilija.mojrestoran.ui.dialog.DeleteDialog;
import com.ilija.mojrestoran.ui.dialog.DialogDataType;
import com.ilija.mojrestoran.util.ToastMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilija.tomic on 1/19/2016.
 */
public class KategorijeListAdapter extends ArrayAdapter<Kategorija> {

    private Context context;
    private Fragment fragment;
    private DataChangeDialogListener dataChangeDialogListener;
    private ArrayList<Kategorija> kategorijas;

    public KategorijeListAdapter(Fragment fragment, int resource, ArrayList<Kategorija> objects, DataChangeDialogListener dataChangeDialogListener) {
        super(fragment.getContext(), resource, objects);

        this.context = fragment.getContext();
        this.kategorijas = objects;
        this.dataChangeDialogListener = dataChangeDialogListener;
        this.fragment = fragment;

    }

    @Override
    public Kategorija getItem(int position) {
        return kategorijas.get(position);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item_admin_kategorija, null);
        }

        view.setTag(position);

        ImageButton edit = (ImageButton) view.findViewById(R.id.btn_edit);
        edit.setTag(position);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment editKategorija = new AddEditDialog(getItem(position).getId(), dataChangeDialogListener, DialogDataType.KATEGORIJA);
                FragmentActivity fragmentActivity = (FragmentActivity) context;
                editKategorija.show(fragmentActivity.getFragmentManager(), "EditKategorija");
            }
        });
        ImageButton delete = (ImageButton) view.findViewById(R.id.btn_delete);
        delete.setTag(position);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItem(position).getPodkategorijaArrayList().size() > 0) {
                    ToastMessage.showToast(context, "Kategorija ne moze biti obrisana jer sadrzi podkategorije!");
                    return;
                }
                DialogFragment deleteKategorija = new DeleteDialog(getItem(position).getId(), dataChangeDialogListener, DialogDataType.KATEGORIJA);
                FragmentActivity fragmentActivity = (FragmentActivity) context;
                deleteKategorija.show(fragmentActivity.getFragmentManager(), "DeleteKategorija");
            }
        });

        TextView naziv = (TextView) view.findViewById(R.id.tv_naziv_kategorije);
        naziv.setText(getItem(position).getNaziv());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PodkategorijaFragment podkategorijaFragment = (PodkategorijaFragment) fragment.getFragmentManager().findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + 1);
                podkategorijaFragment.setString(getItem(position).getId());

                TabLayout tabLayout = (TabLayout)((Activity) context).findViewById(R.id.sliding_tabs);
                TabLayout.Tab tab = tabLayout.getTabAt(1);
                tab.select();
//                dataChangeDialogListener.onMenuListClick("KAT", getItem(position).getId());
            }
        });

        return view;
    }
}
