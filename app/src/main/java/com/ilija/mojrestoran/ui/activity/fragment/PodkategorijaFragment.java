package com.ilija.mojrestoran.ui.activity.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.ilija.mojrestoran.AppObject;
import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.model.Kategorija;
import com.ilija.mojrestoran.model.Podkategorija;
import com.ilija.mojrestoran.ui.adapter.PodkategorijaListAdapter;
import com.ilija.mojrestoran.ui.dialog.AddEditDialog;
import com.ilija.mojrestoran.ui.dialog.DataChangeListener;
import com.ilija.mojrestoran.ui.dialog.DialogDataType;
import com.ilija.mojrestoran.util.Utilities;

import java.util.ArrayList;

/**
 */
public class PodkategorijaFragment extends Fragment implements DataChangeListener, View.OnClickListener {

    private EditText etNaziv;
    private ImageButton btnSearch;
    private FloatingActionButton fabAdd;
    private Kategorija selectedKategorija;
    private Spinner spKategorija;

    private ListView podkategorije;
    private PodkategorijaListAdapter podkategorijaListAdapter;
    private ArrayList<Podkategorija> listPodkategorijas;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> strings = new ArrayList<>();

    public PodkategorijaFragment() {
    }

    public static PodkategorijaFragment newInstance() {
        return new PodkategorijaFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_podkategorija, container, false);

        etNaziv = (EditText) view.findViewById(R.id.et_search_podkategorija);
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

        spKategorija = (Spinner) view.findViewById(R.id.spinner_kategorija);
        Utilities.populateSpinnerStrings(strings, AppObject.getAppInstance().getMojRestoran().getKategorijaArrayList(), "kategorija");
        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, strings);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKategorija.setAdapter(arrayAdapter);
        spKategorija.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selectedKategorija = null;
                } else {
                    for (Kategorija kategorija : AppObject.getAppInstance().getMojRestoran().getKategorijaArrayList())
                        if (kategorija.getNaziv().equals(arrayAdapter.getItem(position)))
                            selectedKategorija = kategorija;
                }
                search();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listPodkategorijas = new ArrayList<>();
        if (selectedKategorija != null) {
            for (Podkategorija podkategorija : AppObject.getAppInstance().getMojRestoran().getPodkategorijaArrayList())
                if (podkategorija.getKategorija().getId().equals(selectedKategorija.getId()))
                    listPodkategorijas.add(podkategorija);

        } else
            listPodkategorijas = new ArrayList<>(AppObject.getAppInstance().getMojRestoran().getPodkategorijaArrayList());

        podkategorijaListAdapter = new PodkategorijaListAdapter(this, R.layout.list_item_admin_podkategorija, listPodkategorijas, this);
        podkategorije = (ListView) view.findViewById(R.id.lv_podkategorija);
        podkategorije.setAdapter(podkategorijaListAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void clearSearch() {
        etNaziv.setText("");
        spKategorija.setSelection(0);
        search();
    }

    private void search() {

        String nazivSearch = etNaziv.getText().toString();

        listPodkategorijas.clear();

        for (Podkategorija podkategorija : AppObject.getAppInstance().getMojRestoran().getPodkategorijaArrayList()) {
            if (selectedKategorija != null || !nazivSearch.isEmpty()) {
                if (selectedKategorija != null && !podkategorija.getKategorija().getId().equals(selectedKategorija.getId()))
                    continue;
                if (!nazivSearch.isEmpty() && !podkategorija.getNaziv().startsWith(nazivSearch))
                    continue;

                listPodkategorijas.add(podkategorija);
            } else {
                listPodkategorijas.add(podkategorija);
            }
        }

        podkategorijaListAdapter.notifyDataSetChanged();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Utilities.populateSpinnerStrings(strings, AppObject.getAppInstance().getMojRestoran().getKategorijaArrayList(), "kategorija");
            arrayAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDataChanged() {
        search();
    }

    @Override
    public void onClick(View v) {
        AddEditDialog addKategorija = new AddEditDialog();
        addKategorija.setAddEditDialog(null, this, DialogDataType.PODKATEGORIJA);
        if (selectedKategorija != null)
            addKategorija.setKategorija(selectedKategorija);
        addKategorija.show(getActivity().getFragmentManager(), "AddPodkategorija");
    }

    public void setSelectedKategorija(Kategorija kategorija) {
        Utilities.populateSpinnerStrings(strings, AppObject.getAppInstance().getMojRestoran().getKategorijaArrayList(), "kategorija");
        arrayAdapter.notifyDataSetChanged();
        spKategorija.setSelection(arrayAdapter.getPosition(kategorija.getNaziv()));
    }

}
