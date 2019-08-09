package com.dzakdzaks.ta_umroh.retrofit;


import com.dzakdzaks.ta_umroh.admin.response.ResponseAllJamaah;
import com.dzakdzaks.ta_umroh.home.response.ResponseAddJamaah;
import com.dzakdzaks.ta_umroh.home.response.ResponseAddTiket;
import com.dzakdzaks.ta_umroh.home.response.ResponseEditJamaah;
import com.dzakdzaks.ta_umroh.home.response.ResponseGetJamaah;
import com.dzakdzaks.ta_umroh.home.response.ResponsePaket;
import com.dzakdzaks.ta_umroh.home.response.ResponseTiket;
import com.dzakdzaks.ta_umroh.home.response.ResponseTiketUpdateStatus;
import com.dzakdzaks.ta_umroh.user.response.ResponseLogin;
import com.dzakdzaks.ta_umroh.user.response.ResponseProfile;
import com.dzakdzaks.ta_umroh.user.response.ResponseRegister;
import com.dzakdzaks.ta_umroh.user.response.ResponseUpdate;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("user_register.php")
    Call<ResponseRegister> requestRegister(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("alamat") String alamat,
            @Field("ttl") String ttl,
            @Field("goldar") String goldar,
            @Field("notelp") String notelp,
            @Field("role") String role
    );

    @FormUrlEncoded
    @POST("user_edit.php")
    Call<ResponseProfile> requestEditUser(
            @Field("id") String id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("alamat") String alamat,
            @Field("ttl") String ttl,
            @Field("goldar") String goldar,
            @Field("notelp") String notelp
    );

    @FormUrlEncoded
    @POST("user_edit_pass.php")
    Call<ResponseProfile> requestEditPass(
            @Field("id") String id,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("user_login.php")
    Call<ResponseLogin> requestLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("user_update.php")
    Call<ResponseUpdate> requestUpdate(
            @Field("id") String id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("alamat") String alamat,
            @Field("ttl") String ttl,
            @Field("goldar") String goldar,
            @Field("notelp") String notelp,
            @Field("role") String role
    );

    @FormUrlEncoded
    @POST("paket_get.php")
    Call<ResponsePaket> requestGetPaket(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("paket_delete.php")
    Call<ResponsePaket> requestDeletePaket(
            @Field("id") String id
    );


    @FormUrlEncoded
    @POST("paket_add.php")
    Call<ResponsePaket> requestAddPaket(
            @Field("nama") String name,
            @Field("durasi") String email,
            @Field("transit") String password,
            @Field("jarak_to_madinah") String alamat,
            @Field("jarak_to_mekah") String ttl,
            @Field("maskapai") String goldar,
            @Field("harga") String notelp,
            @Field("keberangkatan") String role
    );

    @FormUrlEncoded
    @POST("paket_edit.php")
    Call<ResponsePaket> requestEditPaket(
            @Field("id") String id,
            @Field("nama") String name,
            @Field("durasi") String email,
            @Field("transit") String password,
            @Field("jarak_to_madinah") String alamat,
            @Field("jarak_to_mekah") String ttl,
            @Field("maskapai") String goldar,
            @Field("harga") String notelp,
            @Field("keberangkatan") String role
    );

    @FormUrlEncoded
    @POST("tiket_get.php")
    Call<ResponseTiket> requestGetTiket(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("tiket_get_all.php")
    Call<ResponseTiket> getAllTiket(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("tiket_sudah_bayar.php")
    Call<ResponseTiket> requestTiketSudahBayar(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("tiket_belum_bayar.php")
    Call<ResponseTiket> requestTiketBelumBayar(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("tiket_di_proses.php")
    Call<ResponseTiket> requestTiketDiProses(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("tiket_di_proses_peruser.php")
    Call<ResponseTiket> requestTiketDiProsesPerUser(
            @Field("id") String id
    );


    @FormUrlEncoded
    @POST("tiket_add.php")
    Call<ResponseAddTiket> sendTiket(
            @Field("id_user") String idUser,
            @Field("id_paket") String idPaket
    );

    @FormUrlEncoded
    @POST("tiket_delete.php")
    Call<ResponseAddTiket> deleteTiket(
            @Field("id") String id
    );


    @FormUrlEncoded
    @POST("tiket_update_status.php")
    Call<ResponseTiketUpdateStatus> sendStatusTiket(
            @Field("id") String idTiket,
            @Field("id_user") String idUser,
            @Field("id_paket") String idPaket,
            @Field("status") String status,
            @Field("image") String img,
            @Field("keterangan") String keterangan
    );

    @FormUrlEncoded
    @POST("tiket_konfirmasi.php")
    Call<ResponseTiketUpdateStatus> confirmTiket(
            @Field("id") String idUser,
            @Field("id_user") String idser,
            @Field("id_paket") String idPaket,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("jamaah_add.php")
    Call<ResponseAddJamaah> addJamaah(
            @Field("id_user") String a,
            @Field("no_ktp") String b,
            @Field("nama_lengkap") String c,
            @Field("tempat_lahir") String d,
            @Field("pekerjaan") String e,
            @Field("tanggal_lahir") String f,
            @Field("jenis_kelamin") String g,
            @Field("alamat") String h,
            @Field("nama_ibu_kandung") String i,
            @Field("kewarganegaraan") String j,
            @Field("no_telpon") String k,
            @Field("email") String l,
            @Field("keperluan") String m
    );

    @FormUrlEncoded
    @POST("jamaah_edit.php")
    Call<ResponseEditJamaah> editJamaah(
            @Field("id_jamaah") String aa,
            @Field("id_user") String a,
            @Field("no_ktp") String b,
            @Field("nama_lengkap") String c,
            @Field("tempat_lahir") String d,
            @Field("pekerjaan") String e,
            @Field("tanggal_lahir") String f,
            @Field("jenis_kelamin") String g,
            @Field("alamat") String h,
            @Field("nama_ibu_kandung") String i,
            @Field("kewarganegaraan") String j,
            @Field("no_telpon") String k,
            @Field("email") String l,
            @Field("keperluan") String m
    );

    @FormUrlEncoded
    @POST("jamaah_get.php")
    Call<ResponseGetJamaah> getJamaah(
            @Field("id_user") String a
    );

    @FormUrlEncoded
    @POST("jamaah_get_all.php")
    Call<ResponseAllJamaah> getAllJamaah(
            @Field("id") String a
    );

    @FormUrlEncoded
    @POST("pembayaran_add.php")
    Call<ResponseTiket> addPembayaran(
            @Field("id") String a
    );



}
