package com.ilija.mojrestoran.ui.activity.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.model.Kategorija;

import org.w3c.dom.Text;

/**
 */
public class PodkategorijaFragment extends Fragment {

    public PodkategorijaFragment() {}

    public static PodkategorijaFragment newInstance() {
        return new PodkategorijaFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_podkategorija, container, false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setKategorija(Kategorija kategorija) {}

}
