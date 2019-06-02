package com.dzakdzaks.ta_umroh.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dzakdzaks.ta_umroh.R;
import com.dzakdzaks.ta_umroh.home.KonfirmasiTiket;
import com.dzakdzaks.ta_umroh.home.PaketActivity;
import com.dzakdzaks.ta_umroh.home.TiketActivity;
import com.dzakdzaks.ta_umroh.home.response.PaketItem;
import com.dzakdzaks.ta_umroh.home.response.ResponseAddTiket;
import com.dzakdzaks.ta_umroh.home.response.ResponsePaket;
import com.dzakdzaks.ta_umroh.retrofit.ApiService;
import com.dzakdzaks.ta_umroh.retrofit.InitLibrary;
import com.dzakdzaks.ta_umroh.session.UserSession;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterPaket extends RecyclerView.Adapter<AdapterPaket.ViewPaketHolder> {

    private List<PaketItem> paket;
    private Context context;
    UserSession session;


    public AdapterPaket(List<PaketItem> paket, Context context) {
        this.paket = paket;
        this.context = context;
    }

    EditText inputNama, inputDurasi, inputJarakToMadinah, inputJarakToMekah, inputMaskapai, inputHarga, inputBerangkat;
    Spinner spinnerTransit;
    String nama, durasi, transit, jarakMadinah, jarakMekah, maskapai, harga, berangkat;


    @NonNull
    @Override
    public ViewPaketHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_paket, parent, false);
        session = new UserSession(context);
        return new ViewPaketHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPaketHolder holder, final int position) {
        holder.tvPaket.setText("Paket Umroh " + paket.get(position).getNama());
        holder.tvDurasi.setText(paket.get(position).getDurasi());
        holder.tvKeberangkatan.setText("Berangkat " + paket.get(position).getKeberangkatan());
        holder.tvTransit.setText(paket.get(position).getTransit());
        holder.tvMaskapai.setText(paket.get(position).getMaskapai());
        holder.tvToMadinah.setText(paket.get(position).getJarakToMadinah());
        holder.tvToMekkah.setText(paket.get(position).getJarakToMekah());
        holder.tvHarga.setText("Rp." + paket.get(position).getHarga());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getRootView().getContext());
                alert.setTitle("Booking Paket Umroh " + paket.get(position).getNama());
                alert.setIcon(R.mipmap.ic_launcher_round);
                alert.setMessage("Anda yakin ingin memesan paket ini?");
                alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String idUser = session.getSpId();
                        ApiService apiService = InitLibrary.getInstance();
                        Call<ResponseAddTiket> callTiket = apiService.sendTiket(idUser, paket.get(position).getId());
                        callTiket.enqueue(new Callback<ResponseAddTiket>() {
                            @Override
                            public void onResponse(Call<ResponseAddTiket> call, Response<ResponseAddTiket> response) {
                                String msg = response.body().getMsg();
                                if (response.isSuccessful()) {
                                    Toast.makeText(context, msg + " \uD83D\uDE00", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(context, TiketActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(i);
                                    ((Activity) view.getContext()).finish();
                                } else {
                                    Toast.makeText(context, msg + " \uD83D\uDE2D", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseAddTiket> call, Throwable t) {
                                Toast.makeText(context, "Tidak ada koneksi internet \uD83D\uDE2D", Toast.LENGTH_SHORT).show();

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
//                Intent i = new Intent(context, DetailPaketActivity.class);
//                i.putExtra("id", paket.get(position).getId());
//                i.putExtra("nama", paket.get(position).getNama());
//                i.putExtra("durasi", paket.get(position).getDurasi());
//                i.putExtra("keberangkatan", paket.get(position).getKeberangkatan());
//                i.putExtra("transit", paket.get(position).getTransit());
//                i.putExtra("maskapai", paket.get(position).getMaskapai());
//                i.putExtra("toMadinah", paket.get(position).getJarakToMadinah());
//                i.putExtra("toMekah", paket.get(position).getJarakToMekah());
//                i.putExtra("harga", paket.get(position).getHarga());
//                Toast.makeText(context, paket.get(position).getNama() + " clicked", Toast.LENGTH_SHORT).show();
//                context.startActivity(i);
            }
        });

        if (session.getSpRole().equals("1")) {
            holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View view) {

                    final CharSequence[] items = {"Hapus Paket", "Edit Paket",
                            "Cancel"};
                    final AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    builder.setTitle("Menu Paket");
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int item) {
                            if (items[item].equals("Hapus Paket")) {
                                final AlertDialog.Builder alert = new AlertDialog.Builder(view.getRootView().getContext());
                                alert.setTitle("Hapus Paket");
                                alert.setIcon(R.mipmap.ic_launcher_round);
                                alert.setMessage("Anda yakin ingin menghapus paket ini?");
                                alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(context, "hapus", Toast.LENGTH_SHORT).show();
                                        ApiService apiService = InitLibrary.getInstance();
                                        Call<ResponsePaket> call = apiService.requestDeletePaket(paket.get(position).getId());
                                        call.enqueue(new Callback<ResponsePaket>() {
                                            @Override
                                            public void onResponse(Call<ResponsePaket> call, Response<ResponsePaket> response) {
                                                if (response.isSuccessful()) {
                                                    Toast.makeText(context, "Sukses Hapus Paket", Toast.LENGTH_SHORT).show();
                                                    context.startActivity(new Intent(context, PaketActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                                    ((Activity) view.getContext()).finish();

                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ResponsePaket> call, Throwable t) {
                                                Toast.makeText(context, "Gagal Hapus Paket", Toast.LENGTH_SHORT).show();
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
                            } else if (items[item].equals("Edit Paket")) {
                                AlertDialog.Builder alert = new AlertDialog.Builder(view.getRootView().getContext());
                                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                final View dialogView = inflater.inflate(R.layout.form_add_paket, null);
                                alert.setView(dialogView);
                                alert.setCancelable(true);
                                alert.setTitle("Edit Paket");
                                alert.setIcon(R.drawable.ic_assignment_black_24dp);

                                inputNama = dialogView.findViewById(R.id.input_nama);
                                inputDurasi = dialogView.findViewById(R.id.input_durasi);
                                inputJarakToMadinah = dialogView.findViewById(R.id.input_toMadinah);
                                inputJarakToMekah = dialogView.findViewById(R.id.input_toMekah);
                                inputMaskapai = dialogView.findViewById(R.id.input_maskapai);
                                inputHarga = dialogView.findViewById(R.id.input_harga);
                                inputBerangkat = dialogView.findViewById(R.id.input_keberangkatan);
                                spinnerTransit = dialogView.findViewById(R.id.spinner_transit);

                                inputNama.setText(paket.get(position).getNama());
                                String replaceAll = paket.get(position).getDurasi().replaceAll("Hari", "").trim();
                                inputDurasi.setText(replaceAll);
                                inputJarakToMadinah.setText(paket.get(position).getJarakToMadinah());
                                inputJarakToMekah.setText(paket.get(position).getJarakToMekah());
                                inputMaskapai.setText(paket.get(position).getMaskapai());
                                inputHarga.setText(paket.get(position).getHarga());
                                inputBerangkat.setText(paket.get(position).getKeberangkatan());


                                List<String> jenisKelaminOption = new ArrayList<String>();
                                jenisKelaminOption.add("Transit");
                                jenisKelaminOption.add("Tidak Transit");

                                transit = paket.get(position).getTransit();

                                ArrayAdapter<String> spinnerHamilAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, jenisKelaminOption);
                                spinnerHamilAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerTransit.setAdapter(spinnerHamilAdapter);
                                if (transit != null) {
                                    int spinnerPosition = spinnerHamilAdapter.getPosition(transit);
                                    spinnerTransit.setSelection(spinnerPosition);
                                }
                                spinnerTransit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        transit = parent.getItemAtPosition(position).toString();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                                alert.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        nama = inputNama.getText().toString();
                                        durasi = inputDurasi.getText().toString() + " Hari";
                                        jarakMadinah = inputJarakToMadinah.getText().toString();
                                        jarakMekah = inputJarakToMekah.getText().toString();
                                        maskapai = inputMaskapai.getText().toString();
                                        harga = inputHarga.getText().toString();
                                        berangkat = inputBerangkat.getText().toString();

                                        ApiService service = InitLibrary.getInstance();
                                        Call<ResponsePaket> call = service.requestEditPaket(paket.get(position).getId(), nama, durasi, transit, jarakMadinah, jarakMekah, maskapai, harga, berangkat);
                                        call.enqueue(new Callback<ResponsePaket>() {
                                            @Override
                                            public void onResponse(Call<ResponsePaket> call, Response<ResponsePaket> response) {
                                                if (response.isSuccessful()) {
                                                    Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                                    context.startActivity(new Intent(context, PaketActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                                    ((Activity) view.getContext()).finish();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ResponsePaket> call, Throwable t) {
                                                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                                                Log.d("gua dzakdzaks", t.toString());
                                            }
                                        });
                                    }
                                });

                                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                alert.show();
                            } else if (items[item].equals("Cancel")) {
                                dialogInterface.dismiss();
                            }
                        }
                    });
                    builder.show();

                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (paket == null) {
            return 0;
        } else {
            return paket.size();
        }
    }

    public static class ViewPaketHolder extends RecyclerView.ViewHolder {

        TextView tvPaket, tvDurasi, tvKeberangkatan, tvTransit, tvMaskapai, tvToMadinah, tvToMekkah, tvHarga;
        CardView cardView;

        public ViewPaketHolder(View v) {
            super(v);
            tvPaket = v.findViewById(R.id.tvPaket);
            tvDurasi = v.findViewById(R.id.tvDurasi);
            tvKeberangkatan = v.findViewById(R.id.tvKeberangkatan);
            tvTransit = v.findViewById(R.id.tvTransit);
            tvMaskapai = v.findViewById(R.id.tvMaskapai);
            tvToMadinah = v.findViewById(R.id.tvToMadinah);
            tvToMekkah = v.findViewById(R.id.tvToMekkah);
            tvHarga = v.findViewById(R.id.tvHarga);
            cardView = v.findViewById(R.id.cardPaket);
        }
    }
}
