package com.dzakdzaks.ta_umroh.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dzakdzaks.ta_umroh.R;
import com.dzakdzaks.ta_umroh.retrofit.ApiService;
import com.dzakdzaks.ta_umroh.retrofit.InitLibrary;
import com.dzakdzaks.ta_umroh.user.response.ResponseRegister;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.input_name)
    EditText inputName;
    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.input_alamat)
    EditText inputAlamat;
    @BindView(R.id.input_ttl)
    EditText inputTtl;
    @BindView(R.id.input_goldar)
    EditText inputGoldar;
    @BindView(R.id.input_notelp)
    EditText inputNotelp;
    @BindView(R.id.btn_signup)
    Button btnSignup;
    @BindView(R.id.link_login)
    TextView linkLogin;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doRegister();
            }
        });
    }

    private void doRegister() {
        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String pass = inputPassword.getText().toString();
        String alamat = inputAlamat.getText().toString();
        String ttl = inputTtl.getText().toString();
        String goldar = inputGoldar.getText().toString();
        String no = inputNotelp.getText().toString();
        showProgressBar();
        if (name.isEmpty() || name.length() < 3) {
            hideProgressBar();
            inputName.setError("Masukkan 3 karakter atau lebih!");
            inputName.requestFocus();
        } else if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            hideProgressBar();
            inputEmail.setError("Masukkan email yang valid!");
            inputEmail.requestFocus();
        } else if (pass.isEmpty() || pass.length() < 3 || pass.length() > 20) {
            hideProgressBar();
            inputPassword.setError("Masukkan 3 sampai 20 karakter!");
            inputPassword.requestFocus();
        } else if (alamat.isEmpty()) {
            hideProgressBar();
            inputAlamat.setError("Tidak Boleh Kosong!");
            inputAlamat.requestFocus();
        } else if (ttl.isEmpty()) {
            hideProgressBar();
            inputTtl.setError("Tidak Boleh Kosong!");
            inputTtl.requestFocus();
        } else if (goldar.isEmpty()) {
            hideProgressBar();
            inputGoldar.setError("Tidak Boleh Kosong!");
            inputGoldar.requestFocus();
        } else if (no.isEmpty()) {
            hideProgressBar();
            inputNotelp.setError("Tidak Boleh Kosong!");
            inputNotelp.requestFocus();
        } else {
            ApiService apiService = InitLibrary.getInstance();
            Call<ResponseRegister> callRegister = apiService.requestRegister(name, email, pass, alamat, ttl, goldar, "+62" + no, "2");
            callRegister.enqueue(new Callback<ResponseRegister>() {
                @Override
                public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                    if (response.isSuccessful()) {
                        hideProgressBar();
                        String msg = response.body().getMsg();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
//                      Snackbar.make(getWindow().getDecorView().getRootView(), "Registrasi akun berhasil! Silahkan Login... \uD83D\uDE00", Snackbar.LENGTH_SHORT).show();
                        Toast.makeText(RegisterActivity.this, msg + " \uD83D\uDE00", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseRegister> call, Throwable t) {
                    hideProgressBar();
                    Toast.makeText(RegisterActivity.this, "Tidak ada koneksi internet \uD83D\uDE2D", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }


    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}
