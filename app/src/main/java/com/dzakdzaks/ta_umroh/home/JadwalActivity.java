package com.dzakdzaks.ta_umroh.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dzakdzaks.ta_umroh.R;
import com.dzakdzaks.ta_umroh.global.EmptyRecyclerView;
import com.dzakdzaks.ta_umroh.home.adapter.AdapterJadwal;
import com.dzakdzaks.ta_umroh.home.adapter.AdapterTiket;
import com.dzakdzaks.ta_umroh.home.response.ResponseTiket;
import com.dzakdzaks.ta_umroh.home.response.TiketItem;
import com.dzakdzaks.ta_umroh.retrofit.ApiService;
import com.dzakdzaks.ta_umroh.retrofit.InitLibrary;
import com.dzakdzaks.ta_umroh.session.UserSession;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JadwalActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rvJadwal)
    EmptyRecyclerView rvJadwal;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.empty_view)
    LinearLayout emptyView;


    UserSession session;

    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);
        ButterKnife.bind(this);

        session = new UserSession(this);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        rvJadwal.setLayoutManager(linearLayoutManager);
        rvJadwal.setHasFixedSize(true);



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
                getJadwal();
            }
        });
    }

    private void getJadwal() {
        String id = session.getSpId();
        ApiService apiService = InitLibrary.getInstance();

        Call<ResponseTiket> call = apiService.requestTiketSudahBayar(id);
        call.enqueue(new Callback<ResponseTiket>() {
            @Override
            public void onResponse(Call<ResponseTiket> call, Response<ResponseTiket> response) {
                if (response.isSuccessful()) {
                    String msg = response.body().getMsg();
                    List<TiketItem> tiketItems = response.body().getTiket();
                    AdapterJadwal adapterJadwal = new AdapterJadwal(tiketItems, getApplicationContext());
                    rvJadwal.setAdapter(adapterJadwal);
                    if (adapterJadwal.getItemCount() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                    }
                    swipeRefresh.setRefreshing(false);
                    Toast.makeText(JadwalActivity.this, "Semua Jadwal Keberangkatan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTiket> call, Throwable t) {
                Toast.makeText(JadwalActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        getJadwal();
    }
}
