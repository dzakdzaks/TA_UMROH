package com.dzakdzaks.ta_umroh.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.dzakdzaks.ta_umroh.R;
import com.dzakdzaks.ta_umroh.admin.ConfirmPaymentActivity;
import com.dzakdzaks.ta_umroh.admin.JadwalAdminActivity;
import com.dzakdzaks.ta_umroh.admin.ListJamaahActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardActivity extends AppCompatActivity {

    @BindView(R.id.tvAdminName)
    TextView tvAdminName;
    @BindView(R.id.cardAdminKonfirmasi)
    CardView cardAdminKonfirmasi;
    @BindView(R.id.cardAdminKeberangkatan)
    CardView cardAdminKeberangkatan;
    @BindView(R.id.cardAdminJamaah)
    CardView cardAdminJamaah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.cardAdminKonfirmasi, R.id.cardAdminKeberangkatan, R.id.cardAdminJamaah})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cardAdminKonfirmasi:
                startActivity(new Intent(getApplicationContext(), ConfirmPaymentActivity.class));
                break;
            case R.id.cardAdminKeberangkatan:
                startActivity(new Intent(getApplicationContext(), JadwalAdminActivity.class));
                break;
            case R.id.cardAdminJamaah:
                startActivity(new Intent(getApplicationContext(), ListJamaahActivity.class));
                break;
        }
    }
}
