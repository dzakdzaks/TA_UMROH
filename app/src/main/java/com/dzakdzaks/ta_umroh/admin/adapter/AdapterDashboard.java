package com.dzakdzaks.ta_umroh.admin.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dzakdzaks.ta_umroh.R;
import com.dzakdzaks.ta_umroh.admin.ConfirmPaymentActivity;
import com.dzakdzaks.ta_umroh.global.GlobalVariable;
import com.dzakdzaks.ta_umroh.global.PreviewPhoto;
import com.dzakdzaks.ta_umroh.home.KonfirmasiTiket;
import com.dzakdzaks.ta_umroh.home.TiketActivity;
import com.dzakdzaks.ta_umroh.home.response.ResponseAddTiket;
import com.dzakdzaks.ta_umroh.home.response.ResponseTiketUpdateStatus;
import com.dzakdzaks.ta_umroh.home.response.TiketItem;
import com.dzakdzaks.ta_umroh.retrofit.ApiService;
import com.dzakdzaks.ta_umroh.retrofit.InitLibrary;
import com.dzakdzaks.ta_umroh.session.UserSession;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterDashboard extends RecyclerView.Adapter<AdapterDashboard.ViewTiketHolder> {

    private List<TiketItem> tiket;
    private Context context;
    UserSession session;


    public AdapterDashboard(List<TiketItem> tiket, Context context) {
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
    public void onBindViewHolder(@NonNull final ViewTiketHolder holder, final int position) {
        final String namaTiket = tiket.get(position).getNama();
        final String namaUser = tiket.get(position).getNamaLengkap();
        final String harga = tiket.get(position).getHarga();
        String berangkat = tiket.get(position).getKeberangkatan();
        String hargaTiket = tiket.get(position).getHarga();
        String statusTiket = tiket.get(position).getStatus();
        final String bukti = tiket.get(position).getBukti();
        final String keterangan = tiket.get(position).getKeteranganBukti();
        Glide.with(context).load(GlobalVariable.BASE_URL + tiket.get(position).getBukti()).into(holder.img);
        holder.tvKeterangan.setText(tiket.get(position).getKeteranganBukti());

        holder.tvTiket.setText("Paket Umroh " + namaTiket);
        holder.tvName.setVisibility(View.VISIBLE);
        holder.tvName.setText(namaUser);
        holder.tvKeberangkatan.setText("Berangkat " + berangkat);
        holder.tvHarga.setText("Rp." + hargaTiket);
        if (statusTiket.equals("Belum Dibayar")) {
            holder.tvStatus1.setBackgroundResource(R.drawable.backtext);
            holder.tvStatus2.setPaintFlags(holder.tvStatus1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvStatus3.setPaintFlags(holder.tvStatus1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else if (statusTiket.equals("Di Proses")) {
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
                final AlertDialog.Builder alert = new AlertDialog.Builder(view.getRootView().getContext() );
                alert.setTitle("Tiket Paket Umroh " + namaTiket + " " + namaUser);
                alert.setIcon(R.mipmap.ic_launcher_round);
                alert.setMessage("Anda yakin " + namaUser + " telah membayar tiket sebesar " + "Rp." + harga + " ?");
                alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String idTiket = tiket.get(position).getIdTiket();
                        String idUser = tiket.get(position).getIdUser();
                        String idPaket = tiket.get(position).getIdPaket();
                        ApiService service = InitLibrary.getInstance();
                        Call<ResponseTiketUpdateStatus> call = service.sendStatusTiket(idTiket, idUser, idPaket, "Sudah Dibayar", bukti, keterangan);
                        call.enqueue(new Callback<ResponseTiketUpdateStatus>() {
                            @Override
                            public void onResponse(Call<ResponseTiketUpdateStatus> call, Response<ResponseTiketUpdateStatus> response) {
                                String msg = response.body().getMsg();
                                if (response.isSuccessful()) {
                                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(context, ConfirmPaymentActivity.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(i);
                                    ((Activity)view.getContext()).finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseTiketUpdateStatus> call, Throwable t) {
                                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();

////                Intent i = new Intent(context, DetailTiketActivity.class);
////                i.putExtra("id_tiket", tiket.get(position).getIdTiket());
////                i.putExtra("id_user", tiket.get(position).getIdUser());
////                i.putExtra("name", tiket.get(position).getName());
////                i.putExtra("email", tiket.get(position).getEmail());
////                i.putExtra("alamat", tiket.get(position).getAlamat());
////                i.putExtra("ttl", tiket.get(position).getTtl());
////                i.putExtra("goldar", tiket.get(position).getGoldar());
////                i.putExtra("notelp", tiket.get(position).getNotelp());
////                i.putExtra("role", tiket.get(position).getRole());
////                i.putExtra("id_paket", tiket.get(position).getIdPaket());
////                i.putExtra("nama", tiket.get(position).getNama());
////                i.putExtra("durasi", tiket.get(position).getDurasi());
////                i.putExtra("transit", tiket.get(position).getTransit());
////                i.putExtra("toMadinah", tiket.get(position).getJarakToMadinah());
////                i.putExtra("toMekah", tiket.get(position).getJarakToMekah());
////                i.putExtra("maskapai", tiket.get(position).getMaskapai());
////                i.putExtra("harga", tiket.get(position).getHarga());
////                i.putExtra("berangkat", tiket.get(position).getKeberangkatan());
////                i.putExtra("status", tiket.get(position).getStatus());
////                Toast.makeText(context, tiket.get(position).getIdTiket() + " clicked", Toast.LENGTH_SHORT).show();
////                context.startActivity(i);
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {

                final AlertDialog.Builder alert = new AlertDialog.Builder(view.getRootView().getContext());
                alert.setTitle("Tolak Tiket");
                alert.setIcon(R.mipmap.ic_launcher_round);
                alert.setMessage("Anda yakin ingin menolak tiket ini?");
                alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(context, "hapus", Toast.LENGTH_SHORT).show();
                        ApiService apiService = InitLibrary.getInstance();
                        Call<ResponseTiketUpdateStatus> call = apiService.sendStatusTiket(tiket.get(position).getIdTiket(), tiket.get(position).getIdUser(), tiket.get(position).getIdPaket(), "Belum Dibayar", tiket.get(position).getBukti(), tiket.get(position).getKeteranganBukti());
                        call.enqueue(new Callback<ResponseTiketUpdateStatus>() {
                            @Override
                            public void onResponse(Call<ResponseTiketUpdateStatus> call, Response<ResponseTiketUpdateStatus> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(context, "Sukses Menolak Tiket", Toast.LENGTH_SHORT).show();
                                    context.startActivity(new Intent(context, ConfirmPaymentActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    ((Activity) view.getContext()).finish();

                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseTiketUpdateStatus> call, Throwable t) {
                                Toast.makeText(context, "Gagal Menolak Tiket", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.show();
                return true;
            }
        });

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, PreviewPhoto.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("image", tiket.get(position).getBukti());
                i.putExtra("keterangan", tiket.get(position).getKeteranganBukti());
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

        TextView tvTiket, tvName, tvKeberangkatan, tvHarga, tvStatus1, tvStatus2, tvStatus3, tvKeterangan;
        CardView cardView;
        ImageView img;

        public ViewTiketHolder(View v) {
            super(v);
            tvTiket = v.findViewById(R.id.tvTiket);
            tvName = v.findViewById(R.id.tvName);
            tvKeberangkatan = v.findViewById(R.id.tvKeberangkatan);
            tvHarga = v.findViewById(R.id.tvHarga);
            tvStatus1 = v.findViewById(R.id.tvStatus1);
            tvStatus2 = v.findViewById(R.id.tvStatus2);
            tvStatus3 = v.findViewById(R.id.tvStatus3);
            tvKeterangan = v.findViewById(R.id.tvketerangan);
            img = v.findViewById(R.id.imgBukti);
            cardView = v.findViewById(R.id.cardTiket);
        }
    }

}