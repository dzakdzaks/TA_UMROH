package com.dzakdzaks.ta_umroh.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dzakdzaks.ta_umroh.home.MainActivity;
import com.dzakdzaks.ta_umroh.R;
import com.dzakdzaks.ta_umroh.retrofit.ApiService;
import com.dzakdzaks.ta_umroh.retrofit.InitLibrary;
import com.dzakdzaks.ta_umroh.session.UserSession;
import com.dzakdzaks.ta_umroh.user.response.ResponseLogin;
import com.dzakdzaks.ta_umroh.user.response.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.link_signup)
    TextView linkSignup;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    UserSession session;

    private static final String TAG = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        session = new UserSession(this);

        if (session.getSPSudahLogin()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        linkSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogin();
            }
        });
    }

    private void doLogin() {
        String email = inputEmail.getText().toString();
        String pass = inputPassword.getText().toString();
        showProgressBar();
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            hideProgressBar();
            inputEmail.setError("Masukkan email yang valid!");
            inputEmail.requestFocus();
        } else if (pass.isEmpty() || pass.length() < 3 || pass.length() > 20) {
            hideProgressBar();
            inputPassword.setError("Masukkan 3 sampai 20 karakter");
            inputPassword.requestFocus();
        } else {
            ApiService apiService = InitLibrary.getInstance();
            Call<ResponseLogin> callLogin = apiService.requestLogin(email, pass);
            callLogin.enqueue(new Callback<ResponseLogin>() {
                @Override
                public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                    String error = response.body().getError();
                    String msg = response.body().getMsg();
                    if (response.isSuccessful()) {
                        hideProgressBar();
                        if (error.equals("false")) {
                            User user = response.body().getUser();
                            String id = user.getId();
                            String name = user.getName();
                            String email = user.getEmail();
                            String password = user.getPassword();
                            String alamat = user.getAlamat();
                            String ttl = user.getTtl();
                            String goldar = user.getGoldar();
                            String notelp = user.getNotelp();
                            String role = user.getRole();

                            Log.d(TAG, id + "\n" +
                                    name + "\n" +
                                    email + "\n" +
                                    password + "\n" +
                                    alamat + "\n" +
                                    ttl + "\n" +
                                    goldar + "\n" +
                                    notelp + "\n" +
                                    role);

                            session.saveSPString(UserSession.SP_ID, id);
                            session.saveSPString(UserSession.SP_NAME, name);
                            session.saveSPString(UserSession.SP_EMAIL, email);
                            session.saveSPString(UserSession.SP_PASSWORD, password);
                            session.saveSPString(UserSession.SP_ALAMAT, alamat);
                            session.saveSPString(UserSession.SP_TTL, ttl);
                            session.saveSPString(UserSession.SP_GOLDAR, goldar);
                            session.saveSPString(UserSession.SP_NO, notelp);
                            session.saveSPString(UserSession.SP_ROLE, role);
                            session.saveSPBoolean(UserSession.SP_SUDAH_LOGIN, true);

//                            startActivity(new Intent(LoginActivity.this, HomeActivity.class)
//                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                            Toast.makeText(LoginActivity.this, msg + " \uD83D\uDE00", Toast.LENGTH_SHORT).show();
                        } else {
                            hideProgressBar();
                            Toast.makeText(LoginActivity.this, msg + " \uD83D\uDE2D", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        hideProgressBar();
                        Toast.makeText(LoginActivity.this, msg + " \uD83D\uDE2D", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseLogin> call, Throwable t) {
                    hideProgressBar();
                    Log.d("kampuy", t.toString());
                    Toast.makeText(LoginActivity.this, "Tidak ada koneksi internet \uD83D\uDE2D", Toast.LENGTH_SHORT).show();

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
