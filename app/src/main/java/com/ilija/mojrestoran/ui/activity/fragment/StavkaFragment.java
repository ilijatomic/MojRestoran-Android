package com.ilija.mojrestoran.ui.activity.fragment;


import android.app.DialogFragment;
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
import com.ilija.mojrestoran.model.Stavka;
import com.ilija.mojrestoran.ui.adapter.StavkaListAdapter;
import com.ilija.mojrestoran.ui.dialog.AddEditDialog;
import com.ilija.mojrestoran.ui.dialog.DataChangeDialogListener;
import com.ilija.mojrestoran.ui.dialog.DialogDataType;
import com.ilija.mojrestoran.util.Utilities;

import java.util.ArrayList;

/**
 */
public class StavkaFragment extends Fragment implements DataChangeDialogListener, View.OnClickListener {

    private EditText etNaziv;
    private EditText etCena;
    private ImageButton btnSearch;
    private FloatingActionButton fabAdd;
    private Kategorija selectedKategorija;
    private Spinner spKategorija;
    private Podkategorija selectedPodkategorija;
    private Spinner spPodkategorija;

    private ListView stavkes;
    private StavkaListAdapter stavkaListAdapter;
    private ArrayList<Stavka> listStavkas;
    private ArrayAdapter<String> arrayAdapterKategorija;
    private ArrayList<String> stringsKategorija = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterPodkategorija;
    private ArrayList<String> stringsPodkategorija = new ArrayList<>();

    public StavkaFragment() {
    }

    public static StavkaFragment newInstance() {
        return new StavkaFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stavka, container, false);
        etNaziv = (EditText) view.findViewById(R.id.et_search_stavka);
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
        Utilities.populateSpinnerStrings(stringsKategorija, AppObject.getAppInstance().getMojRestoran().getKategorijaArrayList(), "kategorija");
        arrayAdapterKategorija = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, stringsKategorija);
        arrayAdapterKategorija.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKategorija.setAdapter(arrayAdapterKategorija);
        spKategorija.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selectedKategorija = null;
                    Utilities.populateSpinnerStrings(stringsPodkategorija, AppObject.getAppInstance().getMojRestoran().getPodkategorijaArrayList(), "podkategorija");
                    arrayAdapterPodkategorija.notifyDataSetChanged();
                } else {
                    for (Kategorija kategorija : AppObject.getAppInstance().getMojRestoran().getKategorijaArrayList())
                        if (kategorija.getNaziv().equals(arrayAdapterKategorija.getItem(position))) {
                            selectedKategorija = kategorija;
                            AppObject.getAppInstance().populatePodkategorijeOfKategorija(selectedKategorija, stringsPodkategorija);
                            arrayAdapterPodkategorija.notifyDataSetChanged();
                        }
                }
                search();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spPodkategorija = (Spinner) view.findViewById(R.id.spinner_podkategorija);
        Utilities.populateSpinnerStrings(stringsPodkategorija, AppObject.getAppInstance().getMojRestoran().getPodkategorijaArrayList(), "podkategorija");
        arrayAdapterPodkategorija = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, stringsPodkategorija);
        arrayAdapterPodkategorija.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPodkategorija.setAdapter(arrayAdapterPodkategorija);
        spPodkategorija.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selectedPodkategorija = null;
                } else {
                    for (Podkategorija podkategorija : AppObject.getAppInstance().getMojRestoran().getPodkategorijaArrayList())
                        if (podkategorija.getNaziv().equals(arrayAdapterPodkategorija.getItem(position))) {
                            selectedPodkategorija = podkategorija;
                            spKategorija.setSelection(arrayAdapterKategorija.getPosition(selectedPodkategorija.getKategorija().getNaziv()));
                        }
                }
                search();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listStavkas = new ArrayList<>();
        if (selectedPodkategorija != null) {
            for (Stavka stavka : AppObject.getAppInstance().getMojRestoran().getStavkaArrayList())
                if (stavka.getPodkategorija().getId().equals(selectedKategorija.getId()))
                    listStavkas.add(stavka);
        } else if (selectedKategorija != null) {
            for (Stavka stavka : AppObject.getAppInstance().getMojRestoran().getStavkaArrayList())
                if (stavka.getPodkategorija().getKategorija().getId().equals(selectedKategorija.getId()))
                    listStavkas.add(stavka);
        } else {
            listStavkas = new ArrayList<>(AppObject.getAppInstance().getMojRestoran().getStavkaArrayList());
        }

        stavkaListAdapter = new StavkaListAdapter(this, R.layout.list_item_admin_stavka, listStavkas, this);
        stavkes = (ListView) view.findViewById(R.id.lv_stavka);
        stavkes.setAdapter(stavkaListAdapter);
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
        spPodkategorija.setSelection(0);
        search();
    }

    private void search() {

        String nazivSearch = etNaziv.getText().toString();

        listStavkas.clear();

        for (Stavka stavka : AppObject.getAppInstance().getMojRestoran().getStavkaArrayList()) {
            if (selectedKategorija != null && selectedPodkategorija != null && !nazivSearch.isEmpty()) {
                if (stavka.getPodkategorija().getKategorija().getId().equals(selectedKategorija.getId())
                        && stavka.getPodkategorija().getId().equals(selectedPodkategorija.getId())
                        && stavka.getNaziv().startsWith(nazivSearch)) {
                    listStavkas.add(stavka);
                }
            } else if (selectedKategorija != null && selectedPodkategorija != null) {
                if (stavka.getPodkategorija().getId().equals(selectedPodkategorija.getId())
                        && stavka.getPodkategorija().getKategorija().getId().equals(selectedPodkategorija.getId())) {
                    listStavkas.add(stavka);
                }
            } else if (selectedPodkategorija != null) {
                if (stavka.getPodkategorija().getId().equals(selectedPodkategorija.getId())) {
                    listStavkas.add(stavka);
                }
            } else if (selectedKategorija != null) {
                if (stavka.getPodkategorija().getKategorija().getId().equals(selectedKategorija.getId())) {
                    listStavkas.add(stavka);
                }
            } else if (!nazivSearch.isEmpty()) {
                if (!nazivSearch.isEmpty() && stavka.getNaziv().startsWith(nazivSearch)) {
                    listStavkas.add(stavka);
                }
            } else {
                listStavkas.add(stavka);
            }
        }

        stavkaListAdapter.notifyDataSetChanged();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Utilities.populateSpinnerStrings(stringsKategorija, AppObject.getAppInstance().getMojRestoran().getKategorijaArrayList(), "kategorija");
            arrayAdapterKategorija.notifyDataSetChanged();
            Utilities.populateSpinnerStrings(stringsPodkategorija, AppObject.getAppInstance().getMojRestoran().getPodkategorijaArrayList(), "podkategorija");
            arrayAdapterPodkategorija.notifyDataSetChanged();
        }
    }

    @Override
    public void onDataChanged() {
        search();
    }

    @Override
    public void onClick(View v) {
        DialogFragment addStavka = new AddEditDialog(null, this, DialogDataType.STAVKA);
        addStavka.show(getActivity().getFragmentManager(), "AddStavka");
    }

    public void setPodkategorija(Podkategorija podkategorija) {
        Utilities.populateSpinnerStrings(stringsPodkategorija, AppObject.getAppInstance().getMojRestoran().getPodkategorijaArrayList(), "podkategorija");
        arrayAdapterPodkategorija.notifyDataSetChanged();
        spPodkategorija.setSelection(arrayAdapterPodkategorija.getPosition(podkategorija.getNaziv()));
    }

}
