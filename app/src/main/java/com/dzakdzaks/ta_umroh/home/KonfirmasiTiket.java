package com.dzakdzaks.ta_umroh.home;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dzakdzaks.ta_umroh.R;
import com.dzakdzaks.ta_umroh.home.response.ResponseTiketUpdateStatus;
import com.dzakdzaks.ta_umroh.retrofit.ApiService;
import com.dzakdzaks.ta_umroh.retrofit.InitLibrary;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KonfirmasiTiket extends AppCompatActivity {

    @BindView(R.id.input_keterangan)
    EditText inputKeterangan;
    @BindView(R.id.imgBukti)
    ImageView imgBukti;
    @BindView(R.id.btnUpload)
    Button btnUpload;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.input_no_rek)
    EditText inputNoRek;
    @BindView(R.id.input_atas_nama)
    EditText inputAtasNama;
    @BindView(R.id.input_nominal)
    EditText inputNominal;
    @BindView(R.id.img_photo)
    ImageView imgPhoto;

    Bitmap bitmap;
    private static final int IMAGE = 100;
    private static final int TAKE_PICTURE = 200;
    public static final int RequestPermissionCode = 1;

    String idTiket, idUser, idPaket, status, imageBukti, keterangan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_tiket);
        ButterKnife.bind(this);

        idTiket = getIntent().getStringExtra("id_tiket");
        idUser = getIntent().getStringExtra("id_user");
        idPaket = getIntent().getStringExtra("id_paket");
        status = getIntent().getStringExtra("status");
        imageBukti = getIntent().getStringExtra("bukti");
        keterangan = getIntent().getStringExtra("ket_bukti");

    }

    @OnClick({R.id.img_photo, R.id.btnUpload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_photo:
                EnableRuntimePermission();
                selectImage();
                break;
            case R.id.btnUpload:
                upload();
                break;
        }
    }

    private void upload() {
        progressBar.setVisibility(View.VISIBLE);
        inputKeterangan.setEnabled(false);
        imgBukti.setEnabled(false);
        btnUpload.setEnabled(false);
//        String ket = inputKeterangan.getText().toString() + "\n" +
//                inputNoRek.getText().toString() + "\n" +
//                inputAtasNama.getText().toString() + "\n" +
//                "Rp." + inputNominal.getText().toString();
        String ket = String.valueOf(Html.fromHtml("||  <b>Nama Bank : </b>" + inputKeterangan.getText().toString() + "  <br>||  " +
                "<b>No Rekening : </b>" + inputNoRek.getText().toString() + "  <br>||  " +
                "<b>Atas Nama : </b>" + inputAtasNama.getText().toString() + "  <br>||  " +
                "<b>Nominal : </b>" + inputNominal.getText().toString()));
        String newImage = convertToString();
        ApiService apiService = InitLibrary.getInstance();
        Call<ResponseTiketUpdateStatus> call = apiService.sendStatusTiket(idTiket, idUser, idPaket, "1", newImage, ket);
        call.enqueue(new Callback<ResponseTiketUpdateStatus>() {
            @Override
            public void onResponse(Call<ResponseTiketUpdateStatus> call, Response<ResponseTiketUpdateStatus> response) {
                String msg = response.body().getMsg();
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    inputKeterangan.setEnabled(true);
                    imgBukti.setEnabled(true);
                    btnUpload.setEnabled(true);
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseTiketUpdateStatus> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                inputKeterangan.setEnabled(true);
                imgBukti.setEnabled(true);
                btnUpload.setEnabled(true);
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(KonfirmasiTiket.this);
        builder.setTitle("Menu Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, TAKE_PICTURE);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, IMAGE);
                } else if (items[item].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();

    }

    private String convertToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imgBukti.setImageBitmap(bitmap);
//                updateProfileImage();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK && data != null) {

            bitmap = (Bitmap) data.getExtras().get("data");
            imgBukti.setImageBitmap(bitmap);
//            updateProfileImage();
        }
    }

    public void EnableRuntimePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(KonfirmasiTiket.this,
                Manifest.permission.CAMERA)) {

            Toast.makeText(KonfirmasiTiket.this, "CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(KonfirmasiTiket.this, new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

//                    Toast.makeText(ProfileActivity.this, "Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(KonfirmasiTiket.this, "Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

}
