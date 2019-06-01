package com.dzakdzaks.ta_umroh.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dzakdzaks.ta_umroh.R;
import com.dzakdzaks.ta_umroh.global.GlobalVariable;
import com.dzakdzaks.ta_umroh.home.KonfirmasiTiket;
import com.dzakdzaks.ta_umroh.home.TiketActivity;
import com.dzakdzaks.ta_umroh.home.response.ResponseTiketUpdateStatus;
import com.dzakdzaks.ta_umroh.home.response.TiketItem;
import com.dzakdzaks.ta_umroh.retrofit.ApiService;
import com.dzakdzaks.ta_umroh.retrofit.InitLibrary;
import com.dzakdzaks.ta_umroh.session.UserSession;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterTiket extends RecyclerView.Adapter<AdapterTiket.ViewTiketHolder> {

    private List<TiketItem> tiket;
    private Context context;
    UserSession session;


    public AdapterTiket(List<TiketItem> tiket, Context context) {
        this.tiket = tiket;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewTiketHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tiket, parent, false);
        session = new UserSession(context);
        return new ViewTiketHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewTiketHolder holder, final int position) {
        final String namaTiket = tiket.get(position).getNama();
        final String harga = tiket.get(position).getHarga();
        String berangkat = tiket.get(position).getKeberangkatan();
        String hargaTiket = tiket.get(position).getHarga();
        String statusTiket = tiket.get(position).getStatus();

        if (tiket.get(position).getBukti().isEmpty()) {
            holder.img.setVisibility(View.GONE);
            holder.tvKeterangan.setVisibility(View.GONE);
        }

        Glide.with(context).load(GlobalVariable.BASE_URL + tiket.get(position).getBukti()).into(holder.img);
        holder.tvKeterangan.setText(tiket.get(position).getKeteranganBukti());


        holder.tvTiket.setText("Paket Umroh " + namaTiket);
        holder.tvKeberangkatan.setText("Berangkat " + berangkat);
        holder.tvHarga.setText("Rp." + hargaTiket);
        if (statusTiket.equals("Belum Dibayar")) {
            holder.tvStatus1.setBackgroundResource(R.drawable.backtext);
            holder.tvStatus2.setPaintFlags(holder.tvStatus1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvStatus3.setPaintFlags(holder.tvStatus1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else if (statusTiket.equals("Di Proses")) {
            holder.cardView.setEnabled(false);
            holder.tvStatus2.setBackgroundResource(R.drawable.backtext);
            holder.tvStatus1.setPaintFlags(holder.tvStatus2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvStatus3.setPaintFlags(holder.tvStatus2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else if (statusTiket.equals("Sudah Dibayar")) {
            holder.cardView.setEnabled(false);
            holder.tvStatus3.setBackgroundResource(R.drawable.backtext);
            holder.tvStatus1.setPaintFlags(holder.tvStatus3.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvStatus2.setPaintFlags(holder.tvStatus3.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.tvStatus1.setPaintFlags(holder.tvStatus3.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvStatus2.setPaintFlags(holder.tvStatus3.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvStatus3.setPaintFlags(holder.tvStatus2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
//                AlertDialog.Builder alert = new AlertDialog.Builder(view.getRootView().getContext() );
//                alert.setTitle("Bayar Tiket " + namaTiket);
//                alert.setIcon(R.mipmap.ic_launcher_round);
//                alert.setMessage("Anda yakin ingin membayar tiket ini sebesar " + "Rp." + harga + " ?");
//                alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String idTiket = tiket.get(position).getIdTiket();
//                        String idUser = tiket.get(position).getIdUser();
//                        String idPaket = tiket.get(position).getIdPaket();
//                        ApiService service = InitLibrary.getInstance();
//                        Call<ResponseTiketUpdateStatus> call = service.sendStatusTiket(idTiket, idUser, idPaket, "Di Proses");
//                        call.enqueue(new Callback<ResponseTiketUpdateStatus>() {
//                            @Override
//                            public void onResponse(Call<ResponseTiketUpdateStatus> call, Response<ResponseTiketUpdateStatus> response) {
//                                String msg = response.body().getMsg();
//                                if (response.isSuccessful()) {
//                                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
//                                    Intent i = new Intent(context, TiketActivity.class);
//                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    context.startActivity(i);
//                                    ((Activity)view.getContext()).finish();
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<ResponseTiketUpdateStatus> call, Throwable t) {
//                                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                });
//                alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                alert.show();
                Intent i = new Intent(context, KonfirmasiTiket.class);
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
                Toast.makeText(context, tiket.get(position).getIdTiket() + " clicked", Toast.LENGTH_SHORT).show();
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

        TextView tvTiket, tvKeberangkatan, tvHarga, tvStatus1, tvStatus2, tvStatus3, tvKeterangan;
        CardView cardView;
        ImageView img;

        public ViewTiketHolder(View v) {
            super(v);
            tvTiket = v.findViewById(R.id.tvTiket);
            tvKeberangkatan = v.findViewById(R.id.tvKeberangkatan);
            tvHarga = v.findViewById(R.id.tvHarga);
            tvStatus1 = v.findViewById(R.id.tvStatus1);
            tvStatus2 = v.findViewById(R.id.tvStatus2);
            tvStatus3 = v.findViewById(R.id.tvStatus3);
            tvStatus3 = v.findViewById(R.id.tvStatus3);
            tvKeterangan = v.findViewById(R.id.tvketerangan);
            img = v.findViewById(R.id.imgBukti);
            cardView = v.findViewById(R.id.cardTiket);
        }
    }
}