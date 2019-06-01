package com.dzakdzaks.ta_umroh.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dzakdzaks.ta_umroh.R;
import com.dzakdzaks.ta_umroh.home.response.ResponseGetJamaah;
import com.dzakdzaks.ta_umroh.retrofit.ApiService;
import com.dzakdzaks.ta_umroh.retrofit.InitLibrary;
import com.dzakdzaks.ta_umroh.session.UserSession;
import com.dzakdzaks.ta_umroh.user.ProfilrActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.cardJamaah)
    CardView cardJamaah;
    @BindView(R.id.cardPendaftaran)
    CardView cardPendaftaran;
    @BindView(R.id.cardPembayaran)
    CardView cardPembayaran;
    @BindView(R.id.cardKeberangkatan)
    CardView cardKeberangkatan;
    @BindView(R.id.cardDashboard)
    CardView cardDashboard;
    @BindView(R.id.tvName)
    TextView tvName;


    UserSession session;

    String msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new UserSession(this);

        tvName.setText("Hi, " + session.getSpName());

        if (session.getSpRole().equals("2")) {
            cardDashboard.setVisibility(View.GONE);
        }

        getJamaah();

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            logout();
            return true;
        } else if (id == R.id.us) {
            showAbout();
        } else if (id == R.id.profile) {
            startActivity(new Intent(getApplicationContext(), ProfilrActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.cardJamaah, R.id.cardPendaftaran, R.id.cardPembayaran, R.id.cardKeberangkatan, R.id.cardDashboard})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cardJamaah:
                startActivity(new Intent(getApplicationContext(), JamaahActivtiy.class));
                break;
            case R.id.cardPendaftaran:
                startActivity(new Intent(getApplicationContext(), PaketActivity.class));
                break;
            case R.id.cardPembayaran:
                startActivity(new Intent(getApplicationContext(), TiketActivity.class));
                break;
            case R.id.cardKeberangkatan:
                startActivity(new Intent(getApplicationContext(), JadwalActivity.class));
                break;
            case R.id.cardDashboard:
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                break;
        }
    }

    private void logout() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Logout");
        alert.setIcon(R.mipmap.ic_launcher_round);
        alert.setMessage("Anda yakin ingin keluar?");
        alert.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UserSession sesi = new UserSession(MainActivity.this);
                sesi.logout(); //logout
                finish();
            }
        });
        alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }

    private void showAbout() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.about_us, null);
        alert.setView(dialogView);
        alert.setCancelable(true);
        alert.setTitle("Tentang Kita");
        alert.setIcon(R.drawable.ic_person_black_24dp);
        alert.setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();
    }

    private void getJamaah() {
        final ApiService apiService = InitLibrary.getInstance();
        Call<ResponseGetJamaah> getJamaahCall = apiService.getJamaah(session.getSpId());
        getJamaahCall.enqueue(new Callback<ResponseGetJamaah>() {
            @Override
            public void onResponse(Call<ResponseGetJamaah> call, Response<ResponseGetJamaah> response) {
                msg = response.body().getMsg();
                //jamaah tidak ada
                //jamaah ada
                if (response.isSuccessful()) {
                    if (msg.equals("jamaah tidak ada")) {
                        hidePaket();
                    } else if (msg.equals("jamaah ada")) {
                        showPaket();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseGetJamaah> call, Throwable t) {
                Snackbar.make(getWindow().getDecorView().getRootView(), "Failed", Snackbar.LENGTH_LONG).show();
            }
        });
    }


    public void hidePaket() {
        cardPendaftaran.setVisibility(View.GONE);
    }

    public void showPaket() {
        cardPendaftaran.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
