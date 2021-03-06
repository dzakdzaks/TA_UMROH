package com.dzakdzaks.ta_umroh.admin;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dzakdzaks.ta_umroh.R;
import com.dzakdzaks.ta_umroh.admin.adapter.AdapterAllJamaah;
import com.dzakdzaks.ta_umroh.admin.response.AlljamaahItem;
import com.dzakdzaks.ta_umroh.admin.response.ResponseAllJamaah;
import com.dzakdzaks.ta_umroh.global.EmptyRecyclerView;
import com.dzakdzaks.ta_umroh.home.adapter.AdapterJadwal;
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

public class ListJamaahActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

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
        setContentView(R.layout.activity_list_jamaah);
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
                getJamaah();
            }
        });


    }

    private void getJamaah() {
        String id = session.getSpId();
        ApiService apiService = InitLibrary.getInstance();

        Call<ResponseAllJamaah> call = apiService.getAllJamaah(id);
        call.enqueue(new Callback<ResponseAllJamaah>() {
            @Override
            public void onResponse(Call<ResponseAllJamaah> call, Response<ResponseAllJamaah> response) {
                if (response.isSuccessful()) {
                    String msg = response.body().getMsg();
                    List<AlljamaahItem> tiketItems = response.body().getAlljamaah();
                    AdapterAllJamaah adapterJadwal = new AdapterAllJamaah(tiketItems, getApplicationContext());
                    rvJadwal.setAdapter(adapterJadwal);
                    if (adapterJadwal.getItemCount() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                    }
                    swipeRefresh.setRefreshing(false);
                    Toast.makeText(ListJamaahActivity.this, "Semua Jamaah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseAllJamaah> call, Throwable t) {
                Toast.makeText(ListJamaahActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        getJamaah();
    }
}