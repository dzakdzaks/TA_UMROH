package com.dzakdzaks.ta_umroh.global;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dzakdzaks.ta_umroh.R;
import com.github.chrisbanes.photoview.PhotoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PreviewPhoto extends AppCompatActivity {

    @BindView(R.id.photo_view)
    PhotoView photoView;
    @BindView(R.id.btnIvClose)
    Button btnIvClose;
    @BindView(R.id.tvKet)
    TextView tvKeterangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_photo);
        ButterKnife.bind(this);

        String image = getIntent().getStringExtra("image");
        String ket = getIntent().getStringExtra("keterangan");

        Glide.with(this).load(GlobalVariable.BASE_URL + image).into(photoView);
        tvKeterangan.setText(ket);

    }

    @OnClick(R.id.btnIvClose)
    public void onViewClicked() {
        onBackPressed();
    }
}
