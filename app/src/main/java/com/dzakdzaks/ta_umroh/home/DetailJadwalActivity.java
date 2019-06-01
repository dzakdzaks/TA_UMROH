package com.dzakdzaks.ta_umroh.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dzakdzaks.ta_umroh.R;
import com.dzakdzaks.ta_umroh.global.GlobalVariable;
import com.dzakdzaks.ta_umroh.session.UserSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailJadwalActivity extends AppCompatActivity {

    @BindView(R.id.tvPaket)
    TextView tvPaket;
    @BindView(R.id.tvDurasi)
    TextView tvDurasi;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.tvKeberangkatan)
    TextView tvKeberangkatan;
    @BindView(R.id.tvTransit)
    TextView tvTransit;
    @BindView(R.id.tvMaskapai)
    TextView tvMaskapai;
    @BindView(R.id.tvToMadinah)
    TextView tvToMadinah;
    @BindView(R.id.tvToMekkah)
    TextView tvToMekkah;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.tvHarga)
    TextView tvHarga;
    @BindView(R.id.cardPaket)
    CardView cardPaket;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.tvBio)
    TextView tvBio;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.lll)
    LinearLayout lll;
    @BindView(R.id.cardBio)
    CardView cardBio;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tvNoKTP)
    TextView tvNoKTP;
    @BindView(R.id.tvNamaLengkap)
    TextView tvNamaLengkap;
    @BindView(R.id.tvTempatLahir)
    TextView tvTempatLahir;
    @BindView(R.id.tvPekerjaan)
    TextView tvPekerjaan;
    @BindView(R.id.tvTanggalLahir)
    TextView tvTanggalLahir;
    @BindView(R.id.tvJenisKelamin)
    TextView tvJenisKelamin;
    @BindView(R.id.tvAlamat)
    TextView tvAlamat;
    @BindView(R.id.tvNamaIbuKandung)
    TextView tvNamaIbuKandung;
    @BindView(R.id.tvKewarganegaraan)
    TextView tvKewarganegaraan;
    @BindView(R.id.tvNoTelpon)
    TextView tvNoTelpon;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.tvKeperluan)
    TextView tvKeperluan;
    @BindView(R.id.tvketerangan)
    TextView tvketerangan;
    @BindView(R.id.imgBukti)
    ImageView imgBukti;




    UserSession session;

    String idTiket, idUser, idJamaah;
    String noKtp, nama, tempatLahir, pekerjaan, tanggalLahir, jk, alamat, namaIbu, kewarganegaraan, noTelpon, email, keperluan, role;
    String bukti, keterangan;
    String idPaket, namaPaket, durasi, transit, toMadinah, toMekah, maskapai, harga, berangkat, status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_jadwal);
        ButterKnife.bind(this);

        session = new UserSession(this);


        bukti = getIntent().getStringExtra("bukti");
        keterangan = getIntent().getStringExtra("ket_bukti");

        idPaket = getIntent().getStringExtra("id_paket");
        namaPaket = getIntent().getStringExtra("nama");
        durasi = getIntent().getStringExtra("durasi");
        berangkat = getIntent().getStringExtra("berangkat");
        transit = getIntent().getStringExtra("transit");
        maskapai = getIntent().getStringExtra("maskapai");
        toMadinah = getIntent().getStringExtra("toMadinah");
        toMekah = getIntent().getStringExtra("toMekah");
        harga = getIntent().getStringExtra("harga");

        status = getIntent().getStringExtra("status");


        idTiket = getIntent().getStringExtra("id_tiket");
        idUser = getIntent().getStringExtra("id_user");
        idJamaah = getIntent().getStringExtra("id_jamaah");
        noKtp = getIntent().getStringExtra("no_ktp");
        nama = getIntent().getStringExtra("nama_lengkap");
        tempatLahir = getIntent().getStringExtra("tempat_lahir");
        pekerjaan = getIntent().getStringExtra("pekerjaan");
        tanggalLahir = getIntent().getStringExtra("tanggal_lahir");
        jk = getIntent().getStringExtra("jenis_kelamin");
        alamat = getIntent().getStringExtra("alamat");
        namaIbu = getIntent().getStringExtra("nama_ibu_kandung");
        kewarganegaraan = getIntent().getStringExtra("kewarganegaraan");
        noTelpon = getIntent().getStringExtra("no_telpon");
        keperluan = getIntent().getStringExtra("keperluan");
        role = getIntent().getStringExtra("role");


//        Toast.makeText(this, bukti + "\n" + keterangan, Toast.LENGTH_SHORT).show();

        Glide.with(getApplicationContext()).load(GlobalVariable.BASE_URL + bukti).into(imgBukti);
        tvketerangan.setText(keterangan);

        tvPaket.setText("Paket Umroh " + namaPaket);
        tvDurasi.setText(durasi);
        tvKeberangkatan.setText("Berangkat " + berangkat);
        tvTransit.setText(transit);
        tvMaskapai.setText(maskapai);
        tvToMadinah.setText(toMadinah);
        tvToMekkah.setText(toMekah);
        tvHarga.setText("Rp." + harga);

        tvNoKTP.setText(noKtp);
        tvNamaLengkap.setText(nama);
        tvTempatLahir.setText(tempatLahir);
        tvPekerjaan.setText(pekerjaan);
        getDateFormat();
        tvJenisKelamin.setText(jk);
        tvAlamat.setText(alamat);
        tvNamaIbuKandung.setText(namaIbu);
        tvKewarganegaraan.setText(kewarganegaraan);
        tvNoTelpon.setText(noTelpon);
        tvKeperluan.setText(keperluan);
    }

    private void getDateFormat() {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat format2 = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        try {
            Date date = format1.parse(tanggalLahir);
            tanggalLahir = format2.format(date);
            tvTanggalLahir.setText(tanggalLahir);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
