package com.dzakdzaks.ta_umroh.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dzakdzaks.ta_umroh.R;
import com.dzakdzaks.ta_umroh.home.DetailJadwalActivity;
import com.dzakdzaks.ta_umroh.home.response.TiketItem;
import com.dzakdzaks.ta_umroh.session.UserSession;

import java.util.List;

public class AdapterJadwal extends RecyclerView.Adapter<AdapterJadwal.ViewTiketHolder> {

    private List<TiketItem> tiket;
    private Context context;
    UserSession session;


    public AdapterJadwal(List<TiketItem> tiket, Context context) {
        this.tiket = tiket;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewTiketHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_jadwall, parent, false);
        session = new UserSession(context);
        return new ViewTiketHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewTiketHolder holder, final int position) {
        String jadwal = tiket.get(position).getNama();
        String nama = tiket.get(position).getNamaLengkap();
        String berangkat = tiket.get(position).getKeberangkatan();
        holder.tvJadwal.setText("Paket Umroh " + jadwal);
        holder.tvPerson.setText(nama);
        holder.tvKeberangkatan.setText("Berangkat " + berangkat);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailJadwalActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("id_tiket", tiket.get(position).getIdTiket());
                i.putExtra("id_user", tiket.get(position).getIdUser());
                i.putExtra("id_jamaah", tiket.get(position).getIdJamaah());
                i.putExtra("no_ktp", tiket.get(position).getNoKtp());
                i.putExtra("nama_lengkap", tiket.get(position).getNamaLengkap());
                i.putExtra("tempat_lahir", tiket.get(position).getTempatLahir());
                i.putExtra("pekerjaan", tiket.get(position).getPekerjaan());
                i.putExtra("tanggal_lahir", tiket.get(position).getTanggalLahir());
                i.putExtra("jenis_kelamin", tiket.get(position).getJenisKelamin());
                i.putExtra("alamat", tiket.get(position).getAlamat());
                i.putExtra("nama_ibu_kandung", tiket.get(position).getNamaIbuKandung());
                i.putExtra("kewarganegaraan", tiket.get(position).getKewarganegaraan());
                i.putExtra("no_telpon", tiket.get(position).getNoTelpon());
                i.putExtra("keperluan", tiket.get(position).getKeperluan());
                i.putExtra("role", tiket.get(position).getRole());
                i.putExtra("id_paket", tiket.get(position).getIdPaket());
                i.putExtra("nama", tiket.get(position).getNama());
                i.putExtra("durasi", tiket.get(position).getDurasi());
                i.putExtra("transit", tiket.get(position).getTransit());
                i.putExtra("toMadinah", tiket.get(position).getJarakToMadinah());
                i.putExtra("toMekah", tiket.get(position).getJarakToMekah());
                i.putExtra("maskapai", tiket.get(position).getMaskapai());
                i.putExtra("harga", tiket.get(position).getHarga());
                i.putExtra("berangkat", tiket.get(position).getKeberangkatan());
                i.putExtra("status", tiket.get(position).getStatus());
                i.putExtra("bukti", tiket.get(position).getBukti());
                i.putExtra("ket_bukti", tiket.get(position).getKeteranganBukti());
//                Toast.makeText(context, tiket.get(position).getNamaLengkap() + " clicked", Toast.LENGTH_SHORT).show();
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (tiket == null) {
            return 0;
        } else {
            return tiket.size();
        }
    }

    public static class ViewTiketHolder extends RecyclerView.ViewHolder {

        TextView tvPerson, tvJadwal, tvKeberangkatan;
        CardView cardView;

        public ViewTiketHolder(View v) {
            super(v);
            tvPerson = v.findViewById(R.id.tvPerson);
            tvJadwal = v.findViewById(R.id.tvJadwal);
            tvKeberangkatan = v.findViewById(R.id.tvKeberangkatan);
            cardView = v.findViewById(R.id.cardJadwal);
        }
    }
}