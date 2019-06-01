package com.dzakdzaks.ta_umroh.admin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dzakdzaks.ta_umroh.R;
import com.dzakdzaks.ta_umroh.admin.response.AlljamaahItem;
import com.dzakdzaks.ta_umroh.session.UserSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdapterAllJamaah extends RecyclerView.Adapter<AdapterAllJamaah.ViewTiketHolder> {

    private List<AlljamaahItem> tiket;
    private Context context;
    UserSession session;


    public AdapterAllJamaah(List<AlljamaahItem> tiket, Context context) {
        this.tiket = tiket;
        this.context = context;
    }


    @NonNull
    @Override
    public AdapterAllJamaah.ViewTiketHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_jamaah, parent, false);
        session = new UserSession(context);
        return new AdapterAllJamaah.ViewTiketHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterAllJamaah.ViewTiketHolder holder, final int position) {

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.linears.getVisibility() == View.GONE) {
                    holder.linears.setVisibility(View.VISIBLE);
                    holder.img.animate().rotation(180).start();
                    holder.linears.animate().alpha(1.0f);
                } else {
                    holder.linears.setVisibility(View.GONE);
                    holder.img.animate().rotation(360).start();
                    holder.linears.animate().alpha(0.0f);
                }
            }
        });

        holder.tvBio.setText(tiket.get(position).getNamaLengkap());
        holder.tvNoKtp.setText(tiket.get(position).getNoKtp());
        holder.tvNamaLengkap.setText(tiket.get(position).getNamaLengkap());
        holder.tvTempatLahir.setText(tiket.get(position).getTempatLahir());
        holder.tvPekerjaan.setText(tiket.get(position).getPekerjaan());



        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat format2 = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        try {
            Date date = format1.parse(tiket.get(position).getTanggalLahir());
            holder.tvTanggalLahir.setText(format2.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tvJenisKelamin.setText(tiket.get(position).getJenisKelamin());
        holder.tvNamaIbuKandung.setText(tiket.get(position).getNamaIbuKandung());
        holder.tvKewarganegaraan.setText(tiket.get(position).getKewarganegaraan());
        holder.tvNoTelpon.setText(tiket.get(position).getNoTelpon());
        holder.tvEmail.setText(tiket.get(position).getEmail());
        holder.tvKeperluan.setText(tiket.get(position).getKeperluan());
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

        TextView tvBio, tvNoKtp, tvNamaLengkap, tvTempatLahir, tvPekerjaan, tvTanggalLahir, tvJenisKelamin, tvAlamat;
        TextView tvNamaIbuKandung, tvKewarganegaraan, tvNoTelpon, tvEmail, tvKeperluan;
        ImageView img;
        LinearLayout linears;
        CardView cardView;

        public ViewTiketHolder(View v) {
            super(v);
            tvBio = v.findViewById(R.id.tvBio);
            tvNoKtp = v.findViewById(R.id.tvNoKTP);
            tvNamaLengkap = v.findViewById(R.id.tvNamaLengkap);
            tvTempatLahir = v.findViewById(R.id.tvTempatLahir);
            tvPekerjaan = v.findViewById(R.id.tvPekerjaan);
            tvTanggalLahir = v.findViewById(R.id.tvTanggalLahir);
            tvJenisKelamin = v.findViewById(R.id.tvJenisKelamin);
            tvNamaIbuKandung = v.findViewById(R.id.tvAlamat);
            tvKewarganegaraan = v.findViewById(R.id.tvKewarganegaraan);
            tvNoTelpon = v.findViewById(R.id.tvNoTelpon);
            tvEmail = v.findViewById(R.id.tvEmail);
            tvKeperluan = v.findViewById(R.id.tvKeperluan);
            img = v.findViewById(R.id.drop);
            linears = v.findViewById(R.id.view2);
            cardView = v.findViewById(R.id.cardBio);
        }
    }
}
