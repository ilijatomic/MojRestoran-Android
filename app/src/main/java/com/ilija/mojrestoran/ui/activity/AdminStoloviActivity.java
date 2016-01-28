package com.ilija.mojrestoran.ui.activity;

import android.app.DialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.ilija.mojrestoran.AppObject;
import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.model.Sto;
import com.ilija.mojrestoran.ui.adapter.StoloviListAdapter;
import com.ilija.mojrestoran.ui.dialog.AddEditDialog;
import com.ilija.mojrestoran.ui.dialog.DataChangeListener;
import com.ilija.mojrestoran.ui.dialog.DialogDataType;

import java.util.ArrayList;

public class AdminStoloviActivity extends BaseActivity implements DataChangeListener, View.OnClickListener {

    private EditText etBroj;
    private ImageButton btnSearch;
    private FloatingActionButton fabAdd;

    private ListView stolovi;
    private StoloviListAdapter stoloviListAdapter;
    private ArrayList<Sto> listSto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_stolovi);

        addToolbar(true);

        etBroj = (EditText) findViewById(R.id.et_search_broj);
        btnSearch = (ImageButton) findViewById(R.id.btn_search);
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

        fabAdd = (FloatingActionButton) findViewById(R.id.add);
        fabAdd.setOnClickListener(this);

        if (AppObject.getAppInstance().getMojRestoran().getStoArrayList() != null)
            listSto = new ArrayList<>(AppObject.getAppInstance().getMojRestoran().getStoArrayList());
        else
            listSto = new ArrayList<>();

        stoloviListAdapter = new StoloviListAdapter(this, R.layout.list_item_admin_sto, listSto, this);
        stolovi = (ListView) findViewById(R.id.lv_stolovi);
        stolovi.setAdapter(stoloviListAdapter);


    }

    private void clearSearch() {
        etBroj.setText("");
        onDataChanged();
    }

    private void search() {

        String brojSearch = etBroj.getText().toString();

        listSto.clear();
        for (Sto sto : AppObject.getAppInstance().getMojRestoran().getStoArrayList()) {

            if (!brojSearch.isEmpty()) {
                if (!brojSearch.isEmpty() && sto.getBroj() == Integer.parseInt(brojSearch)) {
                    listSto.add(sto);
                    break;
                }
            } else {
                listSto.add(sto);
            }
        }
        stoloviListAdapter.notifyDataSetChanged();

    }


    @Override
    public void onDataChanged() {
        listSto.clear();

        for (Sto sto : AppObject.getAppInstance().getMojRestoran().getStoArrayList())
            listSto.add(sto);
        stoloviListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        DialogFragment addSto = new AddEditDialog(null, this, DialogDataType.STO);
        addSto.show(getFragmentManager(), "AddSto");
    }
}
