package com.dzakdzaks.ta_umroh.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.dzakdzaks.ta_umroh.R;
import com.dzakdzaks.ta_umroh.global.EmptyRecyclerView;
import com.dzakdzaks.ta_umroh.home.adapter.AdapterPaket;
import com.dzakdzaks.ta_umroh.home.adapter.AdapterTiket;
import com.dzakdzaks.ta_umroh.home.response.PaketItem;
import com.dzakdzaks.ta_umroh.home.response.ResponsePaket;
import com.dzakdzaks.ta_umroh.retrofit.ApiService;
import com.dzakdzaks.ta_umroh.retrofit.InitLibrary;
import com.dzakdzaks.ta_umroh.session.UserSession;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaketActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rvPaket)
    EmptyRecyclerView rvPaket;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.empty_view)
    LinearLayout emptyView;
    @BindView(R.id.imgAdd)
    FloatingActionButton imgAdd;

    UserSession session;

    LinearLayoutManager linearLayoutManager;

    EditText inputNama, inputDurasi, inputJarakToMadinah, inputJarakToMekah, inputMaskapai, inputHarga, inputBerangkat;
    Spinner spinnerTransit;
    String nama, durasi, transit, jarakMadinah, jarakMekah, maskapai, harga, berangkat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paket);
        ButterKnife.bind(this);

        session = new UserSession(this);

        if (session.getSpRole().equals("2")) {
            imgAdd.hide();
        }

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAdd();
            }
        });

        linearLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        rvPaket.setLayoutManager(linearLayoutManager);
        rvPaket.setHasFixedSize(true);


        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorPrimary,
                R.color.colorPrimaryDark
        );
        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(true);
                getPaket();
            }
        });
    }

    private void dialogAdd() {
        AlertDialog.Builder alert = new AlertDialog.Builder(PaketActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.form_add_paket, null);
        alert.setView(dialogView);
        alert.setCancelable(true);
        alert.setTitle("Tambah Paket");
        alert.setIcon(R.drawable.ic_assignment_black_24dp);

        inputNama = dialogView.findViewById(R.id.input_nama);
        inputDurasi = dialogView.findViewById(R.id.input_durasi);
        inputJarakToMadinah = dialogView.findViewById(R.id.input_toMadinah);
        inputJarakToMekah = dialogView.findViewById(R.id.input_toMekah);
        inputMaskapai = dialogView.findViewById(R.id.input_maskapai);
        inputHarga = dialogView.findViewById(R.id.input_harga);
        inputBerangkat = dialogView.findViewById(R.id.input_keberangkatan);
        spinnerTransit = dialogView.findViewById(R.id.spinner_transit);


        List<String> jenisKelaminOption = new ArrayList<String>();
        jenisKelaminOption.add("Transit");
        jenisKelaminOption.add("Tidak Transit");

        ArrayAdapter<String> spinnerHamilAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, jenisKelaminOption);
        spinnerHamilAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTransit.setAdapter(spinnerHamilAdapter);
        if (transit != null) {
            int spinnerPosition = spinnerHamilAdapter.getPosition(transit);
            spinnerTransit.setSelection(spinnerPosition);
        }
        spinnerTransit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                transit = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addPaket();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();

    }

    private void addPaket() {
        nama = inputNama.getText().toString();
        durasi = inputDurasi.getText().toString() + " Hari";
        jarakMadinah = inputJarakToMadinah.getText().toString();
        jarakMekah = inputJarakToMekah.getText().toString();
        maskapai = inputMaskapai.getText().toString();
        harga = inputHarga.getText().toString();
        berangkat = inputBerangkat.getText().toString();

        ApiService service = InitLibrary.getInstance();
        Call<ResponsePaket> call = service.requestAddPaket(nama, durasi, transit, jarakMadinah, jarakMekah, maskapai, harga, berangkat);
        call.enqueue(new Callback<ResponsePaket>() {
            @Override
            public void onResponse(Call<ResponsePaket> call, Response<ResponsePaket> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PaketActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    onRestart();
                }
            }

            @Override
            public void onFailure(Call<ResponsePaket> call, Throwable t) {
                Toast.makeText(PaketActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onRefresh() {
        getPaket();
    }

    private void getPaket() {
        String id = session.getSpId();

        final ApiService apiService = InitLibrary.getInstance();

        Call<ResponsePaket> call = apiService.requestGetPaket(id);

        call.enqueue(new Callback<ResponsePaket>() {
            @Override
            public void onResponse(Call<ResponsePaket> call, Response<ResponsePaket> response) {
                if (response.isSuccessful()) {
                    String msg = response.body().getMsg();
                    List<PaketItem> paketItems = response.body().getPaket();
                    AdapterPaket adapterPaket = new AdapterPaket(paketItems, getApplicationContext());
                    rvPaket.setAdapter(adapterPaket);
                    if (adapterPaket.getItemCount() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                    }
                    swipeRefresh.setRefreshing(false);
                    Toast.makeText(PaketActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePaket> call, Throwable t) {
                Toast.makeText(PaketActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
