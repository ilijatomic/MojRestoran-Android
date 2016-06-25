package com.ilija.mojrestoran.ui.activity.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.ilija.mojrestoran.AppObject;
import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.model.Kategorija;
import com.ilija.mojrestoran.ui.adapter.KategorijeListAdapter;
import com.ilija.mojrestoran.ui.dialog.AddEditDialog;
import com.ilija.mojrestoran.ui.dialog.DataChangeListener;
import com.ilija.mojrestoran.ui.dialog.DialogDataType;

import java.util.ArrayList;

public class KategorijaFragment extends Fragment implements View.OnClickListener, DataChangeListener {

    private EditText etNaziv;
    private ImageButton btnSearch;
    private FloatingActionButton fabAdd;

    private ListView kategorije;
    private KategorijeListAdapter kategorijeListAdapter;
    private ArrayList<Kategorija> listKategorija;

    public KategorijaFragment() {
    }

    public static KategorijaFragment newInstance() {
        return new KategorijaFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kategorija, container, false);

        etNaziv = (EditText) view.findViewById(R.id.et_search_kategorija);
        btnSearch = (ImageButton) view.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
        btnSearch.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clearSearch();
                return true;
            }
        });

        fabAdd = (FloatingActionButton) view.findViewById(R.id.add);
        fabAdd.setOnClickListener(this);

        if (AppObject.getAppInstance().getMojRestoran().getKategorijaArrayList() != null)
            listKategorija = new ArrayList<>(AppObject.getAppInstance().getMojRestoran().getKategorijaArrayList());
        else
            listKategorija = new ArrayList<>();

        kategorijeListAdapter = new KategorijeListAdapter(this, R.layout.list_item_admin_kategorija, listKategorija, this);
        kategorije = (ListView) view.findViewById(R.id.lv_kategorija);
        kategorije.setAdapter(kategorijeListAdapter);

        return view;

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void clearSearch() {
        etNaziv.setText("");
        onDataChanged();
    }

    private void search() {

        String nazivSearch = etNaziv.getText().toString();

        listKategorija.clear();
        for (Kategorija kategorija : AppObject.getAppInstance().getMojRestoran().getKategorijaArrayList()) {

            if (!nazivSearch.isEmpty()) {
                if (!nazivSearch.isEmpty() && kategorija.getNaziv().startsWith(nazivSearch)) {
                    listKategorija.add(kategorija);
                }
            } else {
                listKategorija.add(kategorija);
            }
        }
        kategorijeListAdapter.notifyDataSetChanged();

    }


    @Override
    public void onDataChanged() {
        search();
    }

    @Override
    public void onClick(View v) {
        AddEditDialog addKategorija = new AddEditDialog();
        addKategorija.setAddEditDialog(null, this, DialogDataType.KATEGORIJA);
        addKategorija.show(getActivity().getFragmentManager(), "AddKategorija");
    }

}
