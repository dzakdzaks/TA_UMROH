package com.dzakdzaks.ta_umroh.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dzakdzaks.ta_umroh.R;
import com.dzakdzaks.ta_umroh.global.EmptyRecyclerView;
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

public class TiketActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rvTiket)
    EmptyRecyclerView rvTiket;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.empty_view)
    LinearLayout emptyView;


    UserSession session;

    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiket);
        ButterKnife.bind(this);

        session = new UserSession(this);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        rvTiket.setLayoutManager(linearLayoutManager);
        rvTiket.setHasFixedSize(true);



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
                getTiket();
            }
        });
    }

    @Override
    public void onRefresh() {
        getTiket();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tiket, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.semuaTiket) {
            getTiket();
            return true;
        } else if (id == R.id.belumBayar) {
            getTiketBelumBayar();
            return true;
        } else if (id == R.id.proses) {
            getTiketDiProses();
            return true;
        } else if (id == R.id.sudahBayar) {
            getTiketSudahBayar();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getTiket() {
        String id = session.getSpId();
        ApiService apiService = InitLibrary.getInstance();

        Call<ResponseTiket> call = apiService.requestGetTiket(id);
        call.enqueue(new Callback<ResponseTiket>() {
            @Override
            public void onResponse(Call<ResponseTiket> call, Response<ResponseTiket> response) {
                if (response.isSuccessful()) {
                    String msg = response.body().getMsg();
                    List<TiketItem> tiketItems = response.body().getTiket();
                    AdapterTiket adapterTiket = new AdapterTiket(tiketItems, getApplicationContext());
                    rvTiket.setAdapter(adapterTiket);
                    if (adapterTiket.getItemCount() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                    }
                    swipeRefresh.setRefreshing(false);
                    Toast.makeText(TiketActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTiket> call, Throwable t) {
                Toast.makeText(TiketActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getTiketDiProses() {
        String id = session.getSpId();
        ApiService apiService = InitLibrary.getInstance();

        Call<ResponseTiket> call = apiService.requestTiketDiProsesPerUser(id);
        call.enqueue(new Callback<ResponseTiket>() {
            @Override
            public void onResponse(Call<ResponseTiket> call, Response<ResponseTiket> response) {
                if (response.isSuccessful()) {
                    String msg = response.body().getMsg();
                    List<TiketItem> tiketItems = response.body().getTiket();
                    AdapterTiket adapterTiket = new AdapterTiket(tiketItems, getApplicationContext());
                    rvTiket.setAdapter(adapterTiket);
                    if (adapterTiket.getItemCount() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                    }
                    swipeRefresh.setRefreshing(false);
                    Toast.makeText(TiketActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTiket> call, Throwable t) {
                Toast.makeText(TiketActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getTiketBelumBayar() {
        String id = session.getSpId();
        ApiService apiService = InitLibrary.getInstance();

        Call<ResponseTiket> call = apiService.requestTiketBelumBayar(id);
        call.enqueue(new Callback<ResponseTiket>() {
            @Override
            public void onResponse(Call<ResponseTiket> call, Response<ResponseTiket> response) {
                if (response.isSuccessful()) {
                    String msg = response.body().getMsg();
                    List<TiketItem> tiketItems = response.body().getTiket();
                    AdapterTiket adapterTiket = new AdapterTiket(tiketItems, getApplicationContext());
                    rvTiket.setAdapter(adapterTiket);
                    if (adapterTiket.getItemCount() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                    }
                    swipeRefresh.setRefreshing(false);
                    Toast.makeText(TiketActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTiket> call, Throwable t) {
                Toast.makeText(TiketActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getTiketSudahBayar() {
        String id = session.getSpId();
        ApiService apiService = InitLibrary.getInstance();

        Call<ResponseTiket> call = apiService.requestTiketSudahBayar(id);
        call.enqueue(new Callback<ResponseTiket>() {
            @Override
            public void onResponse(Call<ResponseTiket> call, Response<ResponseTiket> response) {
                if (response.isSuccessful()) {
                    String msg = response.body().getMsg();
                    List<TiketItem> tiketItems = response.body().getTiket();
                    AdapterTiket adapterTiket = new AdapterTiket(tiketItems, getApplicationContext());
                    rvTiket.setAdapter(adapterTiket);
                    if (adapterTiket.getItemCount() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                    }
                    swipeRefresh.setRefreshing(false);
                    Toast.makeText(TiketActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTiket> call, Throwable t) {
                Toast.makeText(TiketActivity.this, "Failed", Toast.LENGTH_SHORT).show();
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
