package com.dzakdzaks.ta_umroh.retrofit;


import com.dzakdzaks.ta_umroh.global.GlobalVariable;

import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InitLibrary {

    private static Retrofit retrofit = null;



        public static Retrofit setInit() {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


            return new Retrofit.Builder()
                    .baseUrl(GlobalVariable.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }

        public static ApiService getInstance() {
            return setInit().create(ApiService.class);
        }
}
