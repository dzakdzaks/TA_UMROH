package com.dzakdzaks.ta_umroh.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dzakdzaks.ta_umroh.R;
import com.dzakdzaks.ta_umroh.home.MainActivity;
import com.dzakdzaks.ta_umroh.retrofit.ApiService;
import com.dzakdzaks.ta_umroh.retrofit.InitLibrary;
import com.dzakdzaks.ta_umroh.session.UserSession;
import com.dzakdzaks.ta_umroh.user.response.Profile;
import com.dzakdzaks.ta_umroh.user.response.ResponseProfile;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilrActivity extends AppCompatActivity {

    @BindView(R.id.input_nama)
    EditText inputNama;
    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.input_alamat)
    EditText inputAlamat;
    @BindView(R.id.input_no_telp)
    EditText inputNoTelp;
    @BindView(R.id.input_ttl)
    EditText inputTtl;
    @BindView(R.id.input_goldar)
    EditText inputGoldar;
    @BindView(R.id.btn_edit)
    Button btnEdit;
    @BindView(R.id.input_curr_pass)
    EditText inputCurrPass;
    @BindView(R.id.input_pass)
    EditText inputPass;
    @BindView(R.id.input_pass2)
    EditText inputPass2;
    @BindView(R.id.btn_edit_pass)
    Button btnEditPass;

    UserSession session;

    String id, pass, nama, email, alamat, no, ttl, goldar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilr);
        ButterKnife.bind(this);
        session = new UserSession(this);

        setView();

    }

    private void setView() {
        inputNama.setEnabled(false);
        inputEmail.setEnabled(false);
        inputAlamat.setEnabled(false);
        inputNoTelp.setEnabled(false);
        inputTtl.setEnabled(false);
        inputGoldar.setEnabled(false);
        inputCurrPass.setEnabled(false);
        inputPass.setEnabled(false);
        inputPass2.setEnabled(false);

        id = session.getSpId();
        pass = session.getSpPassword();
        nama = session.getSpName();
        email = session.getSpEmail();
        alamat = session.getSpAlamat();
        no = session.getSpNo();
        ttl = session.getSpTtl();
        goldar = session.getSpGoldar();

        inputNama.setText(nama);
        inputEmail.setText(email);
        inputAlamat.setText(alamat);
        inputNoTelp.setText(no);
        inputTtl.setText(ttl);
        inputGoldar.setText(goldar);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfile();
            }
        });

        btnEditPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPass();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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
            inputNama.setEnabled(true);
            inputEmail.setEnabled(true);
            inputAlamat.setEnabled(true);
            inputNoTelp.setEnabled(true);
            inputTtl.setEnabled(true);
            inputGoldar.setEnabled(true);
            inputCurrPass.setEnabled(true);
            inputPass.setEnabled(true);
            inputPass2.setEnabled(true);
            btnEdit.setVisibility(View.VISIBLE);
            btnEditPass.setVisibility(View.VISIBLE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void editProfile() {

        final String a = inputNama.getText().toString();
        final String b = inputEmail.getText().toString();
        final String c = inputAlamat.getText().toString();
        final String d = inputNoTelp.getText().toString();
        final String e = inputTtl.getText().toString();
        final String f = inputGoldar.getText().toString();

        ApiService apiService = InitLibrary.getInstance();
        Call<ResponseProfile> call = apiService.requestEditUser(id, a, b, c, e, f, d);
        call.enqueue(new Callback<ResponseProfile>() {
            @Override
            public void onResponse(Call<ResponseProfile> call, Response<ResponseProfile> response) {
                String msg = response.body().getMsg();
                if (response.isSuccessful()) {
                    inputNama.setEnabled(false);
                    inputEmail.setEnabled(false);
                    inputAlamat.setEnabled(false);
                    inputNoTelp.setEnabled(false);
                    inputTtl.setEnabled(false);
                    inputGoldar.setEnabled(false);
                    btnEdit.setVisibility(View.GONE);

//                    Profile profile = response.body().getProfile();
//                    String a = profile.getName();
//                    String b = profile.getEmail();
//                    String c = profile.getAlamat();
//                    String d = profile.getNotelp();
//                    String e = profile.getTtl();
//                    String f = profile.getGoldar();

                    session.saveSPString(UserSession.SP_NAME, a);
                    session.saveSPString(UserSession.SP_EMAIL, b);
                    session.saveSPString(UserSession.SP_ALAMAT, c);
                    session.saveSPString(UserSession.SP_TTL, e);
                    session.saveSPString(UserSession.SP_GOLDAR, f);
                    session.saveSPString(UserSession.SP_NO, d);

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();

                    Toast.makeText(ProfilrActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseProfile> call, Throwable t) {

            }
        });
    }

    private void editPass() {
        String a = inputCurrPass.getText().toString();
        String b = inputPass.getText().toString();
        String c = inputPass2.getText().toString();
        if (!a.equals(pass)) {
            inputCurrPass.setError("Password anda tidak benar!");
            inputCurrPass.requestFocus();
        } else if (!b.equals(c)) {
            inputPass2.setError("Password Tidak sama!");
            inputPass2.requestFocus();
        } else if (a.isEmpty()){
            inputCurrPass.setError("Password masih kosong");
            inputCurrPass.requestFocus();
        } else if (b.isEmpty()){
            inputPass.setError("Password masih kosong");
            inputPass.requestFocus();
        } else if (c.isEmpty()){
            inputPass2.setError("Password masih kosong");
            inputPass2.requestFocus();
        } else {
            apiPass();
        }
    }

    private void apiPass() {
        final String b = inputPass.getText().toString();
        ApiService apiService = InitLibrary.getInstance();
        Call<ResponseProfile> call = apiService.requestEditPass(id, b);
        call.enqueue(new Callback<ResponseProfile>() {
            @Override
            public void onResponse(Call<ResponseProfile> call, Response<ResponseProfile> response) {
                String msg = response.body().getMsg();
                if (response.isSuccessful()) {
                    inputCurrPass.setEnabled(true);
                    inputPass.setEnabled(true);
                    inputPass2.setEnabled(true);
                    btnEditPass.setVisibility(View.GONE);

                    session.saveSPString(UserSession.SP_PASSWORD, b);

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();

                    Toast.makeText(ProfilrActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseProfile> call, Throwable t) {

            }
        });
    }
}
