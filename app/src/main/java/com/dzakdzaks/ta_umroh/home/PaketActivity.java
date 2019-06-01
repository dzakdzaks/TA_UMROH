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
import com.dzakdzaks.ta_umroh.home.adapter.AdapterPaket;
import com.dzakdzaks.ta_umroh.home.adapter.AdapterTiket;
import com.dzakdzaks.ta_umroh.home.response.PaketItem;
import com.dzakdzaks.ta_umroh.home.response.ResponsePaket;
import com.dzakdzaks.ta_umroh.retrofit.ApiService;
import com.dzakdzaks.ta_umroh.retrofit.InitLibrary;
import com.dzakdzaks.ta_umroh.session.UserSession;

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

    UserSession session;

    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paket);
        ButterKnife.bind(this);

        session = new UserSession(this);

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


}
