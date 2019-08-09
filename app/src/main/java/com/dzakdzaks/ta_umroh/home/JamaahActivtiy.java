package com.dzakdzaks.ta_umroh.home;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dzakdzaks.ta_umroh.R;
import com.dzakdzaks.ta_umroh.home.response.PaketItem;
import com.dzakdzaks.ta_umroh.home.response.ResponseAddJamaah;
import com.dzakdzaks.ta_umroh.home.response.ResponseEditJamaah;
import com.dzakdzaks.ta_umroh.home.response.ResponseGetJamaah;
import com.dzakdzaks.ta_umroh.home.response.ResponsePaket;
import com.dzakdzaks.ta_umroh.home.response.User;
import com.dzakdzaks.ta_umroh.retrofit.ApiService;
import com.dzakdzaks.ta_umroh.retrofit.InitLibrary;
import com.dzakdzaks.ta_umroh.session.UserSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JamaahActivtiy extends AppCompatActivity {

    //    @BindView(R.id.toolbar)
//    Toolbar toolbar;
    @BindView(R.id.edtNoKTP)
    EditText edtNoKTP;
    @BindView(R.id.edtNamaLengkap)
    EditText edtNamaLengkap;
    @BindView(R.id.edtTempatLahir)
    EditText edtTempatLahir;
    @BindView(R.id.edtPekerjaan)
    EditText edtPekerjaan;
    @BindView(R.id.imgTanggalLahir)
    ImageView imgTanggalLahir;
    @BindView(R.id.tvTanggalLahir)
    TextView tvTanggalLahir;
    @BindView(R.id.edtAlamat)
    EditText edtAlamat;
    @BindView(R.id.edtNamaIbuKandung)
    EditText edtNamaIbuKandung;
    @BindView(R.id.edtKewarganegaraan)
    EditText edtKewarganegaraan;
    @BindView(R.id.edtNoTelp)
    EditText edtNoTelp;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.fabCancel)
    FloatingActionButton fabCancel;
    @BindView(R.id.spinnerKeperluan)
    Spinner spinnerKeperluan;
    @BindView(R.id.spinnerKelamin)
    Spinner spinnerKelamin;

    UserSession session;
    String idSession;
    String msg;
    String idJamaah, noKtp, nama, tempatLahir, pekerjaan, tanggalLahir, jk, alamat, namaIbu, kewarganegaraan, noTelpon, noTelponSebelum, email, keperluan;
    String idKeperluan;

    List<String> valIdKeperluan = new ArrayList<>();
    List<String> valNamaKeperluan = new ArrayList<>();

    String noKtpGTTS, namaGTTS, tempatLahirGTTS, pekerjaanGTTS, alamatGTTS, namaIbuGTTS, kewarganegaraanGTTS, noTelponGTTS, emailGTTS, ttlGTTS, ttlGTTSFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jamaah_activtiy);
        ButterKnife.bind(this);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        setView();
    }

    private void setView() {
        session = new UserSession(this);
        idSession = session.getSpId();

        imgTanggalLahir.setEnabled(false);
        spinnerKelamin.setEnabled(false);
        spinnerKeperluan.setEnabled(false);

        getJamaah();


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
        getMenuInflater().inflate(R.menu.menu_jamaah, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.edit) {
            setViewWhenEdit();
            Snackbar.make(getWindow().getDecorView().getRootView(), "Editing Mode", Snackbar.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setViewWhenEdit() {
        edtNoKTP.setEnabled(true);
        edtNamaLengkap.setEnabled(true);
        edtTempatLahir.setEnabled(true);
        edtPekerjaan.setEnabled(true);
        edtAlamat.setEnabled(true);
        edtNamaIbuKandung.setEnabled(true);
        edtKewarganegaraan.setEnabled(true);
        edtNoTelp.setEnabled(true);
        edtEmail.setEnabled(true);
        imgTanggalLahir.setClickable(true);
        imgTanggalLahir.setEnabled(true);
        spinnerKelamin.setClickable(true);
        spinnerKelamin.setEnabled(true);
        spinnerKeperluan.setClickable(true);
        spinnerKeperluan.setEnabled(true);
        fab.show();
        fabCancel.show();
    }

    private void setViewWhenCancelEdit() {
        edtNoKTP.setEnabled(false);
        edtNamaLengkap.setEnabled(false);
        edtTempatLahir.setEnabled(false);
        edtPekerjaan.setEnabled(false);
        edtAlamat.setEnabled(false);
        edtNamaIbuKandung.setEnabled(false);
        edtKewarganegaraan.setEnabled(false);
        edtNoTelp.setEnabled(false);
        edtEmail.setEnabled(false);
        imgTanggalLahir.setClickable(false);
        imgTanggalLahir.setEnabled(false);
        spinnerKelamin.setClickable(false);
        spinnerKelamin.setEnabled(false);
        spinnerKeperluan.setClickable(false);
        spinnerKeperluan.setEnabled(false);
        fab.hide();
        fabCancel.hide();
    }

    private void getJamaah() {
        final ApiService apiService = InitLibrary.getInstance();
        Call<ResponseGetJamaah> getJamaahCall = apiService.getJamaah(idSession);
        getJamaahCall.enqueue(new Callback<ResponseGetJamaah>() {
            @Override
            public void onResponse(Call<ResponseGetJamaah> call, Response<ResponseGetJamaah> response) {
                msg = response.body().getMsg();
                //jamaah tidak ada
                //jamaah ada
                if (response.isSuccessful()) {

                    if (msg.equals("jamaah tidak ada")) {
                        fab.setImageResource(R.drawable.ic_add_black_24dp);
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                addJamaah();
                                Snackbar.make(getWindow().getDecorView().getRootView(), "Jamaah Added!", Snackbar.LENGTH_LONG).show();
                            }
                        });
                        fabCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                setViewWhenCancelEdit();
                                Snackbar.make(getWindow().getDecorView().getRootView(), "Canceled", Snackbar.LENGTH_LONG).show();

                            }
                        });
                    } else if (msg.equals("jamaah ada")) {
                        fab.setImageResource(R.drawable.ic_save_black_24dp);
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                editJamaah();
                                Snackbar.make(getWindow().getDecorView().getRootView(), "Jamaah Edited!", Snackbar.LENGTH_LONG).show();
                            }
                        });
                        fabCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                setViewWhenCancelEdit();
                                Snackbar.make(getWindow().getDecorView().getRootView(), "Canceled", Snackbar.LENGTH_LONG).show();
                            }
                        });
                    }

                    User user = response.body().getUser();

                    if (user.getIdJamaah() == null) {
                        idJamaah = "";
                    } else {
                        idJamaah = user.getIdJamaah();
                    }

                    if (user.getNoKtp() == null) {
                        noKtp = "";
                    } else {
                        noKtp = user.getNoKtp();
                    }

                    if (user.getNamaLengkap() == null) {
                        nama = "";
                    } else {
                        nama = user.getNamaLengkap();
                    }

                    if (user.getTempatLahir() == null) {
                        tempatLahir = "";
                    } else {
                        tempatLahir = user.getTempatLahir();
                    }

                    if (user.getPekerjaan() == null) {
                        pekerjaan = "";
                    } else {
                        pekerjaan = user.getPekerjaan();
                    }

                    if (user.getTanggalLahir() == null) {
                        tanggalLahir = "";
                    } else {
                        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        SimpleDateFormat format2 = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
                        try {
                            Date date = format1.parse(user.getTanggalLahir());
                            tanggalLahir = format2.format(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }

                    if (user.getJenisKelamin() == null) {
                        jk = "";
                    } else {
                        jk = user.getJenisKelamin();
                    }

                    if (user.getAlamat() == null) {
                        alamat = "";
                    } else {
                        alamat = user.getAlamat();
                    }

                    if (user.getNamaIbuKandung() == null) {
                        namaIbu = "";
                    } else {
                        namaIbu = user.getNamaIbuKandung();
                    }

                    if (user.getKewarganegaraan() == null) {
                        kewarganegaraan = "";
                    } else {
                        kewarganegaraan = user.getKewarganegaraan();
                    }

                    if (user.getNoTelpon() == null) {
                        noTelpon = "";
                    } else {
                        noTelponSebelum = user.getNoTelpon();
                        noTelpon = noTelponSebelum.replace("+62", "");
                    }

                    if (user.getEmail() == null) {
                        email = "";
                    } else {
                        email = user.getEmail();
                    }

                    if (user.getKeperluan() == null) {
                        keperluan = "";
                    } else {
                        keperluan = user.getKeperluan();
                    }

                    //Datepicker
                    final Calendar myCalendar = Calendar.getInstance();
                    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            myCalendar.set(Calendar.YEAR, year);
                            myCalendar.set(Calendar.MONTH, month);
                            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                            String myFormat = "dd MMMM yyyy";
                            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                            tvTanggalLahir.setVisibility(View.VISIBLE);
                            tvTanggalLahir.setText(sdf.format(myCalendar.getTime()));
                        }
                    };
                    imgTanggalLahir.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new DatePickerDialog(JamaahActivtiy.this, date, myCalendar
                                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                        }
                    });
                    //Datepicker


                    List<String> jenisKelaminOption = new ArrayList<String>();
                    jenisKelaminOption.add("Laki - Laki");
                    jenisKelaminOption.add("Perempuan");

                    ArrayAdapter<String> spinnerHamilAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, jenisKelaminOption);
                    spinnerHamilAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerKelamin.setAdapter(spinnerHamilAdapter);
                    if (jk != null) {
                        int spinnerPosition = spinnerHamilAdapter.getPosition(jk);
                        spinnerKelamin.setSelection(spinnerPosition);
                    }
                    spinnerKelamin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            jk = parent.getItemAtPosition(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    Call<ResponsePaket> paketCall = apiService.requestGetPaket(idSession);
                    paketCall.enqueue(new Callback<ResponsePaket>() {
                        @Override
                        public void onResponse(Call<ResponsePaket> call, Response<ResponsePaket> response) {
                            final List<PaketItem> paketItemList = response.body().getPaket();
                            valIdKeperluan = new ArrayList<>();
                            valNamaKeperluan = new ArrayList<>();

                            for (int i = 0; i < paketItemList.size(); i++) {
                                valIdKeperluan.add(paketItemList.get(i).getId());
                                valNamaKeperluan.add(paketItemList.get(i).getNama());
                            }

                            ArrayAdapter<String> spinnerPosyanduAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, valNamaKeperluan);
                            spinnerPosyanduAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerKeperluan.setAdapter(spinnerPosyanduAdapter);
                            if (keperluan != null) {
                                int spinnerPosition = spinnerPosyanduAdapter.getPosition(keperluan);
                                spinnerKeperluan.setSelection(spinnerPosition);
                            }
                            spinnerKeperluan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    idKeperluan = valIdKeperluan.get(position);
                                    keperluan = valNamaKeperluan.get(position);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<ResponsePaket> call, Throwable t) {

                        }
                    });

                    edtNoKTP.setText(noKtp);
                    edtNamaLengkap.setText(nama);
                    edtTempatLahir.setText(tempatLahir);
                    edtPekerjaan.setText(pekerjaan);
                    tvTanggalLahir.setText(tanggalLahir);
                    edtAlamat.setText(alamat);
                    edtNamaIbuKandung.setText(namaIbu);
                    edtKewarganegaraan.setText(kewarganegaraan);
                    edtNoTelp.setText(noTelpon);
                    edtEmail.setText(email);

                    Snackbar.make(getWindow().getDecorView().getRootView(), "Bioadata " + nama, Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseGetJamaah> call, Throwable t) {
                Snackbar.make(getWindow().getDecorView().getRootView(), "Failed", Snackbar.LENGTH_LONG).show();
            }
        });
    }


    private void addJamaah() {
        noKtpGTTS = edtNoKTP.getText().toString();
        namaGTTS = edtNamaLengkap.getText().toString();
        tempatLahirGTTS = edtTempatLahir.getText().toString();
        pekerjaanGTTS = edtPekerjaan.getText().toString();
        alamatGTTS = edtAlamat.getText().toString();
        namaIbuGTTS = edtNamaIbuKandung.getText().toString();
        kewarganegaraanGTTS = edtKewarganegaraan.getText().toString();
        noTelponGTTS = "+62" + edtNoTelp.getText().toString();
        emailGTTS = edtEmail.getText().toString();

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat format2 = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        try {
            ttlGTTS = tvTanggalLahir.getText().toString();
            Date date = format2.parse(ttlGTTS);
            ttlGTTSFinal = format1.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        ApiService apiService = InitLibrary.getInstance();
        Call<ResponseAddJamaah> addJamaahCall = apiService.addJamaah(idSession, noKtpGTTS, namaGTTS, tempatLahirGTTS, pekerjaanGTTS, ttlGTTSFinal, jk, alamatGTTS, namaIbuGTTS, kewarganegaraanGTTS, noTelponGTTS, emailGTTS, keperluan);
        addJamaahCall.enqueue(new Callback<ResponseAddJamaah>() {
            @Override
            public void onResponse(Call<ResponseAddJamaah> call, Response<ResponseAddJamaah> response) {
                if (response.isSuccessful()) {
                    setViewWhenCancelEdit();
                }
            }

            @Override
            public void onFailure(Call<ResponseAddJamaah> call, Throwable t) {

            }
        });
    }

    private void editJamaah() {
        noKtpGTTS = edtNoKTP.getText().toString();
        namaGTTS = edtNamaLengkap.getText().toString();
        tempatLahirGTTS = edtTempatLahir.getText().toString();
        pekerjaanGTTS = edtPekerjaan.getText().toString();
        alamatGTTS = edtAlamat.getText().toString();
        namaIbuGTTS = edtNamaIbuKandung.getText().toString();
        kewarganegaraanGTTS = edtKewarganegaraan.getText().toString();
        noTelponGTTS = "+62" + edtNoTelp.getText().toString();
        emailGTTS = edtEmail.getText().toString();

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat format2 = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        try {
            ttlGTTS = tvTanggalLahir.getText().toString();
            Date date = format2.parse(ttlGTTS);
            ttlGTTSFinal = format1.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ApiService apiService = InitLibrary.getInstance();
        Call<ResponseEditJamaah> editJamaahCall = apiService.editJamaah(idJamaah, idSession, noKtpGTTS, namaGTTS, tempatLahirGTTS, pekerjaanGTTS, ttlGTTSFinal, jk, alamatGTTS, namaIbuGTTS, kewarganegaraanGTTS, noTelponGTTS, emailGTTS, keperluan);
        editJamaahCall.enqueue(new Callback<ResponseEditJamaah>() {
            @Override
            public void onResponse(Call<ResponseEditJamaah> call, Response<ResponseEditJamaah> response) {
                if (response.isSuccessful()) {
                    setViewWhenCancelEdit();
                }
            }

            @Override
            public void onFailure(Call<ResponseEditJamaah> call, Throwable t) {

            }
        });
    }

}
